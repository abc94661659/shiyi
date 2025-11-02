<template>
  <div>
    <MdPreview v-model="markdownContent" :height="'600px'" />
  </div>
</template>

<script setup lang="ts">
import { MdPreview } from "md-editor-v3";
import "md-editor-v3/lib/style.css";

const markdownContent = ref(`# 这是一篇Markdown示例
- 列表项1
- 列表项2

**加粗文本**  *倾斜文本*

$$E=mc^2$$  // 块级公式

行内公式 $a^2 + b^2 = c^2$

\`\`\`mermaid
graph TD;
  A-->B;
  B-->C;
\`\`\`

\`\`\`mermaid
graph TD;
  A-->B;
  B-->C;
\`\`\`
\`\`\`mermaid
graph TD;
  A-->B;
  B-->C;
\`\`\`
\`\`\`mermaid
graph TD;
  A-->B;
  B-->C;
\`\`\`
\`\`\`mermaid
graph TD;
  A-->B;
  B-->C;
\`\`\`
`);
</script>
