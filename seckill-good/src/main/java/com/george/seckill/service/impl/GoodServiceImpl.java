package com.george.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.george.seckill.api.good.pojo.GoodVO;
import com.george.seckill.api.good.pojo.SeckillGoodPO;
import com.george.seckill.api.good.service.IGoodService;
import com.george.seckill.mapper.GoodMapper;
import com.george.seckill.mapper.SeckillGoodMapper;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(interfaceClass = IGoodService.class)
public class GoodServiceImpl implements IGoodService {
    @Resource
    private GoodMapper goodMapper;
    @Resource
    private SeckillGoodMapper seckillGoodMapper;

    @Override
    public List<GoodVO> listGoodsVo() {
        return goodMapper.listGoodsVo();

    }

    @Override
    public GoodVO getGoodsVoByGoodsId(long goodId) {
        return goodMapper.getGoodsVoByGoodsId(goodId);
    }

    @Override
    public SeckillGoodPO getSeckillGood(long goodId){
        QueryWrapper<SeckillGoodPO> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id",goodId);
        SeckillGoodPO seckillGoodPO = seckillGoodMapper.selectOne(wrapper);
        return seckillGoodPO;
    }

    @Override
    public void updateSecGoodStock(SeckillGoodPO seckillGoodPO){
        seckillGoodMapper.updateById(seckillGoodPO);
    }
}
