<template>
  <n-card
    title="评论区"
    :bordered="false"
    :header-extra-style="{ color: 'gray', 'margin-left': '8px' }"
  >
    <template #header-extra> {{ totalComments }} </template>
    <n-flex
      v-for="comment in commentList"
      :key="comment.id"
      :title="`${comment.id}`"
      style="margin-bottom: 15px"
      vertical
    >
      <!-- 主评论内容 -->
      {{ comment.userId }}
      <n-text>{{ comment.content }}</n-text>
      <n-flex align="center" :wrap="false" style="color: gray">
        {{ comment.createTime }}
        <n-button
          text
          @click="handleLike(comment.id)"
          v-if="comment.isLike === true"
        >
          <n-icon style="font-size: 18px">
            <Star />
          </n-icon>
        </n-button>
        <n-button text @click="handleLike(comment.id)" v-else>
          <n-icon>
            <StarOutline />
          </n-icon>
        </n-button>
        <n-button text size="tiny">回复</n-button>
      </n-flex>

      <n-flex
        v-if="expandedParentIds.includes(comment.id)"
        vertical
        style="gap: 10px; margin-left: 60px; margin-top: 10px"
      >
        <!-- 子评论列表 -->
        <n-flex
          v-for="child in childCommentsMap[comment.id]"
          :key="child.id"
          vertical
          style="padding: 5px 0; border-bottom: 1px solid #f0f0f0"
        >
          <n-flex align="center">
            <span>{{ child.userId }}:</span>
            <n-text>{{ child.content }}</n-text>
          </n-flex>
          <n-flex align="center" style="color: gray; margin-top: 5px">
            {{ child.createTime }}
            <n-button
              text
              @click="handleLike(child.id)"
              v-if="child.isLike === true"
            >
              <n-icon>
                <Star />
              </n-icon>
            </n-button>
            <n-button text @click="handleLike(child.id)" v-else>
              <n-icon>
                <StarOutline />
              </n-icon>
            </n-button>
            <n-button text size="tiny">回复</n-button>
          </n-flex>
        </n-flex>

        <!-- 子评论分页 -->
        <n-pagination
          v-if="(childTotalMap[comment.id] || 0) > pageSize"
          :item-count="childTotalMap[comment.id] || 0"
          :page="childPageNumMap[comment.id] || 1"
          :page-size="pageSize"
          @update:page="(newPage) => handleChildPageChange(comment.id, newPage)"
          style="margin-top: 10px"
        />
      </n-flex>

      <!-- 子评论展开/收起按钮（修改后） -->
      <n-flex
        style="gap: 0; color: gray; margin-top: 5px"
        v-if="comment.childCount != 0"
      >
        共 {{ comment.childCount }} 条回复,
        <n-button
          text
          style="color: gray"
          @click="toggleChildComments(comment.id)"
        >
          {{ expandedParentIds.includes(comment.id) ? "收起" : "点击查看" }}
        </n-button>
      </n-flex>

      <n-divider style="margin-top: auto"></n-divider>
    </n-flex>
    <n-pagination
      v-if="totalParentComments > pageSize"
      :item-count="totalParentComments"
      :page="pageNum"
      :page-size="pageSize"
      @update:page="PageNumChange"
    />
  </n-card>
</template>

<script setup lang="ts">
import { Star, StarOutline } from "@vicons/ionicons5";
import { defineProps, ref, onMounted } from "vue";
import {
  likeComment,
  queryParentComment,
  queryChildComment,
} from "../../api/interactionService";

// 定义评论数据类型
interface Comment {
  id: number;
  userId: number;
  parentId: number;
  content: string;
  createTime: string;
  isLike: boolean;
  childComment?: {
    id: number;
    userId: number;
    parentId: number;
    content: string;
    createTime: string;
    likeStatus: boolean;
  };
  childCount: number;
}

// 接收父组件传入的实体类型和ID
const props = defineProps<{
  entityType: string;
  entityId: number;
}>();

// 评论列表相关状态
const commentList = ref<Comment[]>([]);
const pageSize = ref<number>(10);
const pageNum = ref<number>(1);
const totalComments = ref<number>(0);
const totalParentComments = ref<number>(0);
//子评论相关状态
const childCommentsMap = ref<Record<number, Comment[]>>({}); // 按父ID存储子评论
const childTotalMap = ref<Record<number, number>>({}); // 按父ID存储子评论总数
const childPageNumMap = ref<Record<number, number>>({}); // 按父ID存储子评论当前页码
const expandedParentIds = ref<number[]>([]); // 记录展开的父评论ID

// 获取评论列表
const selectParentCommentList = async () => {
  const commentData = {
    entityType: props.entityType,
    entityId: props.entityId,
    pageNum: pageNum.value,
    pageSize: pageSize.value,
  };
  const res = await queryParentComment(commentData);
  commentList.value = res.data.pageInfo.list;
  totalComments.value = res.data.pageInfo.total;
  totalParentComments.value = res.data.totalParentComments;
};

const selectChildCommentList = async (parentId: number) => {
  const commentData = {
    entityType: props.entityType,
    entityId: props.entityId,
    parentId: parentId,
    pageNum: pageNum.value,
    pageSize: pageSize.value,
  };
  const res = await queryChildComment(commentData);
  childCommentsMap.value[parentId] = res.data.list;
  childTotalMap.value[parentId] = res.data.total;
};

const toggleChildComments = async (parentId: number) => {
  const isExpanded = expandedParentIds.value.includes(parentId);
  if (isExpanded) {
    expandedParentIds.value = expandedParentIds.value.filter(
      (id) => id !== parentId
    );
  } else {
    if (!childCommentsMap.value[parentId]) {
      childPageNumMap.value[parentId] = 1; // 初始化页码
      await selectChildCommentList(parentId);
    }
    expandedParentIds.value.push(parentId);
  }
};
const PageNumChange = (newPageNum: number) => {
  pageNum.value = newPageNum;
  selectParentCommentList();
};
const handleChildPageChange = (parentId: number, newPage: number) => {
  childPageNumMap.value[parentId] = newPage;
  selectChildCommentList(parentId);
};

// 处理点赞
const handleLike = async (commentId: number) => {
  const likeData = {
    entityType: "COMMENT",
    entityId: commentId,
  };
  const res = await likeComment(likeData);
  window.$message?.success(res.data);
  selectParentCommentList();
  expandedParentIds.value.forEach((parentId) => {
    selectChildCommentList(parentId);
  });
};

onMounted(() => {
  selectParentCommentList();
});
</script>

<style scoped>
::v-deep .n-card-header__main {
  flex: initial !important;
}
</style>
