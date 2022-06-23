package com.yclin_shopping.lightning_deal_service.db.mappers;

import java.util.List;

import com.yclin_shopping.lightning_deal_service.db.po.SeckillActivity;

public interface SeckillActivityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SeckillActivity record);

    int insertSelective(SeckillActivity record);

    SeckillActivity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillActivity record);

    int updateByPrimaryKey(SeckillActivity record);

    List<SeckillActivity> querySeckillActivitysByStatus(int activityStatus);

    int lockStock(Long seckillActivityId);

    int deductStock(Long seckillActivityId);
}