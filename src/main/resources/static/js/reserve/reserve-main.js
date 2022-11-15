var G_calendar2022 = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
var G_topOffsetIdx = 5;

function reserveItemMake() {
    //변수 선언
    let reserveMainItemMakeBtn = document.querySelector(".reserve-main-item-make");
    let reserveMainItemMakeCloseBtn = document.querySelector(".reserve-main-item-make-title-close");
    let reserveMainItemMakeBtnSend = document.querySelector(".reserve-main-item-make-btn-send");

    mouseOnOffStyleMake(reserveMainItemMakeCloseBtn, "#737373");
    mouseOnOffStyleMake(reserveMainItemMakeBtnSend, "#FFFFFF");

    reserveMainItemMakeCloseBtn.addEventListener("click", function() {
        reserveMainItemMakeBtn.style.display = "none";
    })

    reserveMainItemMakeBtnSend.addEventListener("click", function() {
        reserveMainItemMakeBtnFunc();
    });

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
    let reserveMainItemTitleValue = document.querySelector("#reserveMainItemTitleText").value;
    if (reserveMainItemTitleValue == null || reserveMainItemTitleValue == "") {
        alert("[ERR-902] 아이템 제목을 입력해주세요.");
        return;
    }
    if (typeof reserveMainItemTitleValue != "string") {
        alert("[ERR-903] 아이템 제목은 문자열이어야 합니다.");
        return;
    }
    var tempUserName = "initUserName@InitUserName.com";
    let initReserveConent = "initReserveConent";
    var tempReserveTime = "0000-00-00 00:00~0000-00-00 00:00";
    var data = 'facilityTitle=' + encodeURIComponent(reserveMainItemTitleValue);
    data += '&reserveContent=' + encodeURIComponent(initReserveConent);
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
 *    30분 단위로 나눠줌
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
 *    유저 이름을 split로 나눠주는 함수
 */
function getUserNameSplit(oriData, splitData) {
    //split 함수 예외처리
    if ( typeof oriData != "string" || typeof splitData != "string" ) {
        return null;
    }
    
    if ( oriData.indexOf(splitData) == -1 ) {
        return null;
    }
    return oriData.split(splitData)[0];
}

/**
 *    예약 칸을 나눠주는 함수 버전2
 *    날짜, 30분 단위로 나눠줌
 */
function getResDivModVer2(dayTime, resTime) {

}

/**
 *    설비의 기존 예약 시간(정보, div칸) 을 가져오는 함수
 */
function getCurFacReserveInfo(curFacResTime, calendar2022) {
    //[step 1] : curFacTitle 찾고, 해당 설비예약을 ,로 split 해준다.
    //       ex) 2022-11-05 09:00~2022-11-05 10:00, 2022-11-05 11:00~2022-11-05 12:00
    
    let curReserveTimeArr = curFacResTime.split(",");
    let curResTimeLength = curReserveTimeArr.length;
    let curResStartTimeList = new Array();
    let curResEndTimeList = new Array();
    let todayResDivStartList = new Array();
    let todayResDivEndList = new Array();

    //[step 2] 예약된 시간들을 하나씩 가져와서 분리한다.
    //     ex) 2022-11-05 09:00~2022-11-05 10:00
    //         2022-11-05 11:00~2022-11-05 12:00
    for (let curResTimeIdx = 0; curResTimeIdx < curResTimeLength; curResTimeIdx++) {
        //[step2-1] ~ 표시로 예약 시작과 끝을 구분해준다.
        //      ex) 2022-11-05 09:00    
        //          2022-11-05 10:00
        //          2022-11-05 11:00    
        //          2022-11-05 12:00
        let curResTimes = curReserveTimeArr[curResTimeIdx].split("~");
        let curResTimesLen = curResTimes.length;
        
        //[step2-2] 예약 시작과 끝을 구분해준다.
        for (let curIdx = 0; curIdx < curResTimesLen; curIdx++) {
            //ex )  2022-11-05 
            //      09:00
            //      2022-11-05
            //      10:00
            //      ...
            let startEndTime = curResTimes[curIdx].trim().split(" ");
            if ((curIdx & 1) == 0) {
                //[step2-3] 시작시간
                curResStartTimeList.push(getTimeToMinute(startEndTime[0], startEndTime[1], calendar2022));
                todayResDivStartList.push(getResDivMod(startEndTime[1]));
            } else {
                //[step2-4] 끝시간
                curResEndTimeList.push(getTimeToMinute(startEndTime[0], startEndTime[1], calendar2022));
                todayResDivEndList.push(getResDivMod(startEndTime[1]));
            }
        }
    }
    
    let resultTime = new Array(todayResDivStartList, todayResDivEndList);
    return resultTime;
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
    
    for ( let facIdx = 0; facIdx < facReserveTimesLength; facIdx++ ) {
        //step1. 현재 설비의 예약된 시간을(String형태) 가져온다.
        let curReserveTime = facReserveTimes[facIdx].getAttribute("value");
        
        let curFacResList = getCurFacReserveInfo(curReserveTime, calendar2022);
        
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
                let curDivIdx = divIdx + 1; //div는 1부터 시작
                let curTrIdx = facIdx + G_topOffsetIdx;
                let divResColor = document.querySelector("body > div.reserve-main-facility-table > table > thead > tr:nth-child(" + curTrIdx + ") > th.reserve-iter-list-time > div:nth-child(" + curDivIdx + ")");
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
    var curTopIdx = 0;

    for (var titleIdx = 0; titleIdx < reserveTitlesTimeLength; titleIdx++) {
        let curFacTitle = facTitles[titleIdx].innerHTML;
        reserveTitlesTimes[titleIdx].style.display = "grid";
        reserveTitlesTimes[titleIdx].style.gridTemplateColumns = "repeat(48, 1fr)";
        reserveTitlesTimes[titleIdx].style.gridTemplateRows = "1fr";

        for (let gridIdx = 0; gridIdx < 48; gridIdx++) {
            curTopIdx = titleIdx + G_topOffsetIdx;
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
                this.style.cursor = "pointer";

                // TODO: 굳이 mouseover -> click 이벤트를 사용할 필요가 있을까?
                //       click으로만 활용하게 변경
                // this.addEventListener("click", function() {
                //     reserveTimeGridClickVer2(curFacTitle, gridIdx, curTopIdx, curUserName);
                // });
            })
            div.addEventListener("click", function() {
                reserveTimeGridClickVer2(curFacTitle, gridIdx, curTopIdx);
            });

            reserveTitlesTimes[titleIdx].appendChild(div);
        }
    }
    return;
}

