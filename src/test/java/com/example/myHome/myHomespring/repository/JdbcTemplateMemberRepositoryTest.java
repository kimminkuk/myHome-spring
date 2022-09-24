package com.example.myHome.myHomespring.repository;

import com.example.myHome.myHomespring.domain.Member;
import com.example.myHome.myHomespring.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JdbcTemplateMemberRepositoryTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("요다");
        member.setValue("요다테스트");

        //when
        Long joinId = memberService.join(member);
        System.out.println("join = " + joinId);

        //then
        Member findMember = memberService.findOne(joinId).get();
        assertEquals(member.getName(), findMember.getName());
    }

}