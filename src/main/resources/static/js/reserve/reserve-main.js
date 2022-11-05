function reserveItemMake() {
    let reserveMainItemMakeBtn = document.querySelector(".reserve-main-item-make");
    if (reserveMainItemMakeBtn.style.display == "none") {
        reserveMainItemMakeBtn.style.display = "block";
    } else {
        reserveMainItemMakeBtn.style.display = "none";
    }
    return;
}

function reserveMainItemMakeBtnFunc() {
    //이거도 그냥 ajax로 하지말고, 리다이렉트로 하자
    //let ur = 'http://localhost:8080/reserve/reserve-main/make';
    let ur = 'http://localhost:8080/reserve/reserve-main/make-v2';
    let reserveMainItemTitleValue = document.querySelector("#reserveMainItemTitle").value;
    if (reserveMainItemTitleValue == null || reserveMainItemTitleValue == "") {
        alert("[ERR-902] 아이템 제목을 입력해주세요.");
        return;
    }
    if (typeof reserveMainItemTitleValue != "string") {
        alert("[ERR-903] 아이템 제목은 문자열이어야 합니다.");
        return;
    }
    var tempUserName = "mk.yoda@nklkb.com";
    var tempReserveTime = "0000-00-00 00:00~0000-00-00 00:00";
    var data = 'facilityTitle=' + encodeURIComponent(reserveMainItemTitleValue);
    data += '&reserveTime=' + encodeURIComponent(tempReserveTime);
    data += '&userName=' + encodeURIComponent(tempUserName);
    ur = ur + '?' + data;
    location.href = ur;
    return;
}

function reserveDelVisible() {
    let reserveFacilityTitles = document.querySelectorAll(".reserve-iter-list-title");
    let titlesLength = reserveFacilityTitles.length;
    for (let i = 0; i < titlesLength; i++) {
        let titleDeleteBtn = reserveFacilityTitles[i].querySelector(".title-delete-btn");
        if (titleDeleteBtn.style.display == "none") {
            titleDeleteBtn.style.display = "block";
        } else {
            titleDeleteBtn.style.display = "none";
        }
    }
    return;
}

function reserveItemDeleteMakeInit() {
    let reserveFacilityTitles = document.querySelectorAll(".reserve-iter-list-title");
    let titlesLength = reserveFacilityTitles.length;
    // reserveFacilityTitles iter
    for (let titleIdx = 0; titleIdx < titlesLength; titleIdx++) {
        let titleDeleteBtn = document.createElement("div");
        // titleDeleteBtn class이름은 title-delete-btn
        titleDeleteBtn.className = "title-delete-btn";
        titleDeleteBtn.innerHTML = "X";
        // titleDeleteBtn에 border-radius: 50%를 줘야함.
        titleDeleteBtn.style.borderRadius = "50%";
        titleDeleteBtn.style.background = "white";
        titleDeleteBtn.style.color = "RED";
        titleDeleteBtn.style.width = "20px";
        titleDeleteBtn.style.height = "20px";
        titleDeleteBtn.style.textAlign = "center";
        titleDeleteBtn.style.lineHeight = "20px";
        titleDeleteBtn.style.position = "relative";
        titleDeleteBtn.style.left = "0px";
        // titleDeleteBtn border 1px solid black
        titleDeleteBtn.style.border = "1px solid black";
        
        // titleDeleteBtn 을 숨겨줘
        titleDeleteBtn.style.display = "none";
        // titleDeleteBtn에 마우스를 올리면 deleteBtnMouseOver()를 실행해
        titleDeleteBtn.addEventListener("mouseover", deleteBtnMouseOver);
        
        //reserveFacilityTitles[i]에 titleDeleteBtn을 appendChild
        reserveFacilityTitles[titleIdx].appendChild(titleDeleteBtn);
        
        // titleDeleteBtn에 마우스를 누르면 deleteFacilityTitleBtn()를 textContent를 인자로 실행해
        titleDeleteBtn.addEventListener("click", function(){
            let delTitleText = this.parentElement.textContent;
            delTitleText = delTitleText.replace(titleDeleteBtn.textContent, "");
            deleteFacilityTitleBtn(delTitleText);
        });        
    }
    return;
}

