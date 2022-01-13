package com.george.seckill.api.order.pojo;

import com.george.seckill.api.good.pojo.GoodVO;
import com.george.seckill.api.user.pojo.UserPO;

/**
 * @description 订单详情VO
 * @date 2021.01.13
 * @author linzhuanzge
 */
public class OrderDetailVO {
    /**
     * 用户信息
     */
    private UserPO user;

    /**
     * 商品信息
     */
    private GoodVO goods;
    /**
     * 订单信息
     */
    private OrderPO order;

    public UserPO getUser() {
        return user;
    }

    public void setUser(UserPO user) {
        this.user = user;
    }

    public GoodVO getGoods() {
        return goods;
    }

    public void setGoods(GoodVO goods) {
        this.goods = goods;
    }

    public OrderPO getOrder() {
        return order;
    }

    public void setOrder(OrderPO order) {
        this.order = order;
    }
}
