import React, { Component } from 'react';

class Try extends Component {
    render() {
        return (
            <li>
                <div>{this.props.tryInfo.try}</div>
                <div>{this.props.tryInfo.result}</div>
            </li>
        )
    }
}

class TryVer2 extends Component {
    render() {
        const { tryInfoVer2 } = this.props;
        return (
            <li>
                <div>{tryInfoVer2.tryFirst}</div>
                <div>{tryInfoVer2.trySecond}</div>
                <div>{tryInfoVer2.result}</div>
            </li>
        )
    }
}

export default Try;