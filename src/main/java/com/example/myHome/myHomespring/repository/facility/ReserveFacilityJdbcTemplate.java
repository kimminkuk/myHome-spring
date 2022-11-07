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
    public FacReserveTimeMember reserveFacility(FacReserveTimeMember curFacReserveTime, String reserveTime) {
        // FACILITY_RESERVE_TIME_V2 테이블에 예약시간을 저장합니다.
        //String sql = "update FACILITY_RESERVE_TIME_V2 set reserve_time = ? where fac_title = ?";

        // FACILITY_RESERVE_TIME_V3 테이블에 예약시간을 저장합니다.
        String sql = "update FACILITY_RESERVE_TIME_V3 set reserve_time = ? where fac_title = ?";

        String reserveTimeResult = curFacReserveTime.getReserveTime() + ", " + reserveTime;
        jdbcTemplate.update(sql, reserveTimeResult, curFacReserveTime.getReserveFacTitle());
        curFacReserveTime.setReserveTime(reserveTimeResult);
        return curFacReserveTime;
    }

    @Override
    public FacReserveTimeMember facInitReserveSave(FacReserveTimeMember curFacReserveTime) {
        // FACILITY_RESERVE_TIME_V2 테이블을 처음 생성 시..
        // FACILITY_RESERVE_TIME_V3 로 변경
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("FACILITY_RESERVE_TIME_V3").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fac_title", curFacReserveTime.getReserveFacTitle());
        parameters.put("reserve_content", curFacReserveTime.getReserveContent());
        parameters.put("user_name", curFacReserveTime.getUserName());
        parameters.put("reserve_time", curFacReserveTime.getReserveTime());
        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        curFacReserveTime.setId(key.longValue());
        return curFacReserveTime;
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
    public Optional<FacReserveTimeMember> findByReserveFacTitle(String facTitle) {
        String sql = "select * from FACILITY_RESERVE_TIME_V3 where fac_title = ?";
        List<FacReserveTimeMember> result = jdbcTemplate.query(sql, facReserveTimeMemberRowMapper(), facTitle);
        return result.stream().findAny();
    }

    @Override
    public List<ReserveFacilityTitle> findAll() {
        String sql = "select * from FACILITY_TITLE_V1";
        return jdbcTemplate.query(sql, reserveFacilityTitleRowMapper());
    }

    @Override
    public List<FacReserveTimeMember> findReserveFacAll() {
        String sql = "select * from FACILITY_RESERVE_TIME_V3";
        return jdbcTemplate.query(sql, facReserveTimeMemberRowMapper());
    }

    @Override
    public Optional<ReserveFacilityTitle> delFacility(String delTitle) {
        // 삭제 sql query
        String sql = "delete from FACILITY_TITLE_V1 where title = ?";
        jdbcTemplate.update(sql, delTitle);
        return Optional.empty();
    }

    @Override
    public Optional<FacReserveTimeMember> delReserveFac(String facTitle) {
        String sql = "delete from FACILITY_RESERVE_TIME_V3 where fac_title = ?";
        jdbcTemplate.update(sql, facTitle);
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

    @Override
    public Optional<String> findCurFacReserveTime(String facTitle) {
        String sql = "select * from FACILITY_RESERVE_TIME_V3 where fac_title = ?";
        List<FacReserveTimeMember> result = jdbcTemplate.query(sql, facReserveTimeMemberRowMapper(), facTitle);

        return result.stream().map(FacReserveTimeMember::getReserveTime).findAny();
    }

    private RowMapper<ReserveFacilityTitle> reserveFacilityTitleRowMapper() {
        return (rs, rowNum) -> {
            ReserveFacilityTitle reserveFacilityTitle = new ReserveFacilityTitle();
            reserveFacilityTitle.setId(rs.getLong("id"));
            reserveFacilityTitle.setTitle(rs.getString("title"));
            return reserveFacilityTitle;
        };
    }

    private RowMapper<FacReserveTimeMember> facReserveTimeMemberRowMapper() {
        return (rs, rowNum) -> {
            FacReserveTimeMember facReserveTimeMember = new FacReserveTimeMember();
            facReserveTimeMember.setId(rs.getLong("id"));
            facReserveTimeMember.setReserveFacTitle(rs.getString("fac_title"));
            facReserveTimeMember.setReserveContent(rs.getString("reserve_content"));
            facReserveTimeMember.setUserName(rs.getString("user_name"));
            facReserveTimeMember.setReserveTime(rs.getString("reserve_time"));
            return facReserveTimeMember;
        };
    }
}
