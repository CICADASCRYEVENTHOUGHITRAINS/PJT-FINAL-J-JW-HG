<template #navigations>
  <div class="navigations">
    <h1>{{ currentPage }}</h1>
    <nav v-if="authStore.loginUser" class="navigations-container">
      <div class="navigation">
        <RouterLink :to="{ name: 'main' }" class="nav-item">
          <img :src="mainIcon" alt="컬렉션 아이콘" />
          <span>메인</span>
        </RouterLink>
      </div>
      <div class="navigation">
        <RouterLink :to="{ name: 'cardCollection' }" class="nav-item">
          <img :src="collectionIcon" alt="컬렉션 아이콘" />
          <span>컬렉션</span>
        </RouterLink>
      </div>
      <div class="navigation">
        <RouterLink :to="{ name: 'profile' }" class="nav-item">
          <img :src="userIcon" alt="프로필 아이콘" />
          <span>프로필</span>
        </RouterLink>
      </div>
      <div class="navigation">
        <RouterLink :to="{ name: 'preference' }" class="nav-item">
          <img :src="preferenceIcon" alt="설정 아이콘" />
          <span>설정</span>
        </RouterLink>
      </div>
      <div>
        <RouterLink :to="{ name: 'auth' }" @click="authService.logout()" class="nav-item">
          <img :src="logoutIcon" alt="로그아웃 아이콘" />
          <span>로그아웃</span>
        </RouterLink>
      </div>
    </nav>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import { useAuthStore } from '@/stores/authStore'
import { useAuthService } from '@/composables/auth/useAuthService'

import mainIcon from '@/assets/main_icon.png'
import collectionIcon from '@/assets/collection_icon.png'
import userIcon from '@/assets/user_icon.png'
import preferenceIcon from '@/assets/preference_icon.png'
import logoutIcon from '@/assets/logout_icon.png'

const authService = useAuthService()
const authStore = useAuthStore()

const route = useRoute()
const currentPage = computed(() => route.meta.title)
</script>

<style scoped>
.navigations {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 1rem;
  border-bottom: 1px solid lightgray;
}

.nav-item {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 2px;
  font-size: 0.8rem;
}

h1 {
  font-size: 2rem;
  font-weight: 600;
}

a {
  text-decoration: none;
  color: black;
  text-align: center;
  font-size: 1rem;
  cursor: pointer;
}

a:hover {
  color: #007bff;
}

.navigations-container {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 1rem;
}
</style>
