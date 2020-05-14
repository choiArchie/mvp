package com.ex.mvp.base;

import com.blankj.utilcode.util.Utils;
import com.ex.mvp.R;
import com.ex.mvp.bean.BaseResponse;
import com.ex.mvp.exception.ErrorType;
import com.ex.mvp.exception.ExceptionEngine;
import com.ex.mvp.exception.ServerException;

import rx.Observable;
import rx.functions.Func1;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:
 */
@SuppressWarnings("all")
public class ErrorTransformer<T> implements Observable.Transformer<BaseResponse<T>, T> {
    private static ErrorTransformer errorTransformer = null;
    private static final String TAG = "ErrorTransformer";

    @Override
    public Observable<T> call(Observable<BaseResponse<T>> baseResponseObservable) {
        return baseResponseObservable.map(new Func1<BaseResponse<T>, T>() {
            @Override
            public T call(BaseResponse<T> tBaseResponse) {
                if (tBaseResponse == null) {
                    throw new ServerException(ErrorType.EMPTY_BEAN, Utils.getApp().getResources().getString(R.string.EMPTY_BEAN));
                }
                if (tBaseResponse.getCode() != ErrorType.SUCCESS) {
                    throw new ServerException(tBaseResponse.getCode(), tBaseResponse.getMessage());
                }
                return tBaseResponse.getData();
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                throwable.printStackTrace();
                return Observable.error(ExceptionEngine.handleException(throwable));
            }
        });
    }

    /**
     * @return 线程安全, 双层校验
     */
    public static <T> ErrorTransformer<T> getInstance() {
        if (errorTransformer == null) {
            synchronized (ErrorTransformer.class) {
                if (errorTransformer == null) {
                    errorTransformer = new ErrorTransformer<>();
                }
            }
        }
        return errorTransformer;

    }
}
