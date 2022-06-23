package com.yclin_shopping.lightning_deal_service.mq;

import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.yclin_shopping.lightning_deal_service.db.dao.SeckillActivityDao;
import com.yclin_shopping.lightning_deal_service.db.po.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional
@RocketMQMessageListener(topic = "pay_done", consumerGroup = "pay_done_group")
public class PayDoneConsumer implements RocketMQListener<MessageExt> {

    @Resource
    private SeckillActivityDao seckillActivityDao;

    @Override
    public void onMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("Received the stock deduction request: " + message);
        Order order = JSON.parseObject(message, Order.class);
        seckillActivityDao.deductStock(order.getSeckillActivityId());
    }
}
