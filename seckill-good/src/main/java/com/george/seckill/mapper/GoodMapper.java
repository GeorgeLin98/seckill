package com.george.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.george.seckill.api.good.pojo.GoodPO;
import com.george.seckill.api.good.pojo.GoodVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description 商品Mapper
 * @deta 2021.01.13
 * @author linzhuangze
 */
public interface GoodMapper extends BaseMapper<GoodPO> {
    /**
     * @description 取商品列表
     * @return
     */
    @Select("SELECT g.*, mg.* FROM seckill_goods mg LEFT JOIN goods g ON mg.goods_id=g.id")
    List<GoodVO> listGoodsVo();
    /**
     * @description 通过商品的id查出商品的所有信息（包含该商品的秒杀信息）
     * @param goodId
     * @return
     */
    @Select("SELECT g.*, mg.* FROM seckill_goods mg LEFT JOIN goods g ON mg.goods_id=g.id where g.id = #{goodId}")
    GoodVO getGoodsVoByGoodsId(long goodId);
}
