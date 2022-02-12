package com.george.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description mq配置类
 * @author lzz
 * @date 2022.02.10
 */
@Configuration
public class MQConfig {

    //消息队列名
    public static final String SECOND_KILL_QUEUE = "seckill_queue";
    //交换机名
    public static final String SECOND_KILL_EXCHANGE = "seckill_exchange";
    //路由key
    public static final String SECOND_KILL_ROUTING_KEY = "seckill.#";

    @Bean
    public Queue seckillQueue() {
        return new Queue(SECOND_KILL_QUEUE, true);
    }

    @Bean
    public Exchange seckillExchange(){
        return new TopicExchange(SECOND_KILL_EXCHANGE);
    }

    @Bean
    public Binding seckillBinding(){
        return BindingBuilder.bind(seckillQueue()).to(seckillExchange()).with(SECOND_KILL_ROUTING_KEY).noargs();
    }
}
