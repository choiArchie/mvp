package com.ex.mvp.base;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.ex.mvp.exception.ApiException;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:
 */
@SuppressWarnings("all")
public abstract class CommonSubscriber<T> extends BaseSubscriber<T> {
    private static final String TAG = "CommonSubscriber";
    private Context mContext;

    public CommonSubscriber(Context context) {
        mContext = context;
    }


    @Override
    public void onStart() {
        if (!NetworkUtils.isAvailableByPing()) {
            LogUtils.eTag(TAG, "网络不可用");
        } else {
            LogUtils.eTag(TAG, "网络可用");
        }
    }


    @Override
    protected void onError(ApiException e) {
        LogUtils.eTag(TAG, "ErrorInfo:" + "code:" + e.code + "msg:" + e.msg);
    }

    @Override
    public void onCompleted() {
        LogUtils.eTag(TAG, "Successfully");
    }
}
