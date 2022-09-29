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
    //The valid characters are defined in RFC 7230 and RFC 3986
    //https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/encodeURIComponent
    var data = 'userName=' + encodeURIComponent(userName);
    xhr.open('POST', ur, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(data);
    return;
}