function deleteBtnMouseOver() {
    //background 색을 연한 회색으로 바꿔줘
    this.style.backgroundColor = "#e0e0e0";
    // 마우스를 떼면 background 색을 하얀색으로 바꾸는 function 실행하게 해줘
    this.addEventListener("mouseout", function(){
        this.style.backgroundColor = "white";
    });
    return;
}

function deleteFacilityTitleBtn(delTitle) {
    // 근데 삭제를 ajax로 해야하나???
    // 어차피 리다이렉트를 해야하니깐 그냥 redirect로 하자
    let data = delTitle;
    let ur = 'http://localhost:8080/reserve/reserve-main/delete';
    if (typeof data != "string") {
        alert("[ERR-900] 설비예약은 문자열이어야 합니다.");
        return;
    }

    if (data == null || data == "") {
        alert("[ERR-901] 설비예약은 빈 문자열이 될 수 없습니다.");
        return;
    }
    data = "delFacTitle=" + encodeURIComponent(data);
    ur = ur + '?' + data;
    location.href = ur;
    return;
}


/**
 *    String to hour, minute
 *    달력 정보는 2022
 */
function getTimeToMinute(dayTime, hourTime, calendar2022) {
    let year = 365, hour = 24, minute = 60;
    let dayTimeArr = dayTime.split("-");
    let hourTimeArr = hourTime.split(":");
    let dayTimeToMinute = parseInt(dayTimeArr[0]) * year * hour * minute +
        calendar2022[parseInt(dayTimeArr[1])] * hour * minute +
        parseInt(dayTimeArr[2]) * minute;
    
    let hourTimeToMinute = parseInt(hourTimeArr[0]) * minute + parseInt(hourTimeArr[1]);
    let resultTime = dayTimeToMinute + hourTimeToMinute;
    return resultTime;
}

/**
 *    예약 칸을 나눠주는 함수
 */
function getResDivMod(resTime) {
    let resTimeArr = resTime.split(":");
    let resHour = parseInt(resTimeArr[0]);
    let resMinute = parseInt(resTimeArr[1]);
    let resMod = resHour * 2;
    if (resMinute == 30) {
        resMod += 1;
    }
    return resMod;
}

/**
 *    첫 화면 로드 시, 현재 예약되어 있는 설비 시간을해서 div에 넣는다.
 */
function initDispFacReserveTime() {
    let facReserveTimes = document.querySelectorAll(".reserve-iter-list-time");
    let facReserveTimesLength = facReserveTimes.length;
    let calendar2022 = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
    let resStartTimes = new Array();
    let resEndTimes = new Array();
    let facDispTopOffset = 5;
    
    for ( let facIdx = 0; facIdx < facReserveTimesLength; facIdx++ ) {
        //step1. 현재 설비의 예약된 시간을(String형태) 가져온다.
        let curReserveTime = facReserveTimes[facIdx].getAttribute("value");
        let curReserveTimeArr = curReserveTime.split(",");
        let curResTimeLength = curReserveTimeArr.length;
        let curResStartTimeDiv = new Array();
        let curResEndTimeDiv = new Array();
        
        //step2. 예약된 시간들을 하나씩 설정해준다.
        for ( let curTimeArrIdx = 0; curTimeArrIdx < curResTimeLength; curTimeArrIdx++ ) {
            //step2-1. ~ 표시로 예약 시작과 끝을 구분해준다.
            let curResStartEndTimes = curReserveTimeArr[curTimeArrIdx].split("~");
            let curResStartEndTimesLen = curResStartEndTimes.length;
            //step2-2. 예약 시작과 끝을 구분해준다.
            for ( let curResIdx = 0; curResIdx < curResStartEndTimesLen; curResIdx++ ) {
                //let startEndTime = new Array(curResStartEndTimes[curResIdx].trim().split(" "));
                let startEndTime = curResStartEndTimes[curResIdx].trim().split(" ");
            
                if ( (curResIdx & 1) == 0 ) {
                    resStartTimes.push(getTimeToMinute(startEndTime[0], startEndTime[1], calendar2022));
                    curResStartTimeDiv.push(startEndTime[1]);
                } else {
                    resEndTimes.push(getTimeToMinute(startEndTime[0], startEndTime[1], calendar2022));
                    curResEndTimeDiv.push(startEndTime[1]);
                }
            }            
        }
        let resDivLen = curResEndTimeDiv.length;
        for ( let resDivIdx = 0; resDivIdx < resDivLen; resDivIdx++ ) {
            let divStart = getResDivMod(curResStartTimeDiv[resDivIdx]);
            let divEnd = getResDivMod(curResEndTimeDiv[resDivIdx]);
            for ( let divIdx = divStart; divIdx < divEnd; divIdx++ ) {
                let divResColor = document.querySelector("body > div.reserve-main-facility-table > table > thead > tr:nth-child(" + facIdx + facDispTopOffset + ") > th.reserve-iter-list-time > div:nth-child(" + divIdx + ")");
                divResColor.style.background = "red";
            }
        }
    }

    
    return;
}

