package com.ex.mvp.mine.presenter;

import com.ex.mvp.base.BasePresenter;
import com.ex.mvp.mine.contract.LoginContract;
import com.ex.mvp.mine.model.LoginModel;
import com.ex.mvp.mine.view.LoginView;
import com.ex.mvp.mvp.IModel;

import java.util.HashMap;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:
 */
public class LoginPresenter extends BasePresenter<LoginView> implements LoginContract.LoginPresenter {
    @Override
    public void login(String userName, String pwd) {
        ((LoginModel) getModelMap().get("login")).login(userName, pwd, new LoginModel.LoginCallback() {
            @Override
            public void successInfo(String msg) {
                getView().loginSuccess(msg);
            }

            @Override
            public void failInfo(String errorMsg) {
                getView().loginFailure(errorMsg);
            }
        });
    }

    @Override
    public HashMap<String, IModel> getModelMap() {
        return addMoreModelMap(new LoginModel());
    }

    @Override
    public HashMap<String, IModel> addMoreModelMap(IModel... iModels) {
        HashMap<String, IModel> loginModelMap = new HashMap<>();
        loginModelMap.put("login", iModels[0]);
        return loginModelMap;
    }
}
