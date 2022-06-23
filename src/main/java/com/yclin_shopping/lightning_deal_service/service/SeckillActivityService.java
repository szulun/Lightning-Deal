package com.yclin_shopping.lightning_deal_service.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yclin_shopping.lightning_deal_service.util.RedisService;

@Service
public class SeckillActivityService {
    
    @Resource
    private RedisService redisService;

    public boolean seckillStockValidator(long activityId) {
        String key = "stock:" + activityId;
        return redisService.stockDeductValidator(key);
    }
}
