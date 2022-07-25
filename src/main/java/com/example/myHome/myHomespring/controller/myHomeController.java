package com.example.myHome.myHomespring.controller;

import com.example.myHome.myHomespring.service.RedisMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class myHomeController {
    private final RedisMemberService redisMemberService;

    @Autowired
    public myHomeController(RedisMemberService redisMemberService) {
        this.redisMemberService = redisMemberService;
    }

    @GetMapping("/")
    public String home() {
        return "basic/home";
    }

    @GetMapping("/my-home")
    public String myHomeIndex(Model model) {
        model.addAttribute("myHome", "hello");
        return "basic/home";
    }

    @GetMapping("/my-home/game")
    public String myHomeGame(Model model) {
        return "basic/game";
    }

    @PostMapping("/my-home/game/result")
    public String myHomeGameResult(Model model,
                                   @RequestParam("name") String name,
                                   @RequestParam("score") String score) {
        redisMemberService.join(name, score);
        return "basic/game";
    }
}
