package com.example.myHome.myHomespring.controller;

import com.example.myHome.myHomespring.domain.RedisMember;
import com.example.myHome.myHomespring.service.RedisMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class gamePageController {
    private final RedisMemberService redisGameResultDataService;

    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime myTime;

    @Autowired
    public gamePageController(RedisMemberService redisMemberService) {
        this.redisGameResultDataService = redisMemberService;
    }

    @GetMapping("/game/game-home")
    public String myHomeGame(Model model) {
        return "game/game.html";
    }

    @PostMapping("/game/result")
    public String myHomeGameResult(Model model,
                                   @RequestParam("name") String name,
                                   @RequestParam("score") String score) {

        String nowTime = getNowTime() + "@@" + score;
        RedisMember resultMember = new RedisMember(name, nowTime);
        redisGameResultDataService.join(resultMember);
        return "game/game.html";
    }

    public String getNowTime() {
        myTime = LocalDateTime.now();
        return myTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
