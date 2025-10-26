<template>
  <n-h1>文章id: {{ articleId }}</n-h1>
  <n-skeleton v-if="loading" :rows="5" style="width: 100%" />
  <MdPreview
    v-else
    v-model:model-value="markdownContent"
    :height="'600px'"
    class="z-index: 1"
  />
  <n-card
    v-for="(comment, value) in commentList"
    :key="comment.id"
    vertical
    title="评论区"
  >
    <n-card :title="`${comment.id}`" class="margin-bottom: 15px;">
      {{ comment.content }}
      <n-button
        quaternary
        @click="likeCommentByCommentId(comment.id)"
        v-if="comment.likeStatus === true"
      >
        <n-icon>
          <Star></Star>
        </n-icon>
      </n-button>
      <n-button quaternary @click="likeCommentByCommentId(comment.id)" v-else>
        <n-icon>
          <StarOutline></StarOutline>
        </n-icon>
      </n-button>
    </n-card>
  </n-card>

  <n-button @click="InsertComment()">测试按钮</n-button>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { MdPreview } from "md-editor-v3";
import "md-editor-v3/lib/style.css";
import { useRoute } from "vue-router";
import { getArticleDetailById } from "../api/articleService";
import {
  createComment,
  getCommentList,
  likeComment,
} from "../api/commentService";
import { Star, StarOutline } from "@vicons/ionicons5";
const route = useRoute();
const articleId = route.params.id;
const markdownContent = ref<string>(``);
const loading = ref<boolean>(false);
const pageSize = ref<number>(10);
const pageNum = ref<number>(1);
const commentList = ref<Array<any>>([]);

const selectArticleDetailById = async () => {
  loading.value = true;
  const res = await getArticleDetailById(Number(articleId));
  markdownContent.value = res.data.content;
  loading.value = false;
};

const selectCommentList = async () => {
  const articleIdNum = route.params.id
    ? Array.isArray(route.params.id)
      ? Number(route.params.id[0])
      : Number(route.params.id)
    : 0;
  const commentData = {
    articleId: articleIdNum,
    pageNum: pageNum.value,
    pageSize: pageSize.value,
  };
  const res = await getCommentList(commentData);
  commentList.value = res.data.list;
};

const InsertComment = async () => {
  const articleIdNum = route.params.id
    ? Array.isArray(route.params.id)
      ? Number(route.params.id[0])
      : Number(route.params.id)
    : 0;
  const commentData = {
    articleId: articleIdNum,
    parentId: 0,
    content: "这是一个测试评论",
  };
  const res = await createComment(commentData);
  window.$message?.success(res.data);
  selectCommentList();
};

const likeCommentByCommentId = async (commentId: number) => {
  const res = await likeComment(commentId);
  window.$message?.success(res.data);
  selectCommentList();
};

onMounted(() => {
  selectArticleDetailById();
  selectCommentList();
});
</script>
