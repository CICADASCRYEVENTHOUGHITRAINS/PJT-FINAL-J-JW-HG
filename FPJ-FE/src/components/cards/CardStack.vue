<template>
  <div class="card-container">
    <Card v-for="item in data" :key="item.id" :data="item" />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import Card from './Card.vue'

const data = ref([
  {
    id: 1,
    part: '손목',
    name: '손목 스트레칭',
    info: '손바닥을 위로 향하게 하고 반대손으로 손가락을 아래로 당기기',
    time: 30,
  },
  {
    id: 2,
    part: '목',
    name: '목 스트레칭',
    info: '머리를 좌우로 천천히 기울이기',
    time: 20,
  },
  {
    id: 3,
    part: '어깨',
    name: '어깨 스트레칭',
    info: '어깨를 위아래로 올렸다 내리기',
    time: 40,
  },
])
</script>

<style scoped>
.card-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
}
</style>
