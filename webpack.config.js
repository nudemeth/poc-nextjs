const webpack = require('webpack');
const path = require('path');

module.exports = {
    entry: path.join(__dirname,'client','main.jsx'),
    output: {
        path: path.join(__dirname,'public','js'),
        filename: 'bundle.js'
    },
    module: {
        loaders: [{
            test: /\.jsx$/,
            loader: 'babel-loader',
            exclude: /node_modules/,
            query: {
                presets: ['env','stage-2','react']
            }
        }]
    }
}