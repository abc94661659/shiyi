<!-- Login.vue -->
<template>
  <div class="min-h-screen flex items-center justify-center p-4">
    <div
      class="bg-white rounded-xl shadow-lg p-8 w-full max-w-md transform transition-all"
    >
      <h2 class="text-2xl font-bold text-center text-gray-800 mb-8">
        用户登录
      </h2>

      <form @submit.prevent="handleLogin" class="space-y-6">
        <Input
          v-model="account"
          label="用户名"
          type="text"
          :required="true"
          width="100%"
        />

        <Input
          v-model="password"
          label="密码"
          type="password"
          :required="true"
          width="100%"
        />
        <div class="text-right">
          <a
            href="/register"
            class="text-sm text-blue-500 hover:text-blue-700 transition-colors"
          >
            注册新账号
          </a>
        </div>
        <button
          type="submit"
          class="w-full bg-green-500 hover:bg-green-600 text-white font-medium py-3 px-4 rounded-lg transition duration-300 transform hover:scale-[1.02] active:scale-[0.98] text-lg"
        >
          登录
        </button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import Input from "@/components/common/Input.vue";
import { UserLogin } from "@/api/userService";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/authStore";

// 表单数据
const account = ref<string>("");
const password = ref<string>("");
const router = useRouter();
const authStore = useAuthStore();

// 登录处理函数
const handleLogin = async () => {
  // 简单验证
  if (!account.value.trim()) {
    alert("请输入用户名");
    return;
  }

  if (!password.value.trim()) {
    alert("请输入密码");
    return;
  }
  const LoginData = {
    account: account.value,
    password: password.value,
  };
  const res = await UserLogin(LoginData);
  authStore.setToken(res.data);
  router.push("/");
  window.$message?.success(res.msg);
};
</script>
