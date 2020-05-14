package com.ex.mvp.network;

import android.os.Build;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.ex.mvp.api.ApiService;
import com.ex.mvp.constant.ApiConfig;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:     Retrofit管理者
 */
@SuppressWarnings("all")
public class RetrofitManager {
    private static final String ANDROID = "Android";
    private static OkHttpClient sOkHttpClient;
    private static volatile Retrofit sRetrofit;
    private static ApiService sApiService;


    //这是公共参数
    private static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        .addQueryParameter("User-Agent", getUserAgent())
                        .addQueryParameter("paltform", ANDROID)
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }

    //设置头信息
    private static Interceptor addHeaderInterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .header("token", SPUtils.getInstance().getString("token", ""))
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        return headerInterceptor;
    }

    //设置缓存
    private static Interceptor addCacheInterceptor() {
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isAvailableByPing()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtils.isAvailableByPing()) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周  只对get有用,post没有缓冲
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" +
                                    maxStale)
                            .removeHeader("nyn")
                            .build();
                }
                return response;
            }
        };
        return cacheInterceptor;
    }

    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            synchronized (RetrofitManager.class) {
                if (sRetrofit == null) {
                    //添加一个log拦截器,打印所有的log
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    //可以设置请求过滤的水平,body,basic,headers
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                    //设置 请求的缓存的大小跟位置
                    File cacheFile = new File(Utils.getApp().getCacheDir(), "cache");
                    Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //50Mb 缓存的大小

                    sOkHttpClient = new OkHttpClient
                            .Builder()
                            .addInterceptor(addQueryParameterInterceptor())  //参数添加
                            .addInterceptor(addHeaderInterceptor()) // token过滤
                            .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                            .cache(cache)  //添加缓存
                            .connectTimeout(ApiConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(ApiConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(ApiConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .build();

                    // 获取retrofit的实例
                    sRetrofit = new Retrofit
                            .Builder()
                            .baseUrl(ApiConfig.BASE_URL)
                            .client(sOkHttpClient)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return sRetrofit;
    }

    /**
     * 获取A所有APi接口的类
     *
     * @return
     */
    public static ApiService getApiService() {
        if (sApiService == null) {
            sApiService = getRetrofit().create(ApiService.class);
        }
        return sApiService;
    }


    public static String getUserAgent() {
        String appVersion = AppUtils.getAppVersionName();
        return appVersion
                + ";"
                + ANDROID
                + getAndroidApiString();
    }

    protected static String getAndroidApiString() {
        return Build.VERSION.RELEASE;
    }
}
