function goHomeHtml() {
    var xhr = new XMLHttpRequest();
    var ur = 'http://localhost:8080/my-home';
    xhr.onreadystatechange = function() {
        if(xhr.readyState === xhr.DONE) {
            if (xhr.status === 200 || xhr.status === 201) {
                console.log(xhr.responseText);
                window.location.replace('http://localhost:8080/my-home');
            } else {
                console.error(xhr.responseText);
                alert("goHomeHtml() Error");
            }
        }
    };
    xhr.open('GET', ur, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send();    
    return;
}

function goGameHtml() {
    var xhr = new XMLHttpRequest();
    var ur = 'http://localhost:8080/game/game-home';
    xhr.onreadystatechange = function() {
        if(xhr.readyState === xhr.DONE) {
            if (xhr.status === 200 || xhr.status === 201) {
                console.log(xhr.responseText);
                window.location.replace('http://localhost:8080/game/game-home');
            } else {
                console.error(xhr.responseText);
                alert("goGameHtml() Error");
            }
        }
    };
    xhr.open('GET', ur, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send();
    return;
}

function goRankPageHtml() {
    // XMLHttpRequest 객체 생성
    var xhr = new XMLHttpRequest();
    var ur = 'http://localhost:8080/game/rank-page';
    xhr.onreadystatechange = function() {
        if(xhr.readyState === xhr.DONE) {
            if (xhr.status === 200 || xhr.status === 201) {
                console.log(xhr.responseText);
                window.location.replace('http://localhost:8080/game/rank-page');
            } else {
                console.error(xhr.responseText);
                alert("goRankPageHtml() Error");
            }
        }
    };
    xhr.open('GET', ur, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send();
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

// 더보기 버튼을 누르면 추가 메뉴가 나오게 해줘
function gamePageMoreList() {
    let btnMore = document.querySelector(".btn-more");
    let ulMore = document.querySelector(".ul-more");
    btnMore.addEventListener("click", function(){
        if (ulMore.style.display === "none") {
            ulMore.style.display = "block";
        } else {
            ulMore.style.display = "none";
        }
    });
    //liMore 변수에 class 이름은 btn-more > ul > li 설정해줘
    // let liMore = document.querySelectorAll(".btn-more > ul > li");
    
    // btnMore.addEventListener("click", function() {
    //     if (liMore.style.display === "none") {
    //         liMore.style.display = "block";
    //     } else {
    //         liMore.style.display = "none";
    //     }
    // });
    return;
}

// 서버가 실행하면 바로 실행하게 해줘

var ulMore = document.querySelector(".btn-more > ul");
ulMore.style.display = "none";
    // var liMore = document.querySelectorAll(".btn-more > ul >li");
    // var liMoreLen = liMore.length;
    // for (let i = 0; i < liMoreLen; i++) {
    //     liMore[i].style.display = "none";
    // }