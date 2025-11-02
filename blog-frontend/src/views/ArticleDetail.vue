<template>
  <n-h1>文章id: {{ articleId }}</n-h1>
  <n-skeleton v-if="loading" :rows="5" style="width: 100%" />
  <MdPreview
    v-else-if="markdownContent"
    v-model:model-value="markdownContent"
    :height="'600px'"
    :theme="themeStore.isDark ? 'dark' : 'light'"
  />
  <!-- 内容为空时显示提示，避免组件空渲染 -->
  <div v-else>文章内容加载中...</div>
  <CommentItem :entity-type="'ARTICLE'" :entity-id="Number(articleId)" />

  <n-button @click="InsertComment()">测试按钮</n-button>
</template>

<script setup lang="ts">
import { MdPreview } from "md-editor-v3";
import "md-editor-v3/lib/style.css";
import { useRoute } from "vue-router";
import { getArticleDetailById } from "../api/articleService";
import { createComment } from "../api/interactionService";
import CommentItem from "../components/comment/CommentItem.vue";
import { useThemeStore } from "../stores/theme";

const route = useRoute();
const themeStore = useThemeStore();
const articleId = route.params.id;
const markdownContent = ref<string>(``);
const loading = ref<boolean>(false);

const selectArticleDetailById = async () => {
  loading.value = true;
  const res = await getArticleDetailById(Number(articleId));
  markdownContent.value = res.data.content;
  loading.value = false;
};

const InsertComment = async () => {
  const commentData = {
    entityType: "ARTICLE",
    entityId: Number(articleId),
    parentId: null,
    content: "这是一个测试评论",
  };
  const res = await createComment(commentData);
  window.$message?.success(res.data);
};

onMounted(() => {
  selectArticleDetailById();
});
</script>
