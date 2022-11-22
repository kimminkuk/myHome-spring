package com.example.myHome.myHomespring.service.facility;

import com.example.myHome.myHomespring.domain.facility.FacReserveTimeMember;
import com.example.myHome.myHomespring.domain.facility.ReserveFacilityTitle;
import com.example.myHome.myHomespring.repository.facility.ReserveFacilityRepository;

import java.util.ArrayList;
import java.util.Calendar;
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
        //Update Ver2.0 : Ver1.0은 00:00 ~ 24:00 까지의 시간만 계산했습니다.
        //              : Ver2.0은 날짜까지 포함해서 시간을 계산합니다.
        //validateFacReserveTime(curFacReserve, reserveTime);
        validateFacReserveTimeVer2(curFacReserve, reserveTime);
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
    public void validateFacReserveTimeVer2(FacReserveTimeMember facReserveTimeMember, String reserveTime) {
        // 예약시간이 겹치는지 확인하는 코드
        // 예약시간이 겹치면 예외를 던진다.
        // facReserveTimeMember의 기존 예약 시간하고, 새로운 예약 시간을 비교해서 겹치는지 확인한다.

        //  이러면 흠,,,for문 2번돌려야하는데 어차피 저장해주는 알고리즘이니깐 좀 단순하게 하는게 맞을듯 (유지보수 편하게)
        int[] calendar2022 = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
        List<Integer> curFacResStartTimes = new ArrayList<>();
        List<Integer> curFacResEndTimes = new ArrayList<>();
        String curFacReserveTime = facReserveTimeMember.getReserveTime();
        String[] curFacReserveTimeArr = curFacReserveTime.split(",");

        for (String curFacResTime : curFacReserveTimeArr) {
            // step1 ~ 기준으로 나누기 (연속으로 예약한 목록임)
            // ex) 2022-10-14 11:30~2022-11-11 14:00 -> [2022-10-14 11:30] [2022-11-11 14:00]
            String[] curFacResTimeStep1 = curFacResTime.split("~");

            // step2 해당 설비 예약시간을 전부 반복합니다.
            int cnt = 0;
            for ( String curFacResTimeStep2 : curFacResTimeStep1 ) {

                // ex) 2022-10-14 11:30 -> [2022-10-14] [11:30]
                String[] timeStep3 = curFacResTimeStep2.strip().split(" ");

                //step3   예약시작시간과 예약종료시간을 저장합니다.
                //step3-1 분으로 바꾼 시간을 배열에 넣기
                //step3-2 curFacResTimeStep1의 짝수 홀수 구분해서 넣기
                if ( ( cnt & 1 ) == 0 ) {
                    curFacResStartTimes.add( getResDivModVer2( timeStep3[0], timeStep3[1] ) );
                } else {
                    curFacResEndTimes.add( getResDivModVer2( timeStep3[0], timeStep3[1] ) );
                }
                cnt++;
            }
        }
        //step 4 예약하는 시간을 분으로 바꾸기
        String[] reserveTimeArr = reserveTime.split("~");
        String[] curStartTime = reserveTimeArr[0].strip().split(" ");
        String[] curEndTime = reserveTimeArr[1].strip().split(" ");

        int curReserveStartTime = getResDivModVer2(curStartTime[0], curStartTime[1]);
        int curReserveEndTime = getResDivModVer2(curEndTime[0], curEndTime[1]);

        //step 4-1 혹시, 예약의 끝시간이 처음시간보다 큰 경우 (있을 수 가 있나?? 하지만 날 믿지마)
        if ( curReserveEndTime <= curReserveStartTime ) {
            throw new IllegalStateException("예약 종료시간이 예약 시작시간보다 작습니다.");
        }

        // step5 예약할 시간과 기존 설비 예약시간을 비교해서, 중복되는거 있으면 예외 던지기
        // ex) 가능한 경우 ( 예약하고 싶은 시간이 기존 시간의 끝 부분만 겹치는 경우 혹은 그 이상일 때 )
        // [기존]    |----|  |---|   |---|  |---|
        // [신규]         |--|           |-|    |----|  |---|
        // ex) 불가능한 경우 ( 예약하고 싶은 시간이 기존 시간과 겹치는 경우 )
        // [기존] |-----| |-----|        |---|
        // [신규]               |---------|
        // [신규]   |----| |-----|        |---|

        int resTimeArrLength = curFacResStartTimes.size();
        int resTimeIdx = 0;
        for (resTimeIdx = 0; resTimeIdx < resTimeArrLength; resTimeIdx++) {
            // TODO: 0000-00-00 00:00~0000-00-00 00:00 없애는 코드 구상 중 (후 순위)
            // front-end에서 확인된 에러 방지 추가
            // 근본적으로는 0000-00-00 00:00~0000-00-00 00:00 을 없애야 할거같은데 음.. 일단 다른게 더 급하니 나중에 생각하자.
            if ( ( curFacResEndTimes.get(resTimeIdx) + curFacResStartTimes.get(resTimeIdx) ) == 0 ) {
                continue;
            }
            // 1. 예약시간이 기존시간이랑 오른쪽으로 겹칠때
            // [기존] |-|  |------|       |---|
            // [신규]          |------|
            if ( curFacResEndTimes.get(resTimeIdx) > curReserveStartTime && curFacResEndTimes.get(resTimeIdx) <= curReserveEndTime ) {
                throw new IllegalStateException("[1]이미 예약된 시간입니다.");
            }
            // 2. 예약시간이 기존시간이랑 왼쪽으로 겹칠때
            // [기존]  |--|    |------|   |--|      |------| |--|
            // [신규]      |------|           |------|
            else if ( curFacResStartTimes.get(resTimeIdx) < curReserveEndTime && curFacResStartTimes.get(resTimeIdx) >= curReserveStartTime ) {
                throw new IllegalStateException("[2]이미 예약된 시간입니다.");
            }
            //3-1. 예약시간이 기존시간이랑 왼, 외 전부 겹칠 때
            // [기존]  |------------|
            // [신규]      |------|
            else if ( curFacResStartTimes.get(resTimeIdx) <= curReserveStartTime && curFacResEndTimes.get(resTimeIdx) >= curReserveEndTime ) {
                throw new IllegalStateException("[3-1]이미 예약된 시간입니다.");
            }
            //3-2
            // [기존]  |------------|
            // [신규] |--------------|
            else if ( curFacResStartTimes.get(resTimeIdx) >= curReserveStartTime && curFacResEndTimes.get(resTimeIdx) <= curReserveEndTime) {
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

    /**
     *    getResDivMod 은 예약시간을 30분단위로 나누는 함수이다.
     *    00:00 ~ 24:00까지의 시간만 계산한다.
     */
    int getResDivMod(String hourTime) {
        String[] hourTimeArr = hourTime.split(":");
        int hourTimeToMinute = Integer.parseInt(hourTimeArr[0]) * 2;
        if ( Integer.parseInt(hourTimeArr[1]) == 30 ) {
            hourTimeToMinute += 1;
        }
        return hourTimeToMinute;
    }

    /**
     *    getResDivModVer2 은 예약시간을 30분단위로 나누는 함수이다.
     *    기존에는 00:00 ~ 24:00까지의 시간만 계산했다.
     *    Ver2에서는 날짜까지 포함해서 계산합니다.
     */
    int getResDivModVer2(String dayTime, String hourTime) {
        String[] dayTimeArr = dayTime.split("-");
        String[] hourTimeArr = hourTime.split(":");
        int timeModValue = 48;
        // java calendar code
        // ex) dayTimeArr[1] -> 1 ~ 12 , Calendar.JANUARY -> 0
        int dayOffset = 0;
        Calendar dayOffsetCal = Calendar.getInstance();
        for ( int monthIdx = 1; monthIdx < Integer.parseInt(dayTimeArr[1]); monthIdx++ ) {
            //Calendar 클래스에서 달력의 마지막날짜 가져와서 예약하는 날짜가 포함되어 있는 달 이전 날짜들을 합해준다.
            dayOffsetCal.set(Calendar.YEAR, Integer.parseInt(dayTimeArr[0]));
            dayOffsetCal.set(Calendar.MONTH, monthIdx - 1);
            dayOffset += dayOffsetCal.getActualMaximum(Calendar.DAY_OF_MONTH );
        }

        int dayValue = ( Integer.parseInt(dayTimeArr[0]) * 365 + ( dayOffset + Integer.parseInt(dayTimeArr[2]) ) ) * timeModValue;
        int hourValue = getResDivMod(hourTime);
        return dayValue + hourValue;
    }

}
