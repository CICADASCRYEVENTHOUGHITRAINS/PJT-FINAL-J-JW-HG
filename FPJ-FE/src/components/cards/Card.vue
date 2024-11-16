<template>
  <div class="card-wrapper" :class="{ flipped: !isFlipped }">
    <div class="card-face back" @click="flipCard"></div>
    <div class="card-face front">
      <img src="@/assets/test-stretch-icon.png" alt="" />
      <p>{{ data.name }}</p>
      <p>{{ data.info }}</p>
      <p>{{ data.time }} 초</p>
      <button @click="flipCard">cancel</button>
      <button>confirm</button>
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
  --base-rotate-angle: 7deg;
  --flip-angle: 180deg;
  --width-offset: 160px;
  --hover-translate: 150px;
  --hover-rotate: 8deg;
  -webkit-touch-callout: none; /* iOS Safari */
  -webkit-user-select: none; /* Safari */
  -moz-user-select: none; /* Old versions of Firefox */
  -ms-user-select: none; /* Internet Explorer/Edge */
  user-select: none;

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
  transform-origin: center;
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
  transform: rotateY(var(--flip-angle));
}

.card-face.back {
  transform: rotateY(0deg);
}

.card-wrapper.flipped {
  /* transform: rotateY(0deg) rotate(0deg) scale(var(--scale-factor)); */
  position: fixed;
  top: 0;
  left: 0;
  transform: translate(-50%, -50%) scale(var(--scale-factor));
  z-index: 10;
}

.card-wrapper.flipped .front {
  transform: none;
  z-index: 10;
  width: 400px;
  height: 800px;
}

.card-wrapper.flipped .back {
  transform: rotateY(var(--flip-angle));
}

.card-wrapper:nth-child(1):not(.flipped) {
  transform: translateX(var(--width-offset)) rotate(calc(-1 * var(--base-rotate-angle)));
  z-index: 3;
}

.card-wrapper:nth-child(2):not(.flipped) {
  transform: translateX(0);
  z-index: 2;
}

.card-wrapper:nth-child(3):not(.flipped) {
  transform: translateX(calc(-1 * var(--width-offset))) rotate(var(--base-rotate-angle));
  z-index: 1;
}

.card-wrapper:not(.flipped):hover {
  transform: scale(1.01);
}

.card-wrapper:nth-child(1):not(.flipped):hover {
  transform: translateX(var(--hover-translate)) rotate(calc(-1 * var(--hover-rotate)));
}

.card-wrapper:nth-child(2):not(.flipped):hover {
}

.card-wrapper:nth-child(3):not(.flipped):hover {
  transform: translateX(calc(-1 * var(--hover-translate))) rotate(var(--hover-rotate));
}

.card-wrapper img {
  max-width: 100%;
  -webkit-user-drag: none;
  -khtml-user-drag: none;
  -moz-user-drag: none;
  -o-user-drag: none;
}
</style>
