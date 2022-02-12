package com.george.seckill.service;

import com.george.seckill.api.mq.pojo.SeckillMessageVO;
import com.george.seckill.api.mq.service.IMqProviderService;
import com.george.seckill.config.MQConfig;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description mq生产者类
 * @author lzz
 * @date 2022.02.10
 */
@Service(interfaceClass = IMqProviderService.class)
public class MqProvider implements IMqProviderService{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //路由key
    public static final String SECOND_KILL_ROUTING_KEY = "seckill.message";

    @Override
    public void sendSeckillMessage(String message) {
        rabbitTemplate.convertAndSend(MQConfig.SECOND_KILL_EXCHANGE,SECOND_KILL_ROUTING_KEY,message);
    }
}
