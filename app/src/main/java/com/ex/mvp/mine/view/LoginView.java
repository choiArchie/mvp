package com.ex.mvp.mine.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.ex.mvp.R;
import com.ex.mvp.base.BaseActivity;
import com.ex.mvp.mine.contract.LoginContract;
import com.ex.mvp.mine.presenter.LoginPresenter;
import com.ex.mvp.widget.LoadingDialog;

/**
 * Author:  Jerry
 * Date:    2018/11/14
 * Version   v1.0
 * Desc:    登录页面
 */
@SuppressWarnings("all")
public class LoginView extends BaseActivity<LoginPresenter> implements LoginContract.LoginView {
    private EditText mUsername, mPwd;
    private Button mBtLogin;

    @Override
    protected int getLayoutById() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter loadPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView() {
        mUsername = findViewById(R.id.et_username);
        mPwd = findViewById(R.id.et_pwd);
        mBtLogin = findViewById(R.id.bt_login);
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog.showLoadingDialog(LoginView.this);
                mPresenter.login(getUserName(), getPwd());

            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public String getUserName() {
        return mUsername.getText().toString().trim();
    }

    @Override
    public String getPwd() {
        return mPwd.getText().toString().trim();
    }

    @Override
    public void loginSuccess(String msg) {
        //关闭菊花
        LoadingDialog.cancelLoadingDialog();
        ToastUtils.showShort(msg);
    }

    @Override
    public void loginFailure(String msg) {
        LoadingDialog.cancelLoadingDialog();
        ToastUtils.showShort(msg);
    }
}
