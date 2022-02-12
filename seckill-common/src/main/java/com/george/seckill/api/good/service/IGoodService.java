package com.george.seckill.api.good.service;

import com.george.seckill.api.good.pojo.GoodVO;
import com.george.seckill.api.good.pojo.SeckillGoodPO;

import java.util.List;

/**
 * @description 商品service接口
 * @date 2021.01.13
 * @author linzhuangze
 */
public interface IGoodService {
    /**
     * @description 取商品列表
     * @return
     */
    List<GoodVO> listGoodsVo();

    /**
     * @description 通过商品的id查出商品的所有信息（包含该商品的秒杀信息）
     * @param goodId
     * @return
     */
    GoodVO getGoodsVoByGoodsId(long goodId);

    /**
     * @description 秒杀商品查询
     * @param goodId
     * @return
     */
    SeckillGoodPO getSeckillGood(long goodId);

    /**
     * @description 更新秒杀商品库存
     * @param seckillGoodPO
     * @return
     */
    Integer updateSecGoodStock(SeckillGoodPO seckillGoodPO);

}
