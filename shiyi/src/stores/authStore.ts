// src/stores/authStore.ts
import { defineStore } from "pinia";

const TOKEN_KEY = "access_token";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: localStorage.getItem(TOKEN_KEY) || "",
  }),

  getters: {
    isLogin: (state) => !!state.token,

    // 可选：解析 token payload（用于获取 userId、username 等）
    userInfo: (state) => {
      if (!state.token) return null;
      try {
        const payload = JSON.parse(atob(state.token.split(".")[1]));
        return {
          userId: payload.userId,
          username: payload.userName || payload.username,
          role: payload.role,
        };
      } catch {
        return null;
      }
    },
  },

  actions: {
    setToken(token: string) {
      this.token = token;
      localStorage.setItem(TOKEN_KEY, token);
    },

    removeToken() {
      this.token = "";
      localStorage.removeItem(TOKEN_KEY);
    },
  },
});
