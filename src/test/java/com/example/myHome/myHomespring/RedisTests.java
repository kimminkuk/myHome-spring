package com.example.myHome.myHomespring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisConnectionTest() {
        String key = "a";
        String data = "1";

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, data);

        String result = valueOperations.get(key);
        assertEquals(data, result);
    }
}