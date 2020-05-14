package com.ex.mvp.base;

import com.ex.mvp.api.ApiService;
import com.ex.mvp.mvp.IModel;
import com.ex.mvp.network.RetrofitManager;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:    model基类
 */
@SuppressWarnings("all")
public class BaseModel implements IModel {
    protected static ApiService sApiService;

    //初始化网络请求管理者
    static {
        sApiService = RetrofitManager.getApiService();
    }
}
