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
var com_bar1 = document.querySelector("#com_bar1");

let userName = '';
let userScore = 0;
let ob2_count = 0;
let ob2_move = 0;
let control_direction = ob_dir_cond.LEFT;
let game_mutex = game_mutex_cond.WAIT;
var ball_angle = 0;
var ball_pos = new Array(ob_dir_cond.HOLD, ob_dir_cond.HOLD);
var ob2_pos = new Array(ob_dir_cond.HOLD, ob_dir_cond.HOLD);
var ball_velocity = 0.1;
var mouse_pos = new Array(2);
var mouse_pos_init = new Array(2);
const top_wall_pos = 50;
const this_screen_y_percent = document.documentElement.scrollHeight / 100;
const this_screen_x_percent = document.documentElement.scrollWidth / 100;
const player_bar_screen_y = ( document.documentElement.scrollHeight / 100 ) * 20;
var pbar_direct_up_down = ob_dir_cond.HOLD;
var pbar_velocity = 0;
var now_time_timestamp = 0;
var mouse_speed = 0;

function gameInitCondition_ver2() {
    userName = '';
    userScore = 0;
    ob2_count = 0;
    ob2_move = 20;
    ob_top.style.bottom =  55 / this_screen_y_percent + "%";
    ob_bottom.style.top = ( document.documentElement.scrollHeight - 95 ) / this_screen_y_percent + "%";
    ob1.style.left = "30%";
    ob1.style.top = "60px";
    ob2.style.left = "600px";
    ob2.style.top = "60px";
    ball_1.style.top = "30%";
    ball_1.style.bottom = "40%";
    ball_1.style.left = "20%";
    
    player_bar1.style.left = "1%";
    player_bar1.style.right = "2%";
    player_bar1.style.top = "30%";
    player_bar1.style.bottom = "50%";

    com_bar1.style.right = "1%";
    com_bar1.style.top = "30%";
    com_bar1.style.bottom = "50%";

    end_count = 0;
    control_direction = ob_dir_cond.LEFT;
    game_mutex = game_mutex_cond.WAIT;
    for (var i = 0; i < 2; i++) {
        ball_pos[i] = ob_dir_cond.HOLD;
        ob2_pos[i] = ob_dir_cond.HOLD;
        mouse_pos[i] = 0;
        mouse_pos_init[i] = 0;
    }
}
function gameInitCondition() {
    userName = '';
    userScore = 0;
    ob2_count = 0;
    ob2_move = 20;
    ob_top.style.bottom =  55 / this_screen_y_percent + "%";
    ob_bottom.style.top = ( document.documentElement.scrollHeight - 95 ) / this_screen_y_percent + "%";
    ob1.style.left = "30%";
    ob1.style.top = "60px";
    ob2.style.left = "600px";
    ob2.style.top = "60px";
    ball_1.style.top = "30%";
    ball_1.style.bottom = "40%";
    ball_1.style.left = "20%";

    player_bar1.style.left = "1%";
    player_bar1.style.right = "2%";
    player_bar1.style.top = "30%";
    player_bar1.style.bottom = "50%";  

    com_bar1.style.right = "1%";
    com_bar1.style.top = "30%";
    com_bar1.style.bottom = "50%";

    end_count = 0;
    control_direction = ob_dir_cond.LEFT;
    game_mutex = game_mutex_cond.EXECUTE;
    ball_pos[0] = ob_dir_cond.LEFT;
    ball_pos[1] = ob_dir_cond.HOLD;
    ob2_pos[0] = ob_dir_cond.HOLD;
    ob2_pos[1] = ob_dir_cond.DOWN;
    for (var i = 0; i < 2; i++) {
        mouse_pos[i] = 0;
        mouse_pos_init[i] = 0;
    }
}

function gameStart(pName) {
    if ( game_mutex == game_mutex_cond.WAIT ) {
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
    mouse_pos_init[1] = 0;
    requestAnimationFrame(run);
}

function run() {
    userScore += 1;
    
    if (end_count > 50 || game_mutex == game_mutex_cond.WAIT) {
        return;
    }
    count = 5;
    player_movement(player_bar1, mouse_pos);
    ball_movement(ball_1, count, ball_pos);
    ob_judge(ball_1, player_bar1 ,ob_top, ob_bottom, ball_pos);
    
    requestAnimationFrame(run);
}

function gameReset() {
    gameEnd(game_end_cond.RESET);
    gameInitCondition_ver2();
}

function gameStop() {
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
    control_ob.style.left = parseFloat(control_ob.style.left) + ( speed_ob * pos[0] * ball_velocity ) + "%";
    control_ob.style.bottom = parseFloat(control_ob.style.bottom) + ( speed_ob * pos[1] * ball_velocity ) + "%";
    control_ob.style.top = parseFloat(control_ob.style.top) + ( speed_ob * pos[1] * ball_velocity ) + "%";
    return;
}

function ob_judge(control_ob, player_bar, ob1, ob2, b_pos) {
    if ( parseFloat(control_ob.style.left) < parseFloat(player_bar.style.right) ) {
        let c_ob = control_ob.getBoundingClientRect();
        let p_ob = player_bar.getBoundingClientRect();
        if ( parseFloat(c_ob.y) > parseFloat(p_ob.y)
            && parseFloat(c_ob.y + ( 10 * this_screen_y_percent )) < parseFloat(p_ob.y + ( 20 * this_screen_y_percent ))) {
            b_pos[0] = ob_dir_cond.RIGHT;
            b_pos[1] = pbar_direct_up_down * mouse_speed;
            
        }
    }

    if ( parseFloat(control_ob.style.top) < parseFloat(ob1.style.bottom) ) {
        b_pos[1] = b_pos[1] * -1;
    }

    if ( parseFloat(control_ob.style.bottom) > parseFloat(ob2.style.top) ) {
        b_pos[1] = b_pos[1] * -1;
    }

    return;
}

function player_movement(player_bar, mouse_pos) {
    player_bar.style.top = (( mouse_pos[1] + top_wall_pos ) / this_screen_y_percent) + "%";
    player_bar.style.bottom = ( parseFloat(player_bar.style.top) + player_bar_screen_y ) + "%";
    return;
}

function mousemove(event) {
    var now_time = Date.now();
    var time_dt = now_time - now_time_timestamp;
    var distance = Math.abs( mouse_pos[1] - event.pageY );
    mouse_speed = (distance / time_dt).toFixed(2);

    now_time_timestamp = now_time;
    pbar_direct_up_down = mouse_pos[1] - event.pageY; //Up:+ Down:-
    
    if ( pbar_direct_up_down > 0 ) {
        pbar_direct_up_down = ob_dir_cond.UP;
    } else {
        pbar_direct_up_down = ob_dir_cond.DOWN;
    }

    if ( event.pageY < top_wall_pos) {
        mouse_pos[1] = top_wall_pos;
    } else {
        mouse_pos[1] = event.pageY;    
    }
    
    return;
}

function get_angle(x1, y1, x2, y2) {
    var rad = Math.atan2(y2 - y1, x2 - x1);
    
    return ( rad * 180 ) / Math.PI;
}

function set_ball_angle(ob, x1, y1) {
    let ob_pos = ob.getBoundingClientRect();
    ball_angle = get_angle( x1, y1, ob_pos.x, ob_pos.y );
    return;
}

//gameInit();