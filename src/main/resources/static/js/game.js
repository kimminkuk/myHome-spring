var count = 0;
var move = 0;
var end_count = 0;
var run_arrow = 1;
var ob1 = document.querySelector("#obstacle1");
var ob2 = document.querySelector("#obstacle2");
var el = document.querySelector("#box1");

let userName = '';
let userScore = 0;
let ob2_count = 0;
let ob2_move = 0;

function gameInitCondition() {
    userName = '';
    userScore = 0;
    ob2_count = 0;
    ob2_move = 20;
    ob1.style.left = "300px";
    ob2.style.left = "600px";
    ob2.style.top = "0px";
    el.style.left = "0px";
    end_count = 0;    
}

function gameStart(pName) {
    //입력이 문자일때, 공백이 아닐때, 이거 말고 또 있나??
    if (typeof(pName.value) === "string" && pName.value.length !== 0) {
        gameInit(pName);
    } else {
        //TODO:
        //이름 입력 다시하게하는거 HTML창에 띄우자
        //alert는 너무 위험함
        alert("이름 다시 입력해주세요.");
    }
    return;
}

function gameInit(pName) {
    gameInitCondition();    
    userName = pName.value;
    userScore = 0;
    requestAnimationFrame(run);
}

function run() {
    userScore += 1;
    //el.style.left = parseInt(el.style.left) + count + "px";
    if (end_count > 50) {
        return;
    }
    end_count++;
    ob2_count++;
    count = 10;
    
    el.style.left = parseInt(el.style.left) + count + "px";
    el.style.right = parseInt(el.style.left) + 100 +"px";

    if (ob2_count != 0 && ob2_count % 20 == 0) {
        ob2_move = ob2_move * -1;
    }
    ob2.style.top = parseInt(ob2.style.top) + ob2_move + "px";
    ob2.style.bottom = parseInt(ob2.style.bottom) + ob2_move + "px";    

    if (parseInt(el.style.right) >= parseInt(ob1.style.left)) {
        gameEnd();
        return;
    }
    requestAnimationFrame(run);
}

function gameReset() {
    gameInit();
}

function gameEnd() {
    console.log("Game Finish");
    var xhr = new XMLHttpRequest();
    var ur = 'http://localhost:8080/my-home/game/result';
    xhr.onreadystatechange = function () {
        if (xhr.readyState === xhr.DONE) {
            if (xhr.status === 200 || xhr.status === 201) {
                console.log(xhr.responseText);
                //window.location.replace('');
                alert("write ok");
            } else {

                console.error(xhr.responseText);
                alert('write 문제발생');
            }
        }
    };
    //var tt = "테스트입니다."
    var eF_Data = "name=" + encodeURIComponent(userName) + "&score=" + encodeURIComponent(userScore);

    xhr.open('POST', ur, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(eF_Data);
    
    return;
}



//gameInit();