<template>
  <n-loading-bar-provider>
    <n-message-provider :max="2">
      <n-flex>
        <RouterLink to="/">首页</RouterLink> |
        <RouterLink to="/articles/create">创建文章</RouterLink> |
        <RouterLink to="/articles/detail">文章</RouterLink>
        <RouterView />
      </n-flex>
    </n-message-provider>
  </n-loading-bar-provider>
</template>

<script lang="ts">
import { RouterLink, RouterView } from "vue-router";
import { createDiscreteApi } from "naive-ui";

const { message, notification, dialog, loadingBar } = createDiscreteApi([
  "message",
  "notification",
  "dialog",
  "loadingBar",
]);
window.$message = message;
window.$notification = notification;
window.$dialog = dialog;
window.$loadingBar = loadingBar;
</script>

<style scoped></style>
