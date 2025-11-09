import request from "../utils/request";

// 文章创建请求参数类型定义
export interface CommentCreateDTO {
  entityType: string;
  entityId: number;
  parentId: number | null;
  content: string;
}

export interface CommentQueryDTO {
  entityType: string;
  entityId: number;
  pageNum: number;
  pageSize: number;
}

export interface CommentChildQueryDTO {
  entityType: string;
  entityId: number;
  parentId: number;
  pageNum: number;
  pageSize: number;
}

export interface LikeDTO {
  entityType: string;
  entityId: number;
}

/**
 * 创建评论
 * @param data 评论数据
 * @returns 成功响应 { code: 200, msg: string, data: string }
 */
export const createComment = (data: CommentCreateDTO) => {
  return request.post("/interaction/comment/createComment", data);
};

/**
 * 分页查询评论
 * @param data 评论数据
 * @returns 成功响应 { code: 200, msg: string, data: string }
 */
export const queryParentComment = (data: CommentQueryDTO) => {
  return request.get("/interaction/comment/queryParentComment", {
    params: data,
  });
};

export const likeComment = (data: LikeDTO) => {
  return request.post("/interaction/like/likeComment", data);
};
/**
 * 分页查询子评论
 * @param data 评论数据
 * @returns 成功响应 { code: 200, msg: string, data: string }
 */
export const queryChildComment = (data: CommentChildQueryDTO) => {
  return request.get("/interaction/comment/queryChildComment", {
    params: data,
  });
};
