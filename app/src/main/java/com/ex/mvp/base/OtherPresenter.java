package com.ex.mvp.base;

import com.ex.mvp.mvp.IPresenter;
import com.ex.mvp.mvp.IView;

import java.lang.ref.WeakReference;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:  其余的Presenter
 */
@SuppressWarnings("all")
public abstract class OtherPresenter<M extends BaseModel, V extends IView> implements IPresenter {
    private WeakReference mWeakReference;
    protected M mModel;
    protected V mView;

    public M getModel() {
        //使用前先进性初始化model
        mModel = loadModel();
        return mModel;
    }


    @Override
    public void attachView(IView mView) {
        mWeakReference = new WeakReference(mView);
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

    protected abstract M loadModel();
}
