package com.example.myHome.myHomespring.service;

import com.example.myHome.myHomespring.domain.ReserveFacilityTitle;
import com.example.myHome.myHomespring.repository.ReserveFacilityRepository;

import java.util.List;
import java.util.Optional;

public class ReserveFacilityService {
    private final ReserveFacilityRepository reserveFacilityRepository;

    public ReserveFacilityService(ReserveFacilityRepository reserveFacilityRepository) {
        this.reserveFacilityRepository = reserveFacilityRepository;
    }

    /**
     *  설비 예약 타이틀 생성
     */
    public Long join(ReserveFacilityTitle reserveFacilityTitle) {
        validateDuplicateFacilityTitle(reserveFacilityTitle);
        reserveFacilityRepository.save(reserveFacilityTitle);
        return reserveFacilityTitle.getId();
    }

    /**
     *  예약 가능한 설비 전체 조회
     */
    public List<ReserveFacilityTitle> findReserveFacilityTitles() {
        return reserveFacilityRepository.findAll();
    }

    /**
     *  설비 예약 타이틀 찾기
     */
    public Optional<ReserveFacilityTitle> findOne(Long reserveFacilityTitleId) {
        return reserveFacilityRepository.findById(reserveFacilityTitleId);
    }


    private void validateDuplicateFacilityTitle(ReserveFacilityTitle reserveFacilityTitle) {
        reserveFacilityRepository.findByTitle(reserveFacilityTitle.getTitle())
                .ifPresent( title -> {
                    throw new IllegalStateException("이미 존재하는 설비 예약 타이틀입니다.");
                });
    }
}
