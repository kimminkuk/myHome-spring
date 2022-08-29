package com.example.myHome.myHomespring.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.IOException;
import java.util.*;

public class RedisMemberRepository implements RedisRepository {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public Optional<String> findByRedisKey(String key) {
        return Optional.empty();
    }

    @Override
    public void saveByRedisKey(String key, String data) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, data);
        return;
    }

    @Override
    public Map<String, String> findAllVer2() throws IOException {

        return null;
    }

    @Override
    public List<String> findAll() {
        Set<String> redisKeys = redisTemplate.keys("컴퓨터 승리*");
        List<String> keysList = new ArrayList<>();
        Iterator<String> it = redisKeys.iterator();
        while (it.hasNext()) {
            String data = it.next();
            keysList.add(data);
        }
        return new ArrayList<>(keysList);
    }
}
