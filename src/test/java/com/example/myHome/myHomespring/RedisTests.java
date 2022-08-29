package com.example.myHome.myHomespring;

import com.example.myHome.myHomespring.repository.RedisMemberRepository;
import com.example.myHome.myHomespring.service.RedisMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    RedisMemberService redisMemberService;

    @Test
    public void redisConnectionTest() {
        String key = "a";
        String data = "1";

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, data);

        String result = valueOperations.get(key);
        assertEquals(data, result);
    }

    @Test
    public void redisSetOneTest() {
        String key = "test";
        String data = "11";
        redisMemberService.join(key, data);

    }

    @Test
    public void redisFindAll() {
        System.out.println("redisFindAll() Call");
        List<String> findAll = redisMemberService.findAll();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        for (String s : findAll) {
            String testString = valueOperations.get(s);
            System.out.println("redisKey: " + s + " redisValue: " + testString);
        }
        System.out.println("redisFindAll() End");
    }
}