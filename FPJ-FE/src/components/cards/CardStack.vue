<template>
  <RouterLink :to="{ name: 'profile' }">toProfile</RouterLink>
  <div class="card-container">
    <Card v-for="item in exerciseStore.randomlySelectedExerciseData" :key="item.id" :data="item" />
  </div>
</template>

<script setup>
import Card from './Card.vue'
import { useCardStore } from '@/stores/cardStore'
import { useExerciseStore } from '@/stores/exerciseStore'
import { onBeforeMount } from 'vue'
const cardStore = useCardStore()
const exerciseStore = useExerciseStore()

onBeforeMount(async () => {
  await cardStore.getUserCollectedCardData(1)
  await cardStore.getUserRecentlyCollectedCardData(1)
  await exerciseStore.getRandomlySelectedExerciseData()
})
</script>

<style scoped>
.card-container {
  position: relative;
  display: flex;
  box-sizing: border-box;
  justify-content: center;
  align-items: center;
  gap: 10px;
  filter: drop-shadow(1px 1px 10px black);
}
</style>
