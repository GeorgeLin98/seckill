package com.george.seckill.secondkill;

import com.george.seckill.api.cache.service.IRedisService;
import com.george.seckill.api.good.pojo.GoodVO;
import com.george.seckill.api.good.service.IGoodService;
import com.george.seckill.api.order.pojo.OrderPO;
import com.george.seckill.api.order.service.IOrderService;
import com.george.seckill.api.secondkill.service.ISecondKillService;
import com.george.seckill.api.user.pojo.UserPO;
import com.george.seckill.exception.GlobalException;
import com.george.seckill.pojo.ResponseVO;
import com.george.seckill.util.MsgAndCodeEnum;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description 商品controller
 * @author linzhuangze
 * @date 2021.01.13
 */
@Controller
@RequestMapping("/seckill/")
public class SecondKillController {
    private static Logger logger = LoggerFactory.getLogger(SecondKillController.class);

    @Reference(interfaceClass = IRedisService.class)
    IRedisService redisService;

    @Reference(interfaceClass = IGoodService.class)
    IGoodService goodsService;

    @Reference(interfaceClass = ISecondKillService.class)
    ISecondKillService secondKillService;

    @Reference(interfaceClass = IOrderService.class)
    IOrderService orderService;

    @RequestMapping(value = "doSeckill", method = RequestMethod.POST)
    public String doSeckill(Model model, UserPO user, long goodsId) {
        if(null == user){
            return "login";
        }
        model.addAttribute("user",user);
        //查看库存是否足够
        GoodVO goodVO = goodsService.getGoodsVoByGoodsId(goodsId);
        if(goodVO.getStockCount() < 1){
            throw new GlobalException(MsgAndCodeEnum.EMPTY_STOCK.getCode(),MsgAndCodeEnum.EMPTY_STOCK.getMsg());
        }
        //查看是否重复购买
        boolean repeatBuy = orderService.judgeRepeatBuy(goodsId, user.getPhone());
        if(repeatBuy){
            throw new GlobalException(MsgAndCodeEnum.REPEAT_ERROR.getCode(),MsgAndCodeEnum.REPEAT_ERROR.getMsg());
        }
        OrderPO orderPO = secondKillService.seckill(user,goodVO);
        model.addAttribute("order",orderPO);
        model.addAttribute("goods",goodVO);
        return "order_detail";
    }
}
