package com.yclin_shopping.lightning_deal_service.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yclin_shopping.lightning_deal_service.db.dao.SeckillActivityDao;
import com.yclin_shopping.lightning_deal_service.db.po.Order;
import com.yclin_shopping.lightning_deal_service.db.po.SeckillActivity;
import com.yclin_shopping.lightning_deal_service.mq.RocketMQService;
import com.yclin_shopping.lightning_deal_service.util.RedisService;
import com.yclin_shopping.lightning_deal_service.util.SnowFlake;

@Service
public class SeckillActivityService {
    
    @Resource
    private RedisService redisService;

    @Resource
    private SeckillActivityDao seckillActivityDao;

    @Resource
    private RocketMQService rocketMQService;

    /**
    * datacenterId; 数据中心
    * machineId; 机器标识
    * 在分布式环境中可以从机器配置上读取 
    * 单机开发环境中先写死
    */
    private SnowFlake snowFlake = new SnowFlake(1, 1);

    public boolean seckillStockValidator(long activityId) {
        String key = "stock:" + activityId;
        return redisService.stockDeductValidator(key);
    }

    public Order createOrder(long seckillActivityId, long userId) throws Exception {
        // 1. create order
        SeckillActivity activity = seckillActivityDao.querySeckillActivityById(seckillActivityId);
        Order order = new Order();
        order.setOrderNo(String.valueOf(snowFlake.nextId()));
        order.setSeckillActivityId(activity.getId());
        order.setUserId(userId);
        order.setOrderAmount(activity.getSeckillPrice().longValue());

        // 2. send order creation message
        rocketMQService.sendMessage("seckill_order", JSON.toJSONString(order));

        // 3. check order payment status
        // messageDelayLevel: 1s, 5s, 10s, 30s, 1m ~ 10m, 20min, 30m, 1h, 2h
        // rocketMQService.sendDelayMessage("pay_check", JSON.toJSONString(order), 5);

        return order;
    }
}
