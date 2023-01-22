
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
    let marketCapitalizationInputData = document.querySelector(".market-capitalization-input-data");
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
    let infoSalesInputData = document.querySelector(".sales-input-data");
    let strategyList = document.querySelector(".strategy-list");
    let strategySaveInputData = document.querySelector(".strategy-save");
    let strategyDescription = document.querySelector(".strategy-description");
    let strategyDeleteBtn = document.querySelector(".strategy-delete");
    

    let marketCapitalizationText = document.querySelector(".market-capitalization-text");
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
    let infoSalesText = document.querySelector(".sales-text");
    let strategySaveText = document.querySelector(".strategy-save-text");

    // 정보들의 div class
    let martketRankingHighDiv = document.querySelector(".company-info-market-capitalization-ranking-high");
    let martketRankingLowDiv = document.querySelector(".company-info-market-capitalization-ranking-low");
    let martketRankingHighPercentDiv = document.querySelector(".company-info-market-capitalization-percent-high");
    let martketRankingLowPercentDiv = document.querySelector(".company-info-market-capitalization-percent-low");
    let marketCapitalizationDiv = document.querySelector(".company-info-market-capitalization");    
    let infoOperationProfitRatioDiv = document.querySelector(".company-info-operating-profit-ratio");
    let infoNetProfitRatioDiv = document.querySelector(".company-info-net-profit-ration");
    let infoRoeDiv = document.querySelector(".company-info-roe");
    let infoRoaDiv = document.querySelector(".company-info-roa");
    let infoDebtRatioDiv = document.querySelector(".company-info-debt-ratio");
    let infoCapitalRetentionRateDiv = document.querySelector(".company-info-capital-retention-rate");
    let infoEpsDiv = document.querySelector(".company-info-eps");
    let infoPerDiv = document.querySelector(".company-info-per");
    let infoBpsDiv = document.querySelector(".company-info-bps");
    let infoPbrDiv = document.querySelector(".company-info-pbr");
    let infoCashDpsDiv = document.querySelector(".company-info-cash-dps");
    let infoDividendYieldDiv = document.querySelector(".company-info-dividend-yield");
    let infoSalesDiv = document.querySelector(".company-info-sales");

    let searchParsingUpdateDateText = document.querySelector(".search-parsing-update-date-text");
    let loadDataParsingUpdateDateText = document.querySelector("load-data-parsing-update-date-text");

    // 캔버스 관련 변수
    let canvas = document.getElementById("myCanvas");

    let marketRankingArr = new Array( martketRankingHighInputData, martketRankingLowInputData, martketRankingHighPercentInputData, martketRankingLowPercentInputData, marketCapitalizationInputData );
    let infoDataArr = new Array( 
        martketRankingHighInputData, martketRankingLowInputData, martketRankingHighPercentInputData, martketRankingLowPercentInputData, marketCapitalizationInputData,
        infoOperationProfitRatioInputData, infoNetProfitRatioInputData, infoRoeInputData, infoRoaInputData, 
        infoDebtRatioInputData, infoCapitalRetentionRateInputData, infoEpsInputData, infoPerInputData, 
        infoBpsInputData, infoPbrInputData, infoCashDpsInputData, infoDividendYieldInputData, infoSalesInputData
    );

    let infoTextArr = new Array( 
        martketRankingHighText, martketRankingLowText, martketRankingHighPercentText, martketRankingLowPercentText, marketCapitalizationText,
        infoOperationProfitRatioText, infoNetProfitRatioText, infoRoeText, infoRoaText, 
        infoDebtRatioText, infoCapitalRetentionRateText, infoEpsText, infoPerText, 
        infoBpsText, infoPbrText, infoCashDpsText, infoDividendYieldText, infoSalesText
    );

    let infoDivArr = new Array(
        martketRankingHighDiv, martketRankingLowDiv, martketRankingHighPercentDiv, martketRankingLowPercentDiv, marketCapitalizationDiv,
        infoOperationProfitRatioDiv, infoNetProfitRatioDiv, infoRoeDiv, infoRoaDiv,
        infoDebtRatioDiv, infoCapitalRetentionRateDiv, infoEpsDiv, infoPerDiv,
        infoBpsDiv, infoPbrDiv, infoCashDpsDiv, infoDividendYieldDiv, infoSalesDiv
    );
    
    let searchParsingBtn = document.querySelector(".search-parsing-btn");
    let searchMemoryBtn = document.querySelector(".search-memory-btn");
    let loadParsingBtn = document.querySelector(".load-data-parsing-btn");
    let loadExcelBtn = document.querySelector(".load-data-memory-btn");
    let saveParsingBtn = document.querySelector(".save-parsing-btn");
    let month1Btn = document.querySelector(".month-1-btn");
    let month2Btn = document.querySelector(".month-2-btn");
    let month6Btn = document.querySelector(".month-6-btn");
    let monthSelBtn = document.querySelector(".month-sel-btn");
    let graphMonthSelArr = new Array( month1Btn, month2Btn, month6Btn, monthSelBtn );
    let divColorArr = new Array( strategyDescription, strategySaveInputData, searchParsingBtn, searchMemoryBtn, 
                                 strategyDeleteBtn, loadParsingBtn, loadExcelBtn, saveParsingBtn,
                                 month1Btn, month2Btn, month6Btn, monthSelBtn );

    marketRankingOperation(marketRankingArr);
    infoStyleTopAdjustment(infoDivArr);
    infoStyleAdjustment(infoDataArr);
    infoStyleAdjustment(infoTextArr);
    mouseOnOffStyleListVer(infoDataArr);
    objectClearText(strategySaveText);
    mouseOnOffStyleListVer3(divColorArr, "#FFFFFF", "#00FF00");
    mouseOnOffStyleVer2(strategyList);
    
    // 네이버 금융 파싱 버튼 클릭 이벤트
    naverFinanceParsingBtn(searchParsingBtn, searchParsingUpdateDateText);
    
    // 전략 불러오기 버튼
    loadNaverFinanceParsingBtn(loadParsingBtn, infoDataArr, strategyList, loadDataParsingUpdateDateText);

    // 현재 전략을 저장합니다.
    strategySaveBtn(strategySaveText, strategySaveInputData, infoDataArr);

    // 현재 전략을 삭제합니다.
    strategyDeleteBtnFunc(strategyDeleteBtn);

    // 선택한 전략을 불러옵니다.
    loadOneStrategy(strategyList);

    // 결과 전략을 저장합니다.
    SaveParsingResultData(saveParsingBtn);
    //SaveParsingResultDataVer2(saveParsingBtn);
    //SaveParsingResultDataVer3(saveParsingBtn);
    //SaveParsingVer4();
    //SaveParsingResultDataVer4(saveParsingBtn);

    // 캔버스 그리기
    //InitCanvas(canvas);
    drawCanvasOfDailyRate(canvas, graphMonthSelArr);
    return;
}

