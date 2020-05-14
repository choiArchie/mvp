package com.ex.mvp.exception;

import com.blankj.utilcode.util.Utils;
import com.ex.mvp.R;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:   异常管理引擎
 */
@SuppressWarnings("all")
public class ExceptionEngine {
    //对应HTTP的状态码
    private static final int FAIL = 0;
    private static final int UNAUTHORIZED = 401;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof ServerException) {             //HTTP错误
            ServerException httpException = (ServerException) e;
            ex = new ApiException(ErrorType.HTTP_ERROR, e);
            switch (httpException.code) {
                case FAIL:
                    ex.msg = Utils.getApp().getResources().getString(R.string.server_error_account_or_pwd);
                    break;
                case UNAUTHORIZED:
                    ex.msg = Utils.getApp().getResources().getString(R.string.server_error_need_token);
                    break;
                case NOT_FOUND:
                    ex.msg = Utils.getApp().getResources().getString(R.string.server_error_exception);
                    break;
                case REQUEST_TIMEOUT:
                    ex.msg = Utils.getApp().getResources().getString(R.string.server_error_request_timeout);
                    break;
                case INTERNAL_SERVER_ERROR:
                    ex.msg = Utils.getApp().getResources().getString(R.string.server_error_shutdown);
                    break;
                default:
                    ex.msg = Utils.getApp().getResources().getString(R.string.server_error_net_error);  //其它均视为网络错误
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {    //服务器返回的错误
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException.code, resultException);
            ex.msg = resultException.msg;
            return ex;
        } else if (e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(ErrorType.PARSE_ERROR, e);
            ex.msg = Utils.getApp().getResources().getString(R.string.client_error_parse_error);            //均视为解析错误
            return ex;
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException || e
                instanceof ConnectTimeoutException) {
            ex = new ApiException(ErrorType.NETWORK_ERROR, e);
            ex.msg = Utils.getApp().getResources().getString(R.string.client_error_net_connect_error);  //均视为网络错误
            return ex;
        } else if (e instanceof HttpException) {
            if ("HTTP 404 Not Found".equals(e.getMessage())) {
                ex = new ApiException(ErrorType.NETWORK_ERROR, e);
                ex.msg = Utils.getApp().getResources().getString(R.string.client_error_not_server);
            } else {
                ex = new ApiException(ErrorType.NETWORK_ERROR, e);
                ex.msg = Utils.getApp().getResources().getString(R.string.client_error_other_conn_error);
            }
            return ex;

        } else {
            ex = new ApiException(ErrorType.UNKONW, e);
            ex.msg = Utils.getApp().getResources().getString(R.string.error_unkonwn);          //未知错误
            return ex;
        }
    }
}
