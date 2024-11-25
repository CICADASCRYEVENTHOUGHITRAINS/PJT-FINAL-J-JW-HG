<template #SidepanelNav>
  <div class="sidepanel-nav">
    <h1>{{ currentPage }}</h1>
    <nav v-if="authStore.loginUser" class="navigations-container">
      <div class="navigation">
        <RouterLink :to="{ name: 'cardCollection' }">
          <img :src="collectionIcon" alt="컬렉션 아이콘" />
        </RouterLink>
      </div>
      <div class="navigation">
        <RouterLink :to="{ name: 'profile' }">
          <img :src="userIcon" alt="프로필 아이콘" />
        </RouterLink>
      </div>
      <div class="navigation">
        <RouterLink :to="{ name: 'preference' }">
          <img :src="preferenceIcon" alt="설정 아이콘" />
        </RouterLink>
      </div>
      <div>
        <RouterLink :to="{ name: 'auth' }">
          <img :src="logoutIcon" alt="로그아웃 아이콘" @click="authService.logout()" />
        </RouterLink>
      </div>
    </nav>
  </div>
</template>

<script setup>
import NavBase from './NavBase.vue'

import collectionIcon from '@/assets/collection_icon.png'
import userIcon from '@/assets/user_icon.png'
import preferenceIcon from '@/assets/preference_icon.png'
import logoutIcon from '@/assets/logout_icon.png'
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import { useAuthStore } from '@/stores/authStore'
import { useAuthService } from '@/composables/auth/useAuthService'

const authService = useAuthService()
const authStore = useAuthStore()

const route = useRoute()
const currentPage = computed(() => route.meta.title)
</script>

<style scoped>
.sidepanel-nav {
  position: fixed;
  /* display: flex; */
  right: 0;
  /* flex-direction: column; */
  /* width: 100%; */
  /* height: 100%; */
}

a,
button {
  text-decoration: none;
  color: black;
  text-align: center;
  cursor: pointer;
}
</style>
