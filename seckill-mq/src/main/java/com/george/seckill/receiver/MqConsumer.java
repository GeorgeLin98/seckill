package com.george.seckill.receiver;

import com.george.seckill.api.cache.service.IRedisService;
import com.george.seckill.api.good.pojo.GoodVO;
import com.george.seckill.api.good.service.IGoodService;
import com.george.seckill.api.mq.pojo.SeckillMessageVO;
import com.george.seckill.api.order.util.OrderUtil;
import com.george.seckill.api.secondkill.service.ISecondKillService;
import com.george.seckill.api.user.pojo.UserPO;
import com.george.seckill.config.MQConfig;
import com.george.seckill.exception.GlobalException;
import com.george.seckill.util.JsonUtil;
import com.george.seckill.util.MsgAndCodeEnum;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @description mq消费者类
 * @author lzz
 * @date 2022.02.10
 */
@Service
public class MqConsumer {

    @Reference(interfaceClass = IRedisService.class)
    IRedisService redisService;

    @Reference(interfaceClass = IGoodService.class)
    IGoodService goodsService;

    @Reference(interfaceClass = ISecondKillService.class)
    ISecondKillService secondKillService;

    @RabbitListener(queues = MQConfig.SECOND_KILL_QUEUE)
    public void receiveSkInfo(String message) {
        //Json转换
        SeckillMessageVO seckillMessageVO = JsonUtil.stringToBean(message, SeckillMessageVO.class);
        UserPO user = seckillMessageVO.getUser();
        long goodsId = seckillMessageVO.getGoodsId();
        //查询商品数据
        GoodVO goodVO = goodsService.getGoodsVoByGoodsId(goodsId);
        //判断库存
        if(goodVO.getStockCount() < 1){
            return;
        }
        //判断是否重复抢购
        Integer isRepeat = redisService.get(String.format(OrderUtil.ORDER_KEY, user.getPhone(), goodsId), Integer.class);
        if(isRepeat!=null){
            return;
        }
        //下单
        secondKillService.seckill(user,goodVO);
    }
}
