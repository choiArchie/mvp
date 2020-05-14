package com.ex.mvp.mvp;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:    业务层接口
 */
@SuppressWarnings("all")
public interface IPresenter<V extends IView> {
    //绑定view
    void attachView(V view);

    //清除绑定 防止内层泄漏
    void detachView();

    //获取view
    IView getView();
}
