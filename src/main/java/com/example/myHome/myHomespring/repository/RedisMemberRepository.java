package com.example.myHome.myHomespring.repository;

import com.example.myHome.myHomespring.domain.RedisMember;

import java.util.List;
import java.util.Optional;

public interface RedisMemberRepository {
    //save
    RedisMember save(RedisMember member);

    // redis 에서는 키가 있냐 없냐만 구분
    Optional<Boolean> findByKey(String key);

    //findAll
    List<RedisMember> findAll();

    //getValue
    Optional<String> getValue(String key);
}
