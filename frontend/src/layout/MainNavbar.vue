<template>
  <md-toolbar
    id="toolbar"
    md-elevation="0"
    class="md-transparent md-absolute"
    :class="extraNavClasses"
    :color-on-scroll="colorOnScroll"
  >
    <div class="md-toolbar-row md-collapse-lateral">
      <div class="md-toolbar-section-start">
        <router-link to="/">
          <h3 class="md-title">길냥이히어로즈</h3>
        </router-link>
      </div>

      <div id="inputs" v-if="showSearchbar" style="margin-bottom: 15px;">
        <div
          id="camera"
          style="margin-top:15px; margin-left:20px; display:inline-block; float:right;"
        >
          <label for="searchImage">
            <i class="fas fa-camera"></i>
          </label>
          <md-input
            id="searchImage"
            ref="imageInput"
            type="file"
            style="display:none;"
            @change="onChangeImages"
          ></md-input>
        </div>
        <div style="display:inline-block; float:left" class="md-layout">
          <md-field class="md-form-group">
            <i style="margin-top:10px;" class="fas fa-search"></i>
            <md-input
              @keydown.enter="searchName"
              v-model="search"
              placeholder="Search Cat's Name"
              style="font-color: #000000 !important;"
            ></md-input>
          </md-field>
        </div>
      </div>

      <div class="md-toolbar-section-end">
        <div v-if="isLoggedIn">
          <div
            class="md-just-icon md-toolbar-toggle"
            style="background-color: transparent !important"
          >
            <md-badge :md-content="memberinfo.news" style="margin-top: -7px;">
              <!-- <md-icon>notifications</md-icon> -->
              <div @click="newsfeed(memberinfo.news)">
                <i class="fas fa-bell" style="margin-top: 10px; margin-right: 10px"></i>
              </div>
            </md-badge>
          </div>
        </div>
        <!-- </div>

        <div class="md-toolbar-section-end">-->
        <md-button
          class="md-just-icon md-simple md-toolbar-toggle"
          :class="{ toggled: toggledClass }"
          @click="toggleNavbarMobile()"
        >
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </md-button>

        <div class="md-collapse">
          <div class="md-collapse-wrapper">
            <mobile-menu nav-mobile-section-start="false">
              <!-- Here you can add your items from the section-start of your toolbar -->
            </mobile-menu>
            <md-list @click="toggleNavbarMobile()">
              <md-list-item v-if="!isLoggedIn">
                <router-link to="/login">
                  <p>LOGIN</p>
                </router-link>
              </md-list-item>

              <md-list-item v-if="!isLoggedIn">
                <router-link to="/join">
                  <p>JOIN</p>
                </router-link>
              </md-list-item>

              <md-list-item v-if="isLoggedIn" @click="logout">
                <router-link to="/">
                  <p>LOGOUT</p>
                </router-link>
              </md-list-item>

              <md-list-item v-if="isLoggedIn">
                <router-link to="/profile">
                  <p>PROFILE</p>
                </router-link>
              </md-list-item>

              <md-list-item>
                <router-link to="/create">
                  <p>CREATE</p>
                </router-link>
              </md-list-item>

              <md-list-item>
                <router-link to="/news">
                  <p>NEWS</p>
                </router-link>
              </md-list-item>

              <md-list-item>
                <router-link to="/funding">
                  <p>FUNDING</p>
                </router-link>
              </md-list-item>
            </md-list>
          </div>
        </div>
      </div>
    </div>
  </md-toolbar>
</template>

<script>
let resizeTimeout;
function resizeThrottler(actualResizeHandler) {
  // ignore resize events as long as an actualResizeHandler execution is in the queue
  if (!resizeTimeout) {
    resizeTimeout = setTimeout(() => {
      resizeTimeout = null;
      actualResizeHandler();

      // The actualResizeHandler will execute at a rate of 15fps
    }, 66);
  }
}

import MobileMenu from "@/layout/MobileMenu";
import axios from "axios";

