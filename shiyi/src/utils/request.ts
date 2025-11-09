import axios, {
  type InternalAxiosRequestConfig,
  type AxiosResponse,
} from "axios";
import type { AxiosInstance } from "axios";
import { useAuthStore } from "@/stores/authStore";

const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "", // 从环境变量获取基础地址
  timeout: 10000, // 请求超时时间
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
});

service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const authStore = useAuthStore();
    if (authStore.token) {
      config.headers.Authorization = `Bearer ${authStore.token}`;
    }
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
    if (error.response?.status === 401) {
      const authStore = useAuthStore();
      authStore.removeToken();
      window.$message?.error("登录已过期，请重新登录");
      // 跳转到登录页（注意：在非组件中使用 router 需要单独引入）
      // setTimeout(() => {
      //   window.location.href = "/login";
      // }, 1500);
    } else {
      window.$message?.error(error.message || "网络异常，请稍后重试");
    }
    return Promise.reject(error);
  }
);

export default service;
