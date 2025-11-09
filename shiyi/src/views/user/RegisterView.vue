<template>
  <div class="min-h-screen flex items-center justify-center p-4">
    <div
      class="bg-white rounded-xl shadow-lg p-8 w-full max-w-md transform transition-all"
    >
      <h2 class="text-2xl font-bold text-center text-gray-800 mb-8">
        用户注册
      </h2>

      <form @submit.prevent="handleRegister" class="space-y-6">
        <Input
          v-model="userName"
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

        <Input
          v-model="email"
          label="邮箱"
          type="email"
          :required="true"
          width="100%"
        />

        <div class="flex space-x-3">
          <Input
            v-model="verificationCode"
            label="验证码"
            type="text"
            :required="true"
            width="100%"
          />
          <button
            type="button"
            @click="sendVerificationCode"
            :disabled="countdown > 0"
            class="mt-6 self-end px-4 py-2 bg-gray-200 hover:bg-gray-300 text-gray-800 rounded-lg transition duration-300 disabled:opacity-50"
          >
            {{ countdown > 0 ? `${countdown}s` : "发送验证码" }}
          </button>
        </div>

        <Input
          v-model="phone"
          label="手机号"
          type="tel"
          :required="false"
          width="100%"
        />

        <button
          type="submit"
          class="w-full bg-green-500 hover:bg-green-600 text-white font-medium py-3 px-4 rounded-lg transition duration-300 transform hover:scale-[1.02] active:scale-[0.98] text-lg"
        >
          注册
        </button>
      </form>

      <div class="mt-4 text-center">
        <a
          href="/login"
          class="text-sm text-blue-500 hover:text-blue-700 transition-colors"
        >
          已有账号？立即登录
        </a>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onUnmounted } from "vue";
import { useRouter } from "vue-router";
import Input from "@/components/common/Input.vue";
import { UserRegister } from "@/api/userService";

// 表单数据
const userName = ref<string>("");
const password = ref<string>("");
const email = ref<string>("");
const phone = ref<string>("");
const verificationCode = ref<string>("");

// 验证码倒计时
const countdown = ref<number>(0);
const countdownInterval = ref<number | null>(null); // 使用 number 类型替代 NodeJS.Timeout

// 路由
const router = useRouter();

// 发送验证码
const sendVerificationCode = () => {
  if (!email.value.trim()) {
    alert("请先输入邮箱");
    return;
  }

  // 模拟发送验证码
  console.log(`发送验证码到: ${email.value}`);
  window.$message?.info("验证码已发送（模拟）");

  // 开始倒计时
  countdown.value = 60;
  if (countdownInterval.value) {
    clearInterval(countdownInterval.value);
  }

  countdownInterval.value = window.setInterval(() => {
    countdown.value--;
    if (countdown.value <= 0) {
      clearInterval(countdownInterval.value as number);
      countdownInterval.value = null;
    }
  }, 1000);
};

// 注册处理函数
const handleRegister = async () => {
  // 简单验证
  if (!userName.value.trim()) {
    alert("请输入用户名");
    return;
  }

  if (!password.value.trim()) {
    alert("请输入密码");
    return;
  }

  if (!email.value.trim()) {
    alert("请输入邮箱");
    return;
  }

  if (!verificationCode.value.trim()) {
    alert("请输入验证码");
    return;
  }

  const registerData = {
    userName: userName.value,
    password: password.value,
    email: email.value,
    phone: phone.value || null, // 如果手机号为空则传null
  };

  try {
    const res = await UserRegister(registerData);
    window.$message?.success("注册成功！");
    // 注册成功后跳转到登录页面
    router.push("/login");
  } catch (error) {
    console.error("注册失败:", error);
    window.$message?.error("注册失败，请重试");
  }
};

// 组件卸载时清理定时器
onUnmounted(() => {
  if (countdownInterval.value) {
    clearInterval(countdownInterval.value);
  }
});
</script>
