import React, { Component, createRef } from 'react';
import Try from './Try';

function getNumbers() {
    const result = [];
    const candidates = [1,2,3,4,5,6,7,8,9];
    for (let i = 0; i < 4; i+=1) {
        const chosen = candidates.splice(Math.floor(Math.random() * (9-i)), 1)[0];
        result.push(chosen);
    }
    return result;
}

class CbaseBall extends Component {
    state = {
        value: '',   //입력 값
        result: '',  //결과
        tries: [],   //스트라이크, 볼 기록
        answer: getNumbers(),
    };

    onSubmitResult = (e) => {
        const {value, tries, answer} = this.state;
        e.preventDefault();
        if (value === answer.join('')) {
            this.setState((prevState) => {
                return {
                    tries: [...prevState.tries, {try: value, result: '홈런!'}],
                    result: '홈런!'
                }

            });
            alert("홈런! 게임 다시시작합니다");
            this.setState({
                answer: getNumbers(),
                value: '',
                tries: [],
                result: '',
            });
            this.inputRef.current.focus();
        } else {
            const answerArray = value.split('').map((v) => parseInt(v));
            // 몇 스트라이크, 몇 볼인지 판단해주고 tries에 저장합니다.
            let strike = 0;
            let ball = 0;

            // 예외처리. 10번이상 시도 시, 실패
            if (tries.length >= 9) {
                this.setState({
                    value: '',
                    tries: [],
                    result: '',
                    answer: getNumbers(),
                });
                alert('시도를 10회이상 했습니다!. 게임 다시시작합니디ㅏ!.');
                this.inputRef.current.focus();
            } else {
                // 1. 스트라이크, 볼 체크
                for (let i = 0; i < 4; i+=1) {
                    if (value[i] === answer.join('')[i]) {
                        strike += 1;
                    } else if(answer.join('').includes(value[i])) {
                        ball += 1;
                    }
                }
                // 2.결과를 tries에 작성한다.
                this.setState((prevState) => {
                    return {
                        tries: [...prevState.tries, {try: value, result: `${strike}스트라이크, ${ball}볼`}],
                        value: '',
                    };
                });
            }
        }
    };

    onChangeInput = (e) => {
        console.log(this.state.answer);
        this.setState({
            value: e.target.value,
        });
    };

    inputRef = createRef();

    render() {
        const {result, value, tries} = this.state;
        return (
            <>
                <h1>야구게임! {result}</h1>
                <form onSubmit={this.onSubmitResult}>
                    <input ref={this.inputRef} value={value} onChange={this.onChangeInput}/>
                    <button>송구!</button>
                </form>
                <ul>
                    {tries.map((v, i) => {
                        return (
                            <Try key={`${i + 1}차 시도`} tryInfo={v}/>
                        );
                    })}
                </ul>
            </>
        );
    }
};

export default CbaseBall;