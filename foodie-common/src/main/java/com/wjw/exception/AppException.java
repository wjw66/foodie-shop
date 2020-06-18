package com.wjw.exception;

import lombok.Data;

/**
 * @author : wjwjava01@163.com
 * @date : 23:39 2020/6/10
 * @description :
 */
@Data
public class AppException extends RuntimeException{
    private String msg;
    private Object obj;

    public AppException(String msg) {
        super(msg);
        msg = msg;
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public AppException(String msg, Object obj) {
        this.msg = msg;
        this.obj = obj;
    }
}
