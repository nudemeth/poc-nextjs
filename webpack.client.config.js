const webpack = require('webpack');
const path = require('path');

module.exports = {
    name: 'client',
    entry: path.join(__dirname,'components','main.jsx'),
    output: {
        path: path.join(__dirname,'static','js'),
        filename: 'bundle.js',
        library: ['web']
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
};