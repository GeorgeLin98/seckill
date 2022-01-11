package com.george.seckill.pojo;

import com.george.seckill.util.MsgAndCodeEnum;

/**
 * @description 统一相应结果类：
 * 1、<T> 数据实体类型
 * @date 2021.01.11
 * @author linzhuangze
 */
public class ResponseVO<T> {
    /**
     * 状态码
     */
    private int code;

    /**
     * 状态短语
     */
    private String msg;

    /**
     * 数据实体
     */
    private T data;

    /**
     * @descriptom 成功返回（带数据）
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseVO<T> success(T data){
        return  new ResponseVO<T>(MsgAndCodeEnum.SUCCESS.getCode(), MsgAndCodeEnum.SUCCESS.getMsg(), data);
    }

    /**
     * @description 成功返回
     * @return
     */
    public static <T> ResponseVO<T> success(){
        return  new ResponseVO<T>(MsgAndCodeEnum.SUCCESS.getCode(), MsgAndCodeEnum.SUCCESS.getMsg(), null);
    }

    /**
     * @description 失败返回
     * @return
     */
    public static <T> ResponseVO<T> fail(){
        return new ResponseVO<T>(MsgAndCodeEnum.FAIL.getCode(), MsgAndCodeEnum.FAIL.getMsg(), null);
    }

    /**
     * @description 失败返回(自定义)
     * @param code
     * @param msg
     * @return
     */
    public static <T> ResponseVO<T> fail(int code,String msg){
        return new ResponseVO<T>(code,msg,null);
    }

    public ResponseVO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
