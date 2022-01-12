package com.george.seckill.exception;

/**
 * @description 全局异常
 * @date 2021.01.12
 * @author linzhuangze
 */
public class GlobalException extends RuntimeException{
    private int code;

    private String msg;

    public GlobalException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
