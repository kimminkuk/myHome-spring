package com.example.myHome.myHomespring.controller.reactGame;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class reactGamePageController {

    @GetMapping("/react-game/game-home")
    public String reactGameHome() {
        //return "react-game/react-game.html";
        return "fronend-react-test/build/static/index.html";
        //return "fronend-react-test/public/index.html";
    }

    @GetMapping("/react-game/game-home2")
    public String reactGameHome2() {
        return "react-game/build/static/index.html";
    }
}
