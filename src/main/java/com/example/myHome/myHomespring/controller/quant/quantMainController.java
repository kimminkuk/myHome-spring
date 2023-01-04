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
    public String quantMain() {
        System.out.println("[DEBUG] quantMain START");


        System.out.println("[DEBUG] quantMain END");
        return "quant/quant-main";
    }

    @GetMapping("quant/load-strategy")
    public String loadStrategy(Model model,
                               @RequestParam("strategyName") String strategyName) {
        System.out.println("[DEBUG] loadStrategy START + strategyName: " + strategyName);

        QuantStrategyMember quantStrategyMember = quantStrategyService.findStrategy(strategyName).get();

        // 1. back 쪽에서 전략을 split해서 front로 보내주는 방법
        //    아 타임리프로 보내야하니깐 클래스로 묶어서 보내주자
        QuantStrategyInfoMember quantStrategyInfoMember = splitStrategy(quantStrategyMember.getStrategyInfo());
        model.addAttribute("strategyInfo", quantStrategyInfoMember);

        // 2. back 쪽에서 리스트로 보내서 front에서 split 하는 방법

        System.out.println("[DEBUG] loadStrategy END");
        return "quant/quant-main";
    }

    @GetMapping("quant/save-strategy")
    public String saveStrategy(Model model,
                               @RequestParam("userName") String userName,
                               @RequestParam("strategyTitle") String strategyTitle,
                               @RequestParam("strategyInfo") String strategyInfo) {
        System.out.println("[DEBUG] saveStrategy START + strategyTitle: " + strategyTitle + ", strategyInfo: " + strategyInfo);
        QuantStrategyMember quantStrategyMember = new QuantStrategyMember();
        quantStrategyMember.setUserName(userName);
        quantStrategyMember.setSaveTime(getCurDate());
        quantStrategyMember.setStrategyTitle(strategyTitle);
        quantStrategyMember.setStrategyInfo(strategyInfo);
        quantStrategyService.strategySave(quantStrategyMember);

        System.out.println("[DEBUG] saveStrategy END");
        return "quant/quant-main";
    }

    private QuantStrategyInfoMember splitStrategy(String strategyInfo) {
        String[] strategyInfoList = strategyInfo.split("/");
        QuantStrategyInfoMember quantStrategyInfoMember = new QuantStrategyInfoMember(
                strategyInfoList[0], strategyInfoList[1], strategyInfoList[2], strategyInfoList[3],
                strategyInfoList[4], strategyInfoList[5], strategyInfoList[6], strategyInfoList[7],
                strategyInfoList[8], strategyInfoList[9], strategyInfoList[10], strategyInfoList[11],
                strategyInfoList[12], strategyInfoList[13], strategyInfoList[14], strategyInfoList[15]
        );
        return quantStrategyInfoMember;
    }

    private String getCurDate() {
        Calendar calendar = Calendar.getInstance();
        String saveTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
        return saveTime;
    }
}