/**
 *    결과 데이터를 저장합니다.
 *    group-quant-mid-1-left-2-table의 자료입니다.
 *    보안 정책 때문에 Blob는 사용할 수 없을수도?? 
 */
function SaveParsingResultData(object) {
    object.addEventListener("click", function() {
        // data는 group-quant-mid-1-left-2-table의 자료입니다.
        let dataArr = document.querySelectorAll(".quant-table-th");

        // Ver1
        // let writeData = new Array();
        // for (let i = 0; i < dataArr.length; i++) {
        //     let dataNumber = dataArr[i].children[0].textContent.replace(",", "");
        //     //writeData.push(dataNumber + "\t" + dataArr[i].children[1].textContent + "\n");
        //     //writeData.push(dataNumber + "\t" + dataArr[i].children[1].textContent);
        //     writeData.push(dataNumber + " " + dataArr[i].children[1].textContent);
        // }
        //feedback , delete
        // In javascript, arrays ares separated by commas when being printed out.
        // using join() method of the writeData array
        //var blob = new Blob([writeData.join("\n")], { type: 'text/plain' });

        // Ver2 Not use push
        let writeData = "";
        for (let i = 0; i < dataArr.length; i++) {
            let dataNumber = dataArr[i].children[0].textContent.replace(",", "");
            writeData += dataNumber + " " + dataArr[i].children[1].textContent + "\n";
        }

        var fileName = prompt("Please enter the file name: ");
        
        // chech if user entered a file name
        if (fileName !== null) {
            // create a blob object
            var blob = new Blob([writeData], { type: 'text/plain' });

            // check if the browser supports the donwload attribute
            if (URL && 'download' in document.createElement('a')) {
                // create a link element
                var link = document.createElement('a');
                link.href = URL.createObjectURL(blob);
                link.download = fileName;
                link.style.display = 'none';
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
            } else {
                // create a new window and open the blob in it
                window.open(URL.createObjectURL(blob));
            }
        }
    });
    return;
}

