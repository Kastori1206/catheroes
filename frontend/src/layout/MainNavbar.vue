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

      <div id="inputs">
        <!-- <i class="fas fa-camera" @click="searchImage"></i> -->
        <!-- <md-field class="md-form-group" slot="inputs"> -->
        <label for="ttest">
          <i class="fas fa-camera"></i>
        </label>
        <md-input
          id="ttest"
          ref="imageInput"
          type="file"
          style="display:none;"
          @change="onChangeImages"
        ></md-input>
        <!-- </md-field> -->
        <!-- <md-field> -->
        <!-- <label>Only images</label>
        <md-file @change="onChangeImages" accept="image/*" />
        </md-field>-->
        <div class="md-layout">
          <md-field class="md-form-group">
            <i class="fas fa-search"></i>
            <md-input @keydown.enter="searchName" v-model="search" placeholder="Search Cat's Name"></md-input>
          </md-field>
        </div>
      </div>

      <div class="md-toolbar-section-end">
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
            <md-list>
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

              <!-- <md-list-item>
                <router-link to="/detail">
                  <p>DETAIL</p>
                </router-link>
              </md-list-item>-->

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
      image: null,
      extraNavClasses: "",
      toggledClass: false,
      search: "",
      imageInput: "",
      center: this.dong
    };
  },
  computed: {},
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
      // console.log(this.search);
      // console.log(this.centerdong);

      const formData = new FormData();
      formData.append("nickname", this.search);
      formData.append("location", this.centerdong);

      axios
        .post(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "cat/search",
          formData
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
    }
  },
  mounted() {
    document.addEventListener("scroll", this.scrollListener);
  },
  beforeDestroy() {
    document.removeEventListener("scroll", this.scrollListener);
  }
};
</script>