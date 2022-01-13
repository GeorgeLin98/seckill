package com.george.seckill.api.good;

/**
 * @description 商品工具类
 * @date 2021.01.13
 * @author linzhuangze
 */
public class GoodUtil {
    /**
     * 秒杀未开始
     */
    public static int SECOND_KILL_NOT_START = 0;
    /**
     * 秒杀进行中
     */
    public static int SECOND_KILLING = 1;
    /**
     * 秒杀已结束
     */
    public static int SECOND_KILL_END = 2;
    /**
     * 秒杀已结束的剩余时间
     */
    public static int SECOND_KILL_END_TIME =-1;
    /**
     * 秒杀进行中的剩余时间
     */
    public static int SECOND_KILLING_TIME =0;

}
