package com.example.myHome.myHomespring.repository.quant;

import com.example.myHome.myHomespring.domain.quant.QuantStrategyMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


class QuantStrategyMemberRepositoryTest {
    QuantStrategyMemberRepository quantStrategyMemberRepository = new QuantStrategyMemberRepository();

    @AfterEach
    public void afterEach() {
        quantStrategyMemberRepository.clearStore();
    }

    void debugSave(String strategyTitle) {
        // given

        // 오늘 날짜 받아오는 코드
        String userName = "yoda.kim@nklcb.com";
        Calendar calendar = Calendar.getInstance();
        String saveTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
        String companyInfo = "500/x/x/x/12.05/9.44/8.69/6.28/34.12/28,856.02/3,166/17.63/37,528/1.49/2.54/44.73";
        QuantStrategyMember quantStrategyMember = new QuantStrategyMember(userName, saveTime, strategyTitle, companyInfo);

        // when
        quantStrategyMemberRepository.save(quantStrategyMember);
    }

    @Test
    void save() {
        // given

        // 오늘 날짜 받아오는 코드
        String userName = "yoda.kim@nklcb.com";
        Calendar calendar = Calendar.getInstance();
        String saveTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
        String strategyTitle = "strategyTitle";
        String companyInfo = "500/x/x/x/12.05/9.44/8.69/6.28/34.12/28,856.02/3,166/17.63/37,528/1.49/2.54/44.73";

        System.out.println("saveTime: " + saveTime);
        QuantStrategyMember quantStrategyMember = new QuantStrategyMember(userName, saveTime, strategyTitle, companyInfo);

        // when
        quantStrategyMemberRepository.save(quantStrategyMember);
        QuantStrategyMember quantStrategyMember1 = quantStrategyMemberRepository.findById(quantStrategyMember.getId()).get();

        //then
        assertThat(quantStrategyMember1).isEqualTo(quantStrategyMember);
    }

    @Test
    void findByTitle() {
        // given
        String userName = "yoda.kim@nklcb.com";
        Calendar calendar = Calendar.getInstance();
        String saveTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
        String strategyTitle = "strategyTitle";
        String companyInfo = "500/x/x/x/12.05/9.44/8.69/6.28/34.12/28,856.02/3,166/17.63/37,528/1.49/2.54/44.73";
        QuantStrategyMember quantStrategyMember = new QuantStrategyMember(userName, saveTime, strategyTitle, companyInfo);

        String strategyTitle2 = "strategyTitle2";
        String companyInfo2 = "x/x/x/50/12.05/9.44/8.69/6.28/34.12/28,856.02/3,166/17.63/37,528/1.49/2.54/44.73";
        QuantStrategyMember quantStrategyMember2 = new QuantStrategyMember(userName, saveTime, strategyTitle2, companyInfo2);

        // when
        quantStrategyMemberRepository.save(quantStrategyMember);
        quantStrategyMemberRepository.save(quantStrategyMember2);

        String findTitle = "strategyTitle";
        String findTitle2 = "strategyTitle2";

        //when
        QuantStrategyMember quantStrategyMember1 = quantStrategyMemberRepository.findByTitle(findTitle).get();
        QuantStrategyMember quantStrategyMember3 = quantStrategyMemberRepository.findByTitle(findTitle2).get();

        //then
        assertThat(quantStrategyMember1.getStrategyTitle()).isEqualTo(findTitle);
        assertThat(quantStrategyMember3.getStrategyTitle()).isEqualTo(findTitle2);
    }

    @Test
    void delStrategy() {
        // given
        String strategyTitle1 = "strategyTitle1";
        String strategyTitle2 = "strategyTitle2";
        String strategyTitle3 = "strategyTitle3";
        String strategyTitle4 = "strategyTitle4";
        debugSave(strategyTitle1);
        debugSave(strategyTitle2);
        debugSave(strategyTitle3);
        debugSave(strategyTitle4);

        // when
        assertThat(quantStrategyMemberRepository.findAll().size()).isEqualTo(4);
        quantStrategyMemberRepository.delStrategy(strategyTitle1);
        assertThat(quantStrategyMemberRepository.findAll().size()).isEqualTo(3);
    }
}