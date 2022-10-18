function goHomeHtml() {
    window.location.replace('http://localhost:8080/my-home');
    return;
}

function goGameHtml() {
    var ur = 'http://localhost:8080/game/game-home';
    window.location.replace(ur);
    return;
}

function goRankPageHtml() {
    var ur = 'http://localhost:8080/game/rank-page';
    window.location.replace(ur);
    return;
}

function goReservePageHtml() {
    var ur = 'http://localhost:8080/reserve/reserve-main';
    window.location.replace(ur);
    return;
}

function btnLight() {
    let liBtnList = document.querySelectorAll(".li-menu-btn");
    liBtnListLen = liBtnList.length;
    for (let i = 0; i < liBtnListLen; i++) {
        //liBtnList 버튼에 마우스를 올려두면 색이 변하게 해줘
        liBtnList[i].addEventListener("mouseover", function() {
            //연한 회색으로
            liBtnList[i].style.backgroundColor = "#e6e6e6";
        });

        //liBtnList 버튼이 마우스를 떠나면 색이 변하게 해줘
        liBtnList[i].addEventListener("mouseout", function() {
            //흰색으로
            liBtnList[i].style.backgroundColor = "#ffffff";
        });        
    }
    return;
}

function studyPageMoreList() {
    let ulMoreStudy = document.querySelector(".ul-more-study");
    let moreBtnStudy = document.querySelector("#more-btn-study");
    let downDirectHtml = "&#9661";
    let upDirectHtml = "&#9651";

    if (ulMoreStudy.style.display === "none") {
        ulMoreStudy.style.display = "block";
        moreBtnStudy.innerHTML = "접기" + upDirectHtml;
        moreBtnStudy.style.color = "#00cc00";        
    } else {
        ulMoreStudy.style.display = "none";
        moreBtnStudy.innerHTML = "더보기"+ downDirectHtml;
        moreBtnStudy.style.color = "#000000";
    }    
    return;
}

// 더보기 버튼을 누르면 추가 메뉴가 나오게 해줘
function gamePageMoreList() {
    let ulMore = document.querySelector(".ul-more");
    let homeBtnMore = document.querySelector("#home-btn-more");
    let downDirectHtml = "&#9661";
    let upDirectHtml = "&#9651";

    if (ulMore.style.display === "none") {
        ulMore.style.display = "block";
        homeBtnMore.innerHTML = "접기" + upDirectHtml;
        homeBtnMore.style.color = "#00cc00";        
    } else {
        ulMore.style.display = "none";
        homeBtnMore.innerHTML = "더보기"+ downDirectHtml;
        homeBtnMore.style.color = "#000000";
    }

    return;
}

function moreBtnUnderLine() {
    let btnMore = document.querySelectorAll(".li-memu-btn");
    let btnMoreLen = btnMore.length;
    for (let i = 0; i < btnMoreLen; i++) {
        btnMore[i].addEventListener("mouseover", function() {
            btnMore[i].style.textDecoration = "underline";
        });
        btnMore[i].addEventListener("mouseout", function() {
            btnMore[i].style.textDecoration = "none";
        });
    }
    return;
}