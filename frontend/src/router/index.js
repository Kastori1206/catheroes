import Vue from "vue";
import Router from "vue-router";

import constants from "@/lib/constants";

// 유저
import Login from "@/page/user/Login.vue";
import Join from "@/page/user/Join.vue";
import Info from "@/page/user/Info.vue";

// 포스트
import List from "@/page/post/List.vue";

// page not found
import Pagenotfound from "@/page/Pagenotfound.vue";

Vue.use(Router);

export default new Router({
  routes: [
    // 로그인
    {
      path: "/user/login",
      name: constants.URL_TYPE.USER.LOGIN,
      component: Login,
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          next("/");
        } else {
          next();
        }
      },
    },
    // 회원가입
    {
      path: "/user/join",
      name: constants.URL_TYPE.USER.JOIN,
      component: Join,
    },
    // 포스트
    {
      path: "/",
      name: constants.URL_TYPE.POST.MAIN,
      component: List,
    },
    // 회원정보
    {
      path: "/user/info",
      name: constants.URL_TYPE.USER.INFO,
      component: Info,
    },
    // Page Not Found
    {
      path: "*",
      name: "Pagenotfound",
      component: Pagenotfound,
    },
  ],
});
