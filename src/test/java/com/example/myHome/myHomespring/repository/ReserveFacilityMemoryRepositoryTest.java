package com.example.myHome.myHomespring.repository;

import com.example.myHome.myHomespring.domain.ReserveFacilityTitle;
import org.junit.jupiter.api.AfterEach;
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
}