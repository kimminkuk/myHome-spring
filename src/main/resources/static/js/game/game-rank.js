function gameRankSearch(userName) {
    var xhr = new XMLHttpRequest();
    var ur = 'http://localhost:8080/game/rank-search';
    xhr.onreadystatechange = function() {
        if(xhr.readyState === xhr.DONE) {
            if (xhr.status === 200 || xhr.status === 201) {
                
            } else {
                console.error(xhr.responseText);
                alert("gameRankSearch() Error");
            }
        }
    };
    xhr.open('GET', ur + '?userName=' + userName, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send();
    return;
}