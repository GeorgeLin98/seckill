package com.george.seckill.util;

import java.util.UUID;

/**
 * @descritpion UUID工具类
 * @date 2021.01.11
 * @author linzhuangze
 */
public class UUIDUtil {
    private static String HORIZONTAL_LINE_SYMBOL = "-";

    /**
     * @description 返回uuid（32位）
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace(HORIZONTAL_LINE_SYMBOL, "");
    }
}
