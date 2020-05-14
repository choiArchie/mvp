package com.ex.mvp.base;

import com.ex.mvp.mvp.IModel;
import com.ex.mvp.mvp.IPresenter;
import com.ex.mvp.mvp.IView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:    presnetr的基类
 */
@SuppressWarnings("all")
public abstract class BasePresenter<V extends IView> implements IPresenter {
    private WeakReference mWeakReference;
    protected V mView;

    //获取数据层model的集合
    public abstract HashMap<String, IModel> getModelMap();

    @Override
    public void attachView(IView view) {
        mWeakReference = new WeakReference(view);
    }

    @Override
    public void detachView() {
        if (mWeakReference != null) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }

    @Override
    public V getView() {
        return (V) mWeakReference.get();

    }

    /**
     * 如有需要添加多个model
     *
     * @param iModels
     * @return
     */
    public abstract HashMap<String, IModel> addMoreModelMap(IModel... iModels);
}
