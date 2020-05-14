package com.ex.mvp.api;

import com.ex.mvp.bean.BaseResponse;
import com.ex.mvp.bean.LoginBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:    接口管理
 */
@SuppressWarnings("all")
public interface ApiService {
    @FormUrlEncoded
    @POST("/api/public/login")
    Observable<BaseResponse<LoginBean>> login(
            @Field("mobile") String username,
            @Field("password") String pwd
    );
}
