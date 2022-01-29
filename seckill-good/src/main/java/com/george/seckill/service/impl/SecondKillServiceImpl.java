package com.george.seckill.service.impl;

import com.george.seckill.api.good.pojo.GoodVO;
import com.george.seckill.api.good.pojo.SeckillGoodPO;
import com.george.seckill.api.good.service.IGoodService;
import com.george.seckill.api.order.pojo.OrderPO;
import com.george.seckill.api.order.pojo.SeckillOrderPO;
import com.george.seckill.api.order.service.IOrderService;
import com.george.seckill.api.secondkill.service.ISecondKillService;
import com.george.seckill.api.user.pojo.UserPO;
import com.george.seckill.util.SnowFlakeUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * @description 秒杀服务Service实现类
 * @date 2021.01.13
 * @author linzhuanzge
 */
@Service(interfaceClass = ISecondKillService.class)
public class SecondKillServiceImpl implements ISecondKillService {
    @Autowired
    private IGoodService goodService;

    @Reference(interfaceClass = IOrderService.class)
    private IOrderService orderService;

    @Override
    public OrderPO seckill(UserPO user, GoodVO goodVO) {
        //更新秒杀商品库存
        SeckillGoodPO seckillGood = goodService.getSeckillGood(goodVO.getId());
        SeckillGoodPO seckillGoodPO = new SeckillGoodPO();
        seckillGoodPO.setId(seckillGood.getId());
        seckillGoodPO.setGoodsId(goodVO.getId());
        seckillGoodPO.setStockCount(seckillGood.getStockCount()-1);
        goodService.updateSecGoodStock(seckillGoodPO);
        OrderPO orderPO = new OrderPO();
        orderPO.setId(SnowFlakeUtil.getSnowFlakeId());
        orderPO.setGoodsId(goodVO.getId());
        orderPO.setCreateDate(new Date());
        orderPO.setOrderChannel(1);
        orderPO.setGoodsCount(1);
        orderPO.setGoodsName(goodVO.getGoodsName());
        orderPO.setDeliveryAddrId(1L);
        orderPO.setGoodsPrice(goodVO.getGoodsPrice());
        orderPO.setStatus(0);
        orderPO.setUserId(user.getPhone());
        orderService.insertOrder(orderPO);
        SeckillOrderPO seckillOrderPO = new SeckillOrderPO();
        seckillOrderPO.setOrderId(orderPO.getId());
        seckillOrderPO.setGoodsId(goodVO.getId());
        seckillOrderPO.setUserId(user.getPhone());
        orderService.insertSeckillOrder(seckillOrderPO);
        return orderPO;
    }
}
