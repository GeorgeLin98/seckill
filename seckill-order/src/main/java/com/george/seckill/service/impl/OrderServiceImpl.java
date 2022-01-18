package com.george.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.george.seckill.api.order.pojo.OrderPO;
import com.george.seckill.api.order.pojo.SeckillOrderPO;
import com.george.seckill.api.order.service.IOrderService;
import com.george.seckill.mapper.OrderMapper;
import com.george.seckill.mapper.SeckillOrderMapper;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @description 订单service实现类
 * @author linzhuangze
 * @date 2021.01.12
 */
@Service(interfaceClass = IOrderService.class)
public class OrderServiceImpl  implements IOrderService {
    @Resource
    private SeckillOrderMapper seckillOrderMapper;

    @Resource
    private OrderMapper orderMapper;

    @Override
    public boolean judgeRepeatBuy(long goodsId, Long phone) {
        QueryWrapper<SeckillOrderPO> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id",goodsId);
        wrapper.eq("user_id",phone);
        SeckillOrderPO seckillOrderPO = seckillOrderMapper.selectOne(wrapper);
        if(null == seckillOrderPO){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void insertOrder(OrderPO orderPO) {
        orderMapper.insert(orderPO);
    }

    @Override
    public void insertSeckillOrder(SeckillOrderPO seckillGood) {
        seckillOrderMapper.insert(seckillGood);
    }


}
