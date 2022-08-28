package com.example.myHome.myHomespring.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Optional;

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
    public List<String> findAll() {
        return null;
    }
}
