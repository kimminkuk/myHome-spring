function gameRankSearch() {
    var ur = 'http://localhost:8080/game/rank-search';
    let rank_page_input = document.querySelector("#rank-page-input");
    let userName = rank_page_input.value;
    if ( userName == null || userName == "" ) {
        alert("검색할 유저명을 입력해주세요.");
        return;
    }
    //userName 이 string이 아닐때 (HTML에서 작성하는 텍스트가 string이 아닐수 가 있나??)
    if ( typeof userName != "string" ) {
        alert("검색할 유저명은 문자열이어야 합니다.");
        return;
    }

    var data = '?userName=' + encodeURIComponent(userName);
    ur = ur + data;
    
    window.location.replace(ur);
    
    return;
}

function gameRankSearchDebugCode() {
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
    let rank_page_input2 = document.querySelector("#rank-page-input2");

    let userName = rank_page_input2.value;
    if ( userName == null || userName == "" ) {
        alert("검색할 유저명을 입력해주세요.");
        return;
    }
    //userName 이 string이 아닐때
    if ( typeof userName != "string" ) {
        alert("검색할 유저명은 문자열이어야 합니다.");
        return;
    }
    var data = 'userName=' + encodeURIComponent(userName);
    xhr.open('POST', ur, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(data);
    return;
}

function testCode() {
    //ajax post 통신하는 코드
    
    //ajax post 통신 후, 화면 전환 코드
    
    

}