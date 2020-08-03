<template>
  <div class="user" id="login">
    <div class="wrapC table">
      <div class="middle">
        <h1>SS_log</h1>
        <div class="input-wrap">
          <input
            v-model="loginData.email"
            id="email"
            placeholder="이메일을 입력해주세요"
            type="text"
          />
        </div>
        <div class="input-wrap">
          <input
            v-model="loginData.password"
            type="password"
            id="password"
            placeholder="영문, 숫자 혼용 8자 이상"
          />
        </div>
        <button @click="login" class="btn btn--back btn--login">
          로그인 하기
        </button>
        <!-- <button @click="kakao_login" class="btn btn--login">
          <a
            href="https://kauth.kakao.com/oauth/authorize?client_id=a1b5c3fdae5ce5e2ec10b3114bc52aae&redirect_uri=http://localhost:8080/auth/kakao/callback&response_type=code
"
          >
            <img src="@/assets/img/kakao_login_button.png" />
          </a>
        </button> -->
        <KakaoLogin
          api-key="06a0b8cad70afe30f83080c09516c797"
          image="kakao_login_btn_large"
          :on-success="onSuccess"
          :on-failure="onFailure"
        />
        <div class="add-option">
          <div class="wrap">
            <p>아직 회원이 아니신가요?</p>
            <router-link
              v-bind:to="{ name: constants.URL_TYPE.USER.JOIN }"
              class="btn--text"
              >회원가입</router-link
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import "../../assets/css/user.scss";
  import constants from "../../lib/constants";
  import KakaoLogin from "vue-kakao-login";
  import axios from "axios";

  const CLIENT_URL = "http://localhost:3000";
  const SERVER_URL = "http://localhost:8080";
  export default {
    components: {
      KakaoLogin,
    },
    created() {},
    watch: {},
    methods: {
      login() {
        this.$emit("submit-login-data", this.loginData);
      },
      onSuccess(res) {
        console.log(res);
        this.$emit("kakao-login", res);
        // axios
        //   .post("http://localhost:8080/auth/kakao/Login", {
        //     access_token: res.access_token,
        //     expires_in: res.expires_in,
        //     refresh_token: res.refresh_token,
        //     refresh_token_expires_in: res.refresh_token_expires_in,
        //     scope: res.scope,
        //     token_type: res.token_type,
        //   })
        //   .then((res1) => {
        //     console.log(res1);
        //     alert(res1.data.);
        //     this.$cookies.set("auth-token", res1.data.token); //토큰 날라오는거 설정해줘야함!!
        //     this.isLoggedIn = true;
        //     this.$router.push("/");
        //   })
        //   .catch((error) => {
        //     console.log(error);
        //   });
      },
      onFailure(err) {
        console.log(err);
      },
    },
    data: () => {
      return {
        constants,
        loginData: {
          email: "",
          password: "",
        },
        data: "",
        accesstoken: null,
      };
    },
  };
</script>
