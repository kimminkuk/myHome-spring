package com.example.myHome.myHomespring.controller.reserve;

import com.example.myHome.myHomespring.domain.ReserveFacilityTitle;
import com.example.myHome.myHomespring.service.ReserveFacilityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class reserveMainController {

    private final ReserveFacilityService reserveFacilityService;

    public reserveMainController(ReserveFacilityService reserveFacilityService) {
        this.reserveFacilityService = reserveFacilityService;
    }

    @GetMapping("/reserve/reserve-main")
    public String reserveMain(Model model) {
        System.out.println("[DEBUG START] /reserve/reserve-main Call");

        //TODO: STEP1
        //1. 설비 예약 메인 페이지
        //1-1. 음... h2 db 설계를 title 만 있으면 될거같은데??
        //2. 설비 예약 항목들 일렬로 나열
        List<ReserveFacilityTitle> reserveFacilityTitles = reserveFacilityService.findReserveFacilityTitles();
        model.addAttribute("reserveFacilityTitles", reserveFacilityTitles);

        //TODO: STEP2
        //3. 설비 예약 항목을 클릭시, 설비 예약할 수 있고, 바로 예약가능함
        //4. 설비 예약 시간을 설정 후, 클릭 하면 예약 완료
        //5. 설비 예약한 범위는 예약 시간만큼 색깔로 칠해줄거임

        System.out.println("[DEBUG END] /reserve/reserve-main Call");
        return "reserve/reserve-main.html";
    }

    @GetMapping("/reserve/reserve-main/make")
    public String reserveMainMake(Model model,
                                  @RequestParam("facilityTitle") String facilityTitle) {

        System.out.println("[DEBUG START] /reserve/reserve-main/make/facilityTitle: " + facilityTitle + " Call");
        model.addAttribute("facilityTitle", facilityTitle);
        ReserveFacilityTitle reserveFacilityTitle = new ReserveFacilityTitle();
        reserveFacilityTitle.setTitle(facilityTitle);
        reserveFacilityService.join(reserveFacilityTitle);
        System.out.println("[DEBUG END] /reserve/reserve-main/make/facilityTitle: " + facilityTitle + " Call");
        return "reserve/reserve-main";
    }
}
