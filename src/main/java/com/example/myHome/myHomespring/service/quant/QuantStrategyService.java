package com.example.myHome.myHomespring.service.quant;

import com.example.myHome.myHomespring.domain.quant.CompanyCodeMember;
import com.example.myHome.myHomespring.domain.quant.CompanyNameMember;
import com.example.myHome.myHomespring.domain.quant.QuantStrategyInfoMember;
import com.example.myHome.myHomespring.domain.quant.QuantStrategyMember;
import com.example.myHome.myHomespring.repository.quant.QuantStrategyRedisRepository;
import com.example.myHome.myHomespring.repository.quant.QuantStrategyRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.*;
import java.util.function.Function;

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
     *    파싱 데이터 불러오기
     *    1. Text 데이터 읽어오기
     */
    public List<String> getParsingData( String strategyInfo, String parsingDate) {

        //  2023-1-11.txt 데이터 읽어오기
        //  src/main/text/2023-1-11.txt
        //System.out.println("strategyInfo = " + strategyInfo);

        // 이거 수정해야하네 (최종 날짜로 가져와야합니다.)
        //String path = "src/main/text/2023-1-11.txt";
        String path = "src/main/text/" + parsingDate + ".txt";

        // 16개로 나눠야한다.
        // 이걸 매번 실행해야한다고?????/
        // 2500줄을 * 16개의 메모리가 매번 생성된다? 이게 맞나?

        // 그래서, 조건들을 추가하면 흠...
        // 최소 n*log(n) * 16 (n => 2500)
        // 흠.. 속도가 2초 이상걸리면 안되는데...
        // 일단 해보자.
        List<String> lines = new ArrayList<>();

        List<QuantStrategyInfoMember> quantStrategyInfoMembers = new ArrayList<>();

        //16 인벤티지랩@1/2/3/4/794.0/-513.38/-497.99/-42.92/-35.841335/19.75/568.07/-1,376/0/3,713/0/0/0/19
        try {
            lines = Files.readAllLines(Path.of(path));
            for (String line : lines) {
                String[] splitStep1 = line.split("@");
                String[] splitStep2 = splitStep1[1].split("/");
                String companyName = splitStep1[0].split(" ")[1];

                String capitalRankingHigh = splitStep2[0];
                String capitalRankingLow = splitStep2[1];
                String capitalPercentHigh = splitStep2[2];
                String capitalPercentLow = splitStep2[3];
                String marketCapitalization = splitStep2[4];
                String operatingProfitRatio = splitStep2[5];
                String netProfitRatio = splitStep2[6];
                String roe = splitStep2[7];
                String roa = splitStep2[8];
                String debtRatio = splitStep2[9];
                String capitalRetentionRate = splitStep2[10];
                String eps = splitStep2[11];
                String per = splitStep2[12];
                String bps = splitStep2[13];
                String pbr = splitStep2[14];
                String cashDps = splitStep2[15];
                String dividendYield = splitStep2[16];
                String sales = splitStep2[17];
                quantStrategyInfoMembers.add(new QuantStrategyInfoMember(
                        companyName, capitalRankingHigh, capitalRankingLow, capitalPercentHigh,
                        capitalPercentLow, marketCapitalization, operatingProfitRatio, netProfitRatio,
                        roe, roa, debtRatio, capitalRetentionRate,
                        eps, per, bps, pbr, cashDps, dividendYield, sales)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Step 1 Sort of Info 2~6
        // 2. 시가총액 상위 순위 3. 시가총액 하위 순위 4. 시가총액 상위 퍼센트 5. 시가총액 하위 퍼센트 6. 시가총액
        //QuantSortStepCompanyInfo2_6(quantStrategyInfoMembers, 6);

        // 몇 가지 조건을 추가해서 테스트한다.
        // 테스트용으로 그냥 100개까지만 리턴한다.

        // 기본적으로 Sort는 한번 하자. (시가총액으로)
        quantStrategyInfoMembers.sort(Comparator.comparing(QuantStrategyInfoMember::getMarketCapitalizationToFloat));

        // Step 1 Sort 분류
        // x면 서치 안한다.
        // x~ => x이상
        // ~x => x이하
        // x~y => x이상 y이하
        List<String> result = QuanySortVer1(quantStrategyInfoMembers, strategyInfo);

        //List<String> result = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            System.out.println("companyName = " + quantStrategyInfoMembers.get(i).getCompanyName() + " marketCap = " + quantStrategyInfoMembers.get(i).getMarketCapitalization());
//            result.add(quantStrategyInfoMembers.get(i).getCompanyName());
//
//        }
        return result;
    }

    private List<String> QuanySortVer1(List<QuantStrategyInfoMember> quantStrategyInfoMembers, String strategyInfo) {
        List<String> quantMemberSortResult = new ArrayList<>();
        String[] split = strategyInfo.split("/");
        String capitalRankingHigh = split[0];
        String capitalRankingLow = split[1];
        String capitalPercentHigh = split[2];
        String capitalPercentLow = split[3];
        String marketCapitalization = split[4];
        String operatingProfitRatio = split[5];
        String netProfitRatio = split[6];
        String roe = split[7];
        String roa = split[8];
        String debtRatio = split[9];
        String capitalRetentionRate = split[10];
        String eps = split[11];
        String per = split[12];
        String bps = split[13];
        String pbr = split[14];
        String cashDps = split[15];
        String dividendYield = split[16];
        String sales = split[17];

        // 그리고 시가총액으로 솔트는 한번 해줘야한다. (이건 그냥 내가 파이썬에서 해줄까?.. 근데 매번 파싱해오는거니깐 어려워 그냥 여기서 정렬 가볍게 하자)
        // ascending order ( 시작시, 무조건 한번 )
        quantStrategyInfoMembers.sort(Comparator.comparing(QuantStrategyInfoMember::getMarketCapitalizationToFloat));

        // 시가총액 상위 순위 비교
        quantResultRankingHigh(quantStrategyInfoMembers, capitalRankingHigh);

        // 시가총액 상위 퍼센트
        quantResultPercentHigh(quantStrategyInfoMembers, capitalPercentHigh);

        // 시가총액 비교
        QuantResultSort(quantStrategyInfoMembers, marketCapitalization, QuantStrategyInfoMember::getMarketCapitalizationToFloat);

        // 영업 이익률 비교
        QuantResultSort(quantStrategyInfoMembers, operatingProfitRatio, QuantStrategyInfoMember::getOperatingProfitRatioToFloat);

        // 순 이익률 비교
        QuantResultSort(quantStrategyInfoMembers, netProfitRatio, QuantStrategyInfoMember::getNetProfitRationToFloat);

        // ROE 비교
        QuantResultSort(quantStrategyInfoMembers, roe, QuantStrategyInfoMember::getRoeToFloat);

        // ROA 비교
        QuantResultSort(quantStrategyInfoMembers, roa, QuantStrategyInfoMember::getRoaToFloat);

        // 부채비율 비교
        QuantResultSort(quantStrategyInfoMembers, debtRatio, QuantStrategyInfoMember::getDebtRatioToFloat);

        // 유보율 비교
        QuantResultSort(quantStrategyInfoMembers, capitalRetentionRate, QuantStrategyInfoMember::getCapitalRetentionRateToFloat);

        // EPS 비교
        QuantResultSort(quantStrategyInfoMembers, eps, QuantStrategyInfoMember::getEpsToFloat);

        // PER 비교
        QuantResultSort(quantStrategyInfoMembers, per, QuantStrategyInfoMember::getPerToFloat);

        // BPS 비교
        QuantResultSort(quantStrategyInfoMembers, bps, QuantStrategyInfoMember::getBpsToFloat);

        // PBR 비교
        QuantResultSort(quantStrategyInfoMembers, pbr, QuantStrategyInfoMember::getPbrToFloat);

        // 주당배당금 비교
        QuantResultSort(quantStrategyInfoMembers, cashDps, QuantStrategyInfoMember::getCashDpsToFloat);

        // 시가배당률 비교
        QuantResultSort(quantStrategyInfoMembers, dividendYield, QuantStrategyInfoMember::getDividendYieldToFloat);

        // 매출액 비교
        QuantResultSort(quantStrategyInfoMembers, sales, QuantStrategyInfoMember::getSalesToFloat);


        for (int i = 0; i < quantStrategyInfoMembers.size(); i++) {
            quantMemberSortResult.add(quantStrategyInfoMembers.get(i).getCompanyName());
            System.out.println("quantStrategyInfoMembers marketCap = " + quantStrategyInfoMembers.get(i).getMarketCapitalizationToFloat());
        }
        return quantMemberSortResult;
    }

    private static void QuantResultSort(List<QuantStrategyInfoMember> quantStrategyInfoMembers, String infoData, Function<QuantStrategyInfoMember, Float> getMember) {
        // case 1)  10~
        // case 2)  10~100
        // case 3)  ~100
        // case 4)  10
        if ( !infoData.equals("x") ) {
            if ( infoData.contains("~") ) {
                String[] range = infoData.split("~");
                if ( infoData.indexOf("~") == 0) {
                    // case 3)  ~100
                    quantStrategyInfoMembers.removeIf(quantStrategyInfoMember -> getMember.apply(quantStrategyInfoMember) > Float.parseFloat(infoData.substring(1)));
                } else {
                    String[] step1 = infoData.split("~");
                    if ( step1.length == 2 ) {
                        // case 2)  10~100
                        quantStrategyInfoMembers.removeIf(quantStrategyInfoMember -> getMember.apply(quantStrategyInfoMember) < Float.parseFloat(step1[0]) || getMember.apply(quantStrategyInfoMember) > Float.parseFloat(step1[1]));
                    } else {
                        // case 1)  10~
                        quantStrategyInfoMembers.removeIf(quantStrategyInfoMember -> getMember.apply(quantStrategyInfoMember) < Float.parseFloat(step1[0]));
                    }
                }
            } else {
                // case 4)  10
                quantStrategyInfoMembers.removeIf(quantStrategyInfoMember -> getMember.apply(quantStrategyInfoMember) < Float.parseFloat(infoData));
            }
        }
    }

    private static void quantResultPercentHigh(List<QuantStrategyInfoMember> quantStrategyInfoMembers, String percentHigh) {
        // 10~20 => QuantStrategyInfoMember의 10%~20% 사이의 회사들 get해오기
        // case 1)  10~
        // case 2)  10~20
        // case 3)  ~20
        // case 4)  10

        if ( !percentHigh.equals("x") ) {
            int infoPercent = Math.round(quantStrategyInfoMembers.size() / 100);
            // Ver1
            // List<QuantStrategyInfoMember> result = quantStrategyInfoMembers.sublist(0, infoPercent * Integer.parseInt(capitalRankingHigh));
            // Ver2
//            int subListCount = infoPercent * Integer.parseInt(percentHigh);
//            int count = 0;
//            List<QuantStrategyInfoMember> result = new ArrayList<>();
//            for (int i = 0; i < quantStrategyInfoMembers.size(); i++) {
//                if ( count > subListCount ) {
//                    break;
//                }
//                result.add(quantStrategyInfoMembers.get(i));
//                count++;
//            }

            //Ver2 람다식으로
            // 아 잠깐만.. 상위니깐.. 1 ~ 2500 개 중에, 상위 20% => 2000 ~ 2500개지
            if ( percentHigh.contains("~") ) {
                // case 3) ~15% => 85% ~ 100%
                if ( percentHigh.indexOf("~") == 0) {
                    quantStrategyInfoMembers.removeIf(quantStrategyInfoMember -> quantStrategyInfoMembers.indexOf(quantStrategyInfoMember) < infoPercent * (100 - Integer.parseInt(percentHigh.substring(1))));
                } else {
                    String[] step1 = percentHigh.split("~");
                    if ( step1.length == 2 ) {
                        // case 2) 5 ~ 15% => indexOf로 95~85% 사이의 회사들 get해오기
                        int notMoreValue = infoPercent * (100 - Integer.parseInt(step1[0]));
                        int moreValue = infoPercent * (100 - Integer.parseInt(step1[1]));
                        quantStrategyInfoMembers.removeIf(quantStrategyInfoMember -> quantStrategyInfoMembers.indexOf(quantStrategyInfoMember) < moreValue || quantStrategyInfoMembers.indexOf(quantStrategyInfoMember) > notMoreValue );
                    } else {
                        // case 1) 10~ => 하위 10 퍼센트 이하 제거 (90% 아래는 전부 제거)
                        quantStrategyInfoMembers.removeIf(quantStrategyInfoMember -> quantStrategyInfoMembers.indexOf(quantStrategyInfoMember) < infoPercent * (100 - Integer.parseInt(step1[0])));
                    }
                }
            } else {
                // case 4) 10 => 10 퍼센트 이하 제거 (90% 아래는 전부 제거)
                quantStrategyInfoMembers.removeIf(quantStrategyInfoMember -> quantStrategyInfoMembers.indexOf(quantStrategyInfoMember) < infoPercent * (100 - Integer.parseInt(percentHigh)));
            }
        }
    }

    private static void quantResultRankingHigh(List<QuantStrategyInfoMember> quantStrategyInfoMembers, String rankingHigh) {
        // 시가총액 상위 순위
        // case 1)  1000~
        // case 2)  1000~2000
        // case 3)  ~2000
        // case 4)  1000
        if (!rankingHigh.equals("x")) {
            if ( rankingHigh.contains("~") ) {
                //case 3) ~2000 => 2000개 이하까지
                if ( rankingHigh.indexOf("~") == 0) {
                    quantStrategyInfoMembers.removeIf(quantStrategyInfoMember -> quantStrategyInfoMembers.indexOf(quantStrategyInfoMember) > Integer.parseInt(rankingHigh.substring(1)));
                } else {
                    String[] step1 = rankingHigh.split("~");
                    if ( step1.length == 2 ) {
                        // case 2) 1000~2000 => 1000~2000개까지
                        quantStrategyInfoMembers.removeIf(quantStrategyInfoMember -> quantStrategyInfoMembers.indexOf(quantStrategyInfoMember) < Integer.parseInt(step1[0]) || quantStrategyInfoMembers.indexOf(quantStrategyInfoMember) > Integer.parseInt(step1[1]));
                    } else {
                        // case 1) 1000~ => 1000개 이상
                        quantStrategyInfoMembers.removeIf(quantStrategyInfoMember -> quantStrategyInfoMembers.indexOf(quantStrategyInfoMember) < Integer.parseInt(step1[0]));
                    }
                }
            } else {
                // case 4) 1000 => 1000 이상부터..
                quantStrategyInfoMembers.removeIf(quantStrategyInfoMember -> quantStrategyInfoMembers.indexOf(quantStrategyInfoMember) < Integer.parseInt(rankingHigh));
            }
        }
    }

    /**
     *    네이버 금융 파싱
     *    1. ZSet 저장 ( 백업 용 )
     *    2. Text 저장 ( 사용 용 )
     */
    public void naverFinanceParsing() {
        String writeFilePathAndTitle = "src/main/text/" + getCurDate() + ".txt";

        List<String> companyInfoDataList = getAllCompanyInfo();
        List<String> companyDailyRateList = new ArrayList<>();
        //1. ZSet 저장 ( 백업 용 )
        quantStrategyRedisRepository.saveVer2(companyInfoDataList, getCurDate());
        //2. Text 저장 ( 사용 용 )
        parsingResultFileWrite(companyInfoDataList, writeFilePathAndTitle);

        //etc) 일별시세 추가 및 Text 저장
        try {
            getCompanyDailyRate(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                oneCompanyDebtRatio = getOneCompanyDebtRatio(document, curPerformancePos);
                oneCompanyRoa = getOneCompanyRoa(document, curPerformancePos, oneCompanyRoe, oneCompanyDebtRatio);
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
        }

        return companyInfoDataList;
    }

    /**
     *    1. 일별시세 만들기
     *    2. O(n) * O(n) * O(n) => O(n3)
     *    3. 이전 파일이 있으면, 이전 파일을 읽어서, 현재 파일과 비교해서, 새로운 데이터만 추가 ( 생각 중 )
     */
    public void getCompanyDailyRate(Boolean testOnOff) throws Exception{
        if (testOnOff == Boolean.FALSE) return;
        //1. DailyRate 파싱 뚫기

        //2. 각 Company loop 돌면서 데이터 생성 후, List에 저장

        //3. 리턴


        String writeFilePathAndTitleRate = "src/main/text/" + getCurDate() +"-DailyRate" + ".txt";
        String writeFilePathAndTitleDate = "src/main/text/" + getCurDate() +"-DailyDate" + ".txt";
        CompanyCodeMember companyCodeMember = new CompanyCodeMember();
        String[] companyCode = companyCodeMember.getCompanyCode();
        List<String> dailyRateList = new ArrayList<>();
        List<String> dailyDateList = new ArrayList<>();
        int dayMax = 20;
        String dailyRateAll = "";
        String dailyDateAll = "";
        int[] textTrIdx = {3, 4, 5, 6, 7, 11, 12, 13, 14, 15};

        for (int companyCount = 0; companyCount < companyCode.length; companyCount++) {
            for (int day = 1; day <= dayMax; day++) {
                String urlDailyRate2 = "https://finance.naver.com/item/sise_day.naver?code=" + companyCode[companyCount] + "&page=" + day;
                try {
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
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("[DEBUG] urlDailyRate2: " + urlDailyRate2);
                    System.out.println("e = " + e);
                }

                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("[DEBUG] urlDailyRate2: " + urlDailyRate2);
                    System.out.println("e = " + e);
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
        parsingResultFileWrite(dailyRateList, writeFilePathAndTitleRate);
        parsingResultFileWrite(dailyDateList, writeFilePathAndTitleDate);
        return;
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



    private String getOneCompanyRoa(Document document, String companyPerformancePos, String Roe, String DebtRatio) {
        // ROA = 당기순이익 / 총자산(자기 자본 + 부채)
        // ROA = 당기순이익 / 자기자본 * (1+부채비율)
        Elements select = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(3) > td:nth-child(" +
                companyPerformancePos + ")");

        //1. 당기 순이익 획득 ( select.text.trim() )
        String resultText = select.text().trim();
        resultText = textContentBlankCheck(resultText) == false ? "0" : resultText;
        Float netProfit =  companyFigureCnvToFloatVer2(resultText);
        final float EPSILON = 1e-15f;
        if ( select == null || Roe.equals("0") || Math.abs(netProfit) < EPSILON ) {
            return "0";
        }

        //2. 자기 자본 계산 = 당기 순이익 / ROE
        Float equityCapital = netProfit / companyFigureCnvToFloatVer2(Roe);

        //3. ROA 계산 ( 1. 당기 순이익 / 2. 자기 자본 * (1+부채비율) )
        Float debtRatio = companyFigureCnvToFloat(DebtRatio) / 100;
        String roa = String.valueOf(netProfit / (equityCapital * (1 + debtRatio)));

        // 삼성전자 Case
        // 1. 당기 순이익 = 399,074
        // 2. 자기 자본 = 399,074 / 13.92 = 28669
        // 3. ROA = 399,074 / 28669 * (1+0.3992) = 0.14
        return roa;
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

    public Float companyFigureCnvToFloatVer2(String companyFigure) {
        int temp = 99999;
        if (companyFigure.isEmpty()) {
            return Float.valueOf(temp);
        }
        companyFigure = companyFigure.replaceAll(",", "");
        if (companyFigure.compareTo("N/A") == 0) {
            return Float.valueOf(temp);
        } else {
            if (companyFigure.charAt(0) == '∞') {
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
            companyPerformanceArr[i] = i + 2;
        }
        return companyPerformanceArr;
    }
}
