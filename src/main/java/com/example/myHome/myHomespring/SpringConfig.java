package com.example.myHome.myHomespring;

import com.example.myHome.myHomespring.repository.*;
import com.example.myHome.myHomespring.repository.facility.ReserveFacilityJdbcTemplate;
import com.example.myHome.myHomespring.repository.facility.ReserveFacilityRepository;
import com.example.myHome.myHomespring.repository.game.RedisMemberRepository;
import com.example.myHome.myHomespring.repository.game.RedisTemplateMemberRepository;
import com.example.myHome.myHomespring.repository.quant.*;
import com.example.myHome.myHomespring.service.MemberService;
import com.example.myHome.myHomespring.service.facility.ReserveFacilityService;
import com.example.myHome.myHomespring.service.game.RedisMemberService;
import com.example.myHome.myHomespring.service.quant.QuantStrategyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.sql.DataSource;


@Configuration
@EnableRedisRepositories
public class SpringConfig {

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

    private final DataSource dataSource;
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        //return new MemoryMemberRepository();
        return new JdbcTemplateMemberRepository(dataSource);
    }

    @Bean
    public ReserveFacilityService reserveFacilityService() {
        return new ReserveFacilityService(reserveFacilityRepository());
    }

    @Bean
    public ReserveFacilityRepository reserveFacilityRepository() {
        //return new ReserveFacilityMemoryRepository();
        return new ReserveFacilityJdbcTemplate(dataSource);
    }

    @Bean
    public QuantStrategyService quantStrategyService() {
        return new QuantStrategyService(quantStrategyRepository(), quantStrategyRedisRepository());
    }

    @Bean
    public QuantStrategyRepository quantStrategyRepository() {
        //return new QuantStrategyMemberRepository();
        return new QuantStrategyJdbcTemplate(dataSource);
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

    @Bean
    public QuantStrategyRedisRepository quantStrategyRedisRepository() {
        return new QuantStrategyRedisTemplateRepository();
    }
}
