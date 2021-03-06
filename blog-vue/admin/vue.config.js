module.exports = {
  productionSourceMap: false,
  devServer: {
    // proxy: {
    //   "/api": {
    //     target: "http://localhost:9001",
    //     changeOrigin: true,
    //     pathRewrite: {
    //       "^/api": ""
    //     }
    //   }
    // },
    port: 80,
    disableHostCheck: true
  },
  chainWebpack: config => {
    config.resolve.alias.set("@", resolve("src"));
  }
};

const path = require("path");
function resolve(dir) {
  return path.join(__dirname, dir);
}
