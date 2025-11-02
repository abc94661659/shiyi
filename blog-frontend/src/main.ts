import { createApp } from "vue";
import "./assets/style.css";
import App from "./App.vue";
import router from "./router";
import { createPinia } from "pinia";
import { NMessageProvider } from "naive-ui";

const app = createApp(App);
app.component("NMessageProvider", NMessageProvider);
app.use(createPinia());
app.use(router);
app.mount("#app");
