package com.ex.mvp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ex.mvp.mvp.IView;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:  BaseFragment
 */
@SuppressWarnings("all")
public abstract class BaseFragment<P extends BasePresenter> extends LazyLoadFragment implements IView {
    protected P mPresenter;
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutById(), container, false);
            initView(rootView);
            mPresenter = loadPresenter();
            initCommonData();
            initView(rootView);
            initData();
            initListener();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }

        return rootView;
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

    protected abstract void initView(View rootView);

    protected abstract void initListener();

    protected abstract void initData();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
