import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:"/intro",
    children:[
      {
        path: '/user',
        name: 'User',
        component: ()=>import("@/views/User.vue"),
      },{
        path: '/person',
        name: 'Person',
        component: ()=>import("@/views/Person.vue")
      },{
        path: '/goods',
        name: 'Goods',
        component: ()=>import("@/views/Goods.vue")
      },{
        path: '/intro',
        name: 'Intro',
        component: ()=>import("@/views/Intro.vue")
      },{
        path: '/news',
        name: 'News',
        component: ()=>import("@/views/News.vue")
      },{
        path: '/animal',
        name: 'Animal',
        component: ()=>import("@/views/Animal.vue")
      },{
        path: '/test',
        name: 'Test',
        component: ()=>import("@/views/Test.vue")
      },{
        path: '/ticket',
        name: 'Ticket',
        component: ()=>import("@/views/Ticket.vue")
      },{
        path: '/orders',
        name: 'Orders',
        component: ()=>import("@/views/Orders.vue")
      },{
        path: '/question',
        name: 'Question',
        component: ()=>import("@/views/Question.vue")
      },{
        path: '/recognition',
        name: 'Recognition',
        component: ()=>import("@/views/Recognition.vue")
      },{
        path: '/exam',
        name: 'Exam',
        component: ()=>import("@/views/Exam.vue")
      }
    ]
  },{
    path: '/login',
    name: 'Login',
    component: ()=>import("@/views/Login.vue")
  },{
    path: '/register',
    name: 'Register',
    component: ()=>import("@/views/Register.vue")
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
