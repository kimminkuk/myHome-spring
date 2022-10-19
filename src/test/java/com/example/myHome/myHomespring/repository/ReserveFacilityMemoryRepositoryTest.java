package com.example.myHome.myHomespring.repository;

import com.example.myHome.myHomespring.domain.ReserveFacilityTitle;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

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
    }

    @Test
    void findByTitle() {
        //given

        //when

        //then
    }
}