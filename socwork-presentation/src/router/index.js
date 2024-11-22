import { createRouter, createWebHistory } from 'vue-router'

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
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
    },
    {
      path: '/:catchAll(.*)',
      name: 'erreur-404',
      component: () => import('../views/Error404View.vue'),
    },
  ],
})

export default router
