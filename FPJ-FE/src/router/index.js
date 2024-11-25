import { createRouter, createWebHistory } from 'vue-router'
import MainView from '@/views/MainView.vue'
import UserProfileView from '@/views/UserProfileView.vue'
import CollectionView from '@/views/CollectionView.vue'
import PreferenceView from '@/views/PreferenceView.vue'
import AuthView from '@/views/AuthView.vue'
import { useAuthService } from '@/composables/auth/useAuthService'
import { useAuthStore } from '@/stores/authStore'
import { computed } from 'vue'
import { storeToRefs } from 'pinia'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: MainView,
      meta: { layout: 'sidepanel', requiresAuth: true },
    },
    {
      path: '/auth',
      name: 'auth',
      component: AuthView,
      meta: { title: '', layout: 'sidepanel' },
    },
    {
      path: '/profile',
      name: 'profile',
      component: UserProfileView,
      meta: { title: '프로필', layout: 'webView', requiresAuth: true },
    },
    {
      path: '/collection',
      name: 'cardCollection',
      component: CollectionView,
      meta: { title: '컬렉션', layout: 'webView', requiresAuth: true },
    },
    {
      path: '/preference',
      name: 'preference',
      component: PreferenceView,
      meta: { title: '설정', layout: 'webView', requiresAuth: true },
    },
    { path: '/:pathMatch(.*)*', redirect: '/' },
  ],
})

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  const { loginUser: user } = storeToRefs(authStore)
  const isAuthenticated = computed(() => !!user.value)

  if (to.meta.requiresAuth && !isAuthenticated.value) {
    next('/auth') // 인증되지 않은 사용자는 로그인 페이지로 리다이렉트
  } else if (to.name === 'auth' && isAuthenticated.value) {
    next('/') // 인증된 사용자는 메인 페이지로 리다이렉트
  } else {
    next() // 모든 조건을 통과하면 라우팅 허용
  }
})
export default router
