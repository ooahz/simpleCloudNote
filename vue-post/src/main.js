import Vue from "vue"
import App from "./App.vue"
import ElementUI from "element-ui"
import "element-ui/lib/theme-chalk/index.css"
import VueRouter from "vue-router"
import router from "./router"
import mavon from "mavon-editor"
import "mavon-editor/dist/css/index.css"
import axios from "axios"
import store from "./store"


Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(mavon)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app")

// 请求响应拦截器
axios.interceptors.response.use(function (response) {
  if (response.data.result === "logout") {
    console.log("未登录")
    ElementUI.Message.error("请先登录！");
    // sessionStorage.removeItem("user")
    this.$store.commit("setUser", "{}")
    this.router.push({
      path: "/login",
    });
  }
  return response;
}, function (error) {
  ElementUI.Message.error("请求失败！");
  router.push({
    path: "/500",
  });
  return Promise.reject(error);
});
