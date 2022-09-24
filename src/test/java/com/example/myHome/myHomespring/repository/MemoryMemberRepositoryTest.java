package com.example.myHome.myHomespring.repository;

import com.example.myHome.myHomespring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Member member = new Member();
        member.setName("spring");

        Member member2 = new Member();
        member2.setName("spring1");

        //when
        memberRepository.save(member);
        memberRepository.save(member2);

        //then
        Member findMember = memberRepository.findById(member.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
    }

    @Test
    void findByName() {
        //given
        Member member = new Member();
        member.setName("spring");
        memberRepository.save(member);

        Member member2 = new Member();
        member2.setName("spring1");
        memberRepository.save(member2);

        //when
        Member springMember = memberRepository.findByName("spring").get();
        Member springMember1 = memberRepository.findByName("spring1").get();

        //then
        assertThat(springMember).isEqualTo(member);
        assertThat(springMember1).isEqualTo(member2);
    }

    @Test
    void findAll() {
        //given
        Member member = new Member();
        member.setName("spring");
        memberRepository.save(member);

        Member member2 = new Member();
        member2.setName("spring1");
        memberRepository.save(member2);

        Member member3 = new Member();
        member3.setName("spring2");
        memberRepository.save(member3);

        Member member4 = new Member();
        member4.setName("spring3");
        memberRepository.save(member4);

        //when
        memberRepository.findAll();

        //then
        assertThat(memberRepository.findAll().size()).isEqualTo(4);
    }
}