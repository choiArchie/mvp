package com.ex.mvp.base;

import com.ex.mvp.exception.ApiException;

import rx.Subscriber;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:
 */
@SuppressWarnings("all")
public abstract class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onError(Throwable e) {
        ApiException apiException = (ApiException) e;
        onError(apiException);
    }

    protected abstract void onError(ApiException e);

}
