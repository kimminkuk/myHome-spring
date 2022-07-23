package com.example.myHome.myHomespring.service;

import com.example.myHome.myHomespring.repository.RedisRepository;

import java.util.List;
import java.util.Optional;

public class RedisMemberService {
    private final RedisRepository redisRepository;

    public RedisMemberService(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    public void join(String key, String data) {
        //예외처리 못만들겠음.. 스터디
        redisRepository.saveByRedisKey(key, data);
        return;
    }

    public Optional<String> findOne(String key) {
        return redisRepository.findByRedisKey(key);
    }

    public List<String> findMembers(String key) {
        return redisRepository.findAll();
    }
}
