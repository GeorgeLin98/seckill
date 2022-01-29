package com.george.seckill.order;

import com.george.seckill.api.good.pojo.GoodVO;
import com.george.seckill.api.good.service.IGoodService;
import com.george.seckill.api.order.pojo.OrderDetailVO;
import com.george.seckill.api.order.pojo.OrderPO;
import com.george.seckill.api.order.service.IOrderService;
import com.george.seckill.api.secondkill.service.ISecondKillService;
import com.george.seckill.api.user.pojo.UserPO;
import com.george.seckill.pojo.ResponseVO;
import com.george.seckill.util.MsgAndCodeEnum;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description 订单controller
 * @author linzhuangze
 * @date 2021.01.13
 */
@Controller
@RequestMapping("/order/")
public class OrderController {

    @Reference(interfaceClass = IGoodService.class)
    IGoodService goodsService;

    @Reference(interfaceClass = IOrderService.class)
    IOrderService orderService;

    /**
     * 获取订单详情
     * @param user
     * @param orderId
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public ResponseVO<OrderDetailVO> orderInfo(UserPO user, @RequestParam("orderId") long orderId) {
        if (user == null) {
            return ResponseVO.fail(MsgAndCodeEnum.MOBILE_NOT_EXIST.getCode(),MsgAndCodeEnum.MOBILE_NOT_EXIST.getMsg());
        }
        // 获取订单信息
        OrderPO order = orderService.getOrderById(orderId);
        if (order == null) {
            return ResponseVO.fail(MsgAndCodeEnum.ORDER_NOT_EXIST.getCode(),MsgAndCodeEnum.ORDER_NOT_EXIST.getMsg());
        }
        // 如果订单存在，则根据订单信息获取商品信息
        long goodsId = order.getGoodsId();
        GoodVO goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVO vo = new OrderDetailVO();
        // 设置用户信息
        vo.setUser(user);
        // 设置订单信息
        vo.setOrder(order);
        // 设置商品信息
        vo.setGoods(goods);
        return ResponseVO.success(vo);
    }
}
