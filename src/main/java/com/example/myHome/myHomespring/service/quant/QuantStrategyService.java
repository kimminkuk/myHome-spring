package com.example.myHome.myHomespring.service.quant;

import com.example.myHome.myHomespring.domain.quant.QuantStrategyMember;
import com.example.myHome.myHomespring.repository.quant.QuantStrategyRepository;

import java.util.List;
import java.util.Optional;

public class QuantStrategyService {
    private final QuantStrategyRepository quantStrategyRepository;

    public QuantStrategyService(QuantStrategyRepository quantStrategyRepository) {
        this.quantStrategyRepository = quantStrategyRepository;
    }

    /**
     *   전략 저장
     */
    public Long strategySave(QuantStrategyMember quantStrategyMember) {
        validDuplicateStrategy(quantStrategyMember);
        quantStrategyRepository.save(quantStrategyMember);
        return quantStrategyMember.getId();
    }

    /**
     *   전략 찾기
     */
    public Optional<QuantStrategyMember> findStrategy(String strategyTitle) {
        return quantStrategyRepository.findByTitle(strategyTitle);
    }

    /**
     *   전략 전체 조회
     */
    List<QuantStrategyMember> findStrategies() {
        return quantStrategyRepository.findAll();
    }

    /**
     *  전략 삭제
     */
    public Optional<QuantStrategyMember> delStrategy(String delStrategyTitle) {
        return quantStrategyRepository.delStrategy(delStrategyTitle);
    }

    private void validDuplicateStrategy(QuantStrategyMember quantStrategyMember) {
        quantStrategyRepository.findByTitle(quantStrategyMember.getStrategyTitle())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 전략입니다.");
                });
    }
}
