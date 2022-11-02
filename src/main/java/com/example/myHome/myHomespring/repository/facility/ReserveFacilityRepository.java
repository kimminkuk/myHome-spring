package com.example.myHome.myHomespring.repository.facility;

import com.example.myHome.myHomespring.domain.facility.FacReserveTimeMember;
import com.example.myHome.myHomespring.domain.facility.ReserveFacilityTitle;

import java.util.List;
import java.util.Optional;

public interface ReserveFacilityRepository {
    ReserveFacilityTitle save(ReserveFacilityTitle reserveFacilityTitle);
    Optional<ReserveFacilityTitle> findById(Long id);
    Optional<ReserveFacilityTitle> findByTitle(String title);
    List<ReserveFacilityTitle> findAll();

    Optional<ReserveFacilityTitle> delFacility(String delTitle);


    //예약 시간 추가
    FacReserveTimeMember saveReserveTime(ReserveFacilityTitle curFacility, FacReserveTimeMember curFacReserveTime, String rserveTime);
    FacReserveTimeMember reserveFacility(FacReserveTimeMember curFacReserveTime, String reserveTime);
    FacReserveTimeMember facInitReserveSave(FacReserveTimeMember curFacReserveTime);
    List<FacReserveTimeMember> findReserveFacAll();

    Optional<FacReserveTimeMember> findByReserveFacTitle(String facTitle);
    Optional<FacReserveTimeMember> delReserveFac(String facTitle);

    //현재 설비의 예약된 시간들 보기
    //TODO:
    //Optional<String> curFacReserveTime(String facTitle);
}
