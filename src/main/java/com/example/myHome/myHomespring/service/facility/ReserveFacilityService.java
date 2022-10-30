package com.example.myHome.myHomespring.service.facility;

import com.example.myHome.myHomespring.domain.facility.FacReserveTimeMember;
import com.example.myHome.myHomespring.domain.facility.ReserveFacilityTitle;
import com.example.myHome.myHomespring.repository.facility.ReserveFacilityRepository;

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

    /**
     * 설비 예약 타이틀 삭제
     */
    public Optional<ReserveFacilityTitle> delFacility(String delTitle) {
       return reserveFacilityRepository.delFacility(delTitle);
    }

    /**
     * 설비 예약 시간 설정
     */
    public Long reserveTime(ReserveFacilityTitle curFacility, FacReserveTimeMember curFacReserveTime, String reserveTime) {
        reserveFacilityRepository.saveReserveTime(curFacility, curFacReserveTime, reserveTime);
        return curFacReserveTime.getId();
    }

    private void validateDuplicateFacilityTitle(ReserveFacilityTitle reserveFacilityTitle) {
        reserveFacilityRepository.findByTitle(reserveFacilityTitle.getTitle())
                .ifPresent( title -> {
                    throw new IllegalStateException("이미 존재하는 설비 예약 타이틀입니다.");
                });
    }
}
