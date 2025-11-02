<template>
  <n-config-provider
    :locale="zhCN"
    :date-locale="dateZhCN"
    :theme="themeStore.isDark ? darkTheme : lightTheme"
  >
    <n-loading-bar-provider>
      <n-message-provider :max="2">
        <n-layout>
          <n-layout-header :class="{ 'fixed-header': isFixed }" ref="headerRef">
            <Header
              :is-dark="themeStore.isDark"
              @toggle-theme="themeStore.toggleTheme"
            />
          </n-layout-header>
          <n-layout-content
            :style="{
              padding: '100px',
              marginTop: isFixed ? '64px' : '0',
            }"
          >
            平山道<RouterView />
          </n-layout-content>
          <n-layout-footer>尾部</n-layout-footer>
        </n-layout>
      </n-message-provider>
    </n-loading-bar-provider>
  </n-config-provider>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from "vue";
import { RouterView } from "vue-router";
import {
  createDiscreteApi,
  zhCN,
  dateZhCN,
  darkTheme,
  lightTheme,
} from "naive-ui";
import Header from "./components/Header.vue";
import { useThemeStore } from "./stores/theme";

const isFixed = ref(false);
const headerRef = ref<HTMLElement | null>(null);
const scrollThreshold = 64;

const handleScroll = () => {
  const scrollTop = window.scrollY;
  isFixed.value = scrollTop > scrollThreshold;
};

onMounted(() => {
  handleScroll();
  window.addEventListener("scroll", handleScroll);
});

onUnmounted(() => {
  window.removeEventListener("scroll", handleScroll);
});

const { message, notification, dialog, loadingBar } = createDiscreteApi([
  "message",
  "notification",
  "dialog",
  "loadingBar",
]);
const themeStore = useThemeStore();
window.$message = message;
window.$notification = notification;
window.$dialog = dialog;
window.$loadingBar = loadingBar;
</script>

<style scoped>
.n-layout-header {
  height: 64px;
  padding: 12px 32px;
  transition: all 0.3s ease;
}

.fixed-header {
  position: fixed;
  z-index: 9999;
  box-sizing: border-box;
  border-bottom: 1px solid gray;
}

.n-layout-footer {
  padding: 24px;
}
</style>
