const E_quantStrategy = {
    0: "TEMP1",
    1: "TEMP2",
    2: "TEMP3",
    3: "절대망하지않는 시리즈1 (망하는 중)"
};

/**
 *    기본적인 화면 구성
 *    1. 버튼 구성
 */
function quantPageInit() {

    // 시가총액 상위 순위, 하위 순위, 상위 퍼센트, 하위 퍼센트는 and연산임
    let martketRankingHighInputData = document.querySelector(".market-capitalization-ranking-high-input-data");
    let martketRankingLowInputData = document.querySelector(".market-capitalization-ranking-low-input-data");
    let martketRankingHighPercentInputData = document.querySelector(".market-capitalization-percent-high-input-data");
    let martketRankingLowPercentInputData = document.querySelector(".market-capitalization-percent-low-input-data");

    let martketRankingHighText = document.querySelector(".market-capitalization-ranking-high-text");
    let martketRankingLowText = document.querySelector(".market-capitalization-ranking-low-text");
    let martketRankingHighPercentText = document.querySelector(".market-capitalization-percent-high-text");
    let martketRankingLowPercentText = document.querySelector(".market-capitalization-percent-low-text");
    
    // 정보들을 변수에 담은 후, mouseover, mouseout 이벤트를 등록
    let infoOperationProfitRatioInputData = document.querySelector(".operating-profit-ratio-input-data");
    let infoNetProfitRatioInputData = document.querySelector(".net-profit-ration-input-data");
    let infoRoeInputData = document.querySelector(".roe-input-data");
    let infoRoaInputData = document.querySelector(".roa-input-data");
    let infoDebtRatioInputData = document.querySelector(".debt-ratio-input-data");
    let infoCapitalRetentionRateInputData = document.querySelector(".capital-retention-rate-input-data");
    let infoEpsInputData = document.querySelector(".eps-input-data");
    let infoPerInputData = document.querySelector(".per-input-data");
    let infoBpsInputData = document.querySelector(".bps-input-data");
    let infoPbrInputData = document.querySelector(".pbr-input-data");
    let infoCashDpsInputData = document.querySelector(".cash-dps-input-data");
    let infoDividendYieldInputData = document.querySelector(".dividend-yield-input-data");
    let strategyList = document.querySelector(".strategy-list");
    let strategySaveInputData = document.querySelector(".strategy-save");
    let strategyDescription = document.querySelector(".strategy-description");
    let strategyDeleteBtn = document.querySelector(".strategy-delete");
    

    let infoOperationProfitRatioText = document.querySelector(".operating-profit-ratio-text");
    let infoNetProfitRatioText = document.querySelector(".net-profit-ration-text");
    let infoRoeText = document.querySelector(".roe-text");
    let infoRoaText = document.querySelector(".roa-text");
    let infoDebtRatioText = document.querySelector(".debt-ratio-text");
    let infoCapitalRetentionRateText = document.querySelector(".capital-retention-rate-text");
    let infoEpsText = document.querySelector(".eps-text");
    let infoPerText = document.querySelector(".per-text");
    let infoBpsText = document.querySelector(".bps-text");
    let infoPbrText = document.querySelector(".pbr-text");
    let infoCashDpsText = document.querySelector(".cash-dps-text");
    let infoDividendYieldText = document.querySelector(".dividend-yield-text");
    let strategySaveText = document.querySelector(".strategy-save-text");

    let marketRankingArr = new Array( martketRankingHighInputData, martketRankingLowInputData, martketRankingHighPercentInputData, martketRankingLowPercentInputData );
    let infoDataArr = new Array( 
        martketRankingHighInputData, martketRankingLowInputData, martketRankingHighPercentInputData, martketRankingLowPercentInputData, 
        infoOperationProfitRatioInputData, infoNetProfitRatioInputData, infoRoeInputData, infoRoaInputData, 
        infoDebtRatioInputData, infoCapitalRetentionRateInputData, infoEpsInputData, infoPerInputData, 
        infoBpsInputData, infoPbrInputData, infoCashDpsInputData, infoDividendYieldInputData 
    );

    let infoTextArr = new Array( 
        martketRankingHighText, martketRankingLowText, martketRankingHighPercentText, martketRankingLowPercentText, 
        infoOperationProfitRatioText, infoNetProfitRatioText, infoRoeText, infoRoaText, 
        infoDebtRatioText, infoCapitalRetentionRateText, infoEpsText, infoPerText, 
        infoBpsText, infoPbrText, infoCashDpsText, infoDividendYieldText 
    );

    
    let searchParsingBtn = document.querySelector(".search-parsing-btn");
    let searchMemoryBtn = document.querySelector(".search-memory-btn");
    let searchLoadDataBtn = document.querySelector(".search-load-data-btn");

    let divColorArr = new Array( strategyDescription, strategySaveInputData, searchParsingBtn, searchMemoryBtn, searchLoadDataBtn, strategyDeleteBtn );

    marketRankingOperation(marketRankingArr);
    infoStyleAdjustment(infoDataArr);
    infoStyleAdjustment(infoTextArr);
    mouseOnOffStyleListVer(infoDataArr);
    objectClearText(strategySaveText);
    mouseOnOffStyleListVer3(divColorArr, "#FFFFFF", "#00FF00");
    mouseOnOffStyleVer2(strategyList);
    // 네이버 금융 파싱 버튼 클릭 이벤트
    //naverFinanceParsingBtn(strategyDescription);
    
    // 현재 전략을 저장합니다.
    strategySaveBtn(strategySaveText, strategySaveInputData, infoDataArr);

    // 현재 전략을 삭제합니다.
    strategyDeleteBtnFunc(strategyDeleteBtn);

    // 선택한 전략을 불러옵니다.
    loadOneStrategy(strategyList);
    return;
}

