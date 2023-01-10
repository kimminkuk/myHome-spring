package com.example.myHome.myHomespring.service.quant;

import com.example.myHome.myHomespring.domain.quant.CompanyCodeMember;
import com.example.myHome.myHomespring.domain.quant.CompanyNameMember;
import com.example.myHome.myHomespring.domain.quant.QuantStrategyMember;
import com.example.myHome.myHomespring.repository.quant.QuantStrategyRedisRepository;
import com.example.myHome.myHomespring.repository.quant.QuantStrategyRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class QuantStrategyService {
    private final QuantStrategyRepository quantStrategyRepository;
    private final QuantStrategyRedisRepository quantStrategyRedisRepository;

    public QuantStrategyService(QuantStrategyRepository quantStrategyRepository, QuantStrategyRedisRepository quantStrategyRedisRepository) {
        this.quantStrategyRepository = quantStrategyRepository;
        this.quantStrategyRedisRepository = quantStrategyRedisRepository;
    }


    /**
     *   전략 저장
     */
    public Long strategySave(QuantStrategyMember quantStrategyMember) {
        validDuplicateStrategy(quantStrategyMember);
        quantStrategyRepository.save(quantStrategyMember);
        return quantStrategyMember.getId();
    }

    /**
     *   전략 찾기
     */
    public Optional<QuantStrategyMember> findStrategy(String strategyTitle) {
        return quantStrategyRepository.findByTitle(strategyTitle);
    }

    /**
     *   전략 전체 조회
     */
    public List<QuantStrategyMember> findStrategies() {
        return quantStrategyRepository.findAll();
    }

    /**
     *  전략 삭제
     */
    public Optional<QuantStrategyMember> delStrategy(String delStrategyTitle) {
        return quantStrategyRepository.delStrategy(delStrategyTitle);
    }

    /**
     *    네이버 금융 파싱
     *    1. ZSet 저장 ( 백업 용 )
     *    2. Text 저장 ( 사용 용 )
     */
    public void naverFinanceParsing() {
        String writeFilePathAndTitle = "src/main/text/" + getCurDate() + ".txt";

        List<String> companyInfoDataList = getAllCompanyInfo();

        //1. ZSet 저장 ( 백업 용 )
        quantStrategyRedisRepository.saveVer2(companyInfoDataList, getCurDate());
        //2. Text 저장 ( 사용 용 )
        parsingResultFileWrite(companyInfoDataList, writeFilePathAndTitle);
    }

    public List<String> getAllCompanyInfo() {
        String writeFilePathAndTitle = "src/main/text/" + getCurDate() + ".txt";

        CompanyCodeMember companyCodeMember = new CompanyCodeMember();
        CompanyNameMember companyNameMember = new CompanyNameMember();
        String[] companyCode = companyCodeMember.getCompanyCode();
        String[] companyName = companyNameMember.getCompanyName();

        int companyCodeLength = companyCode.length;
        List<String> companyInfoDataList = new ArrayList<>();
        Integer[] companyPerformanceArr = getCompanyPerformanceArr();
        String curPerformancePos = String.valueOf(companyPerformanceArr[2]);

        for (int companyNumber = 0; companyNumber < companyCodeLength; companyNumber++) {
            String urlMarketCap = "https://finance.naver.com/item/sise.naver?code=" + companyCode[companyNumber];

            // Ver1
            // Ver2가 실패해서 ROA는 직접 계산을 하겠습니다.
            // 우선 파싱을 뚫겠습니다.
            String urlCompanyInfo = "https://finance.naver.com/item/main.nhn?code=" + companyCode[companyNumber];

            // Ver2
            // full XPATH 가져왔는데 실패
            // Find out what the id of div dynamically changes when html parsing (실패했습니다. 모르겠습니다. 파이썬으로 나중에 다시 해보겠습니다.)
            // String urlCompanyInfo = "https://finance.naver.com/item/coinfo.naver?code=" + companyCode[companyNumber] + "&target=finsum_more";

            String oneCompanyMarketCap = "";
            String oneCompanySalesCap = "";
            String oneCompanyOperationProfitRatio = "";
            String oneCompanyNetProfitRation = "";
            String oneCompanyRoe = "";
            String oneCompanyRoa = "";
            String oneCompanyDebtRatio = "";
            String oneCompanyCapRetentionRate = "";
            String oneCompanyEps = "";
            String oneCompanyPer = "";
            String oneCompanyBps = "";
            String oneCompanyPbr = "";
            String oneCompanyCashDps = "";
            String oneCompanyDividendYield = "";
            try {
                Document document = Jsoup.connect(urlMarketCap).get();
                oneCompanyMarketCap = getOneCompanyMarketCap(document);
            } catch (Exception e) {
                System.out.println("[DEBUG] marketCap: " + "없음");
            }
            try {
                Document document = Jsoup.connect(urlCompanyInfo).get();
                oneCompanySalesCap = getOneCompanySalesCap(document, curPerformancePos);
                oneCompanyOperationProfitRatio = getOneCompanyOperationProfitRatio(document, curPerformancePos);
                oneCompanyNetProfitRation = getOneCompanyNetProfitRation(document, curPerformancePos);
                oneCompanyRoe = getOneCompanyRoe(document, curPerformancePos);
                //oneCompanyRoa = getOneCompanyRoa(document, curPerformancePos);
                oneCompanyRoa = "0";
                oneCompanyDebtRatio = getOneCompanyDebtRatio(document, curPerformancePos);
                oneCompanyCapRetentionRate = getOneCompanyCapRetentionRate(document, curPerformancePos);
                oneCompanyEps = getOneCompanyEps(document, curPerformancePos);
                oneCompanyPer = getOneCompanyPer(document, curPerformancePos);
                oneCompanyBps = getOneCompanyBps(document, curPerformancePos);
                oneCompanyPbr = getOneCompanyPbr(document, curPerformancePos);
                oneCompanyCashDps = getOneCompanyCashDps(document, curPerformancePos);
                oneCompanyDividendYield = getOneCompanyDividendYield(document, curPerformancePos);
            } catch (Exception e) {
                System.out.println("[DEBUG] companyInfo[Fail]: " + urlCompanyInfo);
                System.out.println("e = " + e);
                System.out.println("oneCompanySalesCap = " + oneCompanySalesCap);
            }

            String exData = "1/2/3/4/" + oneCompanyMarketCap + "/" + oneCompanyOperationProfitRatio + "/" + oneCompanyNetProfitRation + "/" +
                            oneCompanyRoe + "/" + oneCompanyRoa + "/" + oneCompanyDebtRatio + "/" + oneCompanyCapRetentionRate + "/" +
                            oneCompanyEps + "/" + oneCompanyPer + "/" + oneCompanyBps + "/" + oneCompanyPbr + "/" + oneCompanyCashDps + "/" +
                            oneCompanyDividendYield + "/" + oneCompanySalesCap;
            companyInfoDataList.add(companyName[companyNumber] + "@" + exData);

            //Jsoup pull XPath get
            // /html/body/div/form/div[1]/div/div[2]/div[3]/div/div/div[14]/table[2]/tbody/tr[1]/td[1]/span


        }

        return companyInfoDataList;
    }

    public static void parsingResultZSetSave(List<String> companyInfoDataList, String ZSetTitle) {
        // ZSet coding
        // Ranking Page 참고
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

    private String getTableId(Document document) {
        ///html/body/div/form/div[1]/div/div[2]/div[3]/div/div/div[14]
        Elements select = document.select("/html/body/div/form/div[1]/div/div[2]/div[3]/div/div/div[14]");
        System.out.println("select = " + select);
        System.out.println("select.first() = " + select.first());
        System.out.println("select.text() = " + select.text());
        System.out.println("select.attr(id) = " + select.attr("id"));
        System.out.println("select.first().attr(id) = " + select.first().attr("id"));
        return select.first().attr("id");
    }

    private String getOneCompanyMarketCap(Document document) {
        Elements elements = document.select("#_sise_market_sum");
        String marketCap = String.valueOf(companyFigureCnvToFloat(elements.text().trim()));
        return marketCap;
    }

    private String getOneCompanySalesCap(Document document, String companyPerformancePos) {
        Elements select = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(1) > td:nth-child(" +
                companyPerformancePos + ")");
        if ( select == null ) {
            return "0";
        }
        String resultText = select.text().trim();
        String salesCap = textContentBlankCheck(resultText) == false ? "0" : resultText;
        return salesCap;
    }

    private String getOneCompanyOperationProfitRatio(Document document, String companyPerformancePos) {
        Elements select = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(4) > td:nth-child(" +
                companyPerformancePos + ")");
        if ( select == null ) {
            return "0";
        }
        String resultText = select.text().trim();
        String operationProfitRatio = textContentBlankCheck(resultText) == false ? "0" : resultText;
        return operationProfitRatio;
    }

    private String getOneCompanyNetProfitRation(Document document, String companyPerformancePos) {
        Elements select = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(5) > td:nth-child(" +
                companyPerformancePos + ")");
        if ( select == null ) {
            return "0";
        }
        String resultText = select.text().trim();
        String netProfitRation = textContentBlankCheck(resultText) == false ? "0" : resultText;
        return netProfitRation;
    }

    private String getOneCompanyRoe(Document document, String companyPerformancePos) {
        // ROE = 당기순이익 / 자기자본
        // ex) 0.1392  = 399,074 / x
        // x = 399,074 / 13.92
        // x = 2866910
        Elements select = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(6) > td:nth-child(" +
                companyPerformancePos + ")");
        if ( select == null ) {
            return "0";
        }
        String resultText = select.text().trim();
        String roe = textContentBlankCheck(resultText) == false ? "0" : resultText;
        return roe;
    }

    private String getOneCompanyRoa(Document document, String companyPerformancePos) {
        // ROA = 당기순이익 / 총자산(자기 자본 + 부채)
        // ROA = 당기순이익 / x*(1+부채비율)
        Elements select = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(6) > td:nth-child(" +
                companyPerformancePos + ")");
        if ( select == null ) {
            return "0";
        }
        String resultText = select.text().trim();
        String roa = textContentBlankCheck(resultText) == false ? "0" : resultText;
        return roa;
    }

    private String getOneCompanyDebtRatio(Document document, String companyPerformancePos) {
        Elements select = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(7) > td:nth-child(" +
                companyPerformancePos + ")");
        if ( select == null ) {
            return "0";
        }
        String resultText = select.text().trim();
        String debtRatio = textContentBlankCheck(resultText) == false ? "0" : resultText;
        return debtRatio;
    }

    private String getOneCompanyCapRetentionRate(Document document, String companyPerformancePos) {
        Elements select = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(9) > td:nth-child(" +
                companyPerformancePos + ")");
        if ( select == null ) {
            return "0";
        }
        String resultText = select.text().trim();
        String capRetentionRate = textContentBlankCheck(resultText) == false ? "0" : resultText;
        return capRetentionRate;
    }

    private String getOneCompanyEps(Document document, String companyPerformancePos) {
        Elements select = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(10) > td:nth-child(" +
                companyPerformancePos + ")");
        if ( select == null ) {
            return "0";
        }
        String resultText = select.text().trim();
        String eps = textContentBlankCheck(resultText) == false ? "0" : resultText;
        return eps;
    }

    private String getOneCompanyPer(Document document, String companyPerformancePos) {
        Elements select = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(11) > td:nth-child(" +
                companyPerformancePos + ")");
        if ( select == null ) {
            return "0";
        }
        String resultText = select.text().trim();
        String per = textContentBlankCheck(resultText) == false ? "0" : resultText;
        return per;
    }

    private String getOneCompanyBps(Document document, String companyPerformancePos) {
        Elements select = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(12) > td:nth-child(" +
                companyPerformancePos + ")");
        if ( select == null ) {
            return "0";
        }
        String resultText = select.text().trim();
        String bps = textContentBlankCheck(resultText) == false ? "0" : resultText;
        return bps;
    }

    private String getOneCompanyPbr(Document document, String companyPerformancePos) {
        Elements select = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(13) > td:nth-child(" +
                companyPerformancePos + ")");
        if ( select == null ) {
            return "0";
        }
        String resultText = select.text().trim();
        String pbr = textContentBlankCheck(resultText) == false ? "0" : resultText;
        return pbr;
    }

    private String getOneCompanyCashDps(Document document, String companyPerformancePos) {
        Elements select = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(14) > td:nth-child(" +
                companyPerformancePos + ")");
        if ( select == null ) {
            return "0";
        }
        String resultText = select.text().trim();
        String cashDps = textContentBlankCheck(resultText) == false ? "0" : resultText;
        return cashDps;
    }

    private String getOneCompanyDividendYield(Document document, String companyPerformancePos) {
        Elements select = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(15) > td:nth-child(" +
                companyPerformancePos + ")");
        if ( select == null ) {
            return "0";
        }
        String resultText = select.text().trim();
        String dividendYield = textContentBlankCheck(resultText) == false ? "0" : resultText;
        return dividendYield;
    }

    public Float companyFigureCnvToFloat(String companyFigure) {
        int temp = 99999;
        if (companyFigure.isEmpty()) {
            return Float.valueOf(temp);
        }
        companyFigure = companyFigure.replaceAll(",", "");
        if (companyFigure.compareTo("N/A") == 0) {
            return Float.valueOf(temp);
        } else {
            if ( companyFigure.charAt(0) == '-' ) {
                return Float.valueOf(temp);
            } else if (companyFigure.charAt(0) == '∞') {
                return Float.valueOf(temp);
            } else if (companyFigure.charAt(0) == '.') {
                companyFigure = '0' + companyFigure;
                return Float.valueOf(companyFigure);
            } else {
                return Float.valueOf(companyFigure);
            }
        }
    }

    public String getCurDate() {
        Calendar calendar = Calendar.getInstance();
        String saveTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
        return saveTime;
    }

    private void validDuplicateStrategy(QuantStrategyMember quantStrategyMember) {
        quantStrategyRepository.findByTitle(quantStrategyMember.getStrategyTitle())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 전략입니다.");
                });
    }

    private Boolean textContentBlankCheck(String textContent) {
        if ( textContent.length() == 0 || textContent.compareTo("-") == 0) {
            return false;
        } else {
            return true;
        }


    }



    /**
     *    실적 위치 유지보수 용
     */
    private Integer[] getCompanyPerformanceArr() {
        Integer[] companyPerformanceArr = new Integer[10];
// 2, // 2019.12 (연간 실적)
// 3, // 2020.12 (연간 실적)
// 4, // 2021.12 (연간 실적)
// 5, // 2022.12 (연간 실적 예측)
//
// 6, // 2021.09 (분기 실적)
// 7, // 2021.12 (분기 실적)
// 8, // 2022.03 (분기 실적)
// 9, // 2022.06 (분기 실적)
// 10,// 2022.09 (분기 실적)
// 11 // 2022.12 (분기 실적 예측)
        for (int i = 0; i < companyPerformanceArr.length; i++) {
            companyPerformanceArr[i] = i + 1;
        }
        return companyPerformanceArr;
    }
}
