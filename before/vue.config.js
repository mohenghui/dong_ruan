module.exports = {
  publicPath: '/',    
  devServer: {
    port: 8080, // 端口
  },
  configureWebpack: {
    devtool: 'source-map'    
  }
  // lintOnSave: false // 取消 eslint 验证
}