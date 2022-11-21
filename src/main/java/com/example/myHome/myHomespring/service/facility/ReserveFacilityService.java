package com.example.myHome.myHomespring.service.facility;

import com.example.myHome.myHomespring.domain.facility.FacReserveTimeMember;
import com.example.myHome.myHomespring.domain.facility.ReserveFacilityTitle;
import com.example.myHome.myHomespring.repository.facility.ReserveFacilityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReserveFacilityService {
    private final ReserveFacilityRepository reserveFacilityRepository;

    public ReserveFacilityService(ReserveFacilityRepository reserveFacilityRepository) {
        this.reserveFacilityRepository = reserveFacilityRepository;
    }

    /**
     *  설비 예약 타이틀 생성
     */
    public Long join(ReserveFacilityTitle reserveFacilityTitle) {
        validateDuplicateFacilityTitle(reserveFacilityTitle);
        reserveFacilityRepository.save(reserveFacilityTitle);
        return reserveFacilityTitle.getId();
    }

    /**
     *  예약 가능한 설비 전체 조회
     */
    public List<ReserveFacilityTitle> findReserveFacilityTitles() {
        return reserveFacilityRepository.findAll();
    }

    /**
     *  예약 가능한 설비 전체 조회 버전2
     */
    public List<FacReserveTimeMember> findReserveFacAll() {
        return reserveFacilityRepository.findReserveFacAll();
    }

    /**
     *  설비 예약 타이틀 찾기
     */
    public Optional<ReserveFacilityTitle> findOne(Long reserveFacilityTitleId) {
        return reserveFacilityRepository.findById(reserveFacilityTitleId);
    }

    /**
     * 설비 예약 타이틀 삭제
     */
    public Optional<ReserveFacilityTitle> delFacility(String delTitle) {
       return reserveFacilityRepository.delFacility(delTitle);
    }

    /**
     * 설비 예약 타이틀 삭제 버전2
     */
    public Optional<FacReserveTimeMember> delReserveFac(String facTitle) {
        return reserveFacilityRepository.delReserveFac(facTitle);
    }

    /**
     * 설비 예약 시간 설정
     */
    public Long reserveTime(ReserveFacilityTitle curFacility, FacReserveTimeMember curFacReserveTime, String reserveTime) {
        reserveFacilityRepository.saveReserveTime(curFacility, curFacReserveTime, reserveTime);
        return curFacReserveTime.getId();
    }

    /**
     * 설비 예약 시간 설정 버전2 ( 조인 안하고 이거 쓰기로함)
     */
    public Long facReserve(FacReserveTimeMember curFacReserve, String reserveTime) {
        validateFacReserveTime(curFacReserve, reserveTime);
        reserveFacilityRepository.reserveFacility(curFacReserve, reserveTime);
        return curFacReserve.getId();
    }

    /**
     * 설비 예약 버전2 처음 만들시
     */
    public Long facReserveFirst(FacReserveTimeMember curFacReserve) {
        validateDuplicateFacilityTitleVer2(curFacReserve);
        reserveFacilityRepository.facInitReserveSave(curFacReserve);
        return curFacReserve.getId();
    }

    /**
     * 선택한 설비의 예약시간 확인하기
     */
    public Optional<String> getCurFacReserveTime(String facTitle) {
        return reserveFacilityRepository.findCurFacReserveTime(facTitle);
    }

    private void validateDuplicateFacilityTitle(ReserveFacilityTitle reserveFacilityTitle) {
        reserveFacilityRepository.findByTitle(reserveFacilityTitle.getTitle())
                .ifPresent( title -> {
                    throw new IllegalStateException("이미 존재하는 설비 예약 타이틀입니다.");
                });
    }


    private void validateDuplicateFacilityTitleVer2(FacReserveTimeMember facReserveTimeMember) {
        reserveFacilityRepository.findByReserveFacTitle(facReserveTimeMember.getReserveFacTitle())
                .ifPresent( fac_title -> {
                    throw new IllegalStateException("이미 존재하는 설비 예약 타이틀입니다.");
                });
    }

    /**
     *  설명은 ReserveFacilityMemoryRepository 에 있음
     */
    private void validateFacReserveTime(FacReserveTimeMember facReserveTimeMember, String reserveTime) {
        int[] calendar2022 = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
        List<Integer> resStartTimes = new ArrayList<>();
        List<Integer> resEndTimes = new ArrayList<>();
        String curFacReserveTime = facReserveTimeMember.getReserveTime();
        String[] curFacReserveTimeArr = curFacReserveTime.split(",");

        for (String curFacResTime : curFacReserveTimeArr) {
            String[] curFacResTimeStep1 = curFacResTime.split("~");

            int cnt = 0;
            for (String curFacResTimeStep2 : curFacResTimeStep1) {
                String[] timeStep3 = curFacResTimeStep2.strip().split(" ");

                if ((cnt & 1) == 0) { //0, 2
                    resStartTimes.add(getTimeToMinute(timeStep3[0], timeStep3[1], calendar2022));
                } else { //1, 3
                    resEndTimes.add(getTimeToMinute(timeStep3[0], timeStep3[1], calendar2022));
                }
                cnt++;
            }
        }

        String[] reserveTimeArr = reserveTime.split("~");
        String[] curStartTime = reserveTimeArr[0].strip().split(" ");
        String[] curEndTime = reserveTimeArr[1].strip().split(" ");

        int curReserveStartTime = getTimeToMinute(curStartTime[0], curStartTime[1], calendar2022);
        int curReserveEndTime = getTimeToMinute(curEndTime[0], curEndTime[1], calendar2022);

        if (curReserveEndTime <= curReserveStartTime) {
            throw new IllegalStateException("예약 종료시간이 예약 시작시간보다 작습니다.");
        }

        int resTimeArrLength = resStartTimes.size();
        int resTimeIdx = 0;
        for (resTimeIdx = 0; resTimeIdx < resTimeArrLength; resTimeIdx++) {
            // 1. 예약시간이 기존시간이랑 오른쪽으로 겹칠때
            // [기존] |-|  |------|       |---|
            // [신규]          |------|
            if (resEndTimes.get(resTimeIdx) > curReserveStartTime && resEndTimes.get(resTimeIdx) <= curReserveEndTime) {
                throw new IllegalStateException("[1]이미 예약된 시간입니다.");
            }
            // 2. 예약시간이 기존시간이랑 왼쪽으로 겹칠때
            // [기존]  |--|    |------|   |--|      |------| |--|
            // [신규]      |------|           |------|
            else if (resStartTimes.get(resTimeIdx) < curReserveEndTime && resStartTimes.get(resTimeIdx) >= curReserveStartTime) {
                throw new IllegalStateException("[2]이미 예약된 시간입니다.");
            }
            //3-1. 예약시간이 기존시간이랑 왼, 외 전부 겹칠 때
            // [기존]  |------------|
            // [신규]      |------|
            else if (resStartTimes.get(resTimeIdx) <= curReserveStartTime && resEndTimes.get(resTimeIdx) >= curReserveEndTime) {
                throw new IllegalStateException("[3-1]이미 예약된 시간입니다.");
            }
            //3-2
            // [기존]  |------------|
            // [신규] |--------------|
            else if (resStartTimes.get(resTimeIdx) >= curReserveStartTime && resEndTimes.get(resTimeIdx) <= curReserveEndTime) {
                throw new IllegalStateException("[3-2]이미 예약된 시간입니다.");
            }
        }

        // step6 예외가 안뜨면 저장하기
        return;
    }

    /**
     *    validateFacReserveTimeVer2 js code 검증 완료
     *    Front-end에서 Update가 안되어있는 상태에서는 예약시간이 겹치는지 확인이 안됨
     *    따라서, Back-end에서도 예약시간이 겹치는지 확인해야함
     */
    private void validateFacReserveTimeVer2(FacReserveTimeMember facReserveTimeMember, String reserveTime) {
        int[] calendar2022 = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        List<Integer> resStartTimes = new ArrayList<>();
        List<Integer> resEndTimes = new ArrayList<>();
        String curFacReserveTime = facReserveTimeMember.getReserveTime();
        String[] curFacReserveTimeArr = curFacReserveTime.split(",");

        for (String curFacResTime : curFacReserveTimeArr) {
            String[] curFacResTimeStep1 = curFacResTime.split("~");

            int cnt = 0;
            for (String curFacResTimeStep2 : curFacResTimeStep1) {
                String[] timeStep3 = curFacResTimeStep2.strip().split(" ");

                if ((cnt & 1) == 0) { //0, 2
                    resStartTimes.add(getTimeToMinute(timeStep3[0], timeStep3[1], calendar2022));
                } else { //1, 3
                    resEndTimes.add(getTimeToMinute(timeStep3[0], timeStep3[1], calendar2022));
                }
                cnt++;
            }
        }

        String[] reserveTimeArr = reserveTime.split("~");
        String[] curStartTime = reserveTimeArr[0].strip().split(" ");
        String[] curEndTime = reserveTimeArr[1].strip().split(" ");

        int curReserveStartTime = getTimeToMinute(curStartTime[0], curStartTime[1], calendar2022);
        int curReserveEndTime = getTimeToMinute(curEndTime[0], curEndTime[1], calendar2022);

        if (curReserveEndTime <= curReserveStartTime) {
            throw new IllegalStateException("예약 종료시간이 예약 시작시간보다 작습니다.");
        }

        int resTimeArrLength = resStartTimes.size();
        int resTimeIdx = 0;
        for (resTimeIdx = 0; resTimeIdx < resTimeArrLength; resTimeIdx++) {
            // 1. 예약시간이 기존시간이랑 오른쪽으로 겹칠때
            // [기존] |-|  |------|       |---|
            // [신규]          |------|
            if (resEndTimes.get(resTimeIdx) > curReserveStartTime && resEndTimes.get(resTimeIdx) <= curReserveEndTime) {
                throw new IllegalStateException("[1]이미 예약된 시간입니다.");
            }
            // 2. 예약시간이 기존시간이랑 왼쪽으로 겹칠때
            // [기존]  |--|    |------|   |--|      |------| |--|
            // [신규]      |------|           |------|
            else if (resStartTimes.get(resTimeIdx) < curReserveEndTime && resStartTimes.get(resTimeIdx) >= curReserveStartTime) {
                throw new IllegalStateException("[2]이미 예약된 시간입니다.");
            }
            //3-1. 예약시간이 기존시간이랑 왼, 외 전부 겹칠 때
            // [기존]  |------------|
            // [신규]      |------|
            else if (resStartTimes.get(resTimeIdx) <= curReserveStartTime && resEndTimes.get(resTimeIdx) >= curReserveEndTime) {
                throw new IllegalStateException("[3-1]이미 예약된 시간입니다.");
            }
            //3-2
            // [기존]  |------------|
            // [신규] |--------------|
            else if (resStartTimes.get(resTimeIdx) >= curReserveStartTime && resEndTimes.get(resTimeIdx) <= curReserveEndTime) {
                throw new IllegalStateException("[3-2]이미 예약된 시간입니다.");
            }
        }

        // step6 예외가 안뜨면 저장하기
        return;
    }

    private int getTimeToMinute(String dayTime, String hourTime, int[] calendar2022) {
        String[] dayTimeArr = dayTime.split("-");
        String[] hourTimeArr = hourTime.split(":");

        int dayTimeToMinute = Integer.parseInt(dayTimeArr[0]) * 365 * 24 * 60 +
                calendar2022[Integer.parseInt(dayTimeArr[1])] * 24 * 60 +
                Integer.parseInt(dayTimeArr[2]) * 60;

        int hourTimeToMinute = Integer.parseInt(hourTimeArr[0]) * 60 + Integer.parseInt(hourTimeArr[1]);

        int resultTime = dayTimeToMinute + hourTimeToMinute;
        return resultTime;
    }
}
