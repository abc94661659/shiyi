import axios, {
  type InternalAxiosRequestConfig,
  type AxiosResponse,
} from "axios";
import type { AxiosInstance } from "axios";
// 创建 axios 实例
const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "", // 从环境变量获取基础地址
  timeout: 10000, // 请求超时时间
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
});

service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // 可在此处添加请求头，如 Token 认证
    // const token = localStorage.getItem('token');
    // if (token && config.headers) {
    //   config.headers.Authorization = `Bearer ${token}`;
    // }
    return config;
  },
  (error) => {
    // console.error('请求错误:', error);
    return Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data;
    if (res.code === 200) {
      return res;
    } else {
      // 非 200 状态码视为失败
      // console.error('接口错误:', res.msg || '未知错误');
      // ElMessage.error(res.msg || '操作失败');
      window.$message?.error(res.msg || "操作失败");
      return Promise.reject(new Error(res.msg || "接口请求失败"));
    }
  },
  (error) => {
    // console.error('网络错误:', error.message);
    window.$message?.error(error.message || "网络异常，请稍后重试");
    return Promise.reject(error);
  }
);

export default service;
