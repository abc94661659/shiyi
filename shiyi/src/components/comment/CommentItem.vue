<template>
  <div
    class="bg-white rounded-lg shadow-sm border border-gray-100 overflow-hidden"
  >
    <!-- 评论区头部 -->
    <div
      class="px-6 py-4 border-b border-gray-100 flex justify-between items-center"
    >
      <h3 class="text-lg font-semibold text-gray-800">评论区</h3>
      <span class="text-gray-500 text-sm">{{ totalComments }}</span>
    </div>

    <!-- 评论列表 -->
    <div class="p-6 space-y-6">
      <!-- 主评论项 -->
      <div
        v-for="comment in commentList"
        :key="comment.id"
        class="space-y-3 pb-6 border-b border-gray-100 last:border-0"
      >
        <!-- 主评论内容 -->
        <div class="space-y-2">
          <span class="text-gray-700 font-medium">{{ comment.userId }}</span>
          <p class="text-gray-600">{{ comment.content }}</p>
          <!-- 评论操作区 -->
          <div class="flex items-center text-gray-500 text-sm space-x-4 pt-1">
            <span>{{ comment.createTime }}</span>
            <button
              @click="handleLike(comment.id)"
              class="flex items-center hover:text-red-500 transition-colors"
            >
              <Icon
                :icon="
                  comment.isLike
                    ? 'streamline-plump:like-1-solid'
                    : 'streamline-plump:like-1-remix'
                "
                width="20"
                height="20"
              />
            </button>
            <button class="hover:text-blue-500 transition-colors">回复</button>
          </div>
        </div>

        <!-- 子评论区域 -->
        <div
          v-if="expandedParentIds.includes(comment.id)"
          class="ml-16 mt-4 space-y-4"
        >
          <!-- 子评论列表 -->
          <div
            v-for="child in childCommentsMap[comment.id]"
            :key="child.id"
            class="py-3 border-b border-gray-100 last:border-0"
          >
            <div class="flex items-start space-x-2">
              <span class="text-gray-700 font-medium">{{ child.userId }}:</span>
              <p class="text-gray-600">{{ child.content }}</p>
            </div>
            <div class="flex items-center text-gray-500 text-sm space-x-4 mt-2">
              <span>{{ child.createTime }}</span>
              <button
                @click="handleLike(child.id)"
                class="flex items-center hover:text-red-500 transition-colors"
              >
                <Icon
                  :icon="
                    comment.isLike
                      ? 'streamline-plump:like-1-solid'
                      : 'streamline-plump:like-1-remix'
                  "
                  width="20"
                  height="20"
                />
              </button>
              <button class="hover:text-blue-500 transition-colors">
                回复
              </button>
            </div>
          </div>

          <!-- 子评论分页 -->
          <div
            v-if="(childTotalMap[comment.id] || 0) > pageSize"
            class="mt-4 flex justify-center"
          >
            <n-pagination
              :page-count="
                Math.ceil((childTotalMap[comment.id] || 0) / pageSize)
              "
              :page="childPageNumMap[comment.id] || 1"
              @update:page="
                (newPage) => handleChildPageChange(comment.id, newPage)
              "
              show-quick-jumper
            />
          </div>
        </div>

        <!-- 子评论展开/收起按钮 -->
        <div
          v-if="comment.childCount != 0"
          class="text-gray-500 text-sm flex items-center mt-2"
        >
          <span>共 {{ comment.childCount }} 条回复,</span>
          <button
            @click="toggleChildComments(comment.id)"
            class="ml-1 hover:text-blue-500 transition-colors"
          >
            {{ expandedParentIds.includes(comment.id) ? "收起" : "点击查看" }}
          </button>
        </div>
      </div>

      <!-- 主评论分页 -->
      <div class="mt-6 flex justify-center">
        <n-pagination
          v-if="totalParentComments > pageSize"
          :page-count="Math.ceil(totalParentComments / pageSize)"
          :page="pageNum"
          @update:page="PageNumChange"
          show-quick-jumper
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps, ref, onMounted } from "vue";
import {
  likeComment,
  queryParentComment,
  queryChildComment,
} from "@/api/interactionService";
import { NPagination } from "naive-ui";
import { Icon } from "@iconify/vue";
interface Comment {
  id: number;
  userId: number;
  parentId: number;
  content: string;
  createTime: string;
  isLike: boolean;
  childCount: number;
}

const props = defineProps<{
  entityType: string;
  entityId: number;
}>();

const commentList = ref<Comment[]>([]);
const pageSize = ref<number>(10);
const pageNum = ref<number>(1);
const totalComments = ref<number>(0);
const totalParentComments = ref<number>(0);

const childCommentsMap = ref<Record<number, Comment[]>>({});
const childTotalMap = ref<Record<number, number>>({});
const childPageNumMap = ref<Record<number, number>>({});
const expandedParentIds = ref<number[]>([]);

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
    pageNum: childPageNumMap.value[parentId] || 1,
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
      childPageNumMap.value[parentId] = 1;
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

const handleLike = async (commentId: number) => {
  const likeData = {
    entityType: "COMMENT",
    entityId: commentId,
  };
  const res = await likeComment(likeData);
  window.$message?.success(res.msg);
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
button {
  background: none;
  border: none;
  cursor: pointer;
  padding: 2px 4px;
}
button:focus {
  outline: none;
}
</style>