/**
 *    시가총액 상위 순위, 하위 순위, 상위 퍼센트, 하위 퍼센트는 and연산임
 */
function marketRankingOperation(marketRankingArr) {
    let marketRankingArrLen = marketRankingArr.length;
    for (let marketRankingIdx = 0; marketRankingIdx < marketRankingArrLen; marketRankingIdx++) {
        marketRankingArr[marketRankingIdx].addEventListener("click", function() {
            for ( let offIdx = 0; offIdx < marketRankingArrLen; offIdx++ ) {
                if ( offIdx != marketRankingIdx ) {
                    marketRankingArr[offIdx].value = "x";
                    //marketRankingArr[offIdx]의 색깔을 회색으로 변경
                    marketRankingArr[offIdx].style.backgroundColor = "black";
                } else {
                    marketRankingArr[offIdx].value = "";
                    marketRankingArr[offIdx].style.backgroundColor = "white";
                }
            }
        });
    }
    return;
}


/**
 *    정보 리스트들의 스타일을 추가 조정
 */
function infoStyleAdjustment(infoArr) {
    let infoArrLen = infoArr.length;
    for (let infoIdx = 0; infoIdx < infoArrLen; infoIdx++) {
        infoArr[infoIdx].style.left = "1%";
    }
    return;
}

/**
 *    마우스 오버, 아웃 리스트로 전부 설정
 */
function mouseOnOffStyleListVer(curObjects) {
    let curObjectsLen = curObjects.length;
    for (let curObjectIdx = 0; curObjectIdx < curObjectsLen; curObjectIdx++) {
        mouseOnOffStyleVer2(curObjects[curObjectIdx]);
    }
    return;
}

/** 
 *    오브젝트, 오리지널 컬러, 마우스on 컬러 추가
 */
function mouseOnOffStyleVer2(curObject) {
    curObject.addEventListener("mouseover", function() {
        this.style.cursor = "pointer";
    });

    curObject.addEventListener("mouseout", function() {
        this.style.cursor = "default";
    });
    return;
}

/**
 *    클릭하면 텍스트를 지운다.
 */
function objectClearText(Object) {
    Object.addEventListener("click", function() {
        Object.value = "";
    });
    return;
}

 /**
  *    리스트로 마우스 오버, 색 추가 (originalColor, mouseOnColor)
  */
