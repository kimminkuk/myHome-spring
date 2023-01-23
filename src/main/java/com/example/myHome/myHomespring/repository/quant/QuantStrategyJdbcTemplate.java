package com.example.myHome.myHomespring.repository.quant;

import com.example.myHome.myHomespring.domain.facility.ReserveFacilityTitle;
import com.example.myHome.myHomespring.domain.quant.ParsingDateMember;
import com.example.myHome.myHomespring.domain.quant.QuantStrategyMember;
import org.apache.catalina.util.ParameterMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class QuantStrategyJdbcTemplate implements QuantStrategyRepository {
    private final JdbcTemplate jdbcTemplate;

    public QuantStrategyJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public QuantStrategyMember save(QuantStrategyMember member) {

        //String sql = "insert into QUANT_STRATEGY_INFO (user_name, save_date, strategy_title, strategy_info) values (?, ?, ?, ?)";
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("QUANT_STRATEGY_INFO").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_name", member.getUserName());
        parameters.put("save_date", member.getSaveTime());
        parameters.put("strategy_title", member.getStrategyTitle());
        parameters.put("strategy_info", member.getStrategyInfo());
        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<QuantStrategyMember> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<QuantStrategyMember> findByTitle(String strategyTitle) {
        String sql = "select * from QUANT_STRATEGY_INFO where strategy_title = ?";
        List<QuantStrategyMember> result = jdbcTemplate.query(sql, strategyRowMapper(), strategyTitle);
        return result.stream().findAny();
    }

    @Override
    public Optional<QuantStrategyMember> delStrategy(String delTitle) {
        String sql = "delete from QUANT_STRATEGY_INFO where strategy_title = ?";
        jdbcTemplate.update(sql, delTitle);

        //h2 delete sql query 실패하는 경우 리턴 작성
        Optional<QuantStrategyMember> result = findByTitle(delTitle);
        if( result.isEmpty() ) {
            return result;
        }
        return Optional.empty();
    }

    @Override
    public List<QuantStrategyMember> findAll() {
        String sql = "select * from QUANT_STRATEGY_INFO";
        return jdbcTemplate.query(sql, strategyRowMapper());
    }

    @Override
    public ParsingDateMember addParsingDate(ParsingDateMember parsingDateMember) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("PARSING_LAST_DATE").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("parsing_date", parsingDateMember.getParsingDate());
        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        parsingDateMember.setId(key.longValue());
        return parsingDateMember;
    }

    @Override
    public Optional<ParsingDateMember> getLastParsingDateById() {
        try {
            String sql = "SELECT ID FROM PARSING_LAST_DATE ORDER BY ID DESC LIMIT 1";
            Long id = jdbcTemplate.queryForObject(sql, Long.class);
            return findByParsingDateId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<ParsingDateMember> findByParsingDateId(Long id) {
        // id로 parsing_date 찾기
        String sql = "select * from PARSING_LAST_DATE where id = ?";
        List<ParsingDateMember> query = jdbcTemplate.query(sql, parsingDateRowMapper(), id);
        return Optional.ofNullable(query.get(0));
    }

    private RowMapper<QuantStrategyMember> strategyRowMapper() {
        return (rs, rowNum) -> {
            QuantStrategyMember quantStrategyMember = new QuantStrategyMember();
            quantStrategyMember.setId(rs.getLong("id"));
            quantStrategyMember.setUserName(rs.getString("user_name"));
            quantStrategyMember.setSaveTime(rs.getString("save_date"));
            quantStrategyMember.setStrategyTitle(rs.getString("strategy_title"));
            quantStrategyMember.setStrategyInfo(rs.getString("strategy_info"));
            return quantStrategyMember;
        };
    }

    private RowMapper<ParsingDateMember> parsingDateRowMapper() {
        return (rs, rowNum) -> {
            ParsingDateMember parsingDateMember = new ParsingDateMember();
            parsingDateMember.setId(rs.getLong("id"));
            parsingDateMember.setParsingDate(rs.getString("parsing_date"));
            return parsingDateMember;
        };
    }
}
