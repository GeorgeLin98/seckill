package com.george.seckill.api.mq.service;

import com.george.seckill.api.mq.pojo.SeckillMessageVO;

/**
 * @description mq生产者类API
 * @author lzz
 * @date 2022.02.10
 */
public interface IMqProviderService {
    /**
     * @description 将用户秒杀信息投递到MQ中（使用direct模式的exchange）
     * @date 2022.02.10
     * @author lzz
     * @param message
     */
    void sendSeckillMessage(String message);
}
