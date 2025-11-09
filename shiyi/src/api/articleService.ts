import request from "@/utils/request";

// 文章创建请求参数类型定义
export interface ArticleCreateDTO {
  title: string;
  summary: string;
  authorId: number;
  categoryId: number;
  coverImage: string;
  content: string;
}

export interface ArticleDetailVO {
  id: number;
  title: string;
  summary: string;
  authorId: number;
  authorName: string;
  categoryId: number;
  categoryName: string;
  coverImage: string;
  content: string;
}

/**
 * 创建文章
 * @param data 文章数据
 * @returns 成功响应 { code: 200, msg: string, data: string }
 */
export const createArticle = (data: ArticleCreateDTO) => {
  return request.post("/article/createArticle", data);
};

export const getArticleDetailById = (id: number) => {
  return request.get(`/article/getArticle/${id}`);
};
