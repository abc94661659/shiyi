<template>
  <n-flex justify="space-between" align="center" style="width: 100%">
    <n-flex style="font-size: 18px">
      <n-skeleton v-if="!imageUrl" width="32px" height="32px" :rounded="4" />
      <n-text style="line-height: 32px">Linshiyi</n-text>
    </n-flex>

    <n-flex align="center">
      <RouterLink to="/">首页</RouterLink> |
      <RouterLink to="/articles/create">创建文章</RouterLink> |
      <RouterLink to="/articles/detail/2">文章</RouterLink>
    </n-flex>

    <n-switch :value="isDark" @update:value="handleThemeToggle">
      <template #checked-icon>
        <n-icon><MoonOutline /></n-icon>
      </template>
      <template #unchecked-icon>
        <n-icon><SunnyOutline /></n-icon>
      </template>
    </n-switch>
  </n-flex>
</template>
<script setup lang="ts">
import { defineProps, defineEmits } from "vue";
import { MoonOutline, SunnyOutline } from "@vicons/ionicons5";
const props = defineProps<{
  isDark: boolean;
}>();

const emit = defineEmits<{
  (e: "toggle-theme"): void;
}>();

// 处理主题切换
const handleThemeToggle = () => {
  emit("toggle-theme");
};
const imageUrl = ref<string>("");
</script>
<style scoped></style>
