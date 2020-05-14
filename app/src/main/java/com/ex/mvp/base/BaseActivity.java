package com.ex.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ex.mvp.mvp.IView;
import com.gyf.immersionbar.ImmersionBar;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:    基类的BaseActivity
 */
@SuppressWarnings("all")
public abstract class BaseActivity<P extends BasePresenter> extends Activity implements IView {
    protected View mView;
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutById());
        //初始化沉浸式状态栏
        ImmersionBar.with(this).keyboardEnable(true).init();
        mPresenter = loadPresenter();
        initCommonData();
        initView();
        initData();
        initListener();
    }

    /**
     * 设置布局文件
     *
     * @return
     */
    protected abstract int getLayoutById();

    /**
     * 加载presenter
     *
     * @return
     */
    protected abstract P loadPresenter();

    /**
     * 初始化公共数据
     */
    protected void initCommonData() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