/**
 *    예약 시간 grid에 div 속성 추가
 * 
 *    div 속성:
 *    document.querySelector("body > div.reserve-main-facility-table > table > thead > tr:nth-child(5) > th.reserve-iter-list-time > div:nth-child(14)")
 */
function reserveTimeGridInit() {
    var facTitles = document.querySelectorAll(".reserve-iter-list-title");
    var reserveTitlesTimes = document.querySelectorAll(".reserve-iter-list-time");
    var reserveTitlesTimeLength = reserveTitlesTimes.length;
    
    for (var titleIdx = 0; titleIdx < reserveTitlesTimeLength; titleIdx++) {
        let curFacTitle = facTitles[titleIdx].innerHTML;
        reserveTitlesTimes[titleIdx].style.display = "grid";
        reserveTitlesTimes[titleIdx].style.gridTemplateColumns = "repeat(48, 1fr)";
        reserveTitlesTimes[titleIdx].style.gridTemplateRows = "1fr";

        for (let gridIdx = 0; gridIdx < 48; gridIdx++) {
            var div = document.createElement("div");
            div.style.className = "reserve-time-grid";
            div.style.border = "1px solid black";
            div.style.backgroundColor = "white";
            div.style.value = "nonReserve";
            div.addEventListener("mouseover", function() {
                //연한 초록색
                // TODO: DB에서 예약시간을 가져와서 예약된 시간은 빨간색으로 표시해준다.
                //       그런데, 미리 색을 칠해버리고 mouseout event를 추가해두니
                //       예상하지 못한 결과들이 발생한다. 그래서 삭제.
                //this.style.backgroundColor = "#e0ffe0";
                //this.addEventListener("mouseout", function() {
                //    this.style.backgroundColor = "white";
                //});

                this.addEventListener("click", function() {
                    reserveTimeGridClickVer2(curFacTitle, gridIdx);
                });
            })
            reserveTitlesTimes[titleIdx].appendChild(div);
        }
    }
    return;
}
/**
 *    아.. 이거 음...왜?
 *    DB로 연결하는게 아니라 해당 칸이 이미 설정 (true?) 되어있는지 확인하고 설정 해야함.
 *    DB연결이 아닌가??? 그러면 어떻게 날짜가 기록되어있지??
 *    날짜는 따로 저장하는건가?????? index에??
 *    Case 1) DB에 저장한다.
 *            1. 설비이름을 앞에 붙이면 될거 같다. (이름에)
 *            2. 시간이 겹치게 저장하면, 해당 설비의 예약 시간을 보고 겹치는지 확인해서 처리가 가능
 *            3. 단점으로는 DB 설계 복잡함, in-memory에 비해 속도가 느림
 * 
 *    Case 2) js In-Memory index에 저장한다.
 *            1. reserveTitlesTimes의 자식들인 div에 값을 넣어둔다.
 *            2. div 각각에 판단할 데이터 (true? 등등)으로 할당되어있는지 확인 후, 예약시간 겹치는지 확인 가능
 *            3. 이건 근데 서버가 항상 켜져있어서 가능한건가?? 
 * 
 *    결론: 어차피 혼자 해보는거니깐 Case 1, Case 2 둘다 하자
 *    먼저 Case 2로 하자. (다른곳에서는 Case2로 하고 서버를 안끌듯?)
 * 
 *    div 속성:
 *    document.querySelector("body > div.reserve-main-facility-table > table > thead > tr:nth-child(5) > th.reserve-iter-list-time > div:nth-child(14)")
 *    
 *    parent 속성
 *    document.querySelector("body > div.reserve-main-facility-table > table > thead > tr:nth-child(6) > th.reserve-iter-list-time > div:nth-child(15)")
 */     
