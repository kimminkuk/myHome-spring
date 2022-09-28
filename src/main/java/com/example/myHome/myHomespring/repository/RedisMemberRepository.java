package com.example.myHome.myHomespring.repository;

import com.example.myHome.myHomespring.domain.RedisMember;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RedisMemberRepository {
    //save
    RedisMember save(RedisMember member);

    // redis 에서는 키가 있냐 없냐만 구분
    Optional<Boolean> findByKey(String key);

    //findAll
    List<RedisMember> findAll();

    //getValue
    Optional<String> getValue(String key);

    /**
     *  랭킹 페이지에서 사용되는 저장 데이터
     */
     void saveRanking(RedisMember member);
     Set<String> getRankingMembers();
     Set<ZSetOperations.TypedTuple<String>> getRankingMembersWithScore();
}
