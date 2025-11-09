<template>
  <header
    class="sticky top-0 z-50 bg-white/95 backdrop-blur-sm shadow-sm transition-all duration-300"
    :style="{ backgroundColor: 'var(--bg-color)' }"
  >
    <nav class="container mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex h-16 items-center justify-between">
        <!-- Logo -->
        <div class="shrink-0">
          <a
            href="/"
            class="text-2xl font-bold text-indigo-600 transition-all duration-300 hover:scale-105 hover:text-indigo-700"
          >
            Linshiyi
          </a>
        </div>

        <!-- 桌面导航菜单（不含登录） -->
        <div class="hidden md:block">
          <div class="ml-10 flex items-center space-x-8">
            <a
              v-for="(item, index) in navItems"
              :key="index"
              :href="item.path"
              @click.prevent="handleNavigation(item.path)"
              class="group inline-flex items-center px-1 pt-1 text-base font-medium transition-all duration-300 relative"
              :class="
                activePath === item.path
                  ? 'text-indigo-600'
                  : 'text-gray-700 hover:text-indigo-500'
              "
            >
              {{ item.name }}
              <span
                class="absolute bottom-0 left-0 h-0.5 w-0 transition-all duration-300 origin-left"
                :class="
                  activePath === item.path
                    ? 'w-full bg-indigo-600'
                    : 'group-hover:w-full group-hover:bg-indigo-200'
                "
              />
            </a>
          </div>
        </div>

        <div class="flex items-center pt-1">
          <div class="mr-4">
            <ThemeSwitch />
          </div>
          <div v-if="authStore.isLogin" class="flex items-center space-x-2">
            <div
              class="w-8 h-8 rounded-full bg-indigo-500 flex items-center justify-center text-white text-base font-medium"
            >
              {{ authStore.userInfo?.username?.charAt(0).toUpperCase() || "U" }}
            </div>
            <span class="text-gray-700 hidden sm:block">
              {{ authStore.userInfo?.username }}
            </span>
            <button
              @click="handleLogout"
              class="text-base text-red-500 hover:text-red-700"
            >
              退出
            </button>
          </div>

          <div v-else class="flex items-center space-x-2">
            <RouterLink
              to="/login"
              class="text-base text-gray-700 hover:text-indigo-600"
              >登录</RouterLink
            >
            <span class="text-gray-400">/</span>
            <RouterLink
              to="/register"
              class="text-base text-gray-700 hover:text-indigo-600"
              >注册</RouterLink
            >
          </div>
        </div>
      </div>
    </nav>
  </header>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import ThemeSwitch from "@/components/common/ThemeSwitch.vue";
import { useAuthStore } from "@/stores/authStore";

const router = useRouter();
const route = useRoute();
const authStore = useAuthStore();

const navItems = [
  { name: "首页", path: "/" },
  { name: "产品", path: "/products" },
  { name: "服务", path: "/services" },
  { name: "关于", path: "/about" },
  { name: "联系", path: "/contact" },
];

const activePath = ref<string>(route.path);

onMounted(() => {
  activePath.value = route.path;
});

const handleNavigation = (path: string) => {
  activePath.value = path;
  router.push(path);
  window.scrollTo({ top: 0, behavior: "smooth" });
};

// 退出登录
const handleLogout = () => {
  authStore.removeToken();
  router.push("/login");
  window.$message?.success("已退出登录");
};
</script>

<style scoped>
a {
  position: relative;
}
</style>