function reserveTimeGridClickVer2(curFacTitle, curIdx) {
    // 높이는 부모노드를 찾던지, nextSlibing, 이것저것 등등으로 찾아야할듯
    let reservePopup = document.querySelector(".reserve-popup-main");
    let curTitle = document.querySelector(".reserve-popup-main-title-text");
    let curDiv = "body > div.reserve-main-facility-table > table > thead > tr:nth-child(5) > th.reserve-iter-list-time > ";
    let curDivChild = "div:nth-child(" + (curIdx + 1) + ")";
    curDiv += curDivChild;
    document.querySelector(curDiv).style.value = "reserve";

    curTitle.innerHTML = curFacTitle;

    let reserveTimeHour = document.querySelector("#reservePopupTimeScroll");
    let reserveTimeMin = document.querySelector("#reservePopupMinuteScroll");

    if (reservePopup.style.display == "none") {
        reserveTimeHour.value = String(curIdx >> 1);
        if (curIdx & 0x1) {
            reserveTimeMin.value = "30";
        } else {
            reserveTimeMin.value = "00";
        }
        let curLeft = curIdx * 15;
        let curTop = 3 * 100;
        reservePopup.style.left = curLeft + "px";
        reservePopup.style.top = curTop + "px";
        
        reservePopup.style.display = "block";
    } else {
        //reservePopup.style.display = "none";
        reservePopupClose();
    }

    return;
}

function reserveTimeGridClick(curFacTitle, curIdx) {
    // 작은 예약 창 만들기
    // 작은 예약 창에는 예약 시간, 예약 내용, 예약 버튼이 있어야함
    // 예약 시간은 현재 클릭한 시간으로 설정되어야함
    // 예약 내용은 예약 시간에 따라서 자동으로 설정되어야함
    // 예약 버튼은 예약 시간에 따라서 자동으로 설정되어야함
    // 예약 버튼을 누르면 예약이 되어야함
    // 예약 버튼을 누르면 예약 창이 닫혀야함
    // 예약 창을 닫을 때는 예약 창을 닫는 버튼이 있어야함

    var reservePopup = document.createElement("div");
    reservePopup.style.position = "abssolute";
    reservePopup.style.className = "reserve-popup-main";
    reservePopup.style.width = "300px";
    reservePopup.style.height = "150px";
    //backgroundColor를 연한 회색으로
    reservePopup.style.backgroundColor = "#e0e0e0";
    reservePopup.style.border = "1px solid black";
    reservePopup.style.zIndex = "100";
    
    var reservePopupTitle = document.createElement("div");
    reservePopupTitle.style.textContent = curFacTitle;

    // reservePopupTitle을 form 형식으로 만들어야함
    var reservePopupContent = document.createElement("div");
    reservePopupContent.style.className = "reserve-popup-title"
    reservePopupContent.style.textContent = "제목";
    
    var reservePopupContentInput = document.createElement("input");
    reservePopupContentInput.style.position = "relative";
    reservePopupContentInput.style.className = "reserve-popup-title";
    reservePopupContentInput.style.width = "80%";
    reservePopupContentInput.style.height = "30px";
    reservePopupContentInput.style.backgroundColor = "white";
    reservePopupContentInput.style.border = "1px solid black";
    reservePopupContentInput.style.textAlign = "center";
    reservePopupContentInput.type = "text";
    reservePopupContentInput.placeholder = "회의실 예약";
    reservePopupContentInput.maxLength = "48";

    this.appendChild(reservePopup);
    reservePopup.appendChild(reservePopupTitle);
    reservePopupContent.appendChild(reservePopupContentInput);
    reservePopup.appendChild(reservePopupContent);
    return;
}

