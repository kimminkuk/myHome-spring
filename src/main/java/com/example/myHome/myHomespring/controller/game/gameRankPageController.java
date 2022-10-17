package com.example.myHome.myHomespring.controller.game;

import com.example.myHome.myHomespring.domain.RedisMember;
import com.example.myHome.myHomespring.service.RedisMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Controller
public class gameRankPageController {
    private final RedisMemberService redisRankPageService;

    @Autowired
    public gameRankPageController(RedisMemberService redisRankPageService) {
        this.redisRankPageService = redisRankPageService;
    }

    //전체 랭킹 조회
    @GetMapping(value = "/game/rank-page-old")
    public String myHomeGameRankPage(Model model) {
        // TODO:
        // 랭킹은 1000위까지 보여주는걸로 redisService를 추가 코딩할거임
        // redis 정렬하는 코드
        // 근데.. redis 정렬하지 말라고했는데... 좋은 방법 없나?
        List<RedisMember> members = redisRankPageService.findMembers();
        model.addAttribute("members", members);
        members.sort((o1, o2) -> Integer.valueOf(o2.getValue()).compareTo(Integer.valueOf(o1.getValue())));

        /**
         *
         * 2022-09-25 22:59:13.202 ERROR 58268 --- [nio-8080-exec-2] o.a.c.c.C.[.[.[/].[dispatcherServlet]
         * : Servlet.service() for servlet [dispatcherServlet] in context with path []
         * threw exception [Request processing failed; nested exception is org.thymeleaf.exceptions.
         * TemplateProcessingException: Exception evaluating SpringEL expression:
         * "members.key" (template: "game/rank-page.html" - line 20, col 25)] with root cause
         *
         * org.springframework.expression.spel.SpelEvaluationException: EL1008E: Property or field 'key' cannot be found on object of type 'java.util.ArrayList' - maybe not public or not valid?
         *
         */
        return "game/rank-page.html";
    }

    // 내 랭킹 검색하는 부분
    @GetMapping("/game/rank-search-old")
    public String searchMyRank(Model model,
                               @RequestParam("userName") String userName) {
        if (redisRankPageService.findOne(userName).get()) {
            model.addAttribute("myRank", redisRankPageService.getValueOfKey(userName).get());
        } else {
            model.addAttribute("myRank", "랭킹에 없습니다.");
        }

        // TODO:
        // 랭킹을 검색할 때, 기존 랭킹을 보여주는 부분이 사라진다.
        // get으로 새롭게 page를 갱신하기 때문이다.
        // 페이지를 하나에서 각각 나눌 수는 없나?
        // 우선은 모르기 떄문에 전체 조회를 한번 더 Call 하는식으로 처리
        List<RedisMember> members = redisRankPageService.findMembers();
        model.addAttribute("members", members);
        members.sort((o1, o2) -> Integer.valueOf(o2.getValue()).compareTo(Integer.valueOf(o1.getValue())));
        return "game/rank-page.html";
    }

    // 랭킹 정렬 하는 부분
    @GetMapping(value = "/game/rank-page")
    public String sortRank(Model model) {
        Set<ZSetOperations.TypedTuple<String>> rankingMembersWithScore = redisRankPageService.getRankingMembersWithScore(0, 999);
        model.addAttribute("rankingMembersWithScore", rankingMembersWithScore);
        return "game/rank-page.html";
    }

    @PostMapping("/game/rank-search")
    public String searchMyRankVer2(Model model,
                               @RequestParam("userName") String userName) {
        Set<ZSetOperations.TypedTuple<String>> rankingMembers = redisRankPageService.getRankingMembersWithScore(0, 999);
        int rank = 0, score = 0;
        String findMember = "";
        for (ZSetOperations.TypedTuple<String> rankingMember : rankingMembers) {
            rank++;
            // String 비교 코드
            if (rankingMember.getValue().equals(userName)) {
                findMember = rankingMember.getValue();
                score = rankingMember.getScore().intValue();
                break;
            }
        }
        if ( findMember.equals("") ) {
            if ( redisRankPageService.findOne(userName).get() ) {
                model.addAttribute("myRank", "순위 밖");
            } else {
                model.addAttribute("myRank", "등록되지 않은 유저입니다.");
            }
        } else {
            model.addAttribute("myRank", rank);
            model.addAttribute("myRankName", findMember);
            model.addAttribute("myRankValue", score);
        }

        //Debug
        System.out.println("[DEBUG]userName = " + userName);
        return "game/rank-page-search.html";
    }

    @GetMapping("/game/rank-search")
    public String searchMyRankGetVer(Model model,
                                   @RequestParam("userName") String userName) {

        Set<ZSetOperations.TypedTuple<String>> rankingMembers = redisRankPageService.getRankingMembersWithScore(0, 999);
        int rank = 0, score = 0;
        String findMember = "";
        //Required request parameter 'userName' for method parameter type String is not present

        for (ZSetOperations.TypedTuple<String> rankingMember : rankingMembers) {
            rank++;
            // String 비교 코드
            if (rankingMember.getValue().equals(userName)) {
                findMember = rankingMember.getValue();
                score = rankingMember.getScore().intValue();
                break;
            }
        }
        if ( findMember.equals("") ) {
            if ( redisRankPageService.findOne(userName).get() ) {
                model.addAttribute("myRank", "순위 밖");
            } else {
                model.addAttribute("myRank", "등록되지 않은 유저입니다.");
            }
        } else {
            model.addAttribute("myRank", rank);
            model.addAttribute("myRankName", findMember);
            model.addAttribute("myRankValue", score);
        }

        //Debug
        System.out.println("[DEBUG GET VERSION]userName = " + userName);
        return "game/rank-page-search.html";
    }
}
