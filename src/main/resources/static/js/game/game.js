const gameEndCond = {
    STOP: 0,
    RESET: 1
};

const gameMutexCond = {
    WAIT: 0,
    EXECUTE: 1
}

const obDirCond = {
    UP: -1,
    DOWN: 1,
    LEFT: -1,
    RIGHT: 1,
    HOLD : 0
}

const gameWinner = {
    PLAYER : 0,
    COMPUTER : 1
}

var ballSpeed = 0;
var move = 0;
var endCount = 0;
var obTop = document.querySelector("#obstacle_top1");
var obBottom = document.querySelector("#obstacle_bottom1");
var ob1 = document.querySelector("#obstacle1");
var ob2 = document.querySelector("#obstacle2");
var ball_1 = document.querySelector("#ball_1");
var playerBar_1 = document.querySelector("#player_bar1");
var comBar_1 = document.querySelector("#com_bar1");

let userName = '';
let userScore = 0;
let controlDirection = obDirCond.LEFT;
let gameMutex = gameMutexCond.WAIT;
var ballAngle = 0;
var ballPos = new Array(obDirCond.HOLD, obDirCond.HOLD);
var obPos_2 = new Array(obDirCond.HOLD, obDirCond.HOLD);
var ballVelocity = 0.1;
var mousePos = new Array(2);
var mousePosInit = new Array(2);
const topWallPos = 50;
const thisScreenYpercent = document.documentElement.scrollHeight / 100;
const thisScreenXpercent = document.documentElement.scrollWidth / 100;
const playerBarScreenY = ( document.documentElement.scrollHeight / 100 ) * 20;
const comBarScreenYofs = ( document.documentElement.scrollHeight / 100 ) * 25;
var pBarDirectUpDown = obDirCond.HOLD;
var nowTimeStamp = 0;
var mouseSpeed = 0;
var comSpeed = ballVelocity;
var comBarCenter = 0;
var ballCenter = 0;
var playerBarCenter = 0;

function gameInitCondition_ver2() {
    userName = '';
    userScore = 0;
    obTop.style.bottom =  55 / thisScreenYpercent + "%";
    obBottom.style.top = ( document.documentElement.scrollHeight - 95 ) / thisScreenYpercent + "%";
    ob1.style.left = "30%";
    ob1.style.top = "60px";
    ob2.style.left = "600px";
    ob2.style.top = "60px";
    ball_1.style.bottom = "50%";
    ball_1.style.top = "40%";
    ball_1.style.left = "45%";
    ball_1.style.right = "50%";
    
    playerBar_1.style.left = "1%";
    playerBar_1.style.right = "2%";
    playerBar_1.style.top = "30%";
    playerBar_1.style.bottom = "50%";

    comBar_1.style.left = "98%";
    comBar_1.style.right = "99%";
    comBar_1.style.top = "30%";
    comBar_1.style.bottom = "55%";

    comBarCenter = getObCenterPos(comBar_1);
    ballCenter = getObCenterPos(ball_1);
    playerBarCenter = getObCenterPos(playerBar_1);

    mouseSpeed = 0;
    ballVelocity = 0.1;
    comSpeed = ballVelocity;
    endCount = 0;
    controlDirection = obDirCond.LEFT;
    gameMutex = gameMutexCond.WAIT;
    for (var i = 0; i < 2; i++) {
        ballPos[i] = obDirCond.HOLD;
        obPos_2[i] = obDirCond.HOLD;
        mousePos[i] = 0;
        mousePosInit[i] = 0;
    }
}
function gameInitCondition() {
    userName = '';
    userScore = 0;
    obTop.style.bottom =  55 / thisScreenYpercent + "%";
    obBottom.style.top = ( document.documentElement.scrollHeight - 95 ) / thisScreenYpercent + "%";
    ob1.style.left = "30%";
    ob1.style.top = "60px";
    ob2.style.left = "600px";
    ob2.style.top = "60px";
    ball_1.style.bottom = "50%";
    ball_1.style.top = "40%";
    ball_1.style.left = "45%";
    ball_1.style.right = "50%";

    playerBar_1.style.left = "1%";
    playerBar_1.style.right = "2%";
    playerBar_1.style.top = "30%";
    playerBar_1.style.bottom = "50%";  

    comBar_1.style.left = "98%";
    comBar_1.style.right = "99%";
    comBar_1.style.top = "30%";
    comBar_1.style.bottom = "55%";
    comBarCenter = getObCenterPos(comBar_1);
    ballCenter = getObCenterPos(ball_1);
    playerBarCenter = getObCenterPos(playerBar_1);
    endCount = 0;
    mouseSpeed = 0;
    ballVelocity = 0.1;
    comSpeed = ballVelocity;
    controlDirection = obDirCond.LEFT;
    gameMutex = gameMutexCond.EXECUTE;
    ballPos[0] = obDirCond.LEFT;
    ballPos[1] = obDirCond.HOLD;
    obPos_2[0] = obDirCond.HOLD;
    obPos_2[1] = obDirCond.DOWN;
    for (var i = 0; i < 2; i++) {
        mousePos[i] = 0;
        mousePosInit[i] = 0;
    }
}

