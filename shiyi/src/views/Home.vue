<template>
  <MdPreview :model-value="content" :theme="themeStore.themeMode"></MdPreview>
</template>
<script setup lang="ts">
import { ref } from "vue";
import { useThemeStore } from "@/stores/theme";
import { MdPreview } from "md-editor-v3";
import "md-editor-v3/lib/style.css";
const themeStore = useThemeStore();
const content = ref<string>(`
# Java is good
\`\`\`mermaid
flowchart TD
  Start --> Stop
\`\`\`
\`\`\`mermaid
flowchart TD
  Start --> Stop
\`\`\`
\`\`\`mermaid
flowchart TD
  Start --> Stop
\`\`\`
\`\`\`mermaid
flowchart TD
  Start --> Stop
\`\`\`
\`\`\`mermaid
flowchart TD
  Start --> Stop
\`\`\`
\`\`\`mermaid
flowchart TD
  Start --> Stop
\`\`\`
\`\`\`mermaid
flowchart TD
  Start --> Stop
\`\`\`
`);
</script>
