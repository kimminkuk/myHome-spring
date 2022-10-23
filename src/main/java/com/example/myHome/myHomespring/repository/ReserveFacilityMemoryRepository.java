package com.example.myHome.myHomespring.repository;

import com.example.myHome.myHomespring.domain.ReserveFacilityTitle;

import java.util.*;

public class ReserveFacilityMemoryRepository implements ReserveFacilityRepository {
    private static Map<Long, ReserveFacilityTitle> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public ReserveFacilityTitle save(ReserveFacilityTitle reserveFacilityTitle) {
        reserveFacilityTitle.setId(++sequence);
        store.put(reserveFacilityTitle.getId(), reserveFacilityTitle);
        return reserveFacilityTitle;
    }

    @Override
    public List<ReserveFacilityTitle> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<ReserveFacilityTitle> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<ReserveFacilityTitle> findByTitle(String title) {
        return store.values().stream()
                .filter(reserveFacilityTitle -> reserveFacilityTitle.getTitle().equals(title))
                .findAny();
    }

    @Override
    public Optional<ReserveFacilityTitle> delFacility(String delTitle) {
        findByTitle(delTitle)
                .ifPresent(findReserveFacilityTitle -> store.remove(findReserveFacilityTitle.getId()));
        return Optional.empty();
    }

    public void clearStore() {
        store.clear();
    }
}
