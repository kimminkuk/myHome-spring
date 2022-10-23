package com.example.myHome.myHomespring.repository;

import com.example.myHome.myHomespring.domain.ReserveFacilityTitle;

import java.util.List;
import java.util.Optional;

public interface ReserveFacilityRepository {
    ReserveFacilityTitle save(ReserveFacilityTitle reserveFacilityTitle);
    Optional<ReserveFacilityTitle> findById(Long id);
    Optional<ReserveFacilityTitle> findByTitle(String title);
    List<ReserveFacilityTitle> findAll();

    Optional<ReserveFacilityTitle> delFacility(String delTitle);
}
