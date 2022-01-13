package com.george.seckill.util;

/**
 * @descritpion 状态码与信息返回类
 * @date 2021.01.11
 * @author linzhuangze
 */
public enum MsgAndCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(500,"系统异常"),
    BIND_ERROR(5001,"参数校验异常：%s"),
    /**
     * 用户模块 5002XX
     */
    FILL_REGISTER_INFO(500219, "请填写注册信息"),
    MOBILE_NOT_EXIST(500214, "手机号不存在"),
    PASSWORD_ERROR(500215, "密码错误"),
    USER_EXIST(500216, "用户已经存在，无需重复注册"),
    REGISTER_FAIL(500218, "注册异常");
    ;

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

    /**
     * @description 动态地填充msg字段
     * @param args
     * @return
     */
    public String fillArgs(Object... args) {
        // 将arg格式化到msg中，组合成一个message
        String message = String.format(this.msg, args);
        return message;
    }
}