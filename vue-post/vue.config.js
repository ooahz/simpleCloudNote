
module.exports = {
    devServer: {
      proxy: {
        '/api|/a': {
          target: 'http://127.0.0.1:8888',
          changeOrigin: true
        },
      },
      port: 8080
    },
}