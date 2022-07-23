package com.example.myHome.myHomespring;

import com.example.myHome.myHomespring.repository.MemberRepository;
import com.example.myHome.myHomespring.repository.MemoryMemberRepository;
import com.example.myHome.myHomespring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
        //return new RedisMemberRepository();
    }

}
