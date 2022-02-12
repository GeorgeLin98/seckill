package com.george.seckill.api.mq.pojo;

import com.george.seckill.api.user.pojo.UserPO;

/**
 * @description mq消息VO类
 * @author lzz
 * @date 2022.02.10
 */
public class SeckillMessageVO {
    private UserPO user;

    private long goodsId;


    public UserPO getUser() {
        return user;
    }

    public void setUser(UserPO user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public SeckillMessageVO(UserPO user, long goodsId) {
        this.user = user;
        this.goodsId = goodsId;
    }

    public SeckillMessageVO() {
    }
}
