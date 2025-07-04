import { createRouter, createWebHistory } from 'vue-router'
import Home from '../pages/Home.vue'
import LogIn from '../pages/Login.vue'
import SignIn from '../pages/Signin.vue'
import Profile from '../pages/Profile.vue'
import PostPage from '../pages/PostPage.vue'

const routes = [
  { path: '/', component: Home },
  { path: '/login', component: LogIn },
  { path: '/signin', component: SignIn },
  { path: '/profile', component: Profile},
  { path: '/post', component: PostPage}
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router