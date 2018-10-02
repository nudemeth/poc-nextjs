const { BundleAnalyzerPlugin } = require("webpack-bundle-analyzer")
const { ANALYZE } = process.env

module.exports = {
    exportPathMap: function () {
        return {
            "/": { page: "/" },
            "/about": { page: "/about" },
            "/p/975" : { page: "/post", query: { id: 975 } }
        }
    },
    webpack: function (config) {
        if (ANALYZE) {
            config.plugins.push(new BundleAnalyzerPlugin({
                analyzerMode: "server",
                analyzerPort: 8888,
                openAnalyzer: true
            }))
        }
        config.module.rules.push({
            test: /\.js$/,
            exclude: /node_modules/,
            loader: "eslint-loader",
        })
        return config
    }
}