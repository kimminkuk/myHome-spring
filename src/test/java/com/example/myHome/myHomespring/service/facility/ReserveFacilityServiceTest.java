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
    public void 설비예약_시간설정() throws Exception {
        //given
        String title = "MT8311_ASAN_BMT#11";
        //then


        return;
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