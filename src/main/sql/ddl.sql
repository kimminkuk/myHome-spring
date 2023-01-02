drop table if exists MEMBER_V2 CASCADE;
create table MEMBER_V2
(
    id bigint generated by default as identity,
    name varchar(255) not null,
    value varchar(255) not null,
    primary key (id)
);


-- Language: sql
-- Path: sql/ddl.sql
drop table if exists FACILITY_TITLE_V1 CASCADE;
create table FACILITY_TITLE_V1
(
    id bigint generated by default as identity,
    title varchar(1023) not null,
    primary key (id)
);

-- Language: sql
-- Path: sql/ddl.sql
-- join으로 FACILITY_TITLE_V1 테이블과 FACILITY_RESERVE_TIME_V1 테이블을 조인하여
-- FACILITY_TITLE_V1 테이블의 title 컬럼과 FACILITY_RESERVE_TIME_V1 테이블의 reserve_time 컬럼을
-- FACILITY_RESERVE_TIME_V1 테이블에 insert
drop table if exists FACILITY_RESERVE_TIME_V1 CASCADE;
create table FACILITY_RESERVE_TIME_V1
(
    id bigint generated by default as identity,
    title varchar(1023) not null,
    user_name varchar(1024) not null,
    reserve_time varchar(1023) not null,
    primary key (id)
);

-- Language: sql
-- Path: sql/ddl.sql
-- JOIN은 지금 상황에 너무 어울리지 않는다. 그래서 다시 테이블을 작성하기로 한다.
drop table if exists FACILITY_RESERVE_TIME_V2 CASCADE;
create table FACILITY_RESERVE_TIME_V2
(
    id bigint generated by default as identity,
    title varchar(1023) not null,
    user_name varchar(1024) not null,
    reserve_time varchar(1023) not null,
    primary key (id)
);

-- Language: sql
-- Path: sql/ddl.sql
-- JOIN은 지금 상황에 너무 어울리지 않는다. 그래서 다시 테이블을 작성하기로 한다.
-- member 이름이 마음에 안들어서 교체
drop table if exists FACILITY_RESERVE_TIME_V2 CASCADE;
create table FACILITY_RESERVE_TIME_V2
(
    id bigint generated by default as identity,
    fac_title varchar(1023) not null,
    user_name varchar(1024) not null,
    reserve_time varchar(1023) not null,
    primary key (id)
);

-- Language: sql
-- Path: sql/ddl.sql
-- 아 미친,, 예약 DB에서 예약내용 멤버를 만들지 않았다.
-- 그래서 다시 생성, reserve_content 컬럼을 추가
drop table if exists FACILITY_RESERVE_TIME_V3 CASCADE;
create table FACILITY_RESERVE_TIME_V3
(
    id bigint generated by default as identity,
    fac_title varchar(1023) not null,
    reserve_content varchar(1023) not null,
    user_name varchar(1024) not null,
    reserve_time varchar(1023) not null,
    primary key (id)
);

-- Language: sql
-- Path: sql/ddl.sql
-- 전략 저장 및 현재 상태 저장 DB
drop table if exists QUANT_STRATEGY_INFO CASCADE;
create table QUANT_STRATEGY_INFO
(
    id bigint generated by default as identity,
    user_name varchar(64) not null,
    save_date varchar(128) not null,
    strategy_title varchar(1024) not null,
    strategy_info varchar(1024) not null,
    primary key (id)
);