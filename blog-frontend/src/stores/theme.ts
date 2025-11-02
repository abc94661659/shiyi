import { defineStore } from "pinia";

export const useThemeStore = defineStore("theme", {
  state: () => ({
    isDark: localStorage.getItem("Theme") === "dark" ? true : false,
  }),
  actions: {
    toggleTheme() {
      this.isDark = !this.isDark;
      localStorage.setItem("Theme", this.isDark ? "dark" : "light");
    },
  },
});
