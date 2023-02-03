import { companyDailyDate } from './dailyDate.js';
import { companyDailyRate } from './dailyRate.js';
import { kospiDailyIndexRate } from './dailyIndexRate.js';
import { kosdaqDailyIndexRate } from './dailyIndexRate.js';
import { dailyOriginal } from './dailyIndexRate.js';
import { dailyOnlyDayVer1 } from './dailyIndexRate.js';
import { dailyOnlyDayVer2 } from './dailyIndexRate.js';


const E_quantStrategy = {
    0: "TEMP1",
    1: "TEMP2",
    2: "TEMP3",
    3: "절대망하지않는 시리즈1 (망하는 중)"
};

const E_day = {
    maxDay: 200
};

const E_graphExtra = {
    width: 50,
    height: 50
};

var G_canvasDrawing = false;

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
    let loadDataParsingUpdateDateText = document.querySelector(".load-data-parsing-update-date-text");

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
        
        params.set('strategyInfo', strategyInfo);
        params.set('strategyTitle', strategyTitle);
        params.set('parsingLatelyDate', loadDataParsingUpdateDateText.textContent);

        // Use the URL API to create the final URL with the query string
        let url = new URL(quantUrl);
        url.search = params;

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
 *    기타는 최소 하루 이상이어야 그림을 그릴 수 있다.
 */
