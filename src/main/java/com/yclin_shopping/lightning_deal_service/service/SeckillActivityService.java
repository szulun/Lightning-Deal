package com.yclin_shopping.lightning_deal_service.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yclin_shopping.lightning_deal_service.db.dao.OrderDao;
import com.yclin_shopping.lightning_deal_service.db.dao.SeckillActivityDao;
import com.yclin_shopping.lightning_deal_service.db.dao.SeckillCommodityDao;
import com.yclin_shopping.lightning_deal_service.db.po.Order;
import com.yclin_shopping.lightning_deal_service.db.po.SeckillActivity;
import com.yclin_shopping.lightning_deal_service.db.po.SeckillCommodity;
import com.yclin_shopping.lightning_deal_service.mq.RocketMQService;
import com.yclin_shopping.lightning_deal_service.util.RedisService;
import com.yclin_shopping.lightning_deal_service.util.SnowFlake;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SeckillActivityService {
    
    @Resource
    private RedisService redisService;

    @Resource
    private SeckillActivityDao seckillActivityDao;

    @Resource
    private RocketMQService rocketMQService;

    @Resource
    private OrderDao orderDao;

    @Resource
    private SeckillCommodityDao seckillCommodityDao;

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
        // messageDelayLevel: 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        rocketMQService.sendDelayMessage("pay_check", JSON.toJSONString(order), 4);

        return order;
    }

    public void payOrderProcess(String orderNo) throws Exception {
        log.info("Complete order payment: " + orderNo);
        Order order = orderDao.queryOrder(orderNo);

        if (order == null) {
            log.error("The order dose not exist: " + orderNo);
            return;
        } else if (order.getOrderStatus() != 1) {
            log.error("Invalid order: " + orderNo);
            return;
        }

        order.setPayTime(new Date());
        // 0: invalid order, 1: order created, waiting for payment, 2: payment completed
        order.setOrderStatus(2);
        orderDao.updateOrder(order);
        
        rocketMQService.sendMessage("pay_done", JSON.toJSONString(order));
    }

    public void pushSeckillInfoToRedis(long seckillActivityId) {
        SeckillActivity seckillActivity = seckillActivityDao.querySeckillActivityById(seckillActivityId);
        redisService.setValue("seckillActivity:" + seckillActivityId, JSON.toJSONString(seckillActivity));

        SeckillCommodity seckillCommodity = seckillCommodityDao.querySeckillCommodityById(seckillActivity.getCommodityId());
        redisService.setValue("seckillCommodity:" + seckillActivity.getCommodityId(), JSON.toJSONString(seckillCommodity));
    }
}
