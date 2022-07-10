var count = 0;
var move = 0;
var end_count = 0;
var run_arrow = 1;
var ob1 = document.querySelector("#obstacle1");
var el = document.querySelector("#box1");

function gameInit() {
    ob1.style.left = "300px";    
    el.style.left = "0px";
    end_count = 0;
    requestAnimationFrame(run);
}

function run() {
    
    //el.style.left = parseInt(el.style.left) + count + "px";
    if (end_count > 50) {
        return;
    }
    end_count++;
    count = 10;
    el.style.left = parseInt(el.style.left) + count + "px";
    el.style.right = parseInt(el.style.left) + 100 +"px";
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
    return;
}

gameInit();