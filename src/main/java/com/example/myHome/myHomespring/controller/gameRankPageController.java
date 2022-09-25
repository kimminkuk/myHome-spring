package com.example.myHome.myHomespring.controller;

import com.example.myHome.myHomespring.domain.RedisMember;
import com.example.myHome.myHomespring.service.RedisMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class gameRankPageController {
    private final RedisMemberService redisRankPageService;

    @Autowired
    public gameRankPageController(RedisMemberService redisRankPageService) {
        this.redisRankPageService = redisRankPageService;
    }

    //전체 랭킹 조회
    @GetMapping("/game/rank-page")
    public String myHomeGameRankPage(Model model) {
        // TODO:
        // 랭킹은 1000위까지 보여주는걸로 redisService를 추가 코딩할거임
        //List<RedisMember> members = redisRankPageService.findMembers();
        //model.addAttribute("members", members);
        /**
         *
         * 2022-09-25 22:59:13.202 ERROR 58268 --- [nio-8080-exec-2] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.thymeleaf.exceptions.TemplateProcessingException: Exception evaluating SpringEL expression: "members.key" (template: "game/rank-page.html" - line 20, col 25)] with root cause
         *
         * org.springframework.expression.spel.SpelEvaluationException: EL1008E: Property or field 'key' cannot be found on object of type 'java.util.ArrayList' - maybe not public or not valid?
         *
         */
        return "game/rank-page.html";
    }

    // 내 랭킹 검색하는 부분
    @GetMapping("/game/my-rank")
    public String myHomeGameRankPageMyRank(Model model,
                                           @RequestParam("name") String name) {
        if (redisRankPageService.findOne(name).get()) {
            model.addAttribute("myRank", redisRankPageService.getValueOfKey(name));
        } else {
            model.addAttribute("myRank", "랭킹에 없습니다.");
        }
        return "game/rank-page.html";
    }
}
