package com.george.seckill.api.good.util;

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
    /**
     * 商品列表缓存
     */
    public static String GOOD_LIST_KEY ="goodList";
    /**
     * 商品库存初始化key
     */
    public static String GOOD_STOCK_KEY ="goodStock_%s";
    /**
     * 商品库存初始化key
     */
    public static String GOOD_RESULT_KEY ="goodResult_%s_%s";
}
