package com.george.seckill.secondkill;

import com.george.seckill.api.cache.service.IRedisService;
import com.george.seckill.api.cache.util.CacheUtil;
import com.george.seckill.api.good.pojo.GoodVO;
import com.george.seckill.api.good.service.IGoodService;
import com.george.seckill.api.good.util.GoodUtil;
import com.george.seckill.api.mq.pojo.SeckillMessageVO;
import com.george.seckill.api.mq.service.IMqProviderService;
import com.george.seckill.api.order.pojo.OrderPO;
import com.george.seckill.api.order.service.IOrderService;
import com.george.seckill.api.order.util.OrderUtil;
import com.george.seckill.api.secondkill.service.ISecondKillService;
import com.george.seckill.api.user.pojo.UserPO;
import com.george.seckill.exception.GlobalException;
import com.george.seckill.pojo.ResponseVO;
import com.george.seckill.util.JsonUtil;
import com.george.seckill.util.MsgAndCodeEnum;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @description 商品controller
 * @author linzhuangze
 * @date 2021.01.13
 */
@Controller
@RequestMapping("/seckill/")
public class SecondKillController implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(SecondKillController.class);

    @Reference(interfaceClass = IRedisService.class)
    IRedisService redisService;

    @Reference(interfaceClass = IGoodService.class)
    IGoodService goodsService;

    @Reference(interfaceClass = ISecondKillService.class)
    ISecondKillService secondKillService;

    @Reference(interfaceClass = IOrderService.class)
    IOrderService orderService;

    @Reference(interfaceClass = IMqProviderService.class)
    IMqProviderService mqProviderService;

    @RequestMapping(value = "doSeckill")
    @ResponseBody
    public ResponseVO<Integer> doSeckill(UserPO user, long goodsId) {
        if(null == user){
            return ResponseVO.fail();
        }
        //预减库存
        long stock = redisService.decr(String.format(GoodUtil.GOOD_STOCK_KEY, goodsId));
//        GoodVO goodVO = goodsService.getGoodsVoByGoodsId(goodsId);
//        if(goodVO.getStockCount() < 1){
//            throw new GlobalException(MsgAndCodeEnum.EMPTY_STOCK.getCode(),MsgAndCodeEnum.EMPTY_STOCK.getMsg());
//        }
        //如果库存已无，则加回去
        if(stock < 0){
            redisService.incr(String.format(GoodUtil.GOOD_STOCK_KEY, goodsId));
            throw new GlobalException(MsgAndCodeEnum.EMPTY_STOCK.getCode(),MsgAndCodeEnum.EMPTY_STOCK.getMsg());
        }
         //查看是否重复购买
//         boolean repeatBuy = orderService.judgeRepeatBuy(goodsId, user.getPhone());
        Integer isRepeat = redisService.get(String.format(OrderUtil.ORDER_KEY, user.getPhone(), goodsId), Integer.class);
        if(isRepeat!=null){
            throw new GlobalException(MsgAndCodeEnum.REPEAT_ERROR.getCode(),MsgAndCodeEnum.REPEAT_ERROR.getMsg());
        }
        //生产消息
        SeckillMessageVO seckillMessageVO = new SeckillMessageVO();
        seckillMessageVO.setUser(user);
        seckillMessageVO.setGoodsId(goodsId);
        String json = JsonUtil.beanToString(seckillMessageVO);
        mqProviderService.sendSeckillMessage(json);
        //秒杀
//        OrderPO orderPO = secondKillService.seckill(user,goodVO);
//        return ResponseVO.success(String.valueOf(orderPO.getId()));
        return ResponseVO.success(0);
    }

    /**
     * @description 返回秒杀结果： orderId：成功， -1：失败，0：排队中
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "result")
    @ResponseBody
    public ResponseVO<Long> getResult(UserPO user, long goodsId) {
        if(null == user){
            return ResponseVO.fail();
        }
        Long orderId = secondKillService.getResult(user,goodsId);
        return ResponseVO.success(orderId);
    }

    /**
     * @description 初始化库存
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodVO> goodVOS = goodsService.listGoodsVo();
        for(GoodVO vo:goodVOS){
            redisService.set(String.format(GoodUtil.GOOD_STOCK_KEY,vo.getId()),vo.getStockCount(), CacheUtil.DEFAULT_CACHE_TIME);
        }
    }
}
