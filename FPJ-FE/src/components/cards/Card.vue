<template>
  <div class="card-container" :class="{ flipped: !isFlipped }" @click="flipCard">
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
* {
  -webkit-touch-callout: none; /* iOS Safari */
  -webkit-user-select: none; /* Safari */
  -moz-user-select: none; /* Old versions of Firefox */
  -ms-user-select: none; /* Internet Explorer/Edge */
  user-select: none;

  --card-width: 200px;
  --card-height: 360px;
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
.card-container {
  position: relative;
  width: var(--card-width);
  height: var(--card-height);
  box-sizing: border-box;
  border-radius: 10px;

  transition: transform 0.2s ease;
  cursor: pointer;
}

.card-face {
  position: absolute;
  width: var(--card-width);
  height: var(--card-height);
  background: linear-gradient(135deg, var(--card-color-emerald), var(--card-color-sapphire));
  border: 2px solid var(--card-color-emerald);
  min-width: 200px;
  border-radius: 10px;

  padding: 1rem;
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

.card-container.flipped .front {
  transform: rotateY(0deg);
}

.card-container.flipped .back {
  transform: rotateY(180deg);
}

.card-container:nth-child(1) {
  transform: translate(120px, 0.5rem) rotate(-7deg);
  z-index: 3;
}

.card-container:nth-child(2) {
  transform: rotate(0deg);
  z-index: 2;
}

.card-container:nth-child(3) {
  transform: translate(-120px, 0.5rem) rotate(7deg);
  z-index: 1;
}

.card-container:nth-child(1):hover {
  transform: scale(1.02) translate(110px, 10px) rotate(-8deg);
  --degree: 45deg;
}

.card-container:nth-child(2):hover {
  transform: scale(1.02) rotate(0deg);
  --degree: 90deg;
}

.card-container:nth-child(3):hover {
  transform: scale(1.02) translate(-110px, 10px) rotate(8deg);
  --degree: 135deg;
}
.card-container img {
  max-width: 200px;
  -webkit-user-drag: none;
  -khtml-user-drag: none;
  -moz-user-drag: none;
  -o-user-drag: none;
}
</style>
