package com.example.myHome.myHomespring;

import com.example.myHome.myHomespring.repository.game.RedisMemberRepository;
import com.example.myHome.myHomespring.repository.game.RedisTemplateMemberRepository;
import com.example.myHome.myHomespring.service.game.RedisMemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@EnableRedisRepositories
public class RedisRepositoryConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisMemberService redisMemberService() {
        return new RedisMemberService(redisMemberRepository());
    }

    @Bean
    public RedisMemberRepository redisMemberRepository() {
        //return new RedisMemoryMemberRepository();
        return new RedisTemplateMemberRepository();
    }
}
