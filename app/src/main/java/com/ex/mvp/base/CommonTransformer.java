package com.ex.mvp.base;

import com.ex.mvp.bean.BaseResponse;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:    转换器
 */
@SuppressWarnings("all")
public class CommonTransformer<T> implements Observable.Transformer<BaseResponse<T>, T> {
    @Override
    public Observable<T> call(Observable<BaseResponse<T>> baseHttpResultObservable) {
        return baseHttpResultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ErrorTransformer.<T>getInstance());
    }
}
