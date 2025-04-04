package com.zoey_shopping.lightning_deal_service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.zoey_shopping.lightning_deal_service.mq.RocketMQService;

@SpringBootTest
public class MQTest {

    @Resource
    private RocketMQService service;

    @Test
    public void sendMQTest() throws Exception {
        service.sendMessage("test-yclin", "Hello, world!" + new Date().toString());
    }
}
