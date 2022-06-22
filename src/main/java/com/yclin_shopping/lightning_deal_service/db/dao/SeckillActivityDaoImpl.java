package com.yclin_shopping.lightning_deal_service.db.dao;
import com.yclin_shopping.lightning_deal_service.db.mappers.SeckillActivityMapper;
import com.yclin_shopping.lightning_deal_service.db.po.SeckillActivity;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;


@Repository
public class SeckillActivityDaoImpl implements SeckillActivityDao {
    
    @Resource
    private SeckillActivityMapper seckillActivityMapper;
    
    @Override
    public void inertSeckillActivity(SeckillActivity seckillActivity) {
        seckillActivityMapper.insert(seckillActivity);
    }
    
    @Override
    public SeckillActivity querySeckillActivityById(long activityId) {
        return seckillActivityMapper.selectByPrimaryKey(activityId);
    }
    
    @Override
    public void updateSeckillActivity(SeckillActivity seckillActivity) {
        seckillActivityMapper.updateByPrimaryKey(seckillActivity);
    }
    
    @Override
    public List<SeckillActivity> querySeckillActivitysByStatus(int activityStatus) {
        return seckillActivityMapper.querySeckillActivitysByStatus(activityStatus);
    }
}
