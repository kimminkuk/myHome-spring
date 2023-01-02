package com.example.myHome.myHomespring.repository.quant;

import com.example.myHome.myHomespring.domain.quant.QuantStrategyMember;

import java.util.*;

public class QuantStrategyMemberRepository implements QuantStrategyRepository {

    private static Map<Long, QuantStrategyMember> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public QuantStrategyMember save(QuantStrategyMember member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<QuantStrategyMember> findById(Long id) {

        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<QuantStrategyMember> findByTitle(String title) {
        return store.values().stream()
                .filter(member -> member.getStrategyTitle().equals(title))
                .findAny();
    }

    @Override
    public Optional<QuantStrategyMember> delStrategy(String delTitle) {
        findByTitle(delTitle).ifPresent(member -> store.remove(member.getId()));
        return Optional.empty();
    }

    @Override
    public List<QuantStrategyMember> findAll() { return new ArrayList<>(store.values()); }
}
