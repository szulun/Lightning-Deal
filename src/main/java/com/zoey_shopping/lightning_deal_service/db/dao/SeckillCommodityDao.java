package com.zoey_shopping.lightning_deal_service.db.dao;

import com.zoey_shopping.lightning_deal_service.db.po.SeckillCommodity;

public interface SeckillCommodityDao {
    
    public SeckillCommodity querySeckillCommodityById(long commodityId);
}