function SaveParsingResultDataVer2(object) {
    object.addEventListener("click", function() {
        var data = 'data to write';
        var fileName = prompt("Please enter the file name:", "file.txt");
        
        // check if user entered a file name
        if (fileName !== null) {
            // create a blob object
            var blob = new Blob([data], { type: 'text/plain' });
        
            // check if the browser supports the download attribute
            if (URL && 'download' in document.createElement('a')) {
                // create a link element
                var link = document.createElement('a');
                link.href = URL.createObjectURL(blob);
                link.download = fileName;
                link.style.display = 'none';
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
            } else {
                // create a new window and open the blob in it
                window.open(URL.createObjectURL(blob));
            }
        }
        
        // Create a file save as dialog with the file name and default path
        var saveAs = document.createElement("a");
        saveAs.download = fileName;
        saveAs.href = URL.createObjectURL(blob);
        saveAs.style.display = "none";
        document.body.appendChild(saveAs);
        saveAs.click();
        document.body.removeChild(saveAs);        
    });
    return;
}

/**
 *    파일 저장하는 HTML를 호출합니다.
 */
function SaveParsingResultDataVer3(object, objectFileInput) {
    object.addEventListener("click", function() {
        //objectFileInput.click();
        SaveParsingVer4();
    });
    return;
}

//node.js 에서 파일을 저장하는 경로 만들기
// Client-side JavaScript
function SaveParsingResultDataVer4(object) {
    object.addEventListener("click", function() {
        const fileContent = 'This is the content of the file.';

        fetch('/save-file', {
            method: 'POST',
            body: JSON.stringify({ fileContent }),
            headers: { 'Content-Type': 'application/json' },
        })
        .then((response) => {
            if (!response.ok) {
            throw new Error('An error occurred while saving the file.');
            }
            console.log('The file has been saved.');
        })
        .catch((error) => {
            console.error(error);
        });    
    
    });
    return;
}

// // Server-side Node.js
// const path = require('path');
// const fs = require('fs');
// const express = require('express');
// const app = express();
// app.use(express.json());

// app.post('/save-file', (req, res) => {
//   const fileName = 'example.txt';
//   const filePath = path.join(__dirname, 'files', fileName);

//   fs.writeFile(filePath, req.body.fileContent, (err) => {
//     if (err) {
//       res.status(500).send('An error occurred while saving the file.');
//       return;
//     }
//     res.send('The file has been saved.');
//   });
// });


/**
 *   
 */
