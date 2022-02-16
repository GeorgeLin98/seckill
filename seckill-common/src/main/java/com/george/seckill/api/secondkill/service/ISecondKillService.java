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
    /**
     * @description 查询秒杀结果
     * @param user
     * @param goodsId
     * @return
     */
    Long getResult(UserPO user, long goodsId);

    /**
     * @decriptin 创建秒杀地址
     * @param user
     * @param goodsId
     * @return
     */
    String createPath(UserPO user, long goodsId);

    /**
     * @description 检测秒杀地址
     * @param user
     * @param goodsId
     * @param path
     * @return
     */
    boolean checkPath(UserPO user, long goodsId, String path);

    /**
     * @description 验证验证码是否正确
     * @param user
     * @param goodsId
     * @param verifyCode
     * @return
     */
    boolean checkVerifyCode(UserPO user, long goodsId, String verifyCode);
}
