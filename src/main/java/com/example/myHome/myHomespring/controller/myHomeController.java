package com.example.myHome.myHomespring.controller;

import com.example.myHome.myHomespring.domain.RedisMember;
import com.example.myHome.myHomespring.service.RedisMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class myHomeController {
    private final RedisMemberService redisMemberService;

    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime myTime;

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

        String nowTime = getNowTime() + '@' + score;
        System.out.println(nowTime + "?????");
        RedisMember resultMember = new RedisMember(name, nowTime);
        redisMemberService.join(resultMember);
        return "basic/game";
    }


    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public String getNowTime() {
        myTime = LocalDateTime.now();
        return myTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
