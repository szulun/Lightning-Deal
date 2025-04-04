package com.zoey_shopping.lightning_deal_service;

import java.util.UUID;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.zoey_shopping.lightning_deal_service.service.SeckillActivityService;
import com.zoey_shopping.lightning_deal_service.util.RedisService;

@SpringBootTest
public class RedisTest {
    
    @Resource
    private RedisService redisService;

    @Resource
    private SeckillActivityService seckillActivityService;

    @Test
    public void stockTest() {
        String value = redisService.setValue("stock:19", String.valueOf(10L)).getValue("stock:19");
        System.out.println(value);
    }

    @Test
    public void stockTestSet() {
        redisService.setValue("stock:12", String.valueOf(23L));
    }

    @Test
    public void stockTestGet() {
        String value = redisService.getValue("stock:12");
        System.out.println(value);
    }

    @Test
    public void stockDeductTest() {
        String stock;
        stock = redisService.getValue("stock:12");
        System.out.println("Before deduct: " + stock);
        boolean result = redisService.stockDeductValidator("stock:12");
        System.out.println("Result: " + result);
        stock = redisService.getValue("stock:12");
        System.out.println("After deduct: " + stock);
    }

    @Test
    public void pushSeckillInfoToRedisTest() {
        seckillActivityService.pushSeckillInfoToRedis(19);
    }

    @Test
    public void testConcurrentAddLock() {
        for (int i = 0; i < 10; i++) {
            String requestId = UUID.randomUUID().toString();
            System.out.println(redisService.tryGetDistributedLock("A", requestId, 1000));
            redisService.releaseDistributedLock("A", requestId);
        }
    }
}
