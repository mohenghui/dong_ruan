import Vue from 'vue'
import VueRouter from 'vue-router'

//导入自定义vue
import Blog from '../views/Blog.vue'


//使用
Vue.use(VueRouter)

//定义
const routes = [
  {
    path: '/',
    name: 'Blog',
    component: Blog
  },
  
]

//new 路由
const router = new VueRouter({
  routes
})

// 抛出去    export default
export default router
