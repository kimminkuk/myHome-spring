package com.example.myHome.myHomespring.repository;

import com.example.myHome.myHomespring.domain.ReserveFacilityTitle;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReserveFacilityJdbcTemplate implements ReserveFacilityRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReserveFacilityJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ReserveFacilityTitle save(ReserveFacilityTitle reserveFacilityTitle) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("FACILITY_TITLE_V1").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", reserveFacilityTitle.getTitle());
        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        reserveFacilityTitle.setId(key.longValue());
        return reserveFacilityTitle;
    }

    @Override
    public Optional<ReserveFacilityTitle> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ReserveFacilityTitle> findByTitle(String title) {
        String sql = "select * from FACILITY_TITLE_V1 where title = ?";
        List<ReserveFacilityTitle> result = jdbcTemplate.query(sql, reserveFacilityTitleRowMapper(), title);
        return result.stream().findAny();
    }

    @Override
    public List<ReserveFacilityTitle> findAll() {
        String sql = "select * from FACILITY_TITLE_V1";
        return jdbcTemplate.query(sql, reserveFacilityTitleRowMapper());
    }

    private RowMapper<ReserveFacilityTitle> reserveFacilityTitleRowMapper() {
        return (rs, rowNum) -> {
            ReserveFacilityTitle reserveFacilityTitle = new ReserveFacilityTitle();
            reserveFacilityTitle.setId(rs.getLong("id"));
            reserveFacilityTitle.setTitle(rs.getString("title"));
            return reserveFacilityTitle;
        };
    }
}
