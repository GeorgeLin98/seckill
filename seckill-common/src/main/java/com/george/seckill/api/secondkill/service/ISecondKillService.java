package com.george.seckill.api.secondkill.service;

import com.george.seckill.api.good.pojo.GoodVO;
import com.george.seckill.api.order.pojo.OrderPO;
import com.george.seckill.api.user.pojo.UserPO;

/**
 * @description 秒杀服务Service接口
 * @date 2021.01.13
 * @author linzhuanzge
 */
public interface ISecondKillService {
    /**
     * @description 秒杀
     * @param user
     * @param goodVO
     * @return
     */
    OrderPO seckill(UserPO user, GoodVO goodVO);
}