export default {
  components: {
    MobileMenu
  },
  props: {
    type: {
      type: String,
      default: "white",
      validator(value) {
        return [
          "white",
          "default",
          "primary",
          "danger",
          "success",
          "warning",
          "info"
        ].includes(value);
      }
    },
    colorOnScroll: {
      type: Number,
      default: 0
    },
    isLoggedIn: Boolean,
    centerdong: String
  },
  data() {
    return {
      showSearchbar: true,
      image: null,
      extraNavClasses: "",
      toggledClass: false,
      search: "",
      imageInput: "",
      center: this.dong,
      memberinfo: {
        email: null,
        nickname: null,
        mid: null,
        image: null,
        news: null
      },
    };
  },
  created() {
    
  },
  methods: {
    onChangeImages(e) {
      // console.log(e.target.files);
      const file = e.target.files[0];
      this.image = file;
      const fd = new FormData();
      fd.append("image", this.image);
      axios
        .post(process.env.VUE_APP_DJANGO_API_SERVER_URL + "keras/", fd, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(res => {
          // console.log(res);
          this.breed = res.data;
          // console.log(this.breed)

          this.searchImage(res.data);
        })
        .catch(err => {
          console.log(err);
        });
    },
    bodyClick() {
      let bodyClick = document.getElementById("bodyClick");

      if (bodyClick === null) {
        let body = document.querySelector("body");
        let elem = document.createElement("div");
        elem.setAttribute("id", "bodyClick");
        body.appendChild(elem);

        let bodyClick = document.getElementById("bodyClick");
        bodyClick.addEventListener("click", this.toggleNavbarMobile);
      } else {
        bodyClick.remove();
      }
    },
    toggleNavbarMobile() {
      this.showSearchbar = !this.showSearchbar;
      this.NavbarStore.showNavbar = !this.NavbarStore.showNavbar;
      this.toggledClass = !this.toggledClass;
      this.bodyClick();
    },
    handleScroll() {
      let scrollValue =
        document.body.scrollTop || document.documentElement.scrollTop;
      let navbarColor = document.getElementById("toolbar");
      this.currentScrollValue = scrollValue;
      if (this.colorOnScroll > 0 && scrollValue > this.colorOnScroll) {
        this.extraNavClasses = `md-${this.type}`;
        navbarColor.classList.remove("md-transparent");
      } else {
        if (this.extraNavClasses) {
          this.extraNavClasses = "";
          navbarColor.classList.add("md-transparent");
        }
      }
    },
    scrollListener() {
      resizeThrottler(this.handleScroll);
    },
    logout() {
      console.log("로그아웃요청보냈다고했다");
      this.$emit("submit-logout");
    },
    searchName() {
      axios
        .get(
          `${process.env.VUE_APP_SPRING_API_SERVER_URL}cat/search?nickname=${this.search}&location=${this.centerdong}`
        )
        .then(res => {
          console.log(res);
          this.$emit("submit-search-data", res);
          this.$router.push("/search");
        })
        .catch(error => {});
    },
    searchImage(data) {
      // console.log(data)
      const formData = new FormData();
      formData.append("breed", data);
      formData.append("location", this.centerdong);

      axios
        .post(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "cat/searchImage",
          formData
        )
        .then(res => {
          console.log(res);
          this.$emit("submit-search-data", res);
          this.$router.push("/search");
        })
        .catch(error => {});
    },
    newsCount() {
      console.log("유저정보받아오라고했다");
      const token = this.$cookies.get("auth-token");
      axios
        .post(process.env.VUE_APP_SPRING_API_SERVER_URL + "member/info", null, {
          headers: { Authorization: `${token}` }
        })
        .then(res => {
          console.log("user정보 출력");
          console.log(res.data);
          this.memberinfo.email = res.data.email;
          this.memberinfo.nickname = res.data.nickname;
          this.memberinfo.mid = res.data.mid;
          this.memberinfo.image = res.data.image;
          this.memberinfo.news = res.data.news;
        })
        .catch(error => {
          console.log(error);
        });
    },
    newsfeed(count) {
      alert(count);
      
      axios
        .get(process.env.VUE_APP_SPRING_API_SERVER_URL + "article/newArticle?mid="+this.memberinfo.mid+"&count="+count)        
        .then(res => {
          console.log(res.data)
        })
        .catch(err => {
          console.log(err)
        })
      // 내가 팔로우한 고양이들의 게시글을
      // count 만큼 (대신 내가 쓴거 뺴고)
      // 불러온다.
      
    }
  },
  mounted() {
    document.addEventListener("scroll", this.scrollListener);
    this.newsCount();
  },
  beforeDestroy() {
    document.removeEventListener("scroll", this.scrollListener);
  }
};
</script>