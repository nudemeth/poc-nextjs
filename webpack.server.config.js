const webpack = require('webpack');
const path = require('path');
const nodeExternals = require('webpack-node-externals');
const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
    name: 'server',
    target: 'node',
    externals: [nodeExternals()],
    entry: path.join(__dirname,'index.js'),
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
        new webpack.BannerPlugin({banner: '#!/usr/bin/env node', raw: true}),
        new CopyWebpackPlugin([
            { from: 'views', to: 'views' },
            { from: 'static', to: 'static'}
        ])
    ]
};