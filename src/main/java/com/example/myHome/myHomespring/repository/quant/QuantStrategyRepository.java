package com.example.myHome.myHomespring.repository.quant;

import com.example.myHome.myHomespring.domain.quant.ParsingDateMember;
import com.example.myHome.myHomespring.domain.quant.QuantStrategyMember;

import java.util.List;
import java.util.Optional;

public interface QuantStrategyRepository {
    QuantStrategyMember save(QuantStrategyMember member);
    Optional<QuantStrategyMember> findById(Long id);
    Optional<QuantStrategyMember> findByTitle(String title);
    Optional<QuantStrategyMember> delStrategy(String delTitle);
    List<QuantStrategyMember> findAll();

    ParsingDateMember addParsingDate(ParsingDateMember parsingDateMember);
    Optional<ParsingDateMember> getLastParsingDateById();
    Optional<ParsingDateMember> findByParsingDateId(Long id);
}
