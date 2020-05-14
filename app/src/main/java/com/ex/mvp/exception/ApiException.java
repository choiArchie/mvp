package com.ex.mvp.exception;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:    异常自定义
 */
@SuppressWarnings("all")
public class ApiException extends RuntimeException {
    public int code;
    public String msg;

    public ApiException(int code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

}