function getObCenterPos(obStyle) {
    return (parseFloat(obStyle.style.bottom) + parseFloat(obStyle.style.top)) / 2
}

function gameStart(pName) {
    if ( gameMutex == gameMutexCond.WAIT ) {
        if (typeof(pName.value) === "string" && pName.value.length !== 0) {
            gameInit(pName);
        } else {
            //TODO:
            //이름 입력 다시하게하는거 HTML창에 띄우자
            //alert는 너무 위험함
            alert("이름 다시 입력해주세요.");
        }
    }
    return;
}

function gameInit(pName) {
    gameInitCondition();    
    userName = pName.value;
    userScore = 0;
    window.addEventListener('mousemove', mousemove);
    mousePosInit[1] = 0;
    requestAnimationFrame(run);
}

function run() {
    userScore += 1;
    
    if (endCount > 50 || gameMutex == gameMutexCond.WAIT) {
        return;
    }
    ballSpeed = 5;
    playerMovement(playerBar_1, mousePos);
    ballMovement(ball_1, ballSpeed, ballPos);
    comMovement(comBar_1, ball_1, comBarCenter, ballCenter);
    obJudge(ball_1, playerBar_1, comBar_1, obTop, obBottom, ballPos);
    requestAnimationFrame(run);
}

function gameReset() {
    gameEnd(gameEndCond.RESET);
    gameInitCondition_ver2();
}

function gameStop() {
    gameEnd(gameEndCond.STOP);
    gameInitCondition_ver2();
}

function gameFinish(winner) {
    gameEnd(gameEndCond.STOP, winner);
    gameInitCondition_ver2();
    if ( winner == gameWinner.COMPUTER ) {
        //TODO:
        //COMPUTER 승리 정보 DB
        //COMPUTER 승리, 시간: xxxx-xx-xx xx:xx:xx, 점수: xxxxx
    } else {
        //TODO:
        //플레이어 승리 정보 DB
        //플레이어 승리, 시간: xxxx-xx-xx xx:xx:xx, 점수: xxxxx
    }
}

function gameEndPostTest(userName) {
    gameMutex = gameMutexCond.WAIT;
    var xhr = new XMLHttpRequest();
    var ur = 'http://localhost:8080/game/result';
    
    xhr.onreadystatechange = function() {
        if (xhr.readyState === xhr.DONE && xhr.status === 200) {
            console.log(xhr.responseText);
        }
        else {
            console.log("error");
            errorList.errorCode01();
        }
    };
    var userScore = 100;
    var postRedisData = "name=" + encodeURIComponent(userName) + "&score=" + encodeURIComponent(userScore);
    xhr.open('POST', ur);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(postRedisData);
    return;
}

