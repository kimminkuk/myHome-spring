package com.example.myHome.myHomespring.controller.facilityReserve;

import com.example.myHome.myHomespring.domain.facility.FacReserveTimeMember;
import com.example.myHome.myHomespring.domain.facility.ReserveFacilityTitle;
import com.example.myHome.myHomespring.service.facility.ReserveFacilityService;
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

        return "redirect:/reserve/reserve-main";
    }

    @GetMapping("/reserve/reserve-main-v2")
    public String reserveMainVer2(Model model) {
        System.out.println("[DEBUG START] /reserve/reserve-main-v2 Call");

        //TODO: STEP1
        // 예약된 설비들을 보여준다.
        List<FacReserveTimeMember> reserveFacAll = reserveFacilityService.findReserveFacAll();
        for (FacReserveTimeMember facReserveTimeMember : reserveFacAll) {
            System.out.println("title = " + facReserveTimeMember.getReserveFacTitle());
            System.out.println("Content = " + facReserveTimeMember.getReserveContent());
            System.out.println("userName = " + facReserveTimeMember.getUserName());
            System.out.println("Time = " + facReserveTimeMember.getReserveTime());
        }
        model.addAttribute("reserveFacAll", reserveFacAll);

        System.out.println("[DEBUG END] /reserve/reserve-main-v2 Call");
        return "reserve/reserve-main.html";
    }

    @GetMapping("/reserve/reserve-main/make-v2")
    public String reserveMainMakeVer2(Model model,
                                      @RequestParam("facilityTitle") String facilityTitle,
                                      @RequestParam("reserveContent") String reserveContent,
                                      @RequestParam("reserveTime") String reserveTime,
                                      @RequestParam("userName") String userName) {
        System.out.println("[DEBUG START] /reserve/reserve-main/make-v2/facilityTitle: " + ", facilityTitle = "
                + facilityTitle + ", reserveTime = " + reserveTime + ", userName = " + userName + " reserveContent " + reserveContent +" Call");

        model.addAttribute("facilityTitle", facilityTitle);
        model.addAttribute("reserveTime", reserveTime);
        model.addAttribute("userName", userName);
        model.addAttribute("reserveContent", reserveContent);
        FacReserveTimeMember facReserveTimeMember = new FacReserveTimeMember(facilityTitle, reserveContent, userName, reserveTime);

        reserveFacilityService.facReserveFirst(facReserveTimeMember);
        System.out.println("[DEBUG END] /reserve/reserve-main/make-v2/facilityTitle: " + ", facilityTitle = "
                + facilityTitle + ", reserveTime = " + reserveTime + ", userName = " + userName + " reserveContent " + reserveContent +" Call");

        return "redirect:/reserve/reserve-main-v2";
    }

    @GetMapping("/reserve/reserve-main/fac-reserve")
    public String reserveMainFacReserve(Model model,
                                        @RequestParam("facilityTitle") String facilityTitle,
                                        @RequestParam("reserveContent") String reserveContent,
                                        @RequestParam("reserveTime") String reserveTime,
                                        @RequestParam("userName") String userName ) {
        System.out.println("[DEBUG START] /reserve/reserve-main/fac-reserve/facilityTitle: " + facilityTitle + " userName: " + userName + " reserveTime: " + reserveTime + " reserveContent: " + reserveContent + " Call");

        model.addAttribute("facilityTitle", facilityTitle);
        model.addAttribute("reserveContent", reserveContent);
        model.addAttribute("userName", userName);
        model.addAttribute("reserveTime", reserveTime);

        FacReserveTimeMember facReserveTimeMember = new FacReserveTimeMember(facilityTitle, userName);
        facReserveTimeMember.setReserveContent(reserveContent);
        String curResTime = reserveFacilityService.getCurFacReserveTime(facReserveTimeMember.getReserveFacTitle()).get();
        facReserveTimeMember.setReserveTime(curResTime);
        reserveFacilityService.facReserve(facReserveTimeMember, reserveTime);

        System.out.println("[DEBUG END] /reserve/reserve-main/fac-reserve/facilityTitle: " + facilityTitle + " userName: " + userName + " reserveTime: " + reserveTime + " reserveContent: " + reserveContent + " Call");
        return "redirect:/reserve/reserve-main-v2";
    }

    @GetMapping("/reserve/reserve-main/delete")
    public String reserveMainDelete(Model model,
                                    @RequestParam("delFacTitle") String delFacTitle) {

        System.out.println("[DEBUG START] /reserve/reserve-main/delete/delTitle " + delFacTitle + " Call");
        model.addAttribute("delFacTitle", delFacTitle);
        reserveFacilityService.delFacility(delFacTitle);
        System.out.println("[DEBUG END] /reserve/reserve-main/delete/delTitle: " + delFacTitle + " Call");


        //redirect code
        return "redirect:/reserve/reserve-main-v2";
    }
}
