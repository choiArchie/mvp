package com.ex.mvp.app;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:     程序入口
 */
@SuppressWarnings("all")
public class MvpApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化第三方SDK
        Utils.init(this);
    }
}
