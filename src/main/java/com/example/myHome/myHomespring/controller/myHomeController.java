package com.example.myHome.myHomespring.controller;

import com.example.myHome.myHomespring.service.RedisMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/my-home/test")
    public String redisTest() {
        String key = "banana222";
        String data = "yellow222";
        redisMemberService.join(key,data);
        return "basic/home";
    }

    @GetMapping("/my-home/test2")
    public String redisTest2() {
        return "basic/home";
    }
}
