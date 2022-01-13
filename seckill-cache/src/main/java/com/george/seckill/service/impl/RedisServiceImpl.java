package com.george.seckill.service.impl;

import com.george.seckill.api.cache.service.IRedisService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @description redis缓冲servie实现类
 * @date 2021.01.13
 * @author linzhuangze
 */
@Service(interfaceClass = IRedisService.class)
public class RedisServiceImpl implements IRedisService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public <T> T get(String key, Class<T> clazz) {
        return (T)redisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> void set(String key, T value, long time) {
        redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    @Override
    public long decr(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    @Override
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public boolean lock(String key, String uniqueValue, long expireTime) {
        return false;
    }

    @Override
    public boolean unlock(String key, String uniqueValue) {
        return false;
    }
}
