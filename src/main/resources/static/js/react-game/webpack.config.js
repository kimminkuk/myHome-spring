const path = require('path');
const webpack = require('webpack');

module.exports = {
    name: 'react-game',
    mode: 'development',
    devtool: 'eval',
    resolve: {
        extensions: ['.js', '.jsx']
    },

    entry: {
        app: './react_game_client'
    }, //입력

    module: {
        rules: [{
            test: /\.jsx?/,
            loader: 'babel-loader',
            options: {
                presets: [
                    ['@babel/preset-env', {
                        targets: {
                            browsers: ['last 2 chrome versions'],
                        },
                        debug: true,
                    }],
                    '@babel/preset-react'
                ],
                plugins: [],
            },      
        }],
    },
    plugins: [
        new webpack.LoaderOptionsPlugin({ debug: true }),
    ],
    output: {
        path: path.join(__dirname, './../../../templates/react-game/dist'),
        filename: 'app.js',
    }, //출력
};