function gameEnd(end_cond, winner) {
    if ( winner == gameWinner.COMPUTER ) {
        //TODO:
        //Computer가 승리할때는 DB 저장하지 말자.
        //Computer의 승리는 저장하지 않는것으로 한다.
        return;
    }
    gameMutex = gameMutexCond.WAIT;
    console.log("Game Finish");
    var xhr = new XMLHttpRequest();
    var ur = 'http://localhost:8080/game/result';
    xhr.onreadystatechange = function () {
        if (xhr.readyState === xhr.DONE) {
            if (xhr.status === 200 || xhr.status === 201) {
                console.log(xhr.responseText);
                //window.location.replace('');
                if (end_cond == gameEndCond.STOP) {
                    alert("game End write ok");
                } else if (end_cond == gameEndCond.RESET) {
                    alert("game Reset write ok");
                }
                
            } else {
                console.error(xhr.responseText);
                alert('write 문제발생');
            }
        }
    };

    var winnerName = "플레이어: ";
    userName = winnerName + userName;
    var efData = "name=" + encodeURIComponent(userName) + "&score=" + encodeURIComponent(userScore);

    xhr.open('POST', ur, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(efData);
    
    return;
}

function obJudge(controlOb, playerBar, com_bar, ob1, ob2, b_pos) {
    if ( parseFloat(controlOb.style.left) < parseFloat(playerBar.style.right) ) {
        let cOb = controlOb.getBoundingClientRect();
        let pOb = playerBar.getBoundingClientRect();
        if ( parseFloat(cOb.y) > parseFloat(pOb.y)
            && parseFloat(cOb.y + ( 10 * thisScreenYpercent )) < parseFloat(pOb.y + ( 20 * thisScreenYpercent ))) {
            b_pos[0] = obDirCond.RIGHT;
            b_pos[1] = pBarDirectUpDown * mouseSpeed;
            comSpeed = mouseSpeed;
        }
    }
    else if ( parseFloat(controlOb.style.right) > parseFloat(com_bar.style.left) ) {
        let ball_ob = controlOb.getBoundingClientRect();
        let com_bar_ob = com_bar.getBoundingClientRect();
        if ( parseFloat(ball_ob.y) > parseFloat(com_bar_ob.y)
            && parseFloat(ball_ob.y + ( 10 * thisScreenYpercent )) < parseFloat(com_bar_ob.y + ( 25 * thisScreenYpercent ))) {
            b_pos[0] = obDirCond.LEFT;
            //b_pos[1] = pBarDirectUpDown * mouseSpeed;
        }
    }
    else if ( parseFloat(controlOb.style.top) < parseFloat(ob1.style.bottom) ) {
        b_pos[1] = b_pos[1] * -1;
    }

    else if ( parseFloat(controlOb.style.bottom) > parseFloat(ob2.style.top) ) {
        b_pos[1] = b_pos[1] * -1;
    }

    return;
}

function left_direction_movement(controlOb, speedOb) {
    controlOb.style.left = parseInt(controlOb.style.left) - speedOb + "px";
    controlOb.style.right = parseInt(controlOb.style.left) + 100 + "px";
    return;
}

function right_direction_movement(controlOb, speedOb) {
    controlOb.style.left = parseInt(controlOb.style.left) + speedOb + "px";
    controlOb.style.right = parseInt(controlOb.style.left) + 100 + "px";
    return;
}

function ballMovement(controlOb, speedOb, pos) {
    controlOb.style.left = parseFloat(controlOb.style.left) + ( speedOb * pos[0] * ballVelocity ) + "%";
    controlOb.style.right = parseFloat(controlOb.style.right) + ( speedOb * pos[0] * ballVelocity ) + "%";
    controlOb.style.bottom = parseFloat(controlOb.style.bottom) + ( speedOb * pos[1] * ballVelocity ) + "%";
    controlOb.style.top = parseFloat(controlOb.style.top) + ( speedOb * pos[1] * ballVelocity ) + "%";    
    ballCenter = getObCenterPos(controlOb);

    if ( parseFloat(controlOb.style.right) < 0.1 ) {
        gameFinish(gameWinner.COMPUTER);
        return;
    }
    if ( parseFloat(controlOb.style.left) > 99.9 ) {
        gameFinish(gameWinner.PLAYER);
        return
    }

    return;
}

function playerMovement(playerBar, mousePos) {
    playerBar.style.top = (( mousePos[1] + topWallPos ) / thisScreenYpercent) + "%";
    playerBar.style.bottom = ( parseFloat(playerBar.style.top) + playerBarScreenY ) + "%";
    playerBarCenter = getObCenterPos(playerBar);
    return;
}

function comMovement(comBar, moveOb, comBarCenter, ballCenter) {
    //TODO:
    // Ball의 움직임을 따라서 위, 아래로 이동
    // Ball의 속도를 가져와야하는데 이거 운동량???
    // 속도는 우선.. 플레이어 벽이랑 공이랑 부딪히는 순간의 속도를 가져옴
    // 지금 공의 속도는 그 부딪히는 순간의 속도로 입력했음.
    // 그러니깐, 컴퓨터 바의 속도도 똑같이했다.
    comBarCenter = getObCenterPos(comBar);
    let comBarDirect = obDirCond.DOWN;
    
    // 컴퓨터바가 위로 올라가야함
    if ( ballCenter < comBarCenter ) {
        comBarDirect = obDirCond.UP;
    }
    comBar.style.top = (parseFloat(comBar.style.top) + (( comBarDirect * comSpeed ) / thisScreenYpercent)) + "%";
    comBar.style.bottom = (parseFloat(comBar.style.top) + 25) + "%";
    
    return;
}

function mousemove(event) {
    var nowTime = Date.now();
    var timeDt = nowTime - nowTimeStamp;
    var distance = Math.abs( mousePos[1] - event.pageY );
    mouseSpeed = (distance / timeDt).toFixed(2);

    nowTimeStamp = nowTime;
    pBarDirectUpDown = mousePos[1] - event.pageY; //Up:+ Down:-
    
    if ( pBarDirectUpDown > 0 ) {
        pBarDirectUpDown = obDirCond.UP;
    } else {
        pBarDirectUpDown = obDirCond.DOWN;
    }

    if ( event.pageY < topWallPos) {
        mousePos[1] = topWallPos;
    } else {
        mousePos[1] = event.pageY;    
    }
    
    return;
}

function get_angle(x1, y1, x2, y2) {
    var rad = Math.atan2(y2 - y1, x2 - x1);
    
    return ( rad * 180 ) / Math.PI;
}

function set_ball_angle(ob, x1, y1) {
    let obPos = ob.getBoundingClientRect();
    ballAngle = get_angle( x1, y1, obPos.x, obPos.y );
    return;
}

//gameInit();