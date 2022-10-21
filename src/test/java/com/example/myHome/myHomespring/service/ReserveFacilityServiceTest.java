package com.example.myHome.myHomespring.service;

import com.example.myHome.myHomespring.domain.ReserveFacilityTitle;
import com.example.myHome.myHomespring.repository.ReserveFacilityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
    public void 중복_설비_예약() throws Exception {
        //given
        String title = "MT8311_ASAN_BMT#11";
        ReserveFacilityTitle reserveFacilityTitle = new ReserveFacilityTitle();
        ReserveFacilityTitle reserveFacilityTitle1 = new ReserveFacilityTitle();
        reserveFacilityTitle.setTitle(title);
        reserveFacilityTitle1.setTitle(title);

        //when
        Long join = reserveFacilityService.join(reserveFacilityTitle);
        //Long join1 = reserveFacilityService.join(reserveFacilityTitle1);
        //then


        //1. SpringBoot Test로는 안된다. (왜지?, 트랜잭션이라서 그런가?????)
        //2. In-Memory Test로는 확인 가능
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> reserveFacilityService.join(reserveFacilityTitle1));
        assertEquals("이미 존재하는 설비 예약 타이틀입니다.", e.getMessage());
//        IllegalStateException facilityReserveErrorCode = assertThrows(IllegalStateException.class, () -> reserveFacilityService.join(reserveFacilityTitle1));
//        assertEquals(facilityReserveErrorCode.getMessage(), "이미 존재하는 설비 예약 타이틀입니다.");

    }
}