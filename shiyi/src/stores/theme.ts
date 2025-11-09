import { ref, watch } from "vue";
import { defineStore } from "pinia";

// 定义主题模式类型
type ThemeMode = "light" | "dark";

export const useThemeStore = defineStore("theme", () => {
  // 从本地存储读取默认主题，默认浅色模式
  const themeMode = ref<ThemeMode>(
    (localStorage.getItem("theme") as ThemeMode) || "light"
  );

  // 切换主题模式
  const toggleTheme = () => {
    themeMode.value = themeMode.value === "light" ? "dark" : "light";
  };
  const setTheme = (mode: ThemeMode) => {
    themeMode.value = mode;
  };

  watch(
    themeMode,
    (newMode) => {
      localStorage.setItem("theme", newMode);
      document.documentElement.classList.toggle("dark", newMode === "dark");
    },
    { immediate: true }
  );

  return { themeMode, toggleTheme, setTheme };
});
