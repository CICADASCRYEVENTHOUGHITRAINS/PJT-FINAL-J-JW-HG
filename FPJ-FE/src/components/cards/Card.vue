<template>
  <div class="card-wrapper" :class="{ flipped: !isFlipped }" @click="flipCard">
    <div class="card-face back"></div>
    <div class="card-face front">
      <img src="@/assets/test-stretch-icon.png" alt="" />
      <p>{{ data.name }}</p>
      <p>{{ data.info }}</p>
      <p>{{ data.time }} 초</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
defineProps({
  data: Object,
})
const isFlipped = ref(true)

const flipCard = () => {
  isFlipped.value = !isFlipped.value
}
</script>

<style scoped>
.card-wrapper {
  --card-width: 200px;
  --card-height: 360px;
  -webkit-touch-callout: none; /* iOS Safari */
  -webkit-user-select: none; /* Safari */
  -moz-user-select: none; /* Old versions of Firefox */
  -ms-user-select: none; /* Internet Explorer/Edge */
  user-select: none;

  /* --degree: ; */
  /* --highlight-color: ; */
  /* --base-color: ; */
  /* --shadow-color: ; */

  --card-color-white: #fff;
  --card-color-bronze: #cb7e72;
  --card-color-silver: #e6e3e3;
  --card-color-gold: #ffd200;
  --card-color-platinum: #fff7de;
  --card-color-diamond: #94ebff;
  --card-color-ruby: #ff2c80;
  --card-color-sapphire: rgba(100, 100, 200, 1);
  --card-color-emerald: rgba(0, 204, 255, 1);
  --card-color-obsidian: #1e0028;
  --card-color-black: #000;
  --level-color-low: #91ff63;
  --level-color-middle: #fff941;
  --level-color-high: #ff5652;
}
.card-wrapper {
  position: relative;
  min-width: var(--card-width);
  min-height: var(--card-height);
  box-sizing: border-box;
  transition: transform 0.2s ease;
  cursor: pointer;
}

.card-face {
  height: var(--card-height);
  width: var(--card-width);
  padding: 0.5rem;
  position: absolute;
  background: linear-gradient(135deg, var(--card-color-emerald), var(--card-color-sapphire));
  border: 2px solid var(--card-color-emerald);
  border-radius: 10px;
  backface-visibility: hidden;
  transition:
    transform 0.4s ease,
    opacity 0.4s ease;
}

.card-face.front {
  transform: rotateY(180deg);
}

/* .card-face.back { */
/* } */

.card-wrapper.flipped .front {
  transform: rotateY(0deg);
}

.card-wrapper.flipped .back {
  transform: rotateY(180deg);
}

.card-wrapper:nth-child(1) {
  transform: translateX(160px) rotate(-7deg);
  z-index: 3;
}

.card-wrapper:nth-child(2) {
  transform: translateX(0);
  z-index: 2;
}

.card-wrapper:nth-child(3) {
  transform: translateX(-160px) rotate(7deg);
  z-index: 1;
}

.card-wrapper:hover {
  transform: scale(1.01);
}

.card-wrapper:nth-child(1):hover {
  transform: translateX(150px) rotate(-8deg);
  /* --degree: 45deg; */
}

.card-wrapper:nth-child(2):hover {
  /* --degree: 90deg; */
}

.card-wrapper:nth-child(3):hover {
  transform: translateX(-150px) rotate(8deg);
  /* --degree: 135deg; */
}

.card-wrapper img {
  max-width: 100%;
  -webkit-user-drag: none;
  -khtml-user-drag: none;
  -moz-user-drag: none;
  -o-user-drag: none;
}
</style>