function mouseOnOffStyleListVer3(curObjects, originalColor, mouseOnColor) {
    let curObjectsLen = curObjects.length;
    for (let i = 0; i < curObjectsLen; i++) {
        curObjects[i].addEventListener("mouseover", function() {
            this.style.cursor = "pointer";
            this.style.backgroundColor = mouseOnColor;
        });
    
        curObjects[i].addEventListener("mouseout", function() {
            this.style.cursor = "default";
            this.style.backgroundColor = originalColor;
        });        
    }    
    return;
}

/**
 *    네이버 금융 파싱 버튼 클릭 이벤트
 */
// function naverFinanceParsingBtn(object) {
//     object.addEventListener("click", function() {
        
//     }
    
// }

/**
 *    현재 전략을 저장합니다.
 *    @param 전략이름
 *    @param 전략저장버튼
 *    @param 재무제표리스트
 */
function strategySaveBtn( strategyTitle, strategySaveInputData, infoDataArr ) {
    let quantUr = 'http://localhost:8080/quant/save-strategy';
    let data = '';
    strategySaveInputData.addEventListener("click", function() {
        let userName = "mk.yoda@nklcb.com";
        let strategyInfo = ""
        for ( let infoDataIdx = 0; infoDataIdx < infoDataArr.length - 1; infoDataIdx++ ) {
            strategyInfo += String(infoDataArr[infoDataIdx].value) + "/";
        }
        strategyInfo += String(infoDataArr[infoDataArr.length - 1].value);
        data = 'userName=' + userName + '&strategyTitle=' + strategyTitle.value + '&strategyInfo=' + strategyInfo;

        // 전략 리스트를 갱신 필요   
        // ajax로 전략 리스트를 갱신해볼까?
        location.href = quantUr + '?' + data;
    });
    return;
}

/**
 *    전략을 삭제합니다.
 *    @Param 전략삭제버튼
 */
function strategyDeleteBtnFunc(strategyDeleteBtn) {
    strategyDeleteBtn.addEventListener("click", function() {
        //alert로 경고하고, 삭제하시겠습니까? 물어보기
        let confirmResult = confirm("현재 전략을 삭제하시겠습니까?");
        if (confirmResult == false) {
            return;
        }
        let quantUr = 'http://localhost:8080/quant/delete-strategy';
        let curStrategyTitle = document.querySelector(".strategy-list").value;
        let data = 'strategyTitle=' + curStrategyTitle;
        location.href = quantUr + '?' + data;
    });
    return;
}

/**
 *    전략을 가져옵니다.
 *    @param 전략이름
 */
function loadOneStrategy(strategyList) {

    // 1. 처음에 DB에서 전체 데이터를 조회해서 가져온다.
    //    그리고, 전략 정보들을 리스트에 저장해둔다.
    //    전략을 선택하면, 전략 정보를 가져와서, 화면에 뿌려준다.

    // 2. 전략을 선택하면, DB에서 해당 전략을 조회해서 가져온다.
    //    그리고, 전략 정보를 화면에 뿌려준다.

    // 1번으로 하려고 했는데, 리스트로 데이터들을 가져와서 select로 나누긴했는데..
    // 이 많은 데이터들을 전부 HTML에 작성한다??
    // 음.... 이건 처음부터 설계를 잘못한거같은데??
    // 지금이라도 한번 시도는 해보자

    // select strategyList의 value가 변경되면 실행한다.
    strategyList.addEventListener("change", function() {
        let strategyTitle = strategyList.value;
        let quantUr = 'http://localhost:8080/quant/load-strategy';
        let data = 'strategyTitle=' + strategyTitle;
        location.href = quantUr + '?' + data;

        // let xhr = new XMLHttpRequest();
        // xhr.open('GET', quantUr + '?' + data);
        // xhr.send();
    });

    // ajax 통신 코드
    
    // xhr.open('GET', quantUr + '?' + data);
    // xhr.send();
    // xhr.onload = function() {
    //     if (xhr.status === 200 || xhr.status === 201) {
    //         console.log(xhr.responseText);
    //     } else {
    //         console.error(xhr.responseText);
    //     }
    // };

    return
}

quantPageInit();