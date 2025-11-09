import { createRouter, createWebHistory } from "vue-router";
import Home from "@/views/Home.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/login",
    name: "Login",
    component: () =>
      import(/* webpackChunkName: "user" */ "@/views/user/LoginView.vue"),
  },
  {
    path: "/register",
    name: "Register",
    component: () =>
      import(/* webpackChunkName: "user" */ "@/views/user/RegisterView.vue"),
  },
  {
    path: "/articles/create",
    name: "ArticleCreate",
    component: () =>
      import(
        /* webpackChunkName: "article" */ "@/views/article/ArticleCreate.vue"
      ),
  },
  {
    path: "/articles/detail/:id",
    name: "ArticleDetail",
    component: () =>
      import(
        /* webpackChunkName: "article" */ "@/views/article/ArticleDetail.vue"
      ),
  },
];
const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  window.$loadingBar.start();
  next();
});
router.afterEach(() => {
  window.$loadingBar.finish();
});
export default router;
