package com.yclin_shopping.lightning_deal_service.mq;

import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.yclin_shopping.lightning_deal_service.db.dao.OrderDao;
import com.yclin_shopping.lightning_deal_service.db.dao.SeckillActivityDao;
import com.yclin_shopping.lightning_deal_service.db.po.Order;
import com.yclin_shopping.lightning_deal_service.util.RedisService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RocketMQMessageListener(topic = "pay_check", consumerGroup = "pay_check_group")
public class PayStatusCheckListener implements RocketMQListener<MessageExt> {

    @Resource
    private OrderDao orderDao;

    @Resource
    private SeckillActivityDao seckillActivityDao;

    @Resource
    private RedisService redisService;

    @Override
    @Transactional
    public void onMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("Received order payment check message: " + message);
        Order order = JSON.parseObject(message, Order.class);
        // 1. query order
        Order orderInfo = orderDao.queryOrder(order.getOrderNo());
        if (orderInfo == null) {
            log.info("The order is not exist: " + order.getOrderNo());
            return;
        }
        // 2. check whether the payment is done
        if (orderInfo.getOrderStatus() != 2) {
            // 3. close unpaid order
            log.info("Close unpaid order: " + orderInfo.getOrderNo());
            orderInfo.setOrderStatus(99);
            orderDao.updateOrder(orderInfo);
            // 4. revert the stock in MySQL
            seckillActivityDao.revertStock(order.getSeckillActivityId());
            // 5. revert the stock in Redis
            redisService.revertStock("stock:" + order.getSeckillActivityId());
            // 6. remove the user from the purchased list
            redisService.removeLimitMember(order.getSeckillActivityId(), order.getUserId());
        }
    }
}