function thTimeHeaderInit() {
    // timeHeader Step 1 (시간)
    let thTimeHeaderHour = document.querySelector(".reserve-iter-list-time-header-1");
    thTimeHeaderHour.style.display = "grid";
    thTimeHeaderHour.style.gridTemplateColumns = "repeat(48, 1fr)";
    thTimeHeaderHour.style.gridTemplateRows = "1fr";
    for (let gridIdx = 0; gridIdx < 48; gridIdx++) {
        let div = document.createElement("div");
        div.style.textAlign = "center";
        div.style.fontSize = "12px";
        thTimeHeaderHour.appendChild(div);
    }
    for (let gridIdx = 0; gridIdx < 24; gridIdx++) {
        let hour = "00";
        if (gridIdx < 10) {
            hour = "0" + gridIdx;
        } else {
            hour = gridIdx;
        }
        thTimeHeaderHour.children[gridIdx * 2].innerHTML = hour;
    }

    // timeHeader Step 2 (분)
    let thTimeHeaderMin = document.querySelector(".reserve-iter-list-time-header-2");
    thTimeHeaderMin.style.display = "grid";
    thTimeHeaderMin.style.gridTemplateColumns = "repeat(48, 1fr)";
    thTimeHeaderMin.style.gridTemplateRows = "1fr";
    for (let gridIdx = 0; gridIdx < 48; gridIdx++) {
        let div = document.createElement("div");
        div.style.textAlign = "center";
        div.style.fontSize = "10px";
        thTimeHeaderMin.appendChild(div);
    }
    for (let gridIdx = 0; gridIdx < 48; gridIdx++) {
        if (gridIdx & 0x1) {
            thTimeHeaderMin.children[gridIdx].innerHTML = "30";
        } else {
            thTimeHeaderMin.children[gridIdx].innerHTML = "00";
        }
    }
    return;
}

/**
 *    예약시간 저장 -> LocalStorage
 */
function makeFacReserveTimeBtn() {
    var reserveTitles = document.querySelectorAll(".reserve-iter-list-title");
    let titlesLength = reserveTitles.length;
    let reserveTimeHourValue = document.querySelector("#reservePopupTimeScroll").value;
    let reserveTimeMinValue = document.querySelector("#reservePopupMinuteScroll").value;
    let divHour = parseInt(reserveTimeHourValue) << 1
    let divMin = 0;
    if (reserveTimeMinValue == "30") {
        divMin = 1;
    } else {
        divMin = 0;
    }
    let divNum = divHour + divMin;
    alert("div: " + divNum);


    let curTitle = document.querySelector(".reserve-popup-main-title-text").innerHTML;
    for (let titleIdx = 0; titleIdx < titlesLength; titleIdx++) {
        let reserveTitle = reserveTitles[titleIdx].textContent;
        reserveTitle = reserveTitle.slice(0, reserveTitle.length - 1);
        if (reserveTitle == curTitle) {
            let titleIdxOffset = titleIdx + 5;
            let reserveDiv = document.querySelector("body > div.reserve-main-facility-table > table > thead > tr:nth-child(" + titleIdxOffset + ") > th.reserve-iter-list-time > div:nth-child(" + divNum + ")");
            reserveDiv.style.backgroundColor = "red";
        }
    }
    return;
}

/**
 *    예약시간이 겹치는지 확인
 */
