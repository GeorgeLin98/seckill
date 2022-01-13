package com.george.seckill.secondkill;

import com.george.seckill.api.cache.service.IRedisService;
import com.george.seckill.api.good.service.IGoodService;
import com.george.seckill.api.order.service.IOrderService;
import com.george.seckill.api.secondkill.service.ISecondKillService;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
