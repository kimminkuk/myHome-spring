function reserveItemMake() {
    let reserveMainItemMakeBtn = document.querySelector(".reserve-main-item-make");
    if (reserveMainItemMakeBtn.style.display == "none") {
        reserveMainItemMakeBtn.style.display = "block";
    } else {
        reserveMainItemMakeBtn.style.display = "none";
    }
    return;
}

function reserveMainItemMakeBtn() {
    var xhr = new XMLHttpRequest();
    var ur = 'http://localhost:8080/reserve/reserve-main/make';
    xhr.onreadystatechange = function() {
        if ( xhr.readyState === xhr.DONE ) {
            if (xhr.status === 200 || xhr.status === 201) {
                console.log(xhr.responseText);
            } else {
                console.error(xhr.responseText);
                alert("[ERR-005] 예약 항목 만들기 실패! 통신 오류입니다.");
                
                //TODO: 이건 자바스크립트에서 어떻게 처리하지?? 데이터를 어떻게 받아오지 흠.
                alert("[ERR-006] 예약 항목 만들기 실패! 같은 이름의 설비가 존재합니다.");
            }
        }
    };
    let reserveMainItemTitleValue = document.querySelector("#reserveMainItemTitle").value;
    if (reserveMainItemTitleValue == null || reserveMainItemTitleValue == "") {
        alert("아이템 제목을 입력해주세요.");
        return;
    }
    if (typeof reserveMainItemTitleValue != "string") {
        alert("아이템 제목은 문자열이어야 합니다.");
        return;
    }
    var data = 'facilityTitle=' + encodeURIComponent(reserveMainItemTitleValue);
    ur = ur + '?' + data;
    xhr.open('GET', ur);
    xhr.send();
    return;
}