package com.yclin_shopping.lightning_deal_service.db.dao;
import com.yclin_shopping.lightning_deal_service.db.mappers.SeckillActivityMapper;
import com.yclin_shopping.lightning_deal_service.db.po.SeckillActivity;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;


@Slf4j
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

    @Override
    public boolean lockStock(Long seckillActivityId) {
        int result = seckillActivityMapper.lockStock(seckillActivityId);
        if (result < 1) {
            log.error("庫存鎖定失敗");
            return false;
        }
        return true;
    }

    @Override
    public boolean deductStock(Long seckillActivityId) {
        int result = seckillActivityMapper.deductStock(seckillActivityId);
        if (result < 1) {
            log.error("Deduct stock failed");
            return false;
        }
        return true;
    }

    @Override
    public void revertStock(Long seckillActivityId) {
        seckillActivityMapper.revertStock(seckillActivityId);
    }
}
