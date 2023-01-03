package com.example.myHome.myHomespring.service.quant;

import com.example.myHome.myHomespring.domain.quant.QuantStrategyMember;
import com.example.myHome.myHomespring.repository.quant.QuantStrategyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QuantStrategyServiceTest {
    @Autowired
    QuantStrategyService quantStrategyService;

    @Autowired
    QuantStrategyRepository quantStrategyRepository;

    @Test
    public void 전략생성() throws Exception {
        //given
        int curStrategies = quantStrategyService.findStrategies().size();
        // 오늘 날짜 받아오는 코드
        String userName = "yoda.kim@nklcb.com";
        Calendar calendar = Calendar.getInstance();
        String saveTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
        String strategyTitle = "전략7";
        String companyInfo = "500/x/x/x/12.05/9.44/8.69/6.28/34.12/28,856.02/3,166/17.63/37,528/1.49/2.54/44.73";
        QuantStrategyMember quantStrategyMember = new QuantStrategyMember(userName, saveTime, strategyTitle, companyInfo);
        String strategyTitle2 = "전략8";
        String companyInfo2 = "x/x/10/x/12.05/9.44/8.69/6.28/34.12/28,856.02/3,166/17.63/37,528/1.49/2.54/12.73";
        QuantStrategyMember quantStrategyMember2 = new QuantStrategyMember(userName, saveTime, strategyTitle2, companyInfo2);
        String strategyTitle3 = "전략9";
        String companyInfo3 = "x/500/x/x/12.05/9.44/8.69/6.28/34.12/28,856.02/3,166/17.63/37,528/1.49/x/44.73";
        QuantStrategyMember quantStrategyMember3 = new QuantStrategyMember(userName, saveTime, strategyTitle3, companyInfo3);

        // when
        Long saveId = quantStrategyService.strategySave(quantStrategyMember);
        Long saveId2 = quantStrategyService.strategySave(quantStrategyMember2);
        Long saveId3 = quantStrategyService.strategySave(quantStrategyMember3);
        List<QuantStrategyMember> addStrategies = quantStrategyService.findStrategies();

        //then
        assertThat(addStrategies.size()).isEqualTo(3 + curStrategies);
    }

    @Test
    void 전략삭제() throws Exception {
        //given
        int curStrategies = quantStrategyService.findStrategies().size();
        String strategyTitle = "전략2";

        //when
        quantStrategyService.delStrategy(strategyTitle);
        List<QuantStrategyMember> strategies = quantStrategyService.findStrategies();

        //then
        assertThat(strategies.size()).isEqualTo(curStrategies - 1);
    }

    @Test
    void 전략_중복_검사() throws Exception {
        //given
        // 오늘 날짜 받아오는 코드
        String userName = "yoda.kim@nklcb.com";
        Calendar calendar = Calendar.getInstance();
        String saveTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
        String strategyTitle = "전략1";
        String companyInfo = "500/x/x/x/12.05/9.44/8.69/6.28/34.12/28,856.02/3,166/17.63/37,528/1.49/2.54/44.73";
        QuantStrategyMember quantStrategyMember = new QuantStrategyMember(userName, saveTime, strategyTitle, companyInfo);

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> quantStrategyService.strategySave(quantStrategyMember));

        //then
        org.junit.jupiter.api.Assertions.assertEquals(e.getMessage(), "이미 존재하는 전략입니다.");
    }
}