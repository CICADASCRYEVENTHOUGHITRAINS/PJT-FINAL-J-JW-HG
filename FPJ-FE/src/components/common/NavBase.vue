<template>
  <div class="navigations">
    <slot name="SidepanelNav" />
    <slot name="WebViewNav" />
  </div>
</template>

<script setup>
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
.webview-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 1rem;
  border-bottom: 1px solid lightgray;
}

h1 {
  font-size: 2rem;
  font-weight: 600;
}

a,
button {
  text-decoration: none;
  color: black;
  text-align: center;
  font-size: 1rem;
  cursor: pointer;
}

a:hover,
button:hover {
  color: #007bff;
}

.navigations-container {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 1rem;
}

.navigation {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

a,
button {
  text-decoration: none;
  color: black;
  text-align: center;
  font-size: 1rem;
  cursor: pointer;
}

a:hover,
button:hover {
  color: #007bff;
}
</style>
