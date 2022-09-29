package com.example.myHome.myHomespring.repository;

import com.example.myHome.myHomespring.domain.RedisMember;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

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

    @Override
    public void saveRanking(RedisMember member) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("ranking", member.getKey(), Double.parseDouble(member.getValue()));
        return;
    }

    @Override
    public Set<String> getRankingMembers() {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        Set<String> rankingMembers = zSetOperations.range("ranking", 0, -1);
        return rankingMembers;
    }

    @Override
    public Set<ZSetOperations.TypedTuple<String>> getRankingMembersWithScore(int searchMin, int searchMax) {
        if (searchMin == 0 && searchMax == 0) {
            searchMin = 0;
            searchMax = -1;
        }
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> rankingMembers = zSetOperations.rangeWithScores("ranking", searchMin, searchMax);
        return rankingMembers;
    }


}
