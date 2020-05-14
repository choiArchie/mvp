package com.ex.mvp.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ex.mvp.R;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:   自定义菊花
 */
@SuppressWarnings("all")
public class LoadingDialog {
    /**
     * 加载数据对话框
     */
    private static Dialog mLoadingDialog;

    /**
     * @param context
     * @return 显示对话框
     */
    public static Dialog showLoadingDialog(Context context) {
        return showLoadingDialog(context, true);
    }

    /**
     * 显示加载对话框
     *
     * @param context    上下文
     * @param msg        对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    public static Dialog showLoadingDialog(Context context, boolean cancelable) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        mLoadingDialog = new Dialog(context, R.style.LoadingDialog);
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        return mLoadingDialog;
    }

    /**
     * 关闭加载对话框
     */
    public static void cancelLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.cancel();
        }
    }

}
