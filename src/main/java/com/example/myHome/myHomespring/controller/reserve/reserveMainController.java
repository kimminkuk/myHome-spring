package com.example.myHome.myHomespring.controller.reserve;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class reserveMainController {
    @GetMapping("/reserve/reserve-main")
    public String reserveMain() {
        System.out.println("[DEBUG] /reserve/reserve-main Call");
        return "reserve/reserve-main.html";
    }
}
