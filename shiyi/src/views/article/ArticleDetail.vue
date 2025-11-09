<template>
  <!-- 文章ID标题 -->
  <h1 class="text-3xl font-bold mb-6 text-gray-800 dark:text-gray-200">
    文章id: {{ articleId }}
  </h1>

  <!-- 加载骨架屏 -->
  <div v-if="loading" class="space-y-4 w-full">
    <div class="h-6 bg-gray-200 dark:bg-gray-700 rounded animate-pulse"></div>
    <div
      class="h-4 bg-gray-200 dark:bg-gray-700 rounded animate-pulse w-3/4"
    ></div>
    <div
      class="h-4 bg-gray-200 dark:bg-gray-700 rounded animate-pulse w-full"
    ></div>
    <div
      class="h-4 bg-gray-200 dark:bg-gray-700 rounded animate-pulse w-5/6"
    ></div>
    <div
      class="h-4 bg-gray-200 dark:bg-gray-700 rounded animate-pulse w-2/3"
    ></div>
  </div>

  <!-- Markdown预览区域 -->
  <div v-else-if="markdownContent" class="mt-4">
    <MdPreview
      v-model:model-value="markdownContent"
      :height="'600px'"
      :theme="themeStore.themeMode === 'dark' ? 'dark' : 'light'"
      class="border rounded-lg overflow-hidden"
    />
  </div>

  <!-- 加载提示 -->
  <div v-else class="text-gray-500 dark:text-gray-400 py-8">
    文章内容加载中...
  </div>

  <!-- 评论组件 -->
  <div class="mt-8">
    <CommentItem :entity-type="'ARTICLE'" :entity-id="Number(articleId)" />
  </div>

  <!-- 测试按钮 -->
  <button
    @click="InsertComment()"
    class="mt-6 bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg transition-colors duration-200 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 dark:focus:ring-offset-gray-800"
  >
    测试按钮
  </button>
</template>

<script setup lang="ts">
import { MdPreview } from "md-editor-v3";
import "md-editor-v3/lib/style.css";
import { useRoute } from "vue-router";
import { getArticleDetailById } from "@/api/articleService";
import { createComment } from "@/api/interactionService";
import CommentItem from "@/components/comment/CommentItem.vue";

import { useThemeStore } from "@/stores/theme";
import { ref, onMounted } from "vue";

// 路由参数获取
const route = useRoute();
const themeStore = useThemeStore();
const articleId = route.params.id;
const markdownContent = ref<string>("");
const loading = ref<boolean>(false);

// 获取文章详情
const selectArticleDetailById = async () => {
  loading.value = true;
  try {
    const res = await getArticleDetailById(Number(articleId));
    markdownContent.value = res.data.content;
  } catch (error) {
    console.error("加载文章失败:", error);
  } finally {
    loading.value = false;
  }
};

// 插入评论
const InsertComment = async () => {
  const commentData = {
    entityType: "ARTICLE",
    entityId: Number(articleId),
    parentId: null,
    content: "这是一个测试评论",
  };
  try {
    const res = await createComment(commentData);
    // 原生JS提示（替代Naive UI的message）
    window.alert(res.data || "评论成功");
  } catch (error) {
    console.error("评论失败:", error);
    window.alert("评论失败，请重试");
  }
};

// 组件挂载时加载数据
onMounted(() => {
  selectArticleDetailById();
});
</script>

<style scoped>
/* 可选：添加组件内私有样式 */
:deep(.dark) {
  --md-color-bg: #1f2937;
  --md-color-text: #f3f4f6;
}
:deep(.light) {
  --md-color-bg: #ffffff;
  --md-color-text: #111827;
}
</style>