function drawCanvasOfDailyRate(canvas, drawOptionArr) {

    
    //drawOptionArr의 버튼들을 누르면, 그림을 그린다.
    let drawOptionArrLen = drawOptionArr.length;
    
    for (let i = 0; i < drawOptionArrLen; i++) {
        drawOptionArr[i].addEventListener("click", function() {            
            let drawOption = drawOptionArr[i].getAttribute("value");
            let dayValue = drawOption * 30;
            console.log("click!");
            // 결국.. Back -> front로 데이터 가져오기로 했습니다.
            // 주의사항: 시간이 5초 이상 걸리면 바로 포기입니다...(20~30개의 회사가 이상적인 테스트 환경)
            // 1. Front에서 회사들의 데이터를 Back으로 보냅니다.
            // 2. Back에서 회사들의 데이터를 받아서, 일별 시세를 보내줍니다. (1, 2, 6, 기타)
            // 3. Front에서 받은 데이터를 가지고, 그림을 그립니다.
            // 아..이거 새로고침으로 하면 별로일거같은데..
            // 이건 포기 아무리봐도 데이터를 2->3에서 너무 많은 자원을 소모해야해
            // 일단, javascript로 그냥 데이터가 만들어졌다는 가정으로 하자..
            

            // 회사가 저장된 순서를 가져오기 O(1) Get할거임
            let companyIdxArr = getCompanyIdxResult();
            let canDrawList = new Array();
            let notDrawList = new Array();
            let rateDailyPercentList = new Array();
            let kospiDailyPercent = new Array();
            let kosdaqDailyPercent = new Array();
            const E_companyDataFormat = {
                companyName: 0,
                companyCode: 1
            }
            let companyIdxArrLen = companyIdxArr.length;
            for (let companyIdx = 0; companyIdx < companyIdxArrLen; companyIdx++) {
                let rateList = companyDailyRate[companyIdxArr[companyIdx][E_companyDataFormat.companyCode] - 1];
                let dateList = companyDailyDate[companyIdxArr[companyIdx][E_companyDataFormat.companyCode] - 1];


                // Step 1) 선택한 날짜(달)만큼 Date 및 Rate를 가져 올 수 있는지 판단합니다.
                //         사실 여기서부터 좀 막막함.. 주말은 어떻게 처리할거고 흠.. 연휴는 어떻게 처리할거고..
                //         삼성전자(Idx:2373 - 1) 를 기준으로 잡고 하자. 이거랑 다르면 문제가 있는거다 ㅇㅋ
                //         아.. 이거도 그냥 파이썬으로 미리 분류를 하자. 그래서 텍스트 파일에서 불러와서 pass/fail 찍어주는것만 하자..
                
                //drawDetermineCanDraw(dayValue, dateList);
                let canDraw = drawDetermineCanDraw(dayValue, dateList);
                if (canDraw == false) {
                    notDrawList.push(companyIdxArr[E_companyDataFormat.companyName][companyIdx]);
                    continue;
                } else {
                    canDrawList.push(canDraw);
                }
                // Step 2) 가져올 수 있다면, 가져옵니다. (가져올 수 없으면 해당 element는 그림을 그리지 않고, 다음 element로 넘어갑니다.)
                //         이거는 rateList를 split해서, dayValue만큼 가져오면 될거같은데..
                let rateListSplit = rateList.slice(0, dayValue);
                
                // Step 3) 그림을 그릴 수 있는 데이터들의 +(퍼센트), -(퍼센트) 등으로 잘 정리해서, 하나의 그래프로 그려줄 데이터를 만듭니다.
                //         각 회사의 일별 시세를 매일매일 비교해서 +, -를 구분해서, 그래프로 그려줄 데이터를 만들어야합니다.
                //         ex) 2022-04-05 100원 , 2022-04-06 110원 이면, +10%를 배열에 저장합니다.
                //             2022-04-05 100원 , 2022-04-06 90원 이면, -10%를 배열에 저장합니다.
                //             처음은 무조건 0으로 시작하고, +, - 로 리턴합니다.
                rateDailyPercentList.push(getDailyRatePercent(rateListSplit, rateList[dayValue]));
            }
            // Step 4) 코스피, 코스닥의 지수 데이터를 가져와서, +(퍼센트), -(퍼센트) 등으로 잘 정리해서, 각각 그래프로 그려줄 데이터를 만듭니다.
            kospiDailyPercent.push(getDailyRatePercent(kospiDailyIndexRate.slice(0, dayValue), kospiDailyIndexRate[dayValue]));
            kosdaqDailyPercent.push(getDailyRatePercent(kosdaqDailyIndexRate.slice(0, dayValue), kosdaqDailyIndexRate[dayValue]));

            // Step 5) Step3, Step4의 데이터를 사용해서 그래프를 그립니다. (꺽은선 그래프입니다, X는 일자, Y는 퍼센트입니다.)
            //         그래프를 그릴 수 없는 회사는, 그래프를 그리지 않습니다.
            //         그릴 수 없는 회사명은 맨아래에 표시해줍니다. (10개 이상이면 +... 이런식으로 처리하자)
            //         그래프를 그릴 수 있는 회사는, 그래프를 그립니다. ( 무조건 균등 배분이라고 생각합니다. )
            let companyAveragePercent = getAveragePercent(rateDailyPercentList);
            let xAxis = Array.from({length: dayValue}, (y, x) => x);
            const graphColors = ["green", "red", "purple"];
            const graphData = [companyAveragePercent, kospiDailyPercent[0], kosdaqDailyPercent[0]];

            let minMaxList = getMinMaxValue(companyAveragePercent, kospiDailyPercent[0], kosdaqDailyPercent[0]);

            let ctx = canvas.getContext("2d");
            // canvas.width = canvas.getBoundingClientRect().width - E_graphExtra.width;
            // canvas.height = canvas.getBoundingClientRect().height - E_graphExtra.height;
            canvas.width = canvas.getBoundingClientRect().width ;
            canvas.height = canvas.getBoundingClientRect().height;            
            if ( G_canvasDrawing === true ) { 
                ctx.clearRect(0, 0, canvas.width, canvas.height);

                // text 영역을 초기화 해줍니다.
                // step1) x, y좌표들의 부모 클래스를 가져옵니다.
                let yTextParent = document.querySelector(".group-quant-mid-1-y-text");
                let xTextParent = document.querySelector(".group-quant-mid-1-x-text");

                // step2) 부모 클래스의 자식div이 있다면, 모두 삭제합니다.
                if ( yTextParent.hasChildNodes() ) {
                    while (yTextParent.firstChild) {
                        yTextParent.removeChild(yTextParent.firstChild);
                    }
                }
                if ( xTextParent.hasChildNodes() ) {
                    while (xTextParent.firstChild) {
                        xTextParent.removeChild(xTextParent.firstChild);
                    }
                }
            }
            ctx.lineWidth = 1;
            //ctx.font = "9px Arial";
            ctx.beginPath();
            graphFillText(ctx, graphData, minMaxList, canvas, dayValue);
            drawGraph(ctx, graphData, graphColors, minMaxList, canvas, dayValue)
        });
    }
    return;
}

/**
 *    그래프의 x, y 축의 라벨을 설정합니다.
 */
