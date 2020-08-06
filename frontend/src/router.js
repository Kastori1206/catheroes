import Vue from "vue";
import Router from "vue-router";
import Index from "./views/Index.vue";
import Landing from "./views/Landing.vue";
import Login from "./views/Login.vue";
import Profile from "./views/Profile.vue";
import Detail from "./views/Detail.vue";
import Join from "./views/Join.vue";
import News from "./views/News.vue";
import Create from "./views/Create.vue";
import Test from "./views/Test.vue";
import MainNavbar from "./layout/MainNavbar.vue";
import MainFooter from "./layout/MainFooter.vue";
import Funding from "./views/Funding.vue";

Vue.use(Router);

export default new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "index",
      components: { default: Index, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/landing",
      name: "landing",
      components: { default: Landing, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/login",
      name: "login",
      components: { default: Login, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
      },
    },
    {
      path: "/join",
      name: "join",
      components: { default: Join, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
      },
    },

    {
      path: "/profile",
      name: "profile",
      components: { default: Profile, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          console.log("로그인되어있다고했다");
          next();
        } else {
          console.log("로그인안되어있다고 했다");
          next("/");
        }
      },
    },
    {
      path: "/detail/:catid",
      name: "detail",
      components: { default: Detail, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          console.log("로그인되어있다고했다");
          next();
        } else {
          console.log("로그인안되어있다고 했다");
          next("/");
        }
      },
    },
    {
      path: "/create",
      name: "create",
      components: { default: Create, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
      },
    },
    {
      path: "/test",
      name: "test",
      components: { default: Test, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
      },
    },
    {
      path: "/news",
      name: "news",
      components: { default: News, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          console.log("로그인되어있다고했다");
          next();
        } else {
          console.log("로그인안되어있다고 했다");
          next("/");
        }
      },
    },
    {
      path: "/funding",
      name: "funding",
      components: { default: Funding, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          console.log("로그인되어있다고했다");
          next();
        } else {
          console.log("로그인안되어있다고 했다");
          next("/");
        }
      },
    },
  ],
  scrollBehavior: (to) => {
    if (to.hash) {
      return { selector: to.hash };
    } else {
      return { x: 0, y: 0 };
    }
  },
});
