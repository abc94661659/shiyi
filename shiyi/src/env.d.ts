// src/env.d.ts
import type {
  MessageApi,
  NotificationApi,
  DialogApi,
  LoadingBarApi,
} from "naive-ui";

declare module "*.vue" {
  import type { DefineComponent } from "vue";
  const component: DefineComponent<{}, {}, any>;
  export default component;
}
declare global {
  interface Window {
    $message: MessageApi;
    $notification: NotificationApi;
    $dialog: DialogApi;
    $loadingBar: LoadingBarApi;
  }
}
export {};
