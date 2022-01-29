package com.george.seckill.api.order.service;

import com.george.seckill.api.order.pojo.OrderPO;
import com.george.seckill.api.order.pojo.SeckillOrderPO;

/**
 * @description 订单service接口
 * @date 2021.01.13
 * @author linzhuangze
 */
public interface IOrderService {
    /**
     * @description 判断是否重复购买
     * @param goodsId
     * @param phone
     * @return
     */
    boolean judgeRepeatBuy(long goodsId, Long phone);

    /**
     * @description 新增订单
     * @param orderPO
     */
    void insertOrder(OrderPO orderPO);

    /**
     * @description 新增秒杀订单
     * @param seckillGood
     */
    void insertSeckillOrder(SeckillOrderPO seckillGood);

    /**
     * @description 获取订单详情
     * @param orderId
     * @return
     */
    OrderPO getOrderById(long orderId);
}
