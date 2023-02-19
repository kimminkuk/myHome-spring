package com.example.myHome.myHomespring.controller.reactGame;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class reactGamePageController {

    @GetMapping("/react-game/game-home")
    public String reactGameHome() {
        return "react-game/react-game.html";
    }

    @GetMapping("/react-game/game-home2")
    public String reactGameHome2() {
        return "react-game/react-game-copy.html";
    }

    @GetMapping("/react-game/my-test")
    public String reactGameTest() {
        return "react-game/my-test.html";
    }
}
