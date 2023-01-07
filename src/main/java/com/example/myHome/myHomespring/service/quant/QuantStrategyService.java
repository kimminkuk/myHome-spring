package com.example.myHome.myHomespring.service.quant;

import com.example.myHome.myHomespring.domain.quant.CompanyCodeMember;
import com.example.myHome.myHomespring.domain.quant.CompanyNameMember;
import com.example.myHome.myHomespring.domain.quant.QuantStrategyMember;
import com.example.myHome.myHomespring.repository.quant.QuantStrategyRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class QuantStrategyService {
    private final QuantStrategyRepository quantStrategyRepository;

    public QuantStrategyService(QuantStrategyRepository quantStrategyRepository) {
        this.quantStrategyRepository = quantStrategyRepository;
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
     *    1. Zset 저장 ( 백업 용 )
     *    2. Text 저장 ( 사용 용 )
     */
    public void naverFinanceParsing() {
        getAllCompanyMarketCap();
    }

    /**
     *    파싱한 데이터 불러오기
     *    1. Text에서 불러옵니다.
     */
    public void loadParsingData() {
        //
    }

    private void getAllCompanyMarketCap() {
        String writeFilePathAndTitle = "src/main/text/" + getCurDate() + ".txt";
        CompanyCodeMember companyCodeMember = new CompanyCodeMember();
        CompanyNameMember companyNameMember = new CompanyNameMember();
        String[] companyCode = companyCodeMember.getCompanyCode();
        String[] companyName = companyNameMember.getCompanyName();
        int companyCodeLength = companyCode.length;
        List<String> companyMarketCapTestList = new ArrayList<>();
        for (int i = 0; i < companyCodeLength; i++) {

            String oneCompanyMarketCap = getOneCompanyMarketCap(companyCode[i]);
            companyMarketCapTestList.add(companyName[i] + ":" + oneCompanyMarketCap);
        }
        //1. ZSet 저장 ( 백업 용 )
        parsingResultZSetSave(companyMarketCapTestList, getCurDate());
        //2. Text 저장 ( 사용 용 )
        parsingResultFileWrite(companyMarketCapTestList, writeFilePathAndTitle);
    }

    private static void parsingResultZSetSave(List<String> companyMarketCapTestList, String ZSetTitle) {
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

    private String getOneCompanyMarketCap(String companyCode) {
        String URL = "https://finance.naver.com/item/sise.naver?code=" + companyCode;
        String marketCap = "";
        try {
            Document document = Jsoup.connect(URL).get();
            Elements elements = document.select("#_sise_market_sum");
            marketCap = String.valueOf(companyFigureCnvToFloat(elements.text()));
        } catch (Exception e) {
            System.out.println("[DEBUG] marketCap: " + "없음");
        }

        return marketCap;
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
}
