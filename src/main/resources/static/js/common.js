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
    var ur = 'http://localhost:8080/my-home/game';
    xhr.onreadystatechange = function() {
        if(xhr.readyState === xhr.DONE) {
            if (xhr.status === 200 || xhr.status === 201) {
                console.log(xhr.responseText);
                window.location.replace('http://localhost:8080/my-home/game');
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