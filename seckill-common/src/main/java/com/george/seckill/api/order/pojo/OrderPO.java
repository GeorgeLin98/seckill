package com.george.seckill.api.order.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 订单PO
 * @date 2021.01.13
 * @author linzhuanzge
 */
@TableName("order_info")
public class OrderPO implements Serializable {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 收货地址id
     */
    private Long deliveryAddrId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品数量
     */
    private Integer goodsCount;
    /**
     * 商品价格
     */
    private Double goodsPrice;
    /**
     * 订单来源
     */
    private Integer orderChannel;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 订单支付时间
     */
    private Date payDate;
}
