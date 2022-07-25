package com.bill.reggie.common;

/**
 * 业务异常
 *
 * @author 李建彤
 */
public class CustomException extends RuntimeException {
    public CustomException(String msg) {
        super(msg);
    }
}
