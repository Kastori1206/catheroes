<template>
  <div class="wrapper">
    <div class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div
            class="md-layout-item md-size-33 md-small-size-66 md-xsmall-size-100 md-medium-size-40 mx-auto"
          >
            <login-card header-color="green">
              <h4 slot="title" class="card-title">Login</h4>
              <md-button
                @click="loginFormWithKakao"
                slot="buttons"
                href="javascript:void(0)"
                class="md-simple"
              >
                <img src="@/assets/img/login/kakao_login_medium.png" alt />
              </md-button>
              <p slot="description" class="description">Or Be Classical</p>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>email</md-icon>
                <label>Email...</label>
                <md-input v-model="loginData.email" type="email"></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>Password...</label>
                <md-input @keydown.enter="login" v-model="loginData.password" type="password"></md-input>
              </md-field>
              <md-button
                style="display:block; margin:auto;"
                @click="login"
                slot="footer"
                class="md-simple md-success md-lg"
              >Get Started</md-button>
              <md-button @click="gotoJoin" slot="footer" class="md-simple md-success md-lg">회원가입</md-button>
              <md-button slot="footer" class="md-simple md-success md-lg">아이디/비밀번호 찾기</md-button>
            </login-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { LoginCard } from "@/components";
import axios from "axios";
export default {
  components: {
    LoginCard
  },
  bodyClass: "login-page",
  data() {
    return {
      loginData: {
        email: null,
        password: null
      }
    };
  },
  created() {},
  props: {
    header: {
      type: String,
      default: require("@/assets/img/profile_city.jpg")
    }
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`
      };
    }
  },
  methods: {
    login() {
      console.log("로그인요청보냈다고했다");
      this.$emit("submit-login-data", this.loginData);
    },
    loginFormWithKakao() {
      Kakao.Auth.loginForm({
        success: res => {
          console.log(JSON.stringify(res));
          console.log(this);
          this.$emit("kakao-login", res);
        },
        fail: function(err) {
          alert(JSON.stringify(err));
        }
      });
    },
    gotoJoin() {
      this.$router.push("/join");
    }
  }
};
</script>

<style lang="css"></style>
