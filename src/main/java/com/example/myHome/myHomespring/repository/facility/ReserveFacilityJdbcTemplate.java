package com.example.myHome.myHomespring.repository.facility;

import com.example.myHome.myHomespring.domain.facility.FacReserveTimeMember;
import com.example.myHome.myHomespring.domain.facility.ReserveFacilityTitle;
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

    @Override
    public Optional<ReserveFacilityTitle> delFacility(String delTitle) {
        // 삭제 sql query
        String sql = "delete from FACILITY_TITLE_V1 where title = ?";
        jdbcTemplate.update(sql, delTitle);
        return Optional.empty();
    }

    //조인을 해야하나????? 그냥 새로 만들면 안될려나??????
    // 그냥 새로만들어야겠다. 설계가 너무 꼬인다. 이러면
    @Override
    public FacReserveTimeMember saveReserveTime(ReserveFacilityTitle curFacility, FacReserveTimeMember curFacReserveTime, String reserveTime) {
        //join sql query
        // FACILITY_TITLE_V1 테이블의 title, FACILITY_RESERVE_TIME_V1 테이블의 title 멤버를 사용해서 테이블을 조인합니다.
        // 조인 테이블 코드
        String sql = "insert into FACILITY_RESERVE_TIME_V1 (title, title) values (?, ?)";
        jdbcTemplate.update(sql, curFacility.getTitle(), curFacReserveTime.getReserveTime());
        return curFacReserveTime;
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