/**
 *    예약페이지의 공통 클릭 이벤트 처리
 */

function mouseOnOffStyleMake(curObject, oriColor) {
    
    curObject.addEventListener("mouseover", function() {
        this.style.cursor = "pointer";
        this.style.color = "#00ff00";
    });

    curObject.addEventListener("mouseout", function() {
        this.style.cursor = "default";
        this.style.color = oriColor;
    });
    return;
}

/**
 *    달력의 날짜 클릭 이벤트 처리
 *    조금 특수한 경우라, 함수로 추가 작성
 *    달력의 시작, 끝 부분도 처리를 해줘야하는데 어디서 해주지?
 */

function calendarDayClickEvent(curObject, oriColor, curDateInfo) {

    curObject.addEventListener("click", function() {
        // let reservePopupTimeScroll = document.querySelector("#reservePopupTimeScroll");
        // let createCurDateOption = document.createElement("option");
        // reservePopupTimeScroll.value = curDateInfo;
        // reservePopupTimeScroll.innerHTML = curDateInfo;
        //reserveTimeMinOption[timeIdx] = document.createElement("option");
        //reserveTimeMinOption[timeIdx].value = timeValueHour + ":" + timeValueMin;
        //reserveTimeMinOption[timeIdx].innerHTML = timeValueHour + ":" + timeValueMin;        
        //reserveTimeStartHourMin.appendChild(reserveTimeMinOption[timeIdx]);
        let reserveStartDate = document.querySelector(".reserve-date-start-text");
        reserveStartDate.innerHTML = curDateInfo;
        reserveStartDate.value = curDateInfo;
        closeCalendar();
    });

    curObject.addEventListener("mouseover", function() {
        this.style.cursor = "pointer";
        this.style.color = "#00ff00";
    });

    curObject.addEventListener("mouseout", function() {
        this.style.cursor = "default";
        this.style.color = oriColor;
    });
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
function reserveTimeGridClickVer2(curFacTitle, curIdx, curTrIdx) {
    
    // 변수 선언
    let reservePopup = document.querySelector(".reserve-popup-main");
    let reservePopupCloseBtn = document.querySelector(".reserve-popup-main-title-close");
    let reservePopupBtnSendClose = document.querySelector(".reserve-popup-btn-close");
    let reservePopupBtnDbSend = document.querySelector(".reserve-popup-btn-db-send");
    let curTitle = document.querySelector(".reserve-popup-main-title-text");
    let curUserName = document.querySelector(".reserve-page-user-name-text").value;
    let reserveUserName = getUserNameSplit(curUserName, "@");

    mouseOnOffStyleMake(reservePopupBtnSendClose, "#737373");
    mouseOnOffStyleMake(reservePopupBtnDbSend, "#FFFFFF");
    mouseOnOffStyleMake(reservePopupCloseBtn, "#737373");

    reservePopupBtnSendClose.addEventListener("click", function() {
        reservePopup.style.display = "none"; 
        // 예약 이름 삭제
        document.querySelector(".reserve-popup-content-text").value = "";
        
        // 달력 팝업 닫기
        closeCalendar();
    });

    reservePopupBtnDbSend.addEventListener("click", function() {
        makeFacReserveTimeForDbBtn();
    });

    reservePopupCloseBtn.addEventListener("click", function() {
        reservePopup.style.display = "none";

        // 달력 팝업 닫기
        closeCalendar();
    });

    if (curUserName == "" || curUserName == null) {
        curUserName = "테스트@naver.com";
    }
    
    if ( reserveUserName == null ) {
        reserveUserName = "에러발생";
    }
    let reserveContentInit = reserveUserName + "님의 회의실 예약";
    document.querySelector("#reservePopupContent").placeholder = reserveContentInit;

    let curDiv = "body > div.reserve-main-facility-table > table > thead > tr:nth-child(" + curTrIdx + ") > th.reserve-iter-list-time > ";
    let curDivChild = "div:nth-child(" + (curIdx + 1) + ")";
    curDiv += curDivChild;
    
    document.querySelector(curDiv).style.value = "reserve";

    curTitle.innerHTML = curFacTitle;

    let reserveTimeHour = document.querySelector("#reservePopupTimeScroll");
    let reserveTimeMin = document.querySelector("#reservePopupMinuteScroll");
    let resTimeEnd = new Array(document.querySelector("#reservePopupTimeScrollEnd"), document.querySelector("#reservePopupMinuteScrollEnd"));

    

    if (reservePopup.style.display == "none") {
        reserveTimeHour.value = String(curIdx >> 1);
        resTimeEnd[0].value = reserveTimeHour.value; 
        if (curIdx & 0x1) {
            reserveTimeMin.value = "30";

            resTimeEnd[0].value = String(parseInt(resTimeEnd[0].value) + 1);
            resTimeEnd[1].value = "00";
        } else {
            reserveTimeMin.value = "00";
            resTimeEnd[1].value = "30";
        }
        let curLeft = curIdx * 15;
        let curTop = 3 * 100       
        reservePopup.style.left = curLeft + "px";
        reservePopup.style.top = curTop + "px";
        reservePopup.style.display = "block";

    } else {
        reservePopup.style.display = "none";
        //reservePopupClose();
    }

    //[임시] 캘린더 오픈하는 코드
    let reserveStartTimeText = document.querySelector(".reserve-date-start-text");
    let curDate = new Date().getFullYear() + "-" + ( new Date().getMonth() + 1 ) + "-" + new Date().getDate();
    reserveStartTimeText.value = curDate;
    reserveStartTimeText.innerHTML = curDate;
    let curMonth = new Date().getMonth() + 1;

    reserveStartTimeText.addEventListener("click", function() {
        openCalendar(curMonth);
    });
    reserveTimeHour.addEventListener("click", function() {
        openCalendar(curMonth);
    });
    resTimeEnd[0].addEventListener("click", function() {
        openCalendar(curMonth);
    });
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

/**
 *    캘린더 오픈하는 함수
 */
function openCalendar(curMonth) {
    let openCalendarPopupMain = document.querySelector(".reserve-popup-calendar-main");

    if ( openCalendarPopupMain.style.display == "none" ) {
        openCalendarPopupMain.style.display = "block";
        document.querySelector(".reserve-popup-calendar-main").style.height = openCalendarPopupMain.offsetHeight + "px";
        moveOpenCalendar(curMonth);
    }

    // 다시 눌러서 끄는거는 삭제함
    // 논리가 꼬임 이거 때문에. 그리고 별로 좋은 Flow라고 생각하지도 않음.
    // } else {
    //     closeCalendar();
    // }
    return;
}

/**
 *    캘린더 좌,우 달 이동하는 함수
 */
function moveOpenCalendar(curMonth) {
    initCalendar();
    let curYear = new Date().getFullYear();
    document.querySelector(".calendar-weekdays-ul").style.display = "block";
    document.querySelector(".calendar-text-year-month").value = curYear + "-" +  curMonth;
    document.querySelector(".calendar-text-year-month").innerHTML = curYear + "-" +  curMonth;
    document.querySelectorAll(".calendar-days-ul")[curMonth - 1].style.display = "block";
    return;
}

/**
 *    캘린더 초기화(none) 함수
 */
function initCalendar() {
    // day를 차지하는 칸에 대해서 전체 초기화가 필요함
    // day는 전체 none필요 (12개)
    // day를 초기화 하고, 적절한 month의 day들을 가져온다.
    document.querySelector(".calendar-weekdays-ul").style.display = "none";
    document.querySelector(".calendar-select-month-ul").style.display = "none";
    let calendarMainPopupDayUls = document.querySelectorAll(".calendar-days-ul");
    let dayUlsLen = calendarMainPopupDayUls.length;
    for ( let dayUlIdx = 0; dayUlIdx < dayUlsLen; dayUlIdx++ ) {
        if ( calendarMainPopupDayUls[dayUlIdx].style.display == "block" ) {
            calendarMainPopupDayUls[dayUlIdx].style.display = "none";
        }
    }
    return;
}

function closeCalendar() {
    let openCalendarPopupMain = document.querySelector(".reserve-popup-calendar-main");
    if ( openCalendarPopupMain.style.display == "block" ) {
        initCalendar();
        openCalendarPopupMain.style.display = "none";
    }
    return;
}

/**
 *    현재 달력 날짜 배열 만들기 함수
 */
function makeCalendarYear2022() {
    let calendarMainPopupClose = document.querySelector(".calendar-popup-close");
    let calendarMainPopupPrevMonth = document.querySelector(".calendar-month-prev");
    let calendarMainPopupNextMonth = document.querySelector(".calendar-month-next");
    let calendarMainPopupYearMonthText = document.querySelector(".calendar-text-year-month");
    let calendarMainPopup = document.querySelector(".reserve-popup-calendar-main");
    let calendarMainPopupMonthDiv = document.querySelector(".calendar-month");
    let calendarMainPopupWeekUl = document.querySelector(".calendar-weekdays-ul");

    let calendarMainPopupDayUlArr = new Array();
    for (let monthIdx = 0; monthIdx < 12; monthIdx++) {
        calendarMainPopupDayUlArr[monthIdx] = document.createElement("ul");
        calendarMainPopupDayUlArr[monthIdx].className = "calendar-days-ul";
        calendarMainPopupDayUlArr[monthIdx].style.display = "none";
        calendarMainPopup.appendChild(calendarMainPopupDayUlArr[monthIdx]);
    }

    let calendarMainPopupDayUls = document.querySelectorAll(".calendar-days-ul");
    let calendarSelectMonthUl = document.querySelector(".calendar-select-month-ul");
    let calendarSelectMonthLi = document.querySelectorAll(".calendar-select-month-li");
    let curYear = new Date().getFullYear();
    let monthLiLength = calendarSelectMonthLi.length;
    let calendarMainPopupHeightOri = calendarMainPopup.offsetHeight;
    

    // 달력 닫기
    mouseOnOffStyleMake(calendarMainPopupClose, "#777");
    calendarMainPopupClose.addEventListener("click", function() {
        closeCalendar();
    });

    // Year-Month (ex 2022-11) 을 선택 시, Month를 선택할 수 있는 화면 제공
    calendarMainPopupYearMonthText.addEventListener("click", function() {
        initCalendar();
        calendarMainPopupYearMonthText.value = curYear;
        calendarMainPopupYearMonthText.innerHTML = curYear;
        calendarSelectMonthUl.style.display = "block";
        calendarMainPopup.style.height = "80px";
    })

    // Month를 선택할 때, 해당 Month의 Day를 보여준다.
    for ( let monthIdx = 0; monthIdx < monthLiLength; monthIdx++ ) {
        mouseOnOffStyleMake(calendarSelectMonthLi[monthIdx], "#777");
        calendarSelectMonthLi[monthIdx].addEventListener("click", function() {
            
            initCalendar();

            calendarMainPopupYearMonthText.value = curYear + "-" + ( monthIdx + 1 );
            calendarMainPopupYearMonthText.innerHTML = curYear + "-" + ( monthIdx + 1 );
            calendarMainPopupWeekUl.style.backgroundColor = "#1abc9c";
            calendarMainPopupMonthDiv.style.backgroundColor = "#1abc9c";
            calendarSelectMonthUl.style.display = "none";

            calendarMainPopupWeekUl.style.display = "block";
            calendarMainPopup.style.height = calendarMainPopupHeightOri + "px";
            //calendarMainPopup.appendChild(calendarMainPopupDayUls[monthIdx]);
            calendarMainPopupDayUls[monthIdx].style.display = "block";
        })
    }
    
    calendarMainPopupPrevMonth.addEventListener("click", function() {
        let curMoveMon = calendarMainPopupYearMonthText.value.split("-")[1];
        let prevMoveMon = parseInt(curMoveMon) - 1;
        if ( prevMoveMon <= 0 ) {
            prevMoveMon = 12;
        }
        moveOpenCalendar(prevMoveMon);
    });

    calendarMainPopupNextMonth.addEventListener("click", function() {
        let curMoveMon = calendarMainPopupYearMonthText.value.split("-")[1];
        let nextMoveMon = parseInt(curMoveMon) + 1;
        if ( nextMoveMon > 12 ) {
            nextMoveMon = 1;
        }        
        moveOpenCalendar(nextMoveMon);
    });

    let curDate = new Date().getDate();         //현재 날짜
    let curMon = new Date().getMonth();     //현재 월
    let weekNum = 7;
    
    let calendarActive = document.createElement("span");
    calendarActive.className = "calendar-active";

    
    //2022년의 1월 1일이 토요일이였습니다.
    let monthFirstDayRow = 6; //토요일
    let emptySpace = ' ';
    for ( let monthIdx = 0; monthIdx < 12; monthIdx++ ) {
        
        //dayIdx에서는 컬럼 숫자까지 증가해야합니다.
        //1월  1일이 토요일, Col:0, Row:6 -> 1월2일이 일요일, Col:1, Row:0
        //1월 29일은 토요일, Col:4, Row:6 -> 1월 30일은 일요일, Col:5, Row:0
        //1월 31일은 월요일, Col:5, Row:1 -> 2월 1일은 화요일, Col:0, Row:2 (다음달)
        
        // TODO:
        // 지금 7월달은 왜 배열이 안맞는지 모르겠네;;
        // 달력 popup을 적절한 크기로 다 변경하기 -> 좀 빡세네, 근데 굳이 이렇게해야하나?? 오히려 그림배열이 이상해보이는느낌도 있고 흠..
        // 결국 다음 달 , 저번 달 날짜를 보여줘야 깔끔하지만, 일단 이건 넘어가고 다른거 먼저 하자.

        let curMonMaxCol = parseInt( ( G_calendar2022[monthIdx] ) / weekNum) + 1;
        //let curMonMaxDay = curMonMaxCol * weekNum + monthFirstDayRow;
        let curMonMaxDay = ( curMonMaxCol + 1 ) * weekNum;
        let offsetCurMonRow = monthFirstDayRow;
        let nextMonthStartWeekDataFlag = false;
        
        //for ( let dayIdx = 0; dayIdx < G_calendar2022[monthIdx] + offsetCurMonRow; dayIdx++ ) {
        for ( let dayIdx = 0; dayIdx <= curMonMaxDay; dayIdx++ ) {
            let calendarDay = document.createElement("li");
            calendarDay.className = "calendar-day";

            if ( dayIdx < offsetCurMonRow ) {
                //이전 달 내용 (일단 공백)
                calendarDay.innerHTML = emptySpace;
                calendarDay.value = emptySpace;
            } else if ( dayIdx >= offsetCurMonRow && dayIdx < offsetCurMonRow + G_calendar2022[monthIdx] ) {
                //현재 달 내용
                if ( ( curMon == monthIdx ) && ( curDate == dayIdx - offsetCurMonRow + 1 ) ) {
                    calendarActive.value = curDate;
                    calendarActive.innerHTML = curDate;
                    calendarDay.appendChild(calendarActive);
                } else {
                    calendarDay.innerHTML = dayIdx - offsetCurMonRow + 1;
                    calendarDay.value = dayIdx - offsetCurMonRow + 1;
                }
            } else {
                //다음 달 내용 (일단 공백)
                calendarDay.innerHTML = emptySpace;
                calendarDay.value = emptySpace;

                //처음 들어오는 위치에서 다음달 요일을 유추할 수 있습니다.
                if ( !nextMonthStartWeekDataFlag ) {
                    monthFirstDayRow = dayIdx % weekNum;
                    nextMonthStartWeekDataFlag = true;
                }
            } 
            
            //mouseOnOffStyleMake(calendarDay, "#777");
            let curDateInfo = new Date().getFullYear() + "-" + ( monthIdx + 1 ) + "-" + calendarDay.value; 
            calendarDayClickEvent(calendarDay, "#777", curDateInfo);
            calendarMainPopupDayUls[monthIdx].appendChild(calendarDay);
        }
        //calendarMainPopup.appendChild(calendarMainPopupDayUls[monthIdx]);
        //calendarMainPopupDayUls[monthIdx].style.display = "none";
    }
    // 1.  아 이거,, prev day, next day도 만들어야함 (전달, 다음달 날짜도 알아야합니다.)
    // 2.  현재 날짜에 active 클래스를 주어야함
    // 3.  현재 날짜, 요일을 기준으로 달력을 만들어야함
    // 4.  달력을 만들 때는 7일 단위로 만들어야함
    // 5.  달력을 만들 때는 5~6주 단위로 만들어야함 (이전, 다음달 생각까지 하자)
    // 6.  로우, 컬럼 배열을 이용하자
    // 7.  로우 배열은 0부터 시작하고, 컬럼 배열은 1부터 시작한다
    // 8.  calendarRow, calendarCol을 조합해서 현재 날짜를 알아내고, 그 날짜에 active 클래스를 주어야함
    // 9.  현재 날짜에서 7일 단위로 Calander의 index를 채운다.
    // 10. 각 달의 마지막 날짜를 알아내고, 넘어가는 경우는 next day로 (연한 회색으로 처리)
    // 11. 마지막 칼럼(5) 의 위치를 row배열을 채운다.
    // 12. 일단 1년단위까지만 만들어보자 (2022년 달력입니다.)

    // ex) 1월 1일의 위치를 알아내고 해야하는건가?????
    // ex) 2022-01-01은 토요일이였습니다.
    // ex) 2022-01-01은 Col 0, Row 6에 위치합니다.
    // 현재 날짜를 기준으로, 컬럼을 쪼개야함
    
    //[1월]
    //            0    1   2    3    4    5    6 (Row)
    //          sun  mon tue  wed  thu  fri  sat (Row)
    //(Col) [0]                                 1 
    //(Col) [1]  2     3    4    5    6    7    8                                    
    //(Col) [2]  9    10   11   12   13   14   15                           
    //(Col) [3]  16   17   18   19   20   21   22
    //(Col) [4]  23   24   25   26   27   28   29
    //(Col) [5]  30   31   1(n) 2(n) 3(n) 4(n) 5(n)

    //[2월]
    //            0      1     2    3    4    5    6 (Row)
    //          sun    mon   tue  wed  thu  fri  sat (Row)
    //(Col) [0] 30(p) 31(p)   1     2    3    4    5
    //(Col) [1]  6     7     8     9    10    11    12
    //(Col) [2]  13    14    15   16    17    18    19
    //(Col) [3]  20    21    22   23    24    25    26
    //(Col) [4]  27    28    1(n) 2(n) 3(n) 4(n) 5(n)
    return;
}

/**
 *    오늘 날짜 가져오는 함수
 */
function getNowDate() {
    let curYear = new Date().getFullYear();     //현재 년도
    let curMon = new Date().getMonth() + 1;     //현재 달
    let curDate = new Date().getDate();         //현재 날짜임   
    let curDay = new Date().getDay();           //0:일요일, 1:월요일...
    let curTime = curYear + "-" + curMon + "-" + curDate;

    let curDayStr = new Array("(SUN)", "(MON)", "(TUE)", "(WED)", "(THU)", "(FRI)", "(SAT)");
    let rstCurTime = new Array(curTime, curDayStr[curDay]);
    return rstCurTime;
}

function thTimeHeaderInit() {
    // TODO:  버튼 눌러서 현재 날짜 좌, 우 이동도 시킬거임
    // 오늘 날짜 header
    // ex) 결과: [2022-11-06, (SUN)]
    let curTime = getNowDate();
    let curTimeHeader = document.querySelector(".reserve-cur-date-list");
    curTimeHeader.innerHTML = curTime[0] + " " + curTime[1];


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

    // ID 임시로 생성해둠
    let initId = document.querySelector(".reserve-page-user-name-text");
    initId.value = "yoda@nklbk.com";


    
    // 예약할 수 있는 시간들의 Option 배열 생성
    // 이건 HTML에서하냐 JS 숨기느냐 고민이 많은데, HTML은 너무 길어지니깐 일단 JS로 숨겨보자
    // reserveTimeMin의 option 배열을 만들어줘
    let reserveTimeStartHourMin = document.querySelector("#reservePopupMinuteScroll");
    let reserveTimEndHourMin = document.querySelector("#reservePopupMinuteScrollEnd");
    let reserveTimeMinOption = new Array();
    
    for (let timeIdx = 0; timeIdx < 48; timeIdx++) {
        //timeValue: 00:00 -> 00:30 .... 23:00 -> 23:00
        let timeValueHour = "00";
        let timeValueMin = "00";
        if ( timeIdx < 20 ) {            
            timeValueHour = "0" + String(parseInt(timeIdx >> 1));
        } else {
            timeValueHour = String(parseInt(timeIdx >> 1));
        }
        if ( ( timeIdx & 1 ) ) { //홀수
            timeValueMin = "30";
        } else {
            timeValueMin = "00";
        }
        reserveTimeMinOption[timeIdx] = document.createElement("option");
        reserveTimeMinOption[timeIdx].value = timeValueHour + ":" + timeValueMin;
        reserveTimeMinOption[timeIdx].innerHTML = timeValueHour + ":" + timeValueMin;

        if ( timeIdx != 47 ) { //마지막Index 제외
            reserveTimeStartHourMin.appendChild(reserveTimeMinOption[timeIdx]);
        }
        if ( timeIdx != 0 ) { //첫번째 Index 제외
            reserveTimEndHourMin.appendChild(reserveTimeMinOption[timeIdx].cloneNode(true));
        }
    }

    // 달력도 만들어야합니다...
    // 일단 오늘 날짜로 계속 변경은 해줘야합니다. 그거부터..

    //js 달력 코드
    //https://www.w3schools.com/howto/howto_js_datepicker.asp
    let dateRepo = new Date();
    let calendarMoveLeft = document.querySelector(".calendar-month-prev");
    let calendarMoveRight = document.querySelector(".calendar-month-next");
    let calendarDates = document.querySelectorAll(".calendar-day");
    //현재 년, 월
    let calendarYearMon = document.querySelector(".calendar-text-year-month");


    mouseOnOffStyleMake(calendarMoveLeft, "#c2e0c6");
    mouseOnOffStyleMake(calendarMoveRight, "#c2e0c6");
    mouseOnOffStyleMake(calendarYearMon, "#333");
    for (let dateIdx = 0; dateIdx < calendarDates.length; dateIdx++) {
        mouseOnOffStyleMake(calendarDates[dateIdx], "#777");
    }

    //현재 년, 월 value 입력    
    calendarYearMon.value = dateRepo.getFullYear() + "-" + dateRepo.getMonth();

    // 윤년은 일단 버린다.
    // 2022년 달력 배열 만들기
    makeCalendarYear2022();
    return;
}

/**
 *    해당 설비의 예약 시간을 가져온다.
 */
function getCurFacReserveTime(curFacTitle) {
    var reserveTitles = document.querySelectorAll(".reserve-iter-list-title");
    var reserveTimes = document.querySelectorAll(".reserve-iter-list-time");
    let titlesLength = reserveTitles.length;    
    let curFacReserveTime = "";
    for (let titleIdx = 0; titleIdx < titlesLength; titleIdx++) {
        let reserveTitle = reserveTitles[titleIdx].textContent;
        reserveTitle = reserveTitle.slice(0, reserveTitle.length - 1);
        if (reserveTitle == curFacTitle) {
            let titleIdxOffset = titleIdx + G_topOffsetIdx;
            curFacReserveTime = reserveTimes[titleIdx].getAttribute("value");
            break;
        }
    }    
    return curFacReserveTime;
}

/**
 *     현재 예약할 시간을 index로 분리해준다.
 */
function getCurResTimeToIdx(curResTime) {
    //step1 2022-11-05 10:00~2022-11-05 11:00
    //      2022-11-05
    //           10:00
    //      2022-11-05
    //           11:00
    let curResTimeArr = curResTime.split("~");
    let startTime = getResDivMod(curResTimeArr[0].trim().split(" ")[1]);
    let endTime = getResDivMod(curResTimeArr[1].trim().split(" ")[1]);
    let resultTime = new Array(startTime, endTime);
    return resultTime;
}

/**
 *    예약시간이 겹치는지 확인
 *    여기서는 front에서 미리 div값을 가지고 있는게 좋지 않나???
 *    그냥 계산을 그때 그때 할까?
 *    다른pc에서 예약하면 내 페이지에서는 갱신이 안되어있어서 back-end쪽에서 막을거임
 *    그건 그거고, front에서 1차적으로 걸러주는게 좋은데 매번 연산해야하나??
 *    매번 연산하자 ㅇㅇ 그게맞아 예약할때만 연산하는거니깐 매번 부하를 주지 않는다.
 */
function validateDuplicateReserveTime(curFacTitle, curReserveTime) {
    //init Code
    let errCode = true;

    //step 1 예약 시작 시간이 종료시간보다 큰 경우 (절대로 없지만 혹시 모른다.)    
    let resTime = getConvRserveTime(curReserveTime);
    if (resTime[0] >= resTime[1]) {
        alert("[ERR-1001] 설비 예약 시작시간이 종료시간보다 늦습니다.");
        errCode = false;
    }
    // 현재 설비의 기존 예약시간을 div 배열 단위로 가져옵니다.
    let curResTimes = getCurResTimeToIdx(curReserveTime);
    let curResDivStart = curResTimes[0];
    let curResDivEnd = curResTimes[1];
    let curFacReserveTime = getCurFacReserveTime(curFacTitle);
    if ( curFacReserveTime == "" ) {
        alert("[ERR-1007] 현재 설비의 예약시간이 없습니다.");
        errCode = false;
    }        
    let resDivList = getCurFacReserveInfo(curFacReserveTime, G_calendar2022);
    let curFacResLen = resDivList[0].length;
    let start = 0, end = 1;
    for ( let curResIdx = 0; curResIdx < curFacResLen; curResIdx++ ) {
        if ( (resDivList[start][curResIdx] + resDivList[end][curResIdx]) == 0 ) {
            continue;
        } 
        //step 2 예약시간이 기존시간보다 오른쪽에서 겹칠 때
        //   ex) 기존: |------|      |----|
        //       예약:                 |xxx---|
        if ( resDivList[end][curResIdx] > curResDivStart && resDivList[end][curResIdx] <= curResDivEnd ) {
            alert("[ERR-1002] 설비 예약이 기존시간보다 오른쪽에서 겹칠 때입니다.");
            errCode = false;
        }
        //step 3 예약시간이 기존시간보다 왼쪽에서 겹칠 때
        //   ex) 기존:     |------|      |----|
        //       예약: |---xxx|
        else if ( resDivList[start][curResIdx] < curResDivEnd && resDivList[start][curResIdx] >= curResDivStart ) {
            alert("[ERR-1003] 설비 예약이 기존시간보다 왼쪽에서 겹칠 때입니다.");
            errCode = false;
        }
        //step 4 예약시간이 기존시간이랑 완전히 겹치는 경우
        //   ex) 기존:     |---------------|
        //       예약:         |------|
        else if ( resDivList[start][curResIdx] <= curResDivStart && resDivList[end][curResIdx] >= curResDivEnd ) {
            alert("[ERR-1004] 설비 예약이 기존시간이랑 완전히 겹치는 경우입니다.");
            errCode = false;
        }
        //step 5 예약시간이 기존시간보다 더 크게 겹치는 경우 (절대로 없지만 혹시 모른다. 이 경우는 step 2, 3 에서 먼저 잡힘)
        //   ex) 기존:     |-----|
        //       예약: |---------------|
        else if ( resDivList[start][curResIdx] >= curResDivStart && resDivList[end][curResIdx] <= curResDivEnd ) {
            alert("[ERR-1005] 설비 예약이 기존시간보다 더 크게 겹치는 경우입니다.");
            errCode = false;
        }
    }
    return errCode;
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

    let timeStepStart = timeStep1[0].trim().split(" ");
    let timeStepEnd = timeStep1[1].trim().split(" ");

    //step3 시간을 계산할 수 있는 숫자로 변환
    let timeStep3_1 = getTimeToMinute(timeStepStart[0], timeStepStart[1], calendar2022);
    let timeStep3_2 = getTimeToMinute(timeStepEnd[0], timeStepEnd[1], calendar2022);
    let reserveTimeResult = new Array(timeStep3_1, timeStep3_2);
    return reserveTimeResult;
}

/**
 *    아 이거 db 설계를 다시 해야하나..?
 *    ex) 2022-10-14 11:30~2022-11-11 14:00, 2022-10-14 14:30~2022-11-11 16:00
 *        이런식인데.. 저장한 사람까지 알았어야했네, userName으로 따로 저장하면 될 줄 알았는데 예약 시간마다 다르게 저장해야하네
 *        그렇다면 시간앞에 (userName) 이런식으로 저장하면 되겠다.
 *    ex) (yoda)2022-10-14 11:30~2022-11-11 14:00, (mk.kim)2022-11-11 14:30~2022-11-11 16:00, (jidae)2022-11-14 14:30~2022-11-16 16:00
 *        이렇게해서 split 다시 구현해야할듯요????? ㅎㅎ;
 *        이래서 db 설계를 조금 바꾸면 전체적인걸 다 바꿔야하는구만..
 * 
 *    ex) 아니 근데.. 예약 내용까지 있어야하는데?????
 *        (첫번쨰 요소: 아아디, 두번째 요소: 예약 내용) -> 쉼표로 구분? 이런식으로 해야하나 흠...
 *        (yoda, yoda님의 회의실 예약)2022-10-14 11:30~2022-11-11 14:00, (mk.kim, BMT#3 MK.KIM)2022-11-11 14:30~2022-11-11 16:00, (jidae, YIKJ 출장 미팅룸 예약)2022-11-14 14:30~2022-11-16 16:00
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

    //TODO: 날짜 선택하는 코드 추가해야합니다.
    let tempToday = "2022-11-05";
    let reserveUr = 'http://localhost:8080/reserve/reserve-main/fac-reserve';
    
    let reserveUserName = document.querySelector(".reserve-page-user-name-text").value;
    if ( reserveUserName == "" || reserveUserName == null ) {
        reserveUserName = "mk.yoda@nklkb.com";
    }

    let reserveContent = document.querySelector(".reserve-popup-content-text").value;
    if ( reserveContent == "" || reserveContent == null ) {
        reserveContent = reserveUserName.split("@")[0] + "님의 회의실 예약";
    }

    //step1 code
    let curFacTitle = document.querySelector(".reserve-popup-main-title-text").innerHTML;
    let startTimeHour = document.querySelector("#reservePopupTimeScroll").value;
    let startTimeMinute = document.querySelector("#reservePopupMinuteScroll").value;
    
    let endTimeHour = document.querySelector("#reservePopupTimeScrollEnd").value;
    let endTimeMinute = document.querySelector("#reservePopupMinuteScrollEnd").value;
    
    let startTime = tempToday + " " + String(startTimeHour) + ":" + String(startTimeMinute);
    let endTime = tempToday + " " + String(endTimeHour) + ":" + String(endTimeMinute);

    //String 변환 코드
    let curReserveTime = startTime + "~" + endTime;
    
    //step2 code
    //TODO: 예외처리 넣어야함 (일단 패스했다는 가정합니다.)

    if ( validateDuplicateReserveTime(curFacTitle, curReserveTime) == false ) {
        return;
    }

    //step3 code
    let data = 'facilityTitle=' + encodeURIComponent(curFacTitle)
                + '&reserveContent=' + encodeURIComponent(reserveContent)
                + '&reserveTime=' + encodeURIComponent(curReserveTime) 
                + '&userName=' + encodeURIComponent(reserveUserName);
    reserveUr = reserveUr + '?' + data;
    
    //step4로 이동
    //location.href = reserveUr;

    //step 4 예약확인 페이지를 띄운다.
    //       확인 버튼을 누르면 DB로 예약 데이터를 전송하고, redirect해준다.
    reserveConfirmPage(reserveUr, startTime, endTime, curFacTitle, reserveContent);

    return;
}

/**
 *    예약확인페이지
 */
function reserveConfirmPage(reserveUr, startTime, endTime, curFacTitle, reserveContent) {
    //step0 초기 설정 및 변수 선언
    let startTimeSplit = startTime.split(" ");
    let endTimeSplit = endTime.split(" ");
    let reserveConfirmPagePopup = document.querySelector(".reserve-confirm-page-main");
    let closeBtn = document.querySelector(".reserve-confirm-page-close-btn");
    let confirmBtnOk = document.querySelector(".reserve-confirm-page-btn-ok");
    let confirmBtnCancel = document.querySelector(".reserve-confirm-page-btn-cancel");
    let confirmPlaceText = document.querySelector(".reserve-confirm-page-place-text");
    let confirmReserveTitleText = document.querySelector(".reserve-confirm-page-title-text");

    let confirmReserveStartTimeHour = document.querySelector(".reserve-confirm-page-time-div-start-hour-text");
    let confirmReserveStartTimeMinute = document.querySelector(".reserve-confirm-page-time-div-start-minute-text");
    let confirmReserveEndTimeHour = document.querySelector(".reserve-confirm-page-time-div-end-hour-text");
    let confirmReserveEndTimeMinute = document.querySelector(".reserve-confirm-page-time-div-end-minute-text");    

    confirmReserveStartTimeHour.value = startTimeSplit[0];
    confirmReserveStartTimeMinute.value = startTimeSplit[1];
    confirmReserveEndTimeHour.value = endTimeSplit[0];
    confirmReserveEndTimeMinute.value = endTimeSplit[1];
    confirmPlaceText.value = curFacTitle;
    confirmReserveTitleText.value = reserveContent;

    closeBtn.addEventListener("mouseover", function() {
        closeBtn.style.cursor = "pointer";
        //closeBtn 색을 연한 초록색으로 변경
        closeBtn.style.color = "#00ff00";
    });
    closeBtn.addEventListener("mouseout", function() {
        closeBtn.style.cursor = "default";
        //closeBtn 색을 빨간색으로 변경
        closeBtn.style.color = "#ff0000";
    });
    closeBtn.addEventListener("click", function() {
        reserveConfirmPagePopup.style.display = "none";
    });

    confirmBtnOk.addEventListener("mouseover", function() {
        confirmBtnOk.style.cursor = "pointer";
    });
    confirmBtnOk.addEventListener("mouseout", function() {
        confirmBtnOk.style.cursor = "default";
    });
    
    confirmBtnCancel.addEventListener("mouseover", function() {
        confirmBtnCancel.style.cursor = "pointer";
        //confirmBtnCancel의 백그라운드 색을 아주 연한 회색으로 변경
        confirmBtnCancel.style.backgroundColor = "#f0f0f0";
    });
    confirmBtnCancel.addEventListener("mouseout", function() {
        confirmBtnCancel.style.cursor = "default";
        //confirmBtnCancel의 백그라운드 색을 
        confirmBtnCancel.style.backgroundColor = "#ffffff";
    });


    //step1 예약 내용을 띄운다.
    if ( reserveConfirmPagePopup.style.display == "none" ) {
        reserveConfirmPagePopup.style.display = "block";
    }

    //step2 예약 취소 버튼 누를 시, 해당 예약 삭제
    confirmBtnCancel.addEventListener("click", function() {
        reserveConfirmPagePopup.style.display = "none";
        deleteCurReserve();
    });
    
    //step2-1 확인 버튼 누를 시, 예약 완료
    confirmBtnOk.addEventListener("click", function() {
        reserveConfirmPagePopup.style.display = "none";
        location.href = reserveUr;        
    });

    return;
}

/**
 *    현재 예약한 내용 바로 삭제
 */
function deleteCurReserve() {
    alert("회의실 예약을 취소했습니다.");
    return;
}

/**
 *    onmouseover event ver2   
 */
function btnLightVer2() {
    //step 1: 현재 가리키고 있는 버튼의 정보를 가져온다.
    let curBtnList = new Array( document.querySelector(".reserve-cur-date-left-btn"), document.querySelector(".reserve-cur-date-right-btn"),
                                document.querySelector(".reserve-cur-date-list"));
    
    let curBtnListLen = curBtnList.length;

    //step 2: 현재 마우스가 on 되어 있으면 연한 초록색으로 표시한다.
    for (let i = 0; i < curBtnListLen; i++) {
        //curBtnList[i]가 마우스 on 되어 있는지 확인하는 코드
        curBtnList[i].addEventListener("mouseover", function() {
            // TODO: 그냥 초록색 지운다. 뭔가 없어보임.
            // 색깔은 연한 초록색을 사용한다.
            //curBtnList[i].style.backgroundColor = "#c2e0c6";

            //curBtnList[i]의 커서를 손가락모양으로 변경한다.
            curBtnList[i].style.cursor = "pointer";

            //curBtnList[i]의 텍스트 색깔을 연한 초록색으로 변경한다.
            curBtnList[i].style.color = "#c2e0c6";
        });

        //step 3: 현재 마우스가 out 되어 있으면 흰색으로 표시한다.
        curBtnList[i].addEventListener("mouseout", function() {
            // 색깔은 흰색을 사용한다.
            curBtnList[i].style.backgroundColor = "#ffffff";

            // curBtnList[i]의 텍스트 색깔을 검은색으로 변경한다.
            curBtnList[i].style.color = "#000000";
        });
    }
    return;
}

/**
 *    예약페이지의 현재 날짜를 하루 전으로 이동하는 함수
 */
function reserveDayLeft() {
    return;
}
/**
 *    예약페이지의 현재 날짜를 하루 뒤로 이동하는 함수
 */
function reserveDayRight() {
    return;
}


function reservePopupClose() {
    document.querySelector(".reserve-popup-main").style.display = "none";
    return;
}

 /**
  *    th-header
  */
thTimeHeaderInit();

/**
 *     예약 시간 grid 만들기
 */
reserveTimeGridInit();

/**
 *     설비 예약 타이틀 지우기 버튼
 */
reserveItemDeleteMakeInit();

/**
 *     설비 예약 시간 가져오기
 */
initDispFacReserveTime();