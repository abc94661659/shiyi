<template>
  <div class="max-w-6xl mx-auto p-6">
    <!-- 标题 -->
    <h1 class="text-3xl font-bold text-gray-800 mb-6 flex items-center gap-2">
      <Icon icon="mdi:file-document-edit-outline" width="28" />
      文章创建
    </h1>

    <hr class="my-6 border-gray-200" />

    <form @submit.prevent="insertArticle" class="space-y-6">
      <!-- 标题输入 -->
      <div>
        <label
          for="title"
          class="text-sm font-medium text-gray-700 mb-1 flex items-center gap-1"
        >
          <Icon icon="mdi:format-title" width="16" />
          标题
        </label>
        <div class="relative">
          <div
            class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none text-gray-400"
          >
            <Icon icon="mdi:label" width="18" />
          </div>
          <input
            id="title"
            v-model="title"
            type="text"
            maxlength="50"
            placeholder="请输入标题"
            class="w-full pl-10 px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-2 focus:ring-green-500 focus:border-green-500 outline-none transition"
          />
        </div>
        <div class="text-right text-xs text-gray-500 mt-1">
          {{ title.length }}/50
        </div>
      </div>

      <!-- 摘要输入 -->
      <div>
        <label
          for="summary"
          class="text-sm font-medium text-gray-700 mb-1 flex items-center gap-1"
        >
          <Icon icon="mdi:text-subject" width="16" />
          摘要
        </label>
        <div class="relative">
          <div class="absolute top-2 left-3 text-gray-400">
            <Icon icon="mdi:comment-text-outline" width="18" />
          </div>
          <textarea
            id="summary"
            v-model="summary"
            placeholder="请输入摘要"
            maxlength="500"
            rows="3"
            class="w-full pl-10 px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-2 focus:ring-green-500 focus:border-green-500 outline-none transition resize-none"
          ></textarea>
        </div>
        <div class="text-right text-xs text-gray-500 mt-1">
          {{ summary.length }}/500
        </div>
      </div>
    </form>

    <!-- Markdown 编辑器 -->
    <div class="mt-6">
      <md-editor
        v-model="content"
        placeholder="请输入文章内容"
        :toolbarsExclude="['github']"
        :theme="themeStore.themeMode === 'dark' ? 'dark' : 'light'"
      />
    </div>

    <!-- 保存按钮 -->
    <div class="mt-8 flex justify-end">
      <button
        type="button"
        @click="insertArticle"
        class="px-6 py-2 bg-green-500 hover:bg-green-600 text-white font-medium rounded-md shadow-sm transition duration-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500 flex items-center gap-2"
      >
        <Icon icon="mdi:content-save" width="18" />
        保存文章
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { MdEditor } from "md-editor-v3";
import "md-editor-v3/lib/style.css";
import { createArticle } from "@/api/articleService";
import { useThemeStore } from "@/stores/theme";
import { Icon } from "@iconify/vue";

const themeStore = useThemeStore();

const title = ref<string>("");
const summary = ref<string>("");
const content = ref<string>("");

const authorId = ref<number>(1);
const categoryId = ref<number>(1);
const coverImage = ref<string>("");

const insertArticle = async () => {
  const articleData = {
    title: title.value,
    summary: summary.value,
    authorId: authorId.value,
    categoryId: categoryId.value,
    coverImage: coverImage.value,
    content: content.value,
  };

  try {
    const res = await createArticle(articleData);
    window.$message?.success(res.data.msg || "文章保存成功");
  } catch (error) {
    window.$message?.error("保存失败，请重试");
    console.error("保存文章失败:", error);
  }
};
</script>

<style scoped>
:deep(.md-editor) {
  border-radius: 0.5rem;
}
</style>
