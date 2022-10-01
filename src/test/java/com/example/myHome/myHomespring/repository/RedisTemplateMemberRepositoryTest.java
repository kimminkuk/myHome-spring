package com.example.myHome.myHomespring.repository;

import com.example.myHome.myHomespring.domain.RedisMember;
import com.example.myHome.myHomespring.service.RedisMemberService;
import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
        long startTime = System.currentTimeMillis();
        랭킹_대량_등록();
        long endTime = System.currentTimeMillis();
        랭킹_대량_등록_버전2();
        long endTimeVer2 = System.currentTimeMillis();
        System.out.println("[랭킹_대량_등록]실행 시간 : " + (endTime - startTime) / 1000.0 + "초");
        System.out.println("[랭킹_대량_등록_버전2]실행 시간 : " + (endTimeVer2 - endTime) / 1000.0 + "초");

        //when
        Set<ZSetOperations.TypedTuple<String>> rankingMembersWithScore = redisMemberService.getRankingMembersWithScore(0, 999);
        for (ZSetOperations.TypedTuple<String> stringTypedTuple : rankingMembersWithScore) {
            System.out.println("stringTypedTuple = " + stringTypedTuple.getValue() + " " + stringTypedTuple.getScore());
        }
        //then
        assertThat(rankingMembersWithScore.size()).isEqualTo(1000);
    }

    @Test
    public void 랭킹_대량_등록() throws Exception {

        //StatefulRedisConnection connect code
        RedisClient client = RedisClient.create("redis://localhost");

        // Redis Asynchronous Pipeline
        StatefulRedisConnection<String, String> connection = client.connect();
        RedisAsyncCommands<String, String> commands = connection.async();

        //disable auto-flushing
        commands.setAutoFlushCommands(false);

        // perform a series of independent calls
        ArrayList<RedisFuture<?>> futures = Lists.newArrayList();

        for (int i = 0; i < 200000; i++) {
            //1~100000 랜덤 숫자 생성 코드
            int randomValue = (int) (Math.random() * 200000 + 1);

            futures.add(commands.zadd("ranking", (double)randomValue, "요다" + i));
            futures.add(commands.expire("ranking", 3600));
            //futures.add(commands.set("yoda-" + String.valueOf(i + 1), String.valueOf(randomValue)));
            //futures.add(commands.expire("yoda-" + String.valueOf(i + 1), 3600));
        }

        //write all commands to the transport layer
        commands.flushCommands();

        // synchronization example: Wait until all futures complete
        LettuceFutures.awaitAll(5, TimeUnit.SECONDS, futures.toArray(new RedisFuture[futures.size()]));

        //later
        connection.close();
    }

    @Test
    public void 랭킹_대량_등록_버전2() throws Exception {
        //given
        for (int i = 0; i < 200000; i++) {
            RedisMember redisMember = new RedisMember("[ver2]yoda" + String.valueOf(i + 1), String.valueOf((int) (Math.random() * 200000 + 1)));
            redisMemberService.saveRanking(redisMember);
        }
        return;
    }
}