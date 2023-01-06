package com.example.myHome.myHomespring.controller.quant;

import com.example.myHome.myHomespring.domain.quant.QuantStrategyInfoMember;
import com.example.myHome.myHomespring.domain.quant.QuantStrategyMember;
import com.example.myHome.myHomespring.service.quant.QuantStrategyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Calendar;
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

        List<QuantStrategyMember> quantStrategyMembers = quantStrategyService.findStrategies();
        model.addAttribute("quantStrategyMembers", quantStrategyMembers);

        // 처음 화면에서는 전략 정보 더미를 던집니다.
        String companyInfoDummy = "x/x/x/x/x/x/x/x/x/x/x/x/x/x/x/x/x";
        QuantStrategyInfoMember quantStrategyInfoMember = splitStrategy(companyInfoDummy);
        model.addAttribute("strategyInfo", quantStrategyInfoMember);

        // html option 더미를 던집니다.
        QuantStrategyMember quantStrategyMember = new QuantStrategyMember("dummy", getCurDate(), "전략을 선택해주세요.", companyInfoDummy);
        model.addAttribute("quantStrategyMember", quantStrategyMember);

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

        // 1. back 쪽에서 전략을 split해서 front로 보내주는 방법
        //    아 타임리프로 보내야하니깐 클래스로 묶어서 보내주자
        QuantStrategyInfoMember quantStrategyInfoMember = splitStrategy(quantStrategyMember.getStrategyInfo());
        model.addAttribute("strategyInfo", quantStrategyInfoMember);

        // html option에 로드한 전략이 나오게 합니다.
        model.addAttribute("quantStrategyMember", quantStrategyMember);

        // 2. back 쪽에서 리스트로 보내서 front에서 split 하는 방법

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
        quantStrategyMember.setSaveTime(getCurDate());
        quantStrategyMember.setStrategyTitle(strategyTitle);
        quantStrategyMember.setStrategyInfo(strategyInfo);
        quantStrategyService.strategySave(quantStrategyMember);

        List<QuantStrategyMember> quantStrategyMembers = quantStrategyService.findStrategies();
        model.addAttribute("quantStrategyMembers", quantStrategyMembers);

        // 처음 화면에서는 전략 정보 더미를 던집니다.
        String companyInfoDummy = "x/x/x/x/x/x/x/x/x/x/x/x/x/x/x/x/x";
        QuantStrategyInfoMember quantStrategyInfoMember = splitStrategy(companyInfoDummy);
        model.addAttribute("strategyInfo", quantStrategyInfoMember);

        // html option 더미를 던집니다.
        QuantStrategyMember initPageDummyStrategyMember = new QuantStrategyMember("dummy", getCurDate(), "전략을 선택해주세요.", companyInfoDummy);
        model.addAttribute("quantStrategyMember", initPageDummyStrategyMember);

        System.out.println("[DEBUG] saveStrategy END");
        return "quant/quant-main";
    }

    @GetMapping("quant/delete-strategy")
    public String deleteStrategy(Model model,
                                 @RequestParam("strategyTitle") String strategyTitle) {
        System.out.println("[DEBUG] deleteStrategy START + strategyTitle: " + strategyTitle);
        quantStrategyService.delStrategy(strategyTitle);

        System.out.println("[DEBUG] deleteStrategy END");
        return "quant/quant-main";
    }

    private QuantStrategyInfoMember splitStrategy(String strategyInfo) {
        String[] strategyInfoList = strategyInfo.split("/");
        QuantStrategyInfoMember quantStrategyInfoMember = new QuantStrategyInfoMember(
                strategyInfoList[0], strategyInfoList[1], strategyInfoList[2], strategyInfoList[3],
                strategyInfoList[4], strategyInfoList[5], strategyInfoList[6], strategyInfoList[7],
                strategyInfoList[8], strategyInfoList[9], strategyInfoList[10], strategyInfoList[11],
                strategyInfoList[12], strategyInfoList[13], strategyInfoList[14], strategyInfoList[15],
                strategyInfoList[16]
        );
        return quantStrategyInfoMember;
    }

    private String getCurDate() {
        Calendar calendar = Calendar.getInstance();
        String saveTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
        return saveTime;
    }
}
