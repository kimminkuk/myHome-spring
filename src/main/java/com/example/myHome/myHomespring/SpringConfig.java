package com.example.myHome.myHomespring;

import com.example.myHome.myHomespring.repository.*;
import com.example.myHome.myHomespring.service.MemberService;
import com.example.myHome.myHomespring.service.ReserveFacilityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import javax.sql.DataSource;


@Configuration
@EnableRedisRepositories
public class SpringConfig {
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

}
