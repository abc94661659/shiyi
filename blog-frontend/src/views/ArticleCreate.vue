<template>
  <n-flex>
    <n-h1>文章创建</n-h1>
    <n-divider />
    <n-form>
      <n-form-item label="标题">
        <n-input
          placeholder="请输入标题"
          maxlength="50"
          show-count
          clearable
          v-model:value="title"
        ></n-input>
      </n-form-item>
      <n-form-item label="摘要">
        <n-input
          placeholder="请输入摘要"
          maxlength="500"
          type="textarea"
          :autosize="{
            minRows: 3,
          }"
          v-model:value="summary"
        ></n-input>
      </n-form-item>
    </n-form>
    <!-- markdown 编辑器 -->
    <md-editor
      v-model="content"
      placeholder="请输入文章内容"
      :toolbarsExclude="['github']"
      :theme="themeStore.isDark ? 'dark' : 'light'"
    />
    <n-flex>
      <n-button type="success" @click="insertArticle()">保存文章</n-button>
    </n-flex>
  </n-flex>
</template>

<script setup lang="ts">
import { MdEditor } from "md-editor-v3";
import "md-editor-v3/lib/style.css";
import { createArticle } from "../api/articleService";
import { useMessage } from "naive-ui";
import { useThemeStore } from "../stores/theme";

const themeStore = useThemeStore();
const message = useMessage();
const title = ref<string>("");
const summary = ref<string>("");
const authorId = ref<number>(1);
const categoryId = ref<number>(1);
const coverImage = ref<string>("");
const content = ref<string>("");

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
    message.success(res.data.msg || "文章保存成功");
  } catch (error) {}
};
</script>

<style scoped></style>
