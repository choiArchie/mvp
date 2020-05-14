package com.ex.mvp.mine.contract;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:   合约接口
 */
@SuppressWarnings("all")
public interface LoginContract {
    interface LoginView {
        String getUserName();

        String getPwd();

        void loginSuccess(String msg);

        void loginFailure(String msg);
    }


    interface LoginPresenter {
        void login(String userName, String pwd);
    }
}
