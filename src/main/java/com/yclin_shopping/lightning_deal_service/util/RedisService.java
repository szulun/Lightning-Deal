package com.yclin_shopping.lightning_deal_service.util;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
    
    @Resource
    private JedisPool jedisPool;

    public RedisService setValue(String key, String value) {
        Jedis client = jedisPool.getResource();
        client.set(key, value);
        client.close();
        return this;
    }

    public String getValue(String key) {
        Jedis client = jedisPool.getResource();
        String value = client.get(key);
        client.close();
        return value;
    }
}
