package com.zoey_shopping.lightning_deal_service.mq;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zoey_shopping.lightning_deal_service.db.dao.OrderDao;
import com.zoey_shopping.lightning_deal_service.db.dao.SeckillActivityDao;
import com.zoey_shopping.lightning_deal_service.db.po.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RocketMQMessageListener(topic = "seckill_order", consumerGroup = "seckill_order_group")
public class OrderConsumer implements RocketMQListener<MessageExt> {

    @Resource
    private OrderDao orderDao;

    @Resource
    private SeckillActivityDao seckillActivityDao;

    @Override
    @Transactional
    public void onMessage(MessageExt messageExt) {
        // 1. parse the message of order creation
        String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("Received the request of order creation: " + message);
        Order order = JSON.parseObject(message, Order.class);
        order.setCreateTime(new Date());
        // 2. deduct the stock
        boolean lockStockResult = seckillActivityDao.lockStock(order.getSeckillActivityId());
        if (lockStockResult) {
            // 1 = order created, wait for payment
            order.setOrderStatus(1);
        } else {
            // 0 = No available stock, order creation failed
            order.setOrderStatus(0);
        }
        // 3. insert order to MySQL
        orderDao.insertOrder(order);
    }
}