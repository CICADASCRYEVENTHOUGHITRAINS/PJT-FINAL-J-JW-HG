<template>
  <div class="sidepanel-layout">
    <slot />
    <SidepanelNav />
  </div>
</template>
<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()

import SidepanelNav from '@/components/common/SidepanelNav.vue'
</script>

<style scoped>
.sidepanel-layout {
  width: 100vw;
  height: 100vh;
  position: relative;
  padding: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: linear-gradient(135deg, #d4d4d4 0%, #e2c8c3 100%);
}
</style>
