const webpack = require('webpack');
const path = require('path');
const nodeExternals = require('webpack-node-externals');

module.exports = [{
    name: 'client',
    entry: path.join(__dirname,'src','components','main.jsx'),
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
}, {
    name: 'server',
    target: 'node',
    externals: [nodeExternals()],
    entry: path.join(__dirname,'src','server','main.js'),
    output: {
        path: path.join(__dirname,'bin'),
        filename: 'server.js'
    },
    module: {
        loaders: [{
            test: /\.(js|jsx)$/,
            loader: 'babel-loader',
            exclude: /node_modules/,
            query: {
                presets: ['stage-2','react']
            }
        }]
    },
    plugins: [
        new webpack.BannerPlugin({banner: '#!/usr/bin/env node', raw: true})
    ]
}];