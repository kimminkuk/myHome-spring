package com.example.myHome.myHomespring.controller.game;

import com.example.myHome.myHomespring.domain.game.RedisMember;
import com.example.myHome.myHomespring.service.game.RedisMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        //TODO: 결과 데이터에 날짜 시간을 추가하니 정렬이 매우 힘들었다.
        // 제거하도록한다. 그리고 순수한 int 값만 넣도록 한다.

        //String nowTime = getNowTime() + "@@" + score;
        //RedisMember resultMember = new RedisMember(name, nowTime);
        RedisMember resultMember = new RedisMember(name, score);

        //redisGameResultDataService.join(resultMember);

        //랭킹페이지에 넣을거임 (게임 페이지의 경우)
        redisGameResultDataService.saveRanking(resultMember);
        //logger 코드
        System.out.println("resultMember = " + resultMember);
        System.out.println("name = " + name + " score = " + score);
        System.out.println("resultMember name: " + resultMember.getKey() + " resultMember score: " + resultMember.getValue());

        return "game/game.html";
    }

    public String getNowTime() {
        myTime = LocalDateTime.now();
        return myTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
