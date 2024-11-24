<template #content>
  <div class="card-stack-wrapper" @click.self="resetFocus">
    <FlipCard
      v-for="data in exerciseStore.randomExerciseData.value"
      :key="data.id"
      :data="data"
      @click="handleClick"
    />
  </div>
</template>

<script setup>
import CardBase from '@/components/cards/CardBase.vue'
import FlipCard from '@/components/cards/FlipCard.vue'
import { useCardService } from '@/composables/data/useCardService'
import { useExerciseStore } from '@/stores/exerciseStore'
import { ref, onMounted } from 'vue'

const cardService = useCardService()
const exerciseStore = useExerciseStore()

onMounted(async () => {
  await cardService.fetchRandomExercise()
})
const focusedCard = ref(null)

const handleClick = (data) => {
  focusedCard.value = data.id
}

const resetFocus = () => {
  focusedCard.value = null
}
</script>

<style scoped>
@import '../components/cards/CardFace.module.css';

.card-stack-wrapper {
  position: relative;
  width: 50%;
  height: 50%;
  perspective: 1000px;
}

.card-stack-wrapper .card-wrapper {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.card-stack-wrapper > .flipped {
  z-index: 10;
  width: calc(1.6 * var(--card-width));
  height: calc(1.6 * var(--card-height));
  transform: translate(-50%, -50%);
}

.card-stack-wrapper > :nth-of-type(1):not(.flipped) {
  transform: translate(calc(-50% - var(--width-offset)), -50%)
    rotate(calc(-1 * var(--base-rotate-angle)));
  z-index: 3;
}

.card-stack-wrapper > :nth-of-type(2):not(.flipped) {
  transform: translate(-50%, -50%);
  z-index: 2;
}

.card-stack-wrapper > :nth-of-type(3):not(.flipped) {
  transform: translate(calc(-50% + var(--width-offset)), -50%) rotate(var(--base-rotate-angle));
  z-index: 1;
}

.card-stack-wrapper > :nth-of-type(1):not(.flipped):hover {
  transform: translate(calc(-50% - var(--hover-translate)), -50%)
    rotate(calc(-1 * var(--hover-rotate))) scale(1.01);
}

.card-stack-wrapper > :nth-of-type(2):not(.flipped):hover {
  transform: translate(-50%, -50%) scale(1.02);
}

.card-stack-wrapper > :nth-of-type(3):not(.flipped):hover {
  transform: translate(calc(-50% + var(--hover-translate)), -50%) rotate(var(--hover-rotate))
    scale(1.01);
}
</style>
