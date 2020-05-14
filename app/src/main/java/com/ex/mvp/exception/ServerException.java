package com.ex.mvp.exception;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:    异常自定义
 */
@SuppressWarnings("all")
public class ServerException extends RuntimeException {
    public int code;
    public String msg;

    public ServerException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
