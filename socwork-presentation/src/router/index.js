import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AccountCreateView from "@/views/AccountCreateView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: { name: 'account-create' }
    },
    {
      path: '/creer-un-compte',
      name: 'account-create',
      component: () => import('../views/AccountCreateView.vue'),
      alias: '/index.html'
    },
    {
      path: '/:catchAll(.*)',
      name: 'erreur-404',
      component: () => import('../views/Error404View.vue'),
    },
  ],
})

export default router
