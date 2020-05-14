package com.ex.mvp;

import android.content.Intent;
import android.view.View;

import com.ex.mvp.base.BaseActivity;
import com.ex.mvp.base.BasePresenter;
import com.ex.mvp.mine.view.LoginView;

/**
 * Author:  Jerry
 * Date:    2018/11/14
 * Version   v1.0
 * Desc:    主Activity
 */
public class MainActivity extends BaseActivity {
    @Override
    protected int getLayoutById() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        findViewById(R.id.bt_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginView.class));
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
