package com.example.myHome.myHomespring.service;

import com.example.myHome.myHomespring.domain.ReserveFacilityTitle;
import com.example.myHome.myHomespring.repository.ReserveFacilityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

        //when
        Long joinId = reserveFacilityService.join(reserveFacilityTitle);


        //then
        ReserveFacilityTitle findReserveFacility1 = reserveFacilityService.findOne(joinId).get();
        org.junit.jupiter.api.Assertions.assertEquals(reserveFacilityTitle.getTitle(), findReserveFacility1.getTitle());
    }
}