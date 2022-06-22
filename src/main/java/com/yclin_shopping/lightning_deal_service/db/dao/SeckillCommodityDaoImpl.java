package com.yclin_shopping.lightning_deal_service.db.dao;

import com.yclin_shopping.lightning_deal_service.db.po.SeckillCommodity;
import com.yclin_shopping.lightning_deal_service.db.mappers.SeckillCommodityMapper;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

@Repository
public class SeckillCommodityDaoImpl implements SeckillCommodityDao {
    
    @Resource
    private SeckillCommodityMapper seckillCommodityMapper;

    @Override
    public SeckillCommodity querySeckillCommodityById(long commodityId) {
        return seckillCommodityMapper.selectByPrimaryKey(commodityId);
    }
}
