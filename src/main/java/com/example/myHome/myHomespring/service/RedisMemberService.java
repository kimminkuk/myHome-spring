package com.example.myHome.myHomespring.service;

import com.example.myHome.myHomespring.domain.RedisMember;
import com.example.myHome.myHomespring.repository.RedisMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class RedisMemberService {
    private final RedisMemberRepository redisMemberRepository;
    public RedisMemberService(RedisMemberRepository redisMemberRepository) {
        this.redisMemberRepository = redisMemberRepository;
    }
    /**
     * 회원 가입
     */
    public Optional<String> join(RedisMember member) {
        validateDuplicateMember(member);
        redisMemberRepository.save(member);
        return Optional.ofNullable(member.getKey());
    }

    /**
     * 전체 회원 조회
     */
    public List<RedisMember> findMembers() {
        return redisMemberRepository.findAll();
    }

    /**
     * 회원 조회
     */
    public Optional<Boolean> findOne(String key) {
        return redisMemberRepository.findByKey(key);
    }

    /**
     * 회원 정보 획득
     */
    public Optional<String> getValueOfKey(String key) {
        return redisMemberRepository.getValue(key);
    }
    private void validateDuplicateMember(RedisMember member) {
        if (redisMemberRepository.findByKey(member.getKey())
                .get().booleanValue()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

}
