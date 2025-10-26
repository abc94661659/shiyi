import request from "../utils/request";

// 文章创建请求参数类型定义
export interface CommentCreateDTO {
  articleId: number;
  parentId: number;
  content: string;
}

export interface CommentQueryDTO {
  articleId: number;
  pageNum: number;
  pageSize: number;
}

/**
 * 创建评论
 * @param data 评论数据
 * @returns 成功响应 { code: 200, msg: string, data: string }
 */
export const createComment = (data: CommentCreateDTO) => {
  return request.post("/comment/createComment", data);
};

/**
 * 分页查询评论
 * @param data 评论数据
 * @returns 成功响应 { code: 200, msg: string, data: string }
 */
export const getCommentList = (data: CommentQueryDTO) => {
  return request.get("/comment/getCommentList", { params: data });
};

export const likeComment = (commentId: number) => {
  return request.post("/comment/likeComment", {}, { params: { commentId } });
};
