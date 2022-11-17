/**
 * 
 *     errorCode 1xxx : reserve page 에러 모음
 * 
 * 
 * 
 */

function errorCode01() {
    alert("통신 에러가 발생했습니다.");
    return;
}

function errorCode1001() {
    alert("[ERR-1001] 설비 예약 시작시간이 종료시간보다 늦습니다.");
    return;
}


function errorCode1002() {
    alert("[ERR-1002] 설비 예약이 기존시간보다 오른쪽에서 겹칠 때입니다.");
    return;
}

function errorCode1003() {
    alert("[ERR-1003] 설비 예약이 기존시간보다 왼쪽에서 겹칠 때입니다.");
    return;
}

function errorCode1004() {
    alert("[ERR-1004] 설비 예약이 기존시간이랑 완전히 겹치는 경우입니다.");
    return;
}

function errorCode1005() {
    alert("[ERR-1005] 설비 예약이 기존시간보다 더 크게 겹치는 경우입니다.");
    return;
}

function errorCode1006() {
    alert("[ERR-1006] 설비 예약 실패! (중복 예약)");
    return;
}

function errorCode1007() {
    alert("[ERR-1007] 현재 설비의 예약시간이 없습니다.");
    return;
}

function errorCode1008() {
    alert("[ERR-1008] 문자열 처리 에러 발생");
    return;
}