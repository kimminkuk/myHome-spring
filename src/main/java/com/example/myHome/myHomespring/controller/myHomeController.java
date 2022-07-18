package com.example.myHome.myHomespring.controller;

import com.example.myHome.myHomespring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class myHomeController {
    private final MemberService memberService;

    @Autowired
    public myHomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Autowired
    private RedisTemplate redisTemplate;

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
        String key = "banana";
        String data = "yellow";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, data);
        String result = valueOperations.get(key);
        return "basic/home";
    }
}
