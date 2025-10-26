import { createRouter, createWebHistory } from "vue-router";
import ArticleCreate from "../views/ArticleCreate.vue";
import Home from "../views/Home.vue";
import ArticleDetail from "../views/ArticleDetail.vue";

const routes = [
  {
    path: "/articles/create",
    name: "ArticleCreate",
    component: ArticleCreate,
  },
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/articles/detail/:id",
    name: "ArticleDetail",
    component: ArticleDetail,
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
