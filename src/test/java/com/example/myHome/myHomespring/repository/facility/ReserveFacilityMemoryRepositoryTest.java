package com.example.myHome.myHomespring.repository.facility;

import com.example.myHome.myHomespring.domain.facility.FacReserveTimeMember;
import com.example.myHome.myHomespring.domain.facility.ReserveFacilityTitle;
import com.example.myHome.myHomespring.repository.facility.ReserveFacilityMemoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReserveFacilityMemoryRepositoryTest {
    ReserveFacilityMemoryRepository reserveFacilityRepository = new ReserveFacilityMemoryRepository();

    @AfterEach
    public void afterEach() {
        reserveFacilityRepository.clearStore();
    }

    @Test
    void save() {
        //given
        ReserveFacilityTitle reserveFacilityTitle = new ReserveFacilityTitle();
        reserveFacilityTitle.setTitle("MT8311_ASAN_BMT#1");

        ReserveFacilityTitle reserveFacilityTitle1 = new ReserveFacilityTitle();
        reserveFacilityTitle1.setTitle("MT8311_ASAN_BMT#2");

        //when
        reserveFacilityRepository.save(reserveFacilityTitle);
        reserveFacilityRepository.save(reserveFacilityTitle1);

        //then
        ReserveFacilityTitle findFacility1 = reserveFacilityRepository.findById(reserveFacilityTitle.getId()).get();
        ReserveFacilityTitle findFacility2 = reserveFacilityRepository.findById(reserveFacilityTitle1.getId()).get();

        assertThat(findFacility1).isEqualTo(reserveFacilityTitle);
        assertThat(findFacility2).isEqualTo(reserveFacilityTitle1);
    }

    @Test
    void findByTitle() {
        //given
        String title = "MT8311_ASAN_BMT#1";
        String title1 = "MT8311_ASAN_BMT#2";
        ReserveFacilityTitle reserveFacilityTitle = new ReserveFacilityTitle();
        reserveFacilityTitle.setTitle(title);
        reserveFacilityRepository.save(reserveFacilityTitle);

        ReserveFacilityTitle reserveFacilityTitle1 = new ReserveFacilityTitle();
        reserveFacilityTitle1.setTitle(title1);
        reserveFacilityRepository.save(reserveFacilityTitle1);

        //when
        ReserveFacilityTitle findFacility1 = reserveFacilityRepository.findByTitle(title).get();
        ReserveFacilityTitle findFacility2 = reserveFacilityRepository.findByTitle(title1).get();

        //then
        assertThat(findFacility1).isEqualTo(reserveFacilityTitle);
        assertThat(findFacility2).isEqualTo(reserveFacilityTitle1);
    }

    @Test
    void delFacility() {
        String delTitle = "MT8311_ASAN_BMT#2";
        //given
        ReserveFacilityTitle reserveFacilityTitle = new ReserveFacilityTitle();
        reserveFacilityTitle.setTitle("MT8311_ASAN_BMT#1");

        ReserveFacilityTitle reserveFacilityTitle1 = new ReserveFacilityTitle();
        reserveFacilityTitle1.setTitle(delTitle);

        ReserveFacilityTitle reserveFacilityTitle2 = new ReserveFacilityTitle();
        reserveFacilityTitle2.setTitle("MT8311_ASAN_BMT#3");

        ReserveFacilityTitle reserveFacilityTitle3 = new ReserveFacilityTitle();
        reserveFacilityTitle3.setTitle("MT8311_ASAN_BMT#6");

        //when
        reserveFacilityRepository.save(reserveFacilityTitle);
        reserveFacilityRepository.save(reserveFacilityTitle1);
        reserveFacilityRepository.save(reserveFacilityTitle2);

        //then
        assertThat(reserveFacilityRepository.findAll().size()).isEqualTo(3);

        reserveFacilityRepository.delFacility(delTitle);
        assertThat(reserveFacilityRepository.findAll().size()).isEqualTo(2);
    }


    @Test
    void saveReserveTime() {
        //given
        ReserveFacilityTitle reserveFacilityTitle = new ReserveFacilityTitle();
        reserveFacilityTitle.setTitle("MT8311_ASAN_BMT#1");
        reserveFacilityRepository.save(reserveFacilityTitle);

        String reserveTime1 = "2022-11-14 10:30~2022-11-14 11:30";
        FacReserveTimeMember facReserveTimeMember = new FacReserveTimeMember();
        facReserveTimeMember.setUserName("호요다@kanam.esap.co.kr");
        facReserveTimeMember.setReserveTime("2022-11-14 11:30~2022-11-14 14:00, 2022-11-14 15:30~2022-11-14 16:00");

        String reserveTime2 = "2022-11-15 14:00~2022-11-15 14:30";
        FacReserveTimeMember facReserveTimeMember2 = new FacReserveTimeMember();
        facReserveTimeMember2.setUserName("호요다@kanam.esap.co.kr");
        facReserveTimeMember2.setReserveTime("2022-11-15 11:30~2022-11-14 14:00, 2022-11-15 17:30~2022-11-14 18:00");

        //when
        FacReserveTimeMember saveFacReserve = reserveFacilityRepository.saveReserveTime(reserveFacilityTitle, facReserveTimeMember, reserveTime1);
        FacReserveTimeMember saveFacReserve2 = reserveFacilityRepository.saveReserveTime(reserveFacilityTitle, facReserveTimeMember2, reserveTime2);
        //then
        assertThat(saveFacReserve).isEqualTo(facReserveTimeMember);
        assertThat(saveFacReserve2).isEqualTo(facReserveTimeMember2);
    }

    @Test
    void saveFailReserveTime() {
        //given
        ReserveFacilityTitle reserveFacilityTitle = new ReserveFacilityTitle();
        reserveFacilityTitle.setTitle("MT8311_ASAN_BMT#1");
        reserveFacilityRepository.save(reserveFacilityTitle);
        // 1. 예약시간이 기존시간보다 빠른 경우
        //    expect) [1]이미 예약된 시간입니다.
        //       |------|
        //            |------|
        String reserveTime1 = "2022-11-14 10:30~2022-11-14 11:30";
        FacReserveTimeMember facReserveTimeMember1 = new FacReserveTimeMember();
        facReserveTimeMember1.setUserName("호요다@kanam.esap.co.kr");
        facReserveTimeMember1.setReserveTime("2022-11-14 10:00~2022-11-14 11:00, 2022-11-14 15:30~2022-11-14 16:00");


        // 2. 예약시간이 기존시간이랑 왼쪽으로 겹칠때
        //    expect) [2]이미 예약된 시간입니다.
        //       |------|
        //    |------|
        String reserveTime2 = "2022-11-14 10:00~2022-11-14 11:00";
        FacReserveTimeMember facReserveTimeMember2 = new FacReserveTimeMember();
        facReserveTimeMember2.setUserName("호요다@kanam.esap.co.kr");
        facReserveTimeMember2.setReserveTime("2022-11-14 10:30~2022-11-14 11:30, 2022-11-14 15:30~2022-11-14 16:00");
        //3-1. 예약시간이 기존시간이랑 왼, 외 전부 겹칠 때 ( 기존 시간이 더 큰 경우 )
        //    expect) [3-1]이미 예약된 시간입니다.
        //       |------|
        //         |--|
        String reserveTime3 = "2022-11-14 11:00~2022-11-14 12:00";
        FacReserveTimeMember facReserveTimeMember3 = new FacReserveTimeMember();
        facReserveTimeMember3.setUserName("호요다@kanam.esap.co.kr");
        facReserveTimeMember3.setReserveTime("2022-11-14 10:00~2022-11-14 14:30, 2022-11-14 15:30~2022-11-14 16:00");

        //3-2. 예약시간이 기존시간이랑 왼, 오 전부 겹칠 때 ( 기존 시간이 더 작은 경우, 근데 [1]이랑 겹쳐서 완전 똑같은 경우로 다시 검사 )
        //    지금 보니 이런 경우는 1,2,3 에서 다 잡히네.. 넘기자
        //    expect) [3-2]이미 예약된 시간입니다.
        //      |--------|
        //      |--------|


        //then
        IllegalStateException e = Assertions.assertThrows(IllegalStateException.class, () -> reserveFacilityRepository.saveReserveTime(reserveFacilityTitle, facReserveTimeMember1, reserveTime1));
        IllegalStateException e2 = Assertions.assertThrows(IllegalStateException.class, () -> reserveFacilityRepository.saveReserveTime(reserveFacilityTitle, facReserveTimeMember2, reserveTime2));
        IllegalStateException e3 = Assertions.assertThrows(IllegalStateException.class, () -> reserveFacilityRepository.saveReserveTime(reserveFacilityTitle, facReserveTimeMember3, reserveTime3));

        Assertions.assertEquals("[1]이미 예약된 시간입니다.", e.getMessage());
        Assertions.assertEquals("[2]이미 예약된 시간입니다.", e2.getMessage());
        Assertions.assertEquals("[3-1]이미 예약된 시간입니다.", e3.getMessage());
    }
}