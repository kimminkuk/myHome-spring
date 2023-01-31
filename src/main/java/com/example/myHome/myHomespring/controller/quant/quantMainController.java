package com.example.myHome.myHomespring.controller.quant;

import com.example.myHome.myHomespring.domain.quant.ParsingDateMember;
import com.example.myHome.myHomespring.domain.quant.QuantStrategyInfoMember;
import com.example.myHome.myHomespring.domain.quant.QuantStrategyMember;

import com.example.myHome.myHomespring.service.quant.QuantStrategyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class quantMainController {
    private final QuantStrategyService quantStrategyService;
    public quantMainController(QuantStrategyService quantStrategyService) {
        this.quantStrategyService = quantStrategyService;
    }

    @GetMapping("quant/quant-main")
    public String quantMain(Model model) {
        System.out.println("[DEBUG] quantMain START");

        quantPageDefaultSetting(model);

        System.out.println("[DEBUG] quantMain END");
        return "quant/quant-main";
    }

    @GetMapping("quant/load-strategies")
    public String loadStrategies(Model model) {
        System.out.println("[DEBUG] loadStrategies START");

        List<QuantStrategyMember> quantStrategyMembers = quantStrategyService.findStrategies();
        model.addAttribute("quantStrategyMembers", quantStrategyMembers);

        System.out.println("[DEBUG] loadStrategies END");
        return "quant/quant-main";
    }

    @GetMapping("quant/load-strategy")
    public String loadStrategy(Model model,
                               @RequestParam("strategyTitle") String strategyTitle) {
        System.out.println("[DEBUG] loadStrategy START + strategyTitle: " + strategyTitle);

        List<QuantStrategyMember> quantStrategyMembers = quantStrategyService.findStrategies();
        model.addAttribute("quantStrategyMembers", quantStrategyMembers);

        // findStrategy null 경우에 대한 예외처리 필요
        QuantStrategyMember quantStrategyMember = quantStrategyService.findStrategy(strategyTitle).get();
        if ( quantStrategyMember == null ) {
            System.out.println("[DEBUG] loadStrategy END + quantStrategyMember is null");
            return "quant/quant-main";
        }

        QuantStrategyInfoMember quantStrategyInfoMember = splitStrategy(quantStrategyMember.getStrategyInfo());
        model.addAttribute("strategyInfo", quantStrategyInfoMember);
        ParsingDateMember parsingDateMember = quantStrategyService.getLastParsingDate().get();
        model.addAttribute("parsingDateMember", parsingDateMember);
        model.addAttribute("quantStrategyMember", quantStrategyMember);


        System.out.println("[DEBUG] loadStrategy END");
        return "quant/quant-main";
    }

    @GetMapping("quant/save-strategy")
    public String saveStrategy(Model model,
                               @RequestParam("userName") String userName,
                               @RequestParam("strategyTitle") String strategyTitle,
                               @RequestParam("strategyInfo") String strategyInfo) {
        System.out.println("[DEBUG] saveStrategy START");
        QuantStrategyMember quantStrategyMember = new QuantStrategyMember();
        quantStrategyMember.setUserName(userName);
        quantStrategyMember.setSaveTime( quantStrategyService.getCurDate());
        quantStrategyMember.setStrategyTitle(strategyTitle);
        quantStrategyMember.setStrategyInfo(strategyInfo);
        quantStrategyService.strategySave(quantStrategyMember);

        quantPageDefaultSetting(model);

        System.out.println("[DEBUG] saveStrategy END");
        return "quant/quant-main";
    }

    @GetMapping("quant/delete-strategy")
    public String deleteStrategy(Model model,
                                 @RequestParam("strategyTitle") String strategyTitle) {
        System.out.println("[DEBUG] deleteStrategy START + strategyTitle: " + strategyTitle);
        quantStrategyService.delStrategy(strategyTitle);
        quantPageDefaultSetting(model);
        System.out.println("[DEBUG] deleteStrategy END");
        return "quant/quant-main";
    }

    @GetMapping("quant/naver-finance-parsing")
    public String naverFinanceParsing(Model model) {
        System.out.println("[DEBUG] naverFinanc eParsing START");

        quantPageDefaultSetting(model);

        // 1. 네이버 파싱
        // 2. Zset에 저장 ( 백업용 )
        // 3. .txt에 저장 ( 인 메모리에서 사용 용 )
        // etc) 일별시세 ( 파싱기준 200일 전까지 가져오기)
        quantStrategyService.naverFinanceParsing();

        System.out.println("[DEBUG] naverFinanceParsing END");
        return "quant/quant-main";
    }

    @GetMapping("quant/get-parsing-data")
    public String getParsingData(Model model,
                                 @RequestParam("strategyInfo") String strategyInfo,
                                 @RequestParam("strategyTitle") String strategyTitle,
                                 @RequestParam("parsingLatelyDate") String parsingLatelyDate ) {
        System.out.println("[DEBUG] getParsingData START");

        strategiesBackUpDisplay(model, strategyTitle);

        // 1. ZSet에서 가져오기

        // parsingData를 어떻게 분류해서 front 쪽으로 보내줄까? 흠
        // 리스트로 그냥 넘길까? 음.. 난 100개이상 보기 싫은데..
        // 100개까지만 보내주는걸로할까? 아니면, front에서 100개까지만 보여주는걸로 할까?

        // 어쩔 수 없다.. 조건을 Get으로 보내줘야할듯요.. 아 이건 post로 보낼까?? 한번해보지 뭐

        long startTime = System.currentTimeMillis();
        // 2. Text 파일에서 가져오기
        List<String> parsingData = quantStrategyService.getParsingData(strategyInfo, parsingLatelyDate);

        long endTime = System.currentTimeMillis();

        model.addAttribute("parsingData", parsingData);

        System.out.println("[DEBUG] endTime - startTime: " + (endTime - startTime));
        System.out.println("[DEBUG] getParsingData END");
        return "quant/quant-main";
    }

    @GetMapping("quant/get-daily-rate")
    public String getDailyRate(Model model,
                               @RequestParam("strategyTitle") String strategyTitle,
                               @RequestParam("month") String month) {
        strategiesBackUpDisplay(model, strategyTitle);
        return "quant/quant-main";
    }

    private void strategiesBackUpDisplay(Model model, String strategyTitle) {
// 파싱 최신 날짜 가져오기 추가
        ParsingDateMember parsingDateMember = quantStrategyService.getLastParsingDate().get();
        model.addAttribute("parsingDateMember", parsingDateMember);

        List<QuantStrategyMember> quantStrategyMembers = quantStrategyService.findStrategies();
        model.addAttribute("quantStrategyMembers", quantStrategyMembers);

        QuantStrategyMember quantStrategyMember = quantStrategyService.findStrategy(strategyTitle).get();
        QuantStrategyInfoMember quantStrategyInfoMember = splitStrategy(quantStrategyMember.getStrategyInfo());
        model.addAttribute("strategyInfo", quantStrategyInfoMember);
        model.addAttribute("quantStrategyMember", quantStrategyMember);
    }

    private QuantStrategyInfoMember splitStrategy(String strategyInfo) {
        String[] strategyInfoList = strategyInfo.split("/");
        QuantStrategyInfoMember quantStrategyInfoMember = new QuantStrategyInfoMember(
                strategyInfoList[0], strategyInfoList[1], strategyInfoList[2], strategyInfoList[3],
                strategyInfoList[4], strategyInfoList[5], strategyInfoList[6], strategyInfoList[7],
                strategyInfoList[8], strategyInfoList[9], strategyInfoList[10], strategyInfoList[11],
                strategyInfoList[12], strategyInfoList[13], strategyInfoList[14], strategyInfoList[15],
                strategyInfoList[16], strategyInfoList[17]
        );
        return quantStrategyInfoMember;
    }

    private void quantPageDefaultSetting(Model model) {
        // 파싱 최신 날짜 가져오기 추가
        ParsingDateMember parsingDateMember = quantStrategyService.getLastParsingDate().get();
        List<QuantStrategyMember> quantStrategyMembers = quantStrategyService.findStrategies();

        model.addAttribute("parsingDateMember", parsingDateMember);
        model.addAttribute("quantStrategyMembers", quantStrategyMembers);

        // 처음 화면에서는 전략 정보 더미를 던집니다.
        String companyInfoDummy =   "x/x/x/x/x/x/" +
                                    "x/x/x/x/x/x/" +
                                    "x/x/x/x/x/x";
        QuantStrategyInfoMember quantStrategyInfoMember = splitStrategy(companyInfoDummy);
        model.addAttribute("strategyInfo", quantStrategyInfoMember);

        // html option 더미를 던집니다.
        QuantStrategyMember quantStrategyMember = new QuantStrategyMember("dummy", quantStrategyService.getCurDate(),
                                                                        "전략을 선택해주세요.", companyInfoDummy);
        model.addAttribute("quantStrategyMember", quantStrategyMember);
    }
}
