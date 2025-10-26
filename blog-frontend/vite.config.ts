import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { NaiveUiResolver } from 'unplugin-vue-components/resolvers'

export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      imports: [
        'vue',
        {
          'naive-ui': [
            'useDialog',
            'useMessage',
            'useNotification',
            'useLoadingBar'
          ]
        }
      ],
      // 生成自动导入的类型声明文件
      dts: 'src/auto-imports.d.ts'
    }),
    // 自动导入组件
    Components({
      extensions: ['vue', 'jsx'],
      resolvers: [NaiveUiResolver()],
      // 生成组件类型声明文件
      dts: 'src/components.d.ts'
    })
  ],
})
