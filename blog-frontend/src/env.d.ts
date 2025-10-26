import type { MessageApi, NotificationApi, DialogApi, LoadingBarApi } from 'naive-ui'

// 扩展Window接口，添加自定义属性
declare global {
    interface Window {
        $message: MessageApi
        $notification: NotificationApi
        $dialog: DialogApi
        $loadingBar: LoadingBarApi
    }
}

// 确保此文件被视为模块
export { }