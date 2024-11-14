<template>
  <div class="card-container">
    <Card v-for="item in store.userCollectedCardData" :key="item.id" :data="item" />
  </div>
</template>

<script setup>
import Card from './Card.vue'
import { useCardStore } from '@/stores/cardStore'
import { onBeforeMount } from 'vue'
const store = useCardStore()

onBeforeMount(async () => {
  await store.getUserCollectedCardData(1)
  await store.getUserRecentlyCollectedCardData(1)
  console.log(store.userCollectedCardData)
})
</script>

<style scoped>
.card-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
}
</style>
