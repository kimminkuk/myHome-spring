package com.example.myHome.myHomespring.controller.reserve;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class reserveMainController {
    @GetMapping("/reserve/reserve-main")
    public String reserveMain() {
        System.out.println("[DEBUG] /reserve/reserve-main Call");

        //TODO: STEP1
        //1. 설비 예약 메인 페이지
        //1-1. 음... h2 db 설계를 title 만 있으면 될거같은데??
        //2. 설비 예약 항목들 일렬로 나열


        //TODO: STEP2
        //3. 설비 예약 항목을 클릭시, 설비 예약할 수 있고, 바로 예약가능함
        //4. 설비 예약 시간을 설정 후, 클릭 하면 예약 완료
        //5. 설비 예약한 범위는 예약 시간만큼 색깔로 칠해줄거임

        return "reserve/reserve-main.html";
    }

    @GetMapping("/reserve/reserve-main/make")
    public String reserveMainMake(Model model,
                                  @RequestParam("facilityTitle") String facilityTitle) {

        System.out.println("[DEBUG] /reserve/reserve-main/make/facilityTitle: " + facilityTitle + " Call");
        return "reserve/reserve-main";
    }
}
