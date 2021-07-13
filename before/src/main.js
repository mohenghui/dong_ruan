//导入   import from
import Vue from 'vue'
import App from './App.vue'
///router/index.js
import router from './router'
//vuex/index.js
import vuex from './vuex'

//qs是查询字符串解析和将对象序列化的库
import qs from "qs"


// 导入 自定义全局 css  
import './assets/css/global.css'

// 布局，导入ElementUI  ,JS/CSS    先安装 npm i element-ui -S
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

//数据请求，导入axios，先安装   npm install axios   
import axios from 'axios'


//使用
Vue.use(ElementUI)

//设置后端API路径
axios.defaults.baseURL = 'http://localhost:8082/'


//挂载到vue原型，相当于vue实例多了一个属性 $http，可以使用this.$http
Vue.prototype.$http = axios

// this.$qs  
Vue.prototype.$qs=qs;

//
Vue.config.productionTip = false


//new vue实例
new Vue({
  router,   //路由   router：router
  vuex,     //状态   
  render: h => h(App)     //render ，将APP.vue作为模板渲染过来，APP.vue是根Vue,在它的router-view里假期其他vue
}).$mount('#app')       //vue页面挂载到#app这个div
