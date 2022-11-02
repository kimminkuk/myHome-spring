package com.example.myHome.myHomespring.service.facility;

import com.example.myHome.myHomespring.domain.facility.FacReserveTimeMember;
import com.example.myHome.myHomespring.domain.facility.ReserveFacilityTitle;
import com.example.myHome.myHomespring.repository.facility.ReserveFacilityRepository;
import com.example.myHome.myHomespring.service.facility.ReserveFacilityService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReserveFacilityServiceTest {
    @Autowired
    ReserveFacilityService reserveFacilityService;

    @Autowired
    ReserveFacilityRepository reserveFacilityRepository;

    @Test
    public void 설비예약타이틀생성() throws Exception {
        //given
        String title = "MT8311_ASAN_BMT#11";
        String title1 = "MT8311_ASAN_BMT#22";
        String title2 = "MT8311_ASAN_BMT#33";
        ReserveFacilityTitle reserveFacilityTitle = new ReserveFacilityTitle();
        reserveFacilityTitle.setTitle(title);

        ReserveFacilityTitle reserveFacilityTitle1 = new ReserveFacilityTitle();
        ReserveFacilityTitle reserveFacilityTitle2 = new ReserveFacilityTitle();
        reserveFacilityTitle1.setTitle(title1);
        reserveFacilityTitle2.setTitle(title2);


        //when
        Long joinId = reserveFacilityService.join(reserveFacilityTitle);
        Long join = reserveFacilityService.join(reserveFacilityTitle1);
        Long join1 = reserveFacilityService.join(reserveFacilityTitle2);
        List<ReserveFacilityTitle> reserveFacilityTitles = reserveFacilityService.findReserveFacilityTitles();
        Assertions.assertThat(reserveFacilityTitles.size()).isEqualTo(3);
    }

    @Test
    public void 설비예약_버전2() throws Exception {
        //init
        List<FacReserveTimeMember> initReserveList = reserveFacilityService.findReserveFacAll();
        Long initSize = Long.valueOf(initReserveList.size());

        //given
        String tempInitReserveTime = "0000-00-00 00:00~0000-00-00 00:00";
        String tempInitUserName = "mk.Yoda@nklkb.com";

        String title1 = "MT8311_ASAN_BMT#1";
        FacReserveTimeMember facReserveTimeMember1 = new FacReserveTimeMember();
        facReserveTimeMember1.setReserveTime(tempInitReserveTime);
        facReserveTimeMember1.setUserName(tempInitUserName);
        facReserveTimeMember1.setReserveFacTitle(title1);

        String title2 = "MT8311_ASAN_BMT#2";
        FacReserveTimeMember facReserveTimeMember2 = new FacReserveTimeMember();
        facReserveTimeMember2.setReserveTime(tempInitReserveTime);
        facReserveTimeMember2.setUserName(tempInitUserName);
        facReserveTimeMember2.setReserveFacTitle(title2);

        String title3 = "MT8311_ASAN_BMT#3";
        FacReserveTimeMember facReserveTimeMember3 = new FacReserveTimeMember();
        facReserveTimeMember3.setReserveTime(tempInitReserveTime);
        facReserveTimeMember3.setUserName(tempInitUserName);
        facReserveTimeMember3.setReserveFacTitle(title3);

        //when
        Long reserve1 = reserveFacilityService.facReserveFirst(facReserveTimeMember1);
        Long reserve2 = reserveFacilityService.facReserveFirst(facReserveTimeMember2);
        Long reserve3 = reserveFacilityService.facReserveFirst(facReserveTimeMember3);

        //then
        List<FacReserveTimeMember> reserveFacAll = reserveFacilityService.findReserveFacAll();
        Assertions.assertThat(reserveFacAll.size()).isEqualTo(3 + initSize);

        reserveFacilityService.delReserveFac(title1);
        List<FacReserveTimeMember> reserveFacAll1 = reserveFacilityService.findReserveFacAll();
        Assertions.assertThat(reserveFacAll1.size()).isEqualTo(2 + initSize);
        return;
    }

    @Test
    public void 같은설비_추가예약() throws Exception {
        //init
        String reserveTime2 = "2022-11-02 23:00~2022-11-02 23:30";

        //given
        String tempInitReserveTime = "0000-00-00 00:00~0000-00-00 00:00";
        String tempInitUserName = "mk.Yoda@nklkb.com";

        String title1 = "MT8311_ASAN_BMT#1";
        FacReserveTimeMember facReserveTimeMember1 = new FacReserveTimeMember();
        facReserveTimeMember1.setReserveTime(tempInitReserveTime);
        facReserveTimeMember1.setUserName(tempInitUserName);
        facReserveTimeMember1.setReserveFacTitle(title1);

        //when
        Long reserve1 = reserveFacilityService.facReserveFirst(facReserveTimeMember1);


        //then
        reserveFacilityService.facReserve(facReserveTimeMember1, reserveTime2);

        System.out.println("fac1Time: " + facReserveTimeMember1.getReserveTime());
        return;
    }

    @Test
    public void 중복_설비_예약() throws Exception {
        //given
        String tempInitReserveTime = "0000-00-00 00:00~0000-00-00 00:00";
        String tempInitUserName = "mk.Yoda@nklkb.com";

        String title1 = "MT8311_ASAN_BMT#1";
        FacReserveTimeMember facReserveTimeMember1 = new FacReserveTimeMember();
        facReserveTimeMember1.setReserveTime(tempInitReserveTime);
        facReserveTimeMember1.setUserName(tempInitUserName);
        facReserveTimeMember1.setReserveFacTitle(title1);

        FacReserveTimeMember facReserveTimeMember2 = new FacReserveTimeMember();
        facReserveTimeMember2.setReserveTime(tempInitReserveTime);
        facReserveTimeMember2.setUserName(tempInitUserName);
        facReserveTimeMember2.setReserveFacTitle(title1);

        //when
        Long aLong = reserveFacilityService.facReserveFirst(facReserveTimeMember1);

        System.out.println("1: "+facReserveTimeMember1.getReserveFacTitle() + " 2: " +facReserveTimeMember2.getReserveFacTitle());
        //then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
            reserveFacilityService.facReserveFirst(facReserveTimeMember2);
        });
        //Assertions.assertThat(e.getMessage()).isEqualTo("이미 예약된 설비입니다.");
        org.junit.jupiter.api.Assertions.assertEquals("이미 존재하는 설비 예약 타이틀입니다.", e.getMessage());

    }

    @Test
    public void 설비_삭제() throws Exception {
        //given
        List<ReserveFacilityTitle> reserveFacilityTitles = reserveFacilityService.findReserveFacilityTitles();
        int size = reserveFacilityTitles.size();
        String title = "MT8311_ASAN_BMT#11";
        String title1 = "MT8311_ASAN_BMT#22";
        String title2 = "MT8311_ASAN_BMT#33";
        String title3 = "MT8311_ASAN_BMT#44";
        String inDbTitle1 = "테스트입니다.";
        String inDbTitle2 = "MT6133_ASAN";
        ReserveFacilityTitle reserveFacilityTitle = new ReserveFacilityTitle();
        ReserveFacilityTitle reserveFacilityTitle1 = new ReserveFacilityTitle();
        ReserveFacilityTitle reserveFacilityTitle2 = new ReserveFacilityTitle();
        ReserveFacilityTitle reserveFacilityTitle3 = new ReserveFacilityTitle();
        reserveFacilityTitle.setTitle(title);
        reserveFacilityTitle1.setTitle(title1);
        reserveFacilityTitle2.setTitle(title2);
        reserveFacilityTitle3.setTitle(title3);

        //when
        reserveFacilityService.join(reserveFacilityTitle);
        reserveFacilityService.join(reserveFacilityTitle1);
        reserveFacilityService.join(reserveFacilityTitle2);

        //then
        reserveFacilityTitles = reserveFacilityService.findReserveFacilityTitles();
        Assertions.assertThat(reserveFacilityTitles.size()).isEqualTo(3 + size);

        reserveFacilityService.delFacility(title3);
        reserveFacilityTitles = reserveFacilityService.findReserveFacilityTitles();
        Assertions.assertThat(reserveFacilityTitles.size()).isEqualTo(3 + size);
    }

    @Test
    public void 기존_설비_삭제() {
        //given
        List<ReserveFacilityTitle> reserveFacilityTitles = reserveFacilityService.findReserveFacilityTitles();
        int size = reserveFacilityTitles.size();
        String inDbTitle1 = "테스트입니다.";
        String inDbTitle2 = "MT6133_ASAN";

        //when
        reserveFacilityService.delFacility(inDbTitle1);
        reserveFacilityTitles = reserveFacilityService.findReserveFacilityTitles();

        //then

        Assertions.assertThat(reserveFacilityTitles.size()).isEqualTo(size - 1);
    }
}