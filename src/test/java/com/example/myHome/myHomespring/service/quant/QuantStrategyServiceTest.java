package com.example.myHome.myHomespring.service.quant;

import com.example.myHome.myHomespring.domain.quant.CompanyCodeMember;
import com.example.myHome.myHomespring.domain.quant.CompanyNameMember;
import com.example.myHome.myHomespring.domain.quant.QuantStrategyMember;
import com.example.myHome.myHomespring.repository.quant.QuantStrategyRepository;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
    void 전략가져오기() throws Exception {
        String strategyTitle = "전략2";
        QuantStrategyMember quantStrategyMember = quantStrategyService.findStrategy(strategyTitle).get();
        assertThat(quantStrategyMember.getStrategyTitle()).isEqualTo(strategyTitle);
        System.out.println("quantStrategyMember.getStrategyInfo() = " + quantStrategyMember.getStrategyInfo());
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

    @Test
    void 전체_전략_조회() throws Exception {
        //given
        int curStrategies = quantStrategyService.findStrategies().size();

        //when
        List<QuantStrategyMember> strategies = quantStrategyService.findStrategies();

        //then
        assertThat(strategies.size()).isEqualTo(curStrategies);

        for (QuantStrategyMember strategy : strategies) {
            System.out.println("strategy.Title = " + strategy.getStrategyTitle() + "  strategy.Info = " + strategy.getStrategyInfo());
        }
    }

    @Test
    void 일별시세파싱() throws Exception {
        //given

        String writeFilePathAndTitleRate = "src/main/text/" + quantStrategyService.getCurDate() +"-DailyRate" + ".txt";
        String writeFilePathAndTitleDate = "src/main/text/" + quantStrategyService.getCurDate() +"-DailyDate" + ".txt";
        CompanyCodeMember companyCodeMember = new CompanyCodeMember();
        CompanyNameMember companyNameMember = new CompanyNameMember();
        String[] companyCode = companyCodeMember.getCompanyCode();
        String[] companyName = companyNameMember.getCompanyName();
        List<String> dailyRateList = new ArrayList<>();
        List<String> dailyDateList = new ArrayList<>();
        List<String> companyDailyRateResult = new ArrayList<>();
        List<String> companyDailyDateResult = new ArrayList<>();
        int dayMax = 20;
        //for (int companyCount = 0; companyCount < companyCode.length; companyCount++) {
        String[] testComapny = {"005930", "198940"};
        String dailyRateAll = "";
        String dailyDateAll = "";
        int[] textTrIdx = {3, 4, 5, 6, 7, 11, 12, 13, 14, 15};
        for (int companyCount = 0; companyCount < companyCode.length; companyCount++) {
        //for (int companyCount = 0; companyCount < 2; companyCount++) {
            List<String> companyDailyInfo = new ArrayList<>();
            for (int day = 1; day <= dayMax; day++) {
                String urlDailyRate2 = "https://finance.naver.com/item/sise_day.naver?code=" + companyCode[companyCount] + "&page=" + day;
                Document document = Jsoup.connect(urlDailyRate2).get();
                // 가장 최신 날짜 ( curDate의 날짜와 같다. )
                // 음 이거, 날짜도 비교가 조금 필요하겠다. 예를들어 2023.01.20 -> 2023.01.19 .... 이런식으로 이어지는것을 기대했지만,
                // 실제로는 2023.01.20 -> 2017.02.17 이런애들있음 (재상장이나, 기업분할, 새로 상장한 회사 등등.. 어떻게 처리할까??)
                // 해당 날짜에 맞는 값을 비교하면서 처리할까??
                // 우선 날짜도 가져오자
                for (int trIdx = 0; trIdx < textTrIdx.length; trIdx++) {
                    // tr:nth-child(3 ~ 7), tr:nth-child(11 ~ 15)
                    Elements dailyRate = document.select("body > table.type2 > tbody > tr:nth-child(" + textTrIdx[trIdx] + ") > td:nth-child(2) > span");
                    String dailyRateText = dailyRate.text().trim();
                    String curDateRateRst = textContentBlankCheck(dailyRateText) == false ? "0" : dailyRateText;

                    Elements dateSelect = document.select("body > table.type2 > tbody > tr:nth-child(" + textTrIdx[trIdx] + ") > td:nth-child(1) > span");
                    String dateText = dateSelect.text().trim();
                    String curDateRst = textContentBlankCheck(dateText) == false ? "0" : dateText;

                    if ( curDateRateRst.equals("0") || curDateRst.equals("0") ) {
                        dailyRateAll += curDateRateRst + "/";
                        dailyDateAll += curDateRst + "/";
                    } else {
                        dailyRateAll += curDateRateRst + "/";
                        dailyDateAll += curDateRst + "/";
                    }
                }
            }
            /**
                // 마지막 '/'제거하기 Ver 1
                if (dailyRateAll.endsWith("/")) {
                    dailyRateAll = dailyRateAll.substring(0, dailyRateAll.length() - 1);
                }
                if (dailyDateAll.endsWith("/")) {
                    dailyDateAll = dailyDateAll.substring(0, dailyDateAll.length() - 1);
                }
            **/
            // 마지막 '/' 제거하기 Ver2
            dailyRateAll = dailyRateAll.replaceAll("/$", "");
            dailyDateAll = dailyDateAll.replaceAll("/$", "");

            //dailyRateAll의 마지막 / 제거
            dailyRateList.add(dailyRateAll);
            dailyDateList.add(dailyDateAll);
            dailyDateAll = "";
            dailyRateAll = "";
        }

        //when
        parsingResultFileWrite(dailyRateList, writeFilePathAndTitleRate);
        parsingResultFileWrite(dailyDateList, writeFilePathAndTitleDate);
        //then
    }
    private Boolean textContentBlankCheck(String textContent) {
        if ( textContent.length() == 0 || textContent.compareTo("-") == 0) {
            return false;
        } else {
            return true;
        }
    }

    private static void parsingResultFileWrite(List<String> companyMarketCapTestList, String fileWritePath) {
        // src/main/text/test.txt 파일쓰기 코드
        try {
            FileWriter fw = new FileWriter(fileWritePath);
            int number = 0;
            for (String resultData : companyMarketCapTestList) {
                number++;
                fw.write( String.valueOf(number) +" "+ resultData);
                fw.write("\n");
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}