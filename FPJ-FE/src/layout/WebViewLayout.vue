<template>
  <div class="webview-layout">
    <WebViewNav />
    <section class="content-view">
      <InfoPanel />
      <slot />
    </section>
  </div>
</template>

<script setup>
import WebViewNav from '@/components/common/WebViewNav.vue'
import InfoPanel from '@/components/common/InfoPanel.vue'
</script>
<style scoped>
.webview-layout {
  display: flex;
  flex-direction: column;
}

.content-view {
  display: flex;
  padding: 1rem;
  box-sizing: border-box;
  height: 100vh;
  flex-grow: 1;
}

.content {
  display: flex;
  flex-wrap: wrap;
  flex: 2;
  padding: 1rem;
  height: 80%;
  box-sizing: border-box;
  gap: 1rem;
}
</style>
