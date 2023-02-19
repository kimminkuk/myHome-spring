const React = require('react');
const { useState, useRef, useEffect } = React;
const { Component } = React;

const GuGuDan = () => {
    const [first, setFirst] = useState(Math.ceil(Math.random() * 9));
    const [second, setSecond] = useState(Math.ceil(Math.random() * 9));
    const [value, setValue] = useState('');
    const [result, setResult] = useState('');
    const [goodPoint, setGoodPoint] = useState(0);
    const [failPoint, setFailPoint] = useState(0);
    const [judge, setJudge] = useState('');
    const inputRef = useRef(null);

    const onSubmitResult = (e) => {
        e.preventDefault();
        if (parseInt(value) === first * second) {
            setFirst(Math.ceil(Math.random() * 9));
            setSecond(Math.ceil(Math.random() * 9));
            setGoodPoint(goodPoint + 1);
            setResult('정답입니다.');
            setValue('');
            inputRef.current.focus();
        } else {
            setFailPoint(failPoint + 1);
            setResult('땡');
            setValue('');
            inputRef.current.focus();
        }
    }

    useEffect(() => {
        if (goodPoint > failPoint) {
            setJudge('이기고 있습니다.');
        } else if (goodPoint < failPoint) {
            setJudge('지고 있습니다.');
        } else {
            if (goodPoint > 0 || failPoint > 0) {
                setJudge('동률입니다.');
            }
        }
        inputRef.current.focus();
    }, [goodPoint, failPoint]);
    
    const onChangeInput = (e) => {
        setValue(e.target.value);
    }

    return (
        <>
            <div>{first} 곱하기 {second} 는?</div>
            <form onSubmit={onSubmitResult}>
                <input ref={inputRef} value={value} onChange={onChangeInput} />
                <button>입력!</button>
            </form>
            <div id="result">결과:{result} 승리:{goodPoint} 패배:{failPoint} 기록:{judge}</div>
        </>
    )
}

module.exports = GuGuDan;