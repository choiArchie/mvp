package com.ex.mvp.mine.model;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.ex.mvp.base.BaseModel;
import com.ex.mvp.base.CommonSubscriber;
import com.ex.mvp.base.CommonTransformer;
import com.ex.mvp.bean.LoginBean;
import com.ex.mvp.exception.ApiException;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:    登录的数据处理业务
 */
@SuppressWarnings("all")
public class LoginModel extends BaseModel {
    private boolean isLogin = false;

    public boolean login(@NonNull String username, @NonNull String pwd, @NonNull final LoginCallback callback) {
        sApiService.login(username, pwd)
                .compose(new CommonTransformer<LoginBean>())
                .subscribe(new CommonSubscriber<LoginBean>(Utils.getApp()) {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        //登录成功返回true
                        isLogin = true;
                        callback.successInfo(loginBean.getToken());
                        //保存用户信息

                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        isLogin = false;
                        callback.failInfo(e.msg);
                    }
                });
        return isLogin;
    }


    public interface LoginCallback {
        //成功回调
        void successInfo(String msg);
        //失败回调

        void failInfo(String errorMsg);
    }
}
