package com.george.seckill.util;

/**
 * @descritpion 状态码与信息返回类
 * @date 2021.01.11
 * @author linzhuangze
 */
public enum MsgAndCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(500,"系统异常");

    private MsgAndCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    int code;

    String msg;
}
