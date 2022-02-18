module.exports = {
  transpileDependencies: ["vuetify"],
  devServer: {
    // proxy: {
    //   "/api": {
    //     target: "http://404name.top:9001",
    //     changeOrigin: true,
    //     pathRewrite: {
    //       "^/api": ""
    //     }
    //   },
    // },
    port: 80,
    disableHostCheck: true
  },
  productionSourceMap: false,
  css: {
    extract: true,
    sourceMap: false
  }
};
