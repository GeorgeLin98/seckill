package com.george.seckill.api.cache.service;

import java.util.concurrent.TimeUnit;

/**
 * @description redis缓冲servie接口
 * @date 2021.01.13
 * @author linzhuangze
 */
public interface IRedisService {
    /**
     * @description redis 的get操作，通过key获取存储在redis中的对象
     * @param key    业务层传入的key
     * @param clazz  存储在redis中的对象类型（redis中是以字符串存储的）
     * @param <T>    指定对象对应的类型
     * @return 存储于redis中的对象
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * @description redis的set操作:
     * 1、默认时间单位为秒
     * @param key    键
     * @param value  值
     * @
     */
    <T> void set(String key, T value, long time);

    /**
     * @description 判断key是否存在于redis中
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * @description 自增
     * @param key
     * @return
     */
    long incr(String key);

    /**
     * @description 自减
     * @param key
     * @return
     */
    long decr(String key);


    /**
     * @description 删除缓存中的用户数据
     * @param key
     * @return
     */
    boolean delete(String key);

    /**
     * 获取锁
     *
     * @param key     锁
     * @param uniqueValue 能够唯一标识请求的值，以此保证锁的加解锁是同一个客户端
     * @param expireTime  过期时间, 单位：milliseconds
     * @return
     */
    boolean lock(String key, String uniqueValue, long expireTime);

    /**
     * 使用Lua脚本保证解锁的原子性
     *
     * @param key     锁
     * @param uniqueValue 能够唯一标识请求的值，以此保证锁的加解锁是同一个客户端
     * @return
     */
    boolean unlock(String key, String uniqueValue);
}
