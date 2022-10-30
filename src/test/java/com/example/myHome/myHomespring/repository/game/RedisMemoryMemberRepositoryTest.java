package com.example.myHome.myHomespring.repository.game;

import com.example.myHome.myHomespring.domain.game.RedisMember;
import com.example.myHome.myHomespring.repository.game.RedisMemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RedisMemoryMemberRepositoryTest {
    RedisMemoryMemberRepository redisRepository = new RedisMemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        redisRepository.clearStore();
    }

    @Test
    void save() {
        //given
        RedisMember RedisMember = new RedisMember("spring1", "test1");
        RedisMember redisMember2 = new RedisMember("spring2", "test1");

        //when
        RedisMember save = redisRepository.save(RedisMember);
        RedisMember save1 = redisRepository.save(redisMember2);

        //then
        assertThat(save).isEqualTo(RedisMember);
        assertThat(save1).isEqualTo(redisMember2);
    }

    @Test
    void findByKey() {
        //given
        RedisMember redisMember = new RedisMember("spring1", "test1");
        RedisMember redisMember1 = new RedisMember("spring2", "test2");
        redisRepository.save(redisMember);
        redisRepository.save(redisMember1);

        //when
        Boolean redisMemberTf = redisRepository.findByKey("spring1").get();
        Boolean redisMemberTf2 = redisRepository.findByKey("spring2").get();
        Boolean redisMemberTf3 = redisRepository.findByKey("spring3").get();

        //then
        assertThat(redisMemberTf).isEqualTo(true);
        assertThat(redisMemberTf2).isEqualTo(true);
        assertThat(redisMemberTf3).isEqualTo(false);
    }

    @Test
    void findAllKeys() {
        //given
        RedisMember redisMember = new RedisMember("spring1", "test1");
        RedisMember redisMember2 = new RedisMember("spring2", "test2");
        RedisMember redisMember3 = new RedisMember("spring3", "test3");
        RedisMember redisMember4 = new RedisMember("spring4", "test4");

        //when
        redisRepository.save(redisMember);
        redisRepository.save(redisMember2);
        redisRepository.save(redisMember3);
        redisRepository.save(redisMember4);

        //then
        assertThat(redisRepository.findAll().size()).isEqualTo(4);
        for (RedisMember member : redisRepository.findAll()) {
            System.out.println("member.key = " + member.getKey() + " member.value = " + member.getValue());
        }
    }

    @Test
    void getValueOfKey() {
        //given
        RedisMember redisMember = new RedisMember("spring1", "test1");
        RedisMember redisMember1 = new RedisMember("spring2", "test2");
        RedisMember redisMember2 = new RedisMember("spring3", "test3");
        RedisMember redisMember3 = new RedisMember("spring4", "test4");

        //when
        RedisMember save = redisRepository.save(redisMember);
        RedisMember save1 = redisRepository.save(redisMember1);
        RedisMember save2 = redisRepository.save(redisMember2);
        RedisMember save3 = redisRepository.save(redisMember3);

        //then
        assertThat(redisRepository.getValue("spring1").get()).isEqualTo("test1");
        assertThat(redisRepository.getValue("spring2").get()).isEqualTo("test2");
        assertThat(redisRepository.getValue("spring3").get()).isEqualTo("test3");
        assertThat(redisRepository.getValue("spring4").get()).isEqualTo("test4");

        return;
    }
}