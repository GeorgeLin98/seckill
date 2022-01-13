package com.george.seckill.api.good.pojo;

import com.george.seckill.api.user.pojo.UserPO;

import java.io.Serializable;

/**
 * @description 商品详情VO
 * @date 2021.01.13
 * @author linzhuanzge
 */
public class GoodDetailVO  implements Serializable {
    /**
     * 秒杀状态
     */
    private int seckillStatus = 0;
    /**
     * 秒杀剩余时间
     */
    private int remainSeconds = 0;
    /**
     * 商品VO
     */
    private GoodVO goods;
    /**
     * 用户PO
     */
    private UserPO user;

    public int getSeckillStatus() {
        return seckillStatus;
    }

    public void setSeckillStatus(int seckillStatus) {
        this.seckillStatus = seckillStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodVO getGoods() {
        return goods;
    }

    public void setGoods(GoodVO goods) {
        this.goods = goods;
    }

    public UserPO getUser() {
        return user;
    }

    public void setUser(UserPO user) {
        this.user = user;
    }
}