function SaveParsingVer4() {
    let fileName = "test.txt";
    let text = "test입니다.";
    var a = document.createElement("a");
    a.href = "data:text/plain;charset=utf-8," + encodeURIComponent(text);
    a.download = fileName;
    a.style.display = "none";
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
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
 *    정보 리스트들의 탑 위치를 조정
 */
function infoStyleTopAdjustment(infoArrDiv) {
    let infoArrDivLen = infoArrDiv.length;
    for (let infoIdx = 0; infoIdx < infoArrDivLen; infoIdx++) {
        infoArrDiv[infoIdx].style.top = (infoIdx * 5) + "%";
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
function naverFinanceParsingBtn(object, searchParsingUpdateDateText) {
    object.addEventListener("click", function() {
        
        let confirmResult = confirm("파싱을 시작하시겠습니까?");
        if (confirmResult == false) {
            return;
        }

        //html parsing code
        
        //Get통신
        let quantUr = 'http://localhost:8080/quant/naver-finance-parsing';
        let data = '';
        let xhr = new XMLHttpRequest();
        xhr.open('GET', quantUr);
        xhr.send(data);
        xhr.addEventListener('load', function() {
            if (xhr.status === 200 || xhr.status === 201) {
                alert("파싱이 완료되었습니다.");
                searchParsingUpdateDateText.textContent = "최종:" + getCurDate();
            } else {
                alert("파싱이 실패하였습니다.");
            }
        });
    });
    return;      
}

/**
 *    파싱 데이터를 불러옵니다.
 *    1. 파일 데이터를 읽어옵시다.
 */
function loadNaverFinanceParsingBtn(object, infoDataArr, loadStrategies, loadDataParsingUpdateDateText) {
    //1. Back-end 쪽에서 파일을 읽어와서 계산해주고 결과를 뿌려줍니다.

    object.addEventListener("click", function() {
        let quantUrl = 'http://localhost:8080/quant/get-parsing-data';
        
        // Use the Array.map() method to create an array of the values of the input elements
        let strategyInfo = infoDataArr.map(input => input.value);
        let strategyTitle = loadStrategies.value;


        // Use the Array.join() method to join the values into a single string, separated by "/"
        strategyInfo = strategyInfo.join("/");

        // Use the URLSearchParams API to create the query string
        let params = new URLSearchParams();
        params.set('parsingDataDate', document.querySelector(".search-parsing-update-date-text").textContent);
        params.set('strategyInfo', strategyInfo);
        params.set('strategyTitle', strategyTitle);

        // Use the URL API to create the final URL with the query string
        let url = new URL(quantUrl);
        url.search = params;

        // 파싱 데이터 불러오기 후, 최종 날짜 갱신
        loadDataParsingUpdateDateText.textContent = "최종:" + getCurDate();

        // Redirect to the final URL
        location.href = url;
    });
    return;
}


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

/**
 *    캔버스 그리기 테스트
 */
function InitCanvas(canvas) {
    let ctx = canvas.getContext("2d");

    // Set the canvas width and height
    canvas.width = 300;
    canvas.height = 200;

    //Draw the x and y axis
    ctx.beginPath();
    ctx.moveTo(0, 100);
    ctx.lineTo(300, 100);
    ctx.lineTo(300, 10);
    ctx.stroke();

    // Plot the data points
    for (let i = 0; i <= 200; i++) {
        let x = 50 +i;
        let y = 200 - (i * 20);
        ctx.fillRect(x, y, 1, 1);
    }

    return;
}

/**
 *    시뮬레이션 그림 그리기
 *    1,2,6,기타 버튼 입력시, 그림을 그린다.
 */
function drawCanvasOfDailyRate(canvas, drawOptionArr) {

    //여기서 데이터를 가져와야하나?????

    //drawOptionArr의 버튼들을 누르면, 그림을 그린다.
    let drawOptionArrLen = drawOptionArr.length;
    for (let i = 0; i < drawOptionArrLen; i++) {
        drawOptionArr[i].addEventListener("click", function() {
            let ctx = canvas.getContext("2d");
        });
    }
    return;
}

/**
 *    오늘 날짜를 가져오는 함수
 */
function getCurDate() {
    let today = new Date();
    let year = today.getFullYear();
    let month = today.getMonth() + 1;
    let day = today.getDate();
    let curDate = year + '-' + month + '-' + day;
    return curDate;
}

quantPageInit();