function graphFillText(ctx, graphData, minMaxList, canvas, dayValue) {
    
    /**
     *    수정..23.02.03
     *    x, y축 모두 시작, 끝을 제외하고 5등분해서 라벨을 설정합시다. 아 아닌가? 6개 먼저 해보지 뭐
     */

    // x축은
    //
    //
    //
    //
    //   (시작) 1  2  3  4  5  6 (끝)  xPos를 8로 나눠야지.
    //   만약, 6일치 데이터보다 작다면, 그냥 순서대로 합니다.
    
    // 아 이거 y는 5단위로 끊고 싶은데.. 흐으믕믕므... 그니깐 대충 6등분해서 숫자 딱떨어지게 하고싶어
    // ex) +2 ~ -32 이렇게 있다고 가정 하고. 어떻게 딱 나눠주지??
    // 더하면 34.. 34 / 6 = 5.666666666666667 -> 여기서 뒷자리 버리면, 5
    // 그럼 5씩 더하면 되는거 아닌가??
    // 예제2) -4.9 ~ 30.4 -> 35.3 / 6 = 5.9 -> 여기서 뒷자리 버리면, 5
    // 아 그니깐 최소 ~ 최대값을 일단 6등분하자 그럼, 35.4 / 6 -> 5.9 여기서 뒷자리 버려.

    //              x1 -4.9
    //    1-------
    //              0.01 (round -> 0.00)
    //    2-------
    //              -5.00 
    //    3-------
    //              -10.00
    //    4-------
    //              -15.00
    //    5-------
    //              -20.00
    //    6-------
    //              -25.00
    //    7-------
    //              -30.00
    //    8-------
    //              x2

    // 이게 지금 마이너스인 경우고, 플러스인 경우는? 최소값이 0보다 작으면 이렇게, 0보다 크면?
    //              x1 (최대)
    //    1-------
    //              30.00
    //    2-------
    //              25.00
    //    3-------
    //              20.00
    //    4-------
    //              15.00
    //    5-------
    //              10.00
    //    6-------
    //              5.00
    //    7-------                  7------
    //              0.00                   x2 (최소)
    //    8-------                  8------
    //              x2 (최소)               0.00
    
    // 그래프가 - ~ + 왔다갔다 하는 경우,
    //              x1 (최대)           x1 (최소)
    //    1-------                  1------
    //              15.00                   -15.00
    //    2-------                  2------
    //              10.00                   -10.00
    //    3-------                  3------
    //               5.00                   -5.00
    //    4-------                  4------
    //               0.00                   0.00
    //    5-------                  5------
    //              -5.00                   5.00
    //    6-------                  6------
    //              -10.00                  10.00
    //    7-------                  7------
    //              -15.00                  15.00
    //    8-------                  8------
    //              x2 (최소)           x2 (최대)

    // 그러면, 최소가 0보다 큰 경우랑, 작은 경우 두개를 나눠서 해야겠다.
    const gapCount = 5;
    const gapCountLen = gapCount + 1;
    const xLabels = [];
    const yLabels = [];
    xLabels.push(dailyOnlyDayVer1[dailyOnlyDayVer1.length - 1]);
    yLabels.push(minMaxList[1].toFixed(2)); //최소값이 아래에 있습니다.

    // //const yLabelGap = Math.floor((minMaxList[1] - minMaxList[0]) / 6).toFixed(2); //ex) '5.00'
    const yLabelGap = Math.floor((minMaxList[1] - minMaxList[0]) / gapCount);

    // 아.. 나는 좀 헷갈리니깐 일단 y좌표 갭을 등차로 계산을 할까??
    for ( let i = gapCount; i >= 1; i-- ) {
        yLabels.push((minMaxList[0] + yLabelGap * i).toFixed(2));
    }
    yLabels.push(minMaxList[0].toFixed(2)); //최대값이 그래프의 Max위치 입니다.

    const xLabelGap = Math.floor(dayValue / gapCount); //dayValue가 12라면, 2가 됩니다. 그럼 이제
    for ( let i = 1; i <= gapCount; i++ ) {
        xLabels.push(dailyOnlyDayVer2[dailyOnlyDayVer2.length - 1 - xLabelGap * i]);
    }
    xLabels.push(dailyOnlyDayVer2[0]);

    /*  fillText로 하니깐, canvas 해상도 때문에, 글씨가 깨져서 나옵니다.
        그래프는 어느 정도 깨져도 괜찮습니다. (그래프는 그냥 선이니깐)
        하지만, 텍스트는 깨지면 안됩니다.
        따라서, div 클래스에 text를 넣는식으로 처리하도록 하겠습니다.
        // Draw the x-axis labels
        // 개선점: 시작하는것 년도,날짜 나오게: 22.04.05 이런식 그리고 나머지는 06.05 이런식으로
        for (let i = 0; i < xLabels.length; i++) {
            ctx.fillText(xLabels[i], canvas.width / 8 * i, canvas.height);
            //fillText글자 작게하기
            
        }

        // Draw the y-axis labels
        // 개선점: y의 자리수를 0.00, 5.00 으로 고정시켜야합니다. 
        //       그리고 나오는게 음.. 일단 5등분한 자리에서 나오게 해볼까요?
        for (let i = 0; i < yLabels.length; i++) {
            ctx.fillText(yLabels[i], canvas.width * 0.9, canvas.height / 8 * i);
        }
    */

    // X, Y 좌표의 거리를 구해줍니다.
    // step1) y, x text의 div class를 가져옵니다.
    let yTextParent = document.querySelector(".group-quant-mid-1-y-text");
    let xTextParent = document.querySelector(".group-quant-mid-1-x-text");

    // step2) x, y의 text개수를 가져와서 div를 만들어 주고,
    //        y, x text의 부모 div class에 넣어줍니다.
    //        text는 y, x의 label을 넣어줍니다.
    for ( let i = 0; i < yLabels.length; i++ ) {
        let yTextChild = document.createElement("div");
        yTextChild.classList.add("group-quant-mid-1-y-text-child");
        yTextChild.innerText = yLabels[i];
        yTextParent.appendChild(yTextChild);
    }
    for ( let i = 0; i < xLabels.length; i++ ) {
        let xTextChild = document.createElement("div");
        xTextChild.classList.add("group-quant-mid-1-x-text-child");
        xTextChild.innerText = xLabels[i];
        xTextParent.appendChild(xTextChild);
    }

    // step3) y, x의 Gap(거리)를 이용해서 child div들의 position을 조정해줍니다.
    //        자식 div에 따라서, 거리가 변해야합니다.
    let yTextChilds = document.querySelectorAll(".group-quant-mid-1-y-text-child");
    let xTextChilds = document.querySelectorAll(".group-quant-mid-1-x-text-child");
    let yTextChilsLen = yTextChilds.length;
    let xTextChilsLen = xTextChilds.length;

    for ( let i = 0; i < yTextChilsLen; i++ ) {
        if ( i == 0 ) {
            yTextChilds[0].style.top = 0 + "px";
        } else if ( i == yTextChilsLen - 1 ) {
            yTextChilds[yTextChilsLen - 1].style.top = (yTextParent.clientHeight - 15) + "px";
        } else {
            yTextChilds[i].style.top = canvas.height / gapCountLen * i + "px";
        }
        yTextChilds[i].style.position = "absolute";
    }
    for ( let i = 0; i < xTextChilsLen; i++ ) {

        if ( i == 0 ) {
            xTextChilds[0].style.left = 0 + "px";
        } else if ( i == xTextChilsLen - 1 ) {
            xTextChilds[xTextChilsLen - 1].style.left = (xTextParent.clientWidth - 15) + "px";
        } else {
            xTextChilds[i].style.left = canvas.width / gapCountLen * i + "px";
        }

        xTextChilds[i].style.position = "absolute";
    }

    // step4) x, y childs div들의 font-size 및 style 조정해줍니다.
    for ( let i = 0; i < yTextChilsLen; i++ ) {
        yTextChilds[i].style.fontSize = "0.8rem";
        yTextChilds[i].style.fontWeight = "bold";
    }
    for ( let i = 0; i < xTextChilsLen; i++ ) {
        xTextChilds[i].style.fontSize = "0.8rem";
        xTextChilds[i].style.fontWeight = "bold";
    }

    return;
}

