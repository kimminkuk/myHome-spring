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
}
