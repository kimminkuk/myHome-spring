const game_end_cond = {
    STOP: 0,
    RESET: 1
};

const game_mutex_cond = {
    WAIT: 0,
    EXECUTE: 1
}

const ob_dir_cond = {
    UP: -1,
    DOWN: 1,
    LEFT: -1,
    RIGHT: 1,
    HOLD : 0
}

var count = 0;
var move = 0;
var end_count = 0;
var run_arrow = 1;
var ob_top = document.querySelector("#obstacle_top1");
var ob_bottom = document.querySelector("#obstacle_bottom1");
var ob1 = document.querySelector("#obstacle1");
var ob2 = document.querySelector("#obstacle2");
var ball_1 = document.querySelector("#ball_1");
var player_bar1 = document.querySelector("#player_bar1");

let userName = '';
let userScore = 0;
let ob2_count = 0;
let ob2_move = 0;
let control_direction = ob_dir_cond.LEFT;
let game_mutex = game_mutex_cond.WAIT;
var ball_pos = new Array(ob_dir_cond.HOLD, ob_dir_cond.HOLD);
var ob2_pos = new Array(ob_dir_cond.HOLD, ob_dir_cond.HOLD);

function gameInitCondition_ver2() {
    userName = '';
    userScore = 0;
    ob2_count = 0;
    ob2_move = 20;
    ob1.style.left = "30%";
    ob1.style.top = "60px";
    ob2.style.left = "600px";
    ob2.style.top = "60px";
    ball_1.style.top = "40%";
    ball_1.style.left = "20%";
    player_bar1.style.left = "1%";
    player_bar1.style.right = "2%";
    player_bar1.style.top = "30%";

    end_count = 0;
    control_direction = ob_dir_cond.LEFT;
    game_mutex = game_mutex_cond.WAIT;
    for (var i = 0; i < 2; i++) {
        ball_pos[i] = ob_dir_cond.HOLD;
        ob2_pos[i] = ob_dir_cond.HOLD;    
    }
}
function gameInitCondition() {
    userName = '';
    userScore = 0;
    ob2_count = 0;
    ob2_move = 20;
    ob1.style.left = "30%";
    ob1.style.top = "60px";
    ob2.style.left = "600px";
    ob2.style.top = "60px";
    ball_1.style.top = "40%";
    ball_1.style.left = "20%";

    player_bar1.style.left = "1%";
    player_bar1.style.right = "2%";
    player_bar1.style.top = "30%";    
    end_count = 0;
    control_direction = ob_dir_cond.LEFT;
    game_mutex = game_mutex_cond.EXECUTE;
    ball_pos[0] = ob_dir_cond.LEFT;
    ball_pos[1] = ob_dir_cond.HOLD;
    ob2_pos[0] = ob_dir_cond.HOLD;
    ob2_pos[1] = ob_dir_cond.DOWN;
}

function gameStart(pName) {
    if ( game_mutex == game_mutex_cond.WAIT ) {
        //입력이 문자일때, 공백이 아닐때, 이거 말고 또 있나??
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
    requestAnimationFrame(run);
}

function run() {
    userScore += 1;
    
    if (end_count > 50 || game_mutex == game_mutex_cond.WAIT) {
        return;
    }
    
    ob2_count++;
    count = 10;

    if (ob2_count != 0 && ob2_count % 20 == 0) {
        ob2_move = ob2_move * ob2_pos[1];
    }
    ob2.style.top = parseInt(ob2.style.top) + ob2_move + "px";

    ball_movement(ball_1, count, ball_pos)
    ob_judge(ball_1, player_bar1 ,ob1, ob2, ball_pos);
    if (parseInt(ball_1.style.right) >= parseInt(ob1.style.left)) {
        control_direction = 1;
    }
    if (parseInt(ball_1.style.left) <= 5) {
        control_direction = 0;
    }
    requestAnimationFrame(run);
}

function gameReset() {
    //gameInit();
    //JS는 전체 초기화 느낌 없나??
    //1. requestAnimationFrame 종료하는 방법
    //2.  
    gameEnd(game_end_cond.RESET);
    gameInitCondition_ver2();
}

function gameStop() {
    //Stop Btn Action
    gameEnd(game_end_cond.STOP);
    gameInitCondition_ver2();
}

function gameEnd(end_cond) {
    game_mutex = game_mutex_cond.WAIT;
    console.log("Game Finish");
    var xhr = new XMLHttpRequest();
    var ur = 'http://localhost:8080/my-home/game/result';
    xhr.onreadystatechange = function () {
        if (xhr.readyState === xhr.DONE) {
            if (xhr.status === 200 || xhr.status === 201) {
                console.log(xhr.responseText);
                //window.location.replace('');
                if (end_cond == game_end_cond.STOP) {
                    alert("game End write ok");
                } else if (end_cond == game_end_cond.RESET) {
                    alert("game Reset write ok");
                }
                
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

function left_direction_movement(control_ob, speed_ob) {
    control_ob.style.left = parseInt(control_ob.style.left) - speed_ob + "px";
    control_ob.style.right = parseInt(control_ob.style.left) + 100 + "px";
    return;
}

function right_direction_movement(control_ob, speed_ob) {
    control_ob.style.left = parseInt(control_ob.style.left) + speed_ob + "px";
    control_ob.style.right = parseInt(control_ob.style.left) + 100 + "px";
    return;
}

function ball_movement(control_ob, speed_ob, pos) {
    // control_ob.style.left = parseInt(control_ob.style.left) + (speed_ob * pos[0]) + "px";
    // control_ob.style.top = parseInt(control_ob.style.top) + (speed_ob * pos[1]) + "px";
    control_ob.style.left = parseInt(control_ob.style.left) + (speed_ob * pos[0])*0.1 + "%";
    control_ob.style.top = parseInt(control_ob.style.top) + (speed_ob * pos[1])*0.1 + "%";    
    return;
}

function ob_judge(control_ob, player_bar, ob1, ob2, b_pos) {
    // cond-1
    if ( parseInt(control_ob.style.left) < parseInt(player_bar.style.right) ) {
        //if ( control_ob.style.top < player_bar.style.top && control_ob.style.bottom > player_bar.style.bottom) {
            b_pos[0] = ob_dir_cond.RIGHT;
            b_pos[1] = ob_dir_cond.HOLD;
        //}
    }
    //top
    //if ( control_ob.style.top < ob_top.style.bottom)
    //bottom
    return;
}

//gameInit();