/**
 *    그래프를 그리는 함수입니다.
 */
function drawGraph(ctx, graphData, graphColors, minMaxList, canvas, dayValue) {

    // image 개선 처리 세가지 방법
    // 1) Anti-Aliasing 처리를 해줍니다.
    //    Sommothens out the edges of your graphics and makes them look less pixelated
    ctx.imageSmoothingEnabled = true;

    // 2) Sub-Pixel Rendering
    ctx.translate(0.5, 0.5);

    // 3) High DPI Canvas
    //    this means that can display sharper and clearer images
    //    but,, 캔버스 커지는걸 컨트롤 하기 어렵다.
    // const dpr = window.devicePixelRatio || 1;
    // const rect = canvas.getBoundingClientRect();
    // canvas.width = rect.width * dpr;
    // canvas.height = rect.height * dpr;
    // ctx.scale(dpr, dpr);
    let yAxisStartPos = 0;
    let yHeight = canvas.height;
    let yAxisPercent = Number(canvas.height / (minMaxList[1] - minMaxList[0]));
    let xAxisPercent = Number(canvas.width / (dayValue + 1)); //200일까지 그릴 수 있습니다.
    
    // (최대 + 최소) / 2 -> y축의 중간 값이다.
    let yAxisMidValue = (minMaxList[1] + minMaxList[0]) / 2;
    let yAxisMidPos = yAxisPercent / 2;

    // case 1) 최소값이 0보다 작고, 최대값도 0보다 작은 경우
    if ( minMaxList[0] < 0 && minMaxList[1] < 0 ) {
        yAxisStartPos = minMaxList[0];
    }
    // case 2) 최소값이 0보다 작고, 최대값은 0보다 큰 경우
    else if ( minMaxList[0] < 0 && minMaxList[1] > 0 ) {
        yAxisStartPos = Math.abs(minMaxList[1] * yAxisPercent); //ex) -11 ~ 4 -> 300/15 -> 20칸 여기서 0의 위치는 11칸이다.
    }
    // case 3) 최소값이 0보다 크고, 최대값도 0보다 큰 경우
    else if ( minMaxList[0] > 0 && minMaxList[1] > 0 ) {
        yAxisStartPos = minMaxList[0];
    }
    graphData.forEach((y, x) => {
        ctx.strokeStyle = graphColors[x];
        ctx.beginPath();
        y.forEach((val, xAxisDay) => {
            const xPos = xAxisDay * xAxisPercent;
            let yPos = 0;
            if ( val < 0 ) {
                yPos =  - ( yAxisPercent * (val) );
            } else {
                yPos =  ( yAxisPercent * (val) );
            }
            // case 1) data가 평균값보다 작은 경우
            // if ( val < yAxisMidValue ) {
            //     //yPos =  yAxisMidPos - ( yAxisPercent * (val) );
            //     yPos =  ( yAxisPercent * (val) );
            // }
            
            // // case 2) data가 평균값보다 크거나, 같은 경우
            // else {
            //     //yPos =  yAxisMidPos + ( yAxisPercent * (val) );
            //     yPos =  ( yAxisPercent * (val) );
            // }
            
            // y: 0   -> (0, 0)
            // y: 383 -> (0, 383) 

            if ( xAxisDay === 0 ) {
                ctx.moveTo(xPos, yAxisStartPos);
            } else {
                ctx.lineTo(xPos, yPos);
            }
        });
        ctx.stroke();
    });
    G_canvasDrawing = true;
    return;
}

