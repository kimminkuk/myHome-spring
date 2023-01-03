package com.example.myHome.myHomespring.controller.quant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class quantMainContr정oller {
    @GetMapping("/quant/quant-main")
    public String quantMain() {
        System.out.println("[DEBUG START] quantMain Call()");

        System.out.println("[DEBUG END] quantMain Call()");
        return "quant/quant-main.html";
    }

    @GetMapping("/quant/naver-finance-parsing")
    public String naverFinanceParsing() {
        System.out.println("[DEBUG START] naverFinanceParsing Call()");

        System.out.println("[DEBUG END] naverFinanceParsing Call()");
        return "quant/quant-main.html";
    }
}
