const webpack = require('webpack');
const path = require('path');
const nodeExternals = require('webpack-node-externals');

module.exports = {
    name: 'server',
    target: 'node',
    externals: [nodeExternals()],
    entry: path.join(__dirname,'src','server','main.js'),
    output: {
        path: path.join(__dirname,'dist'),
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
};