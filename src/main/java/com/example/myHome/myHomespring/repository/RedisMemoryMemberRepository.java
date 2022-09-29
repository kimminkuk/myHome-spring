package com.example.myHome.myHomespring.repository;

import com.example.myHome.myHomespring.domain.RedisMember;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;

public class RedisMemoryMemberRepository implements RedisMemberRepository{
    private static Map<String, String> redisStore = new HashMap<>();

    @Override
    public RedisMember save(RedisMember member) {
        redisStore.put(member.getKey(), member.getValue());
        return member;
    }

    @Override
    public Optional<Boolean> findByKey(String key) {
        if (redisStore.containsKey(key)) {
            return Optional.of(true);
        } else {
            return Optional.of(false);
        }
    }

    @Override
    public List<RedisMember> findAll() {
        List<RedisMember> keyList2 = new ArrayList<>();
        for (String s : redisStore.keySet()) {
            RedisMember redisMember = new RedisMember(s, redisStore.get(s));
            keyList2.add(redisMember);
        }
        return keyList2 ;
    }

    @Override
    public Optional<String> getValue(String key) {
        return Optional.ofNullable(redisStore.get(key));
    }

    @Override
    public void saveRanking(RedisMember member) {
        redisStore.put(member.getKey(), member.getValue());
    }

    @Override
    public Set<String> getRankingMembers() {
        return redisStore.keySet();
    }

    @Override
    public Set<ZSetOperations.TypedTuple<String>> getRankingMembersWithScore(int searchMin, int searchMax) {
        //TODO:
        //Hash, Tree?로 구현 예정
        return null;
    }
    public void clearStore() {
        redisStore.clear();
    }
}