/**
 *    데이터들의 최소, 최대값을 구하는 함수입니다.
 *    그래프를 그릴 때, y좌표의 최소, 최대값으로 사용합니다.
 */
function getMinMaxValue(companyAveragePercent, kospiDailyPercent, kosdaqDailyPercent) {
    let minMaxList = new Array(0, 0);            
    let graphDataAllValues = [].concat(companyAveragePercent, kospiDailyPercent, kosdaqDailyPercent);
    minMaxList[0] = Math.min.apply(null, graphDataAllValues) - 1;
    minMaxList[1] = Math.max.apply(null, graphDataAllValues) + 1;
    return minMaxList;
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

/**
 *    선택한 날짜(달)만큼 Date 및 Rate를 가져 올 수 있는지 판단합니다.
 */

function drawDetermineCanDraw(day, dateList) {
    let answer = true;
    
    let originalDate = new Array( '2023.01.20', '2023.01.19', '2023.01.18', '2023.01.17', 
                                  '2023.01.16', '2023.01.13', '2023.01.12', '2023.01.11', 
                                  '2023.01.10', '2023.01.09', '2023.01.06', '2023.01.05', 
                                  '2023.01.04', '2023.01.03', '2023.01.02', '2022.12.29', 
                                  '2022.12.28', '2022.12.27', '2022.12.26', '2022.12.23', 
                                  '2022.12.22', '2022.12.21', '2022.12.20', '2022.12.19', 
                                  '2022.12.16', '2022.12.15', '2022.12.14', '2022.12.13', 
                                  '2022.12.12', '2022.12.09', '2022.12.08', '2022.12.07', 
                                  '2022.12.06', '2022.12.05', '2022.12.02', '2022.12.01', 
                                  '2022.11.30', '2022.11.29', '2022.11.28', '2022.11.25', 
                                  '2022.11.24', '2022.11.23', '2022.11.22', '2022.11.21', 
                                  '2022.11.18', '2022.11.17', '2022.11.16', '2022.11.15', 
                                  '2022.11.14', '2022.11.11', '2022.11.10', '2022.11.09', 
                                  '2022.11.08', '2022.11.07', '2022.11.04', '2022.11.03', 
                                  '2022.11.02', '2022.11.01', '2022.10.31', '2022.10.28', 
                                  '2022.10.27', '2022.10.26', '2022.10.25', '2022.10.24', 
                                  '2022.10.21', '2022.10.20', '2022.10.19', '2022.10.18', 
                                  '2022.10.17', '2022.10.14', '2022.10.13', '2022.10.12', 
                                  '2022.10.11', '2022.10.07', '2022.10.06', '2022.10.05', 
                                  '2022.10.04', '2022.09.30', '2022.09.29', '2022.09.28', 
                                  '2022.09.27', '2022.09.26', '2022.09.23', '2022.09.22', 
                                  '2022.09.21', '2022.09.20', '2022.09.19', '2022.09.16', 
                                  '2022.09.15', '2022.09.14', '2022.09.13', '2022.09.08', 
                                  '2022.09.07', '2022.09.06', '2022.09.05', '2022.09.02', 
                                  '2022.09.01', '2022.08.31', '2022.08.30', '2022.08.29', 
                                  '2022.08.26', '2022.08.25', '2022.08.24', '2022.08.23', 
                                  '2022.08.22', '2022.08.19', '2022.08.18', '2022.08.17', 
                                  '2022.08.16', '2022.08.12', '2022.08.11', '2022.08.10', 
                                  '2022.08.09', '2022.08.08', '2022.08.05', '2022.08.04', 
                                  '2022.08.03', '2022.08.02', '2022.08.01', '2022.07.29', 
                                  '2022.07.28', '2022.07.27', '2022.07.26', '2022.07.25', 
                                  '2022.07.22', '2022.07.21', '2022.07.20', '2022.07.19', 
                                  '2022.07.18', '2022.07.15', '2022.07.14', '2022.07.13', 
                                  '2022.07.12', '2022.07.11', '2022.07.08', '2022.07.07', 
                                  '2022.07.06', '2022.07.05', '2022.07.04', '2022.07.01', 
                                  '2022.06.30', '2022.06.29', '2022.06.28', '2022.06.27', 
                                  '2022.06.24', '2022.06.23', '2022.06.22', '2022.06.21', 
                                  '2022.06.20', '2022.06.17', '2022.06.16', '2022.06.15', 
                                  '2022.06.14', '2022.06.13', '2022.06.10', '2022.06.09', 
                                  '2022.06.08', '2022.06.07', '2022.06.03', '2022.06.02', 
                                  '2022.05.31', '2022.05.30', '2022.05.27', '2022.05.26', 
                                  '2022.05.25', '2022.05.24', '2022.05.23', '2022.05.20', 
                                  '2022.05.19', '2022.05.18', '2022.05.17', '2022.05.16', 
                                  '2022.05.13', '2022.05.12', '2022.05.11', '2022.05.10', 
                                  '2022.05.09', '2022.05.06', '2022.05.04', '2022.05.03', 
                                  '2022.05.02', '2022.04.29', '2022.04.28', '2022.04.27', 
                                  '2022.04.26', '2022.04.25', '2022.04.22', '2022.04.21', 
                                  '2022.04.20', '2022.04.19', '2022.04.18', '2022.04.15', 
                                  '2022.04.14', '2022.04.13', '2022.04.12', '2022.04.11', 
                                  '2022.04.08', '2022.04.07', '2022.04.06', '2022.04.05');

    // Ver1 
    /*
        for (let i = 0; i < day; i++) {
            if ( originalDate[i] !== dateList[i] ) {
                answer = false;
                break;
            }
        }
    */

    // Ver2
    let originalDateList = originalDate.slice(0, day);
    let compareDateList = dateList.slice(0, day);
    answer = JSON.stringify(originalDateList) === JSON.stringify(compareDateList);
    
    return answer
}

/**
 *    데이터의 평균(소수점2자리)을 return 합니다.
 *    @param {*} dataList 평균값을 구하고 싶은 데이터 리스트
 */

function getAveragePercent(dataList) {
    let ave = [];
    let dataElLen = dataList[0].length;
    for (let i = 0; i < dataList[0].length; i++) {
        ave.push(0);
    }
    
    for ( let elIdx = 0; elIdx < dataElLen; elIdx++ ) {
        for (let dataIdx = 0; dataIdx < dataList.length; dataIdx++) {
            ave[elIdx] += dataList[dataIdx][elIdx];
        }
        //ave[elIdx] = parseFloat(ave[elIdx] / dataElLen).toFixed(2); 
        ave[elIdx] = Number(((ave[elIdx] / dataElLen)).toFixed(2));
    }
    return ave;
}

/**
 *    일별 시세를 분모와 일별 시세로 계산합니다/
 *    소수점 2자리까지 반올림합니다.
 */
function getDailyRatePercent(rateList, rateDenominator) {
    
    if ( typeof rateDenominator === "string" ) {
        rateDenominator = parseInt(rateDenominator.replace(/,/g, ''));
    }

    let resultPercentList = new Array();
    resultPercentList.push(0);
    /*
    for (let i = 0; i < rateList.length - 1; i++) {
        // 이전 값이랑 계속 비교할겁니다.
        // 마치 버블 정렬처럼?
        let prev = rateList[i];
        let now = rateList[i + 1];
        let percent = (now - prev) / prev * 100;
        resultPercentList.push(percent);
    }
    */

    // step1) rateList의 데이터들을 String -> Int로 변환합니다.
    //        ,를 제거합니다.
    for (let i = 0; i < rateList.length; i++) {
        rateList[i] = parseInt(rateList[i].replace(/,/g, ''));
    }

    // step2) 
    // index를 거꾸로 했어야 했습니다.!
    // 설정한 기간의 마지막날부터 시작해야 합니다.
    // ex) 현재 날짜가, 2023-1-22 이고, day가 30일이라면
    //     2023-1-22 ~ 2022-12-23
    //     분모가 2022-12-22의 값입니다. (day에서 하루 더 빼야합니다!)
    //     데이터는 2022-12-23 부터 2023-1-22의 날짜에서 분모를 나눈 퍼센트입니다.
    for (let i = rateList.length - 1; i >= 0; i--) {
        let now = rateList[i];
        
        //let percent = parseFloat((now - rateDenominator) / rateDenominator * 100).toFixed(2);
        let percent = Number(((now - rateDenominator) / rateDenominator * 100).toFixed(2));

        resultPercentList.push(percent);
    }
    
    // step3)
    // 잠깐만요... 우리이거.. 합산으로 해야할거같은데????
    // 아 아니야! 처음 시작하는 날짜를 분모로 잡고 전부 계산해줬어야했어!


    return resultPercentList;
}


/**
 *    파싱 데이터 불러오기 종료 후, 
 *    table의 회사 데이터를 가져옵니다.
 */
function getCompanyIdxResult() {
    let tdElements = document.querySelectorAll(".quant-table-th");
    let resultIdx = new Array();

    //Ver 1
    /*
        for (let i = 0; i < tdElements.length; i++) {
            let secondTd = tdElements[i].children[1]; //0번째는 Table의 Index번호, 1번째는 회사이름 + 저장된 위치 ( 1을 뺴줘야함.. 1부터 시작이라)
            let secondTdText = secondTd.innerText;
            let secondTdTextArr = secondTdText.split(" ");
            let companyName = secondTdTextArr[0];
            let companyIdx = parseInt(secondTdTextArr[1]) - 1;
            
            
            resultIdx.push(companyIdx);
        } 
    */   

    //Ver 2
    tdElements.forEach(function(tdElement) {
        let secondTd = tdElement.children[1];
        let secondTdText = secondTd.innerText;
        let secondTdTextArr = secondTdText.split(" ");
        let companyName = secondTdTextArr[0];
        let companyIdx = parseInt(secondTdTextArr[1]) - 1;
        resultIdx.push([companyName, companyIdx]);
    });
    return resultIdx;
}

quantPageInit();

