<template>
  <div id="webview-header">
    <h1>{{ currentPage }}</h1>
    <nav id="links-container">
      <div>
        <img src="@/assets/collection_icon.svg" alt="" />
        <RouterLink :to="{ name: 'cardCollection' }">컬렉션</RouterLink>
      </div>
      <div>
        <img src="@/assets/user_icon.svg" alt="" />
        <RouterLink :to="{ name: 'profile' }">프로필</RouterLink>
      </div>
      <div>
        <img src="@/assets/preference_icon.svg" alt="" />
        <RouterLink :to="{ name: 'preference' }">설정</RouterLink>
      </div>
    </nav>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { computed } from 'vue'

const route = useRoute()
const currentPage = computed(() => route.meta.title)
</script>

<style scoped>
#webview-header {
  background-color: rebeccapurple;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

a {
  text-decoration: none;
  color: black;
}

#links-container {
  display: flex;
  gap: 10px;
}
</style>
