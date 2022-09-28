package com.example.myHome.myHomespring.repository;

import com.example.myHome.myHomespring.domain.RedisMember;
import com.example.myHome.myHomespring.service.RedisMemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RedisTemplateMemberRepositoryTest {
    @Autowired
    RedisMemberService redisMemberService;

    @Autowired
    RedisMemberRepository redisMemberRepository;

    /**
     * 트렌젝션으로 처리해도, redis는 트렌젝션을 지원하지 않는다. ???????
     *
     */
    @Test
    public void 회원가입() throws Exception {
        //given
        RedisMember redisMember = new RedisMember("rlaal888", "2022-09-25 00:00:00");

        //RedisMember redisMember2 = new RedisMember("ansdj123", "음22");

        //when
        String memberKey1 = redisMemberService.join(redisMember).get();
        //String memberKey2 = redisMemberService.join(redisMember2).get();

        //then
        assertThat(memberKey1).isEqualTo(redisMember.getKey());
        //assertThat(memberKey2).isEqualTo(redisMember2.getKey());
        return;
    }

    @Test
    public void 회원_데이터_획득() throws Exception {
        //given
        String inputRedisKey1 = "rlaal888";
        String value1 = redisMemberService.getValueOfKey(inputRedisKey1).get();
        String value2 = redisMemberService.getValueOfKey("ansdj123").get();

        //when
        System.out.println("key: " + inputRedisKey1 + " value: " + value1);
        System.out.println("value2 = " + value2);
        //then
        //assertEquals(value1, "음11");
        assertEquals(value2, "음22");
    }

    @Test
    public void 랭킹_등록() throws Exception {
        //given
        RedisMember redisMember = new RedisMember("요다11", "123");
        RedisMember redisMember2 = new RedisMember("요다12", "124");
        RedisMember redisMember3 = new RedisMember("요다13", "222");
        RedisMember redisMember4 = new RedisMember("요다14", "512");
        RedisMember redisMember5 = new RedisMember("요다15", "12");
        redisMemberService.saveRanking(redisMember);
        redisMemberService.saveRanking(redisMember2);
        redisMemberService.saveRanking(redisMember3);
        redisMemberService.saveRanking(redisMember4);
        redisMemberService.saveRanking(redisMember5);

        //when
//        Set<String> rankingMembers = redisMemberService.getRankingMembers();
//        for (String rankingMember : rankingMembers) {
//            System.out.println("rankingMember = " + rankingMember);
//        }
        Set<ZSetOperations.TypedTuple<String>> rankingMembersWithScore = redisMemberService.getRankingMembersWithScore();
        for (ZSetOperations.TypedTuple<String> stringTypedTuple : rankingMembersWithScore) {
            System.out.println("stringTypedTuple = " + stringTypedTuple.getValue() + " " + stringTypedTuple.getScore());
        }
        //then
        assertThat(rankingMembersWithScore.size()).isEqualTo(5);
    }
}