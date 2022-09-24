package com.example.myHome.myHomespring.repository;

import com.example.myHome.myHomespring.domain.RedisMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class RedisTemplateMemberRepository implements RedisMemberRepository {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public RedisMember save(RedisMember member) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(member.getKey(), member.getValue());
        return member;
    }

    @Override
    public Optional<Boolean> findByKey(String key) {
        return Optional.ofNullable(redisTemplate.hasKey(key));
    }

    //구현은 했는데 쓰지말자. 키가 많아지면 메모리가 터질수도 있음. (코파일럿 수준보소..)
    @Override
    public List<RedisMember> findAll() {
        Set<String> keys = redisTemplate.keys("*");
        List<RedisMember> keyList = new ArrayList<>();
        for (String key : keys) {
            keyList.add(new RedisMember(key, redisTemplate.opsForValue().get(key)));
        }
        return keyList;
    }

    @Override
    public Optional<String> getValue(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return Optional.ofNullable(valueOperations.get(key));
    }
}
