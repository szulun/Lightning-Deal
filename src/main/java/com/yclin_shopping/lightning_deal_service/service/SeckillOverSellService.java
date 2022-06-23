package com.yclin_shopping.lightning_deal_service.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yclin_shopping.lightning_deal_service.db.dao.SeckillActivityDao;
import com.yclin_shopping.lightning_deal_service.db.po.SeckillActivity;

@Service
public class SeckillOverSellService {
    
    @Resource
    private SeckillActivityDao seckillActivityDao;

    public String processSeckill(long activityId) {
        SeckillActivity seckillActivity = seckillActivityDao.querySeckillActivityById(activityId);
        long availableStock = seckillActivity.getAvailableStock();
        String result;
        if (availableStock > 0) {
            result = "恭喜，搶購成功";
            System.out.println(result);
            availableStock = availableStock - 1;
            seckillActivity.setAvailableStock(new Integer("" + availableStock));
            seckillActivityDao.updateSeckillActivity(seckillActivity);
        } else {
            result = "抱歉，搶購失敗，商品已售完";
            System.out.println(result);
        }
        return result;
    }
}
