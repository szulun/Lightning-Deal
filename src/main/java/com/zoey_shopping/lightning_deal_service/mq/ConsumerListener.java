package com.zoey_shopping.lightning_deal_service.mq;

import java.io.UnsupportedEncodingException;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "test-yclin", consumerGroup = "consumerGroup-yclin")
public class ConsumerListener implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            String body = new String(messageExt.getBody(), "UTF-8");
            System.out.println("Receive message: " + body);
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }
    }

}