function validateDuplicateReserveTime(curFacTitle, curReserveTime) {
    return;
}

/**
 *    선택한 시간을 계산 가능한 숫자로 변환
 *    return: reserveTimeResult Array (시작시간, 종료시간)
 */
function getConvRserveTime(reserveTime) {
    //init code 달력등을 표시
    let calendar2022 = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);

    //step1 날짜, 시간, 분 분리
    // ex) 2022-10-14 11:30~2022-11-11 14:00
    //     [2022-10-14 11:30] [2022-11-11 14:00]
    let timeStep1 = reserveTime.split("~");
    let timeStep1Len = timeStep1.length;

    //step2 예약 시작 시간, 종료 시간 분리
    let timeStep2 = timeStep1.trim().split(" ");
    
    //step3 시간을 계산할 수 있는 숫자로 변환
    let timeStep3_1 = getTimeToMinute(timeStep2[0][0], timeStep2[0][1], calendar2022);
    let timeStep3_2 = getTimeToMinute(timeStep2[1][0], timeStep2[1][1], calendar2022);
    let reserveTimeResult = new Array(timeStep3_1, timeStep3_2);
    return reserveTimeResult;
}

/**
 *    예약시간 저장 -> DB에 저장
 */
function makeFacReserveTimeForDbBtn() {
    //step1 선택한 설비의 예약 시작 시간, 종료 시간의 정보를 가져온다.
    //step2 선택한 설비의 기존 예약 시간이 겹치면 경고를 띄우고 취소 시킨다.
    //      이 부분은, Back-end에서도 처리를 했지만 추가로 Front-end에서도 처리를 해준다.
    //step3 현재 설비의 예약 시작 시간, 종료 시간의 정보를 DB에 저장한다.
    //      redirect로 새로고침처럼 행동해서 Display를 갱신한다.
    //      ex) 2022-10-14 11:30~2022-11-11 14:00
    //init code
    let tempToday = "2022-11-05";
    let reserveUr = 'http://localhost:8080/reserve/reserve-main/fac-reserve';
    let reserveUserName = "mk.yoda@nklkb.com";
    let curReserveTime = "";

    //step1 code
    let curFacTitle = document.querySelector(".reserve-popup-main-title-text").innerHTML;
    let startTimeHour = document.querySelector("#reservePopupTimeScroll").value;
    let startTimeMinute = document.querySelector("#reservePopupMinuteScroll").value;
    
    let endTimeHour = document.querySelector("#reservePopupTimeScrollEnd").value;
    let endTimeMinute = document.querySelector("#reservePopupMinuteScrollEnd").value;
    
    let startTime = tempToday + " " + String(startTimeHour) + ":" + String(startTimeMinute);
    let endTime = tempToday + " " + String(endTimeHour) + ":" + String(endTimeMinute);
    
    //String 변환 코드
    curReserveTime = startTime + "~" + endTime;
    
    //step2 code
    //TODO: 예외처리 넣어야함 (일단 패스했다는 가정합니다.)
    validateDuplicateReserveTime(curFacTitle, curReserveTime);

    //step3 code
    let data = 'facilityTitle=' + encodeURIComponent(curFacTitle) 
                + '&reserveTime=' + encodeURIComponent(curReserveTime) 
                + '&userName=' + encodeURIComponent(reserveUserName);
    reserveUr = reserveUr + '?' + data;
    location.href = reserveUr;
    return;
}


/**
 *    예약시간 선택
 */
function makeFacReserveTimeBtnVer2() {
    //redirect로 해도 그냥 잘 나오네?
    
    return;
}


function reservePopupClose() {
    document.querySelector(".reserve-popup-main").style.display = "none";
    return;
}

/**
 *     예약 시간 grid 만들기
 */
reserveTimeGridInit();

/**
 *     설비 예약 타이틀 지우기 버튼
 */
reserveItemDeleteMakeInit();

 /**
  *    th-header
  */
thTimeHeaderInit();

/**
 *     설비 예약 시간 가져오기
 */
 initDispFacReserveTime();