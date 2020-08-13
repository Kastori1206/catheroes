<template>
  <div class="wrapper">
    <div class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div
            class="md-layout-item md-size-33 md-small-size-66 md-xsmall-size-100 md-medium-size-40 mx-auto"
          >
            <login-card header-color="green">
              <h4 slot="title" class="card-title">Join</h4>
              <md-field class="md-form-group" slot="inputs" :class="NmessageClass">
                <md-icon>face</md-icon>
                <label>Nickname...</label>
                <md-input
                  v-model="nickName"
                  id="nickName"
                  ref="nickName"
                  type="text"
                  @blur="isNickname"
                ></md-input>
                <span
                  class="md-helper-text"
                  style="visibility: visible;"
                  v-show="useNickname == '가능'"
                >사용가능한닉네임</span>
                <span class="md-error" style="bottom:-10px">닉네임중복임</span>
              </md-field>
              <md-field class="md-form-group" slot="inputs" :class="EmessageClass">
                <md-icon>email</md-icon>
                <label>Email...</label>
                <md-input v-model="email" id="email" ref="email" type="email" @blur="verifyEmail"></md-input>
                <span
                  class="md-helper-text"
                  style="visibility: visible;"
                  v-show="useEmail == '가능'"
                >사용가능한이메일</span>
                <span class="md-error" style="bottom:-10px">{{ useEmail }}</span>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>Password...</label>
                <md-input
                  v-model="password"
                  id="password"
                  ref="password"
                  :type="passwordType"
                  v-on:keyup="fn_compare_pwd"
                ></md-input>
                <span :class="{active : passwordConfirmType==='text'}" style="display:None">
                  <i id="pwd_compare" ref="pwd_compare" class="fas fa-eye">불일치</i>
                </span>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>Password check...</label>
                <md-input
                  v-model="passwordConfirm"
                  id="passwordConfirm"
                  ref="passwordConfirm"
                  :type="passwordConfirmType"
                  v-on:keyup="fn_compare_pwd"
                ></md-input>
                <span :class="{active : passwordConfirmType==='text'}" style="display:None">
                  <i class="fas fa-eye"></i>
                </span>
              </md-field>
              <md-button
                style="display:block; margin:auto;"
                @click="checkHandler"
                slot="footer"
                class="md-simple md-success md-lg"
              >
                <!-- Get Started -->
                회원가입
              </md-button>
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
import KakaoLogin from "vue-kakao-login";

export default {
  components: {
    LoginCard,
    KakaoLogin
  },
  bodyClass: "login-page",
  data() {
    return {
      email: "",
      nickName: "",
      uid: "",
      password: "",
      passwordConfirm: "",
      isTerm: true,
      passwordType: "password",
      passwordConfirmType: "password",
      useNickname: "",
      useEmail: ""
    };
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/profile_city-1.jpg")
    }
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`
      };
    },
    EmessageClass() {
      if (this.useEmail == "이메일중복" || this.useEmail == "잘못된형식") {
        return {
          "md-invalid": true
        };
      } else {
        return {
          "md-invalid": false
        };
      }
    },
    NmessageClass() {
      if (this.useNickname == "중복") {
        return {
          "md-invalid": true
        };
      } else {
        return {
          "md-invalid": false
        };
      }
    }
  },
  methods: {
    checkHandler() {
      // alert('123');
      let err = true;
      let msg = "";
      err &&
        !this.nickName &&
        ((msg = "닉네임을 입력해주세요.") && !isNickname, (err = false));

      err &&
        !this.email &&
        ((msg = "이메일을 입력해주세요.") && !isEmail, (err = false));
      err &&
        document.getElementById("email_compare").innerHTML == "불일치" &&
        ((msg = "이메일을 확인해주세요."), (err = false));
      err &&
        !this.password &&
        ((msg = "비밀번호를 입력해주세요."), (err = false));
      err &&
        !this.passwordConfirm &&
        ((msg = "비밀번호확인을 입력해주세요."), (err = false));
      // err && (this.password != this.passwordConfirm) && ((msg = '비밀번호를 다시 확인해주세요'), (err = false), this.$refs.password.focus());
      err &&
        !(document.getElementById("pwd_compare").innerHTML == "일치") &&
        ((msg = "비밀번호를 다시 확인해주세요."), (err = false));

      err && !this.isTerm && ((msg = "약관에 동의해주세요."), (err = false));

      if (!err) alert(msg);
      else this.createHandler();
    },
    isNickname() {
      if (this.nickName) {
        const request = new FormData();
        request.append("nickname", this.nickName);
        axios
          .post(
            process.env.VUE_APP_SPRING_API_SERVER_URL + "member/nickname/",
            request
          )
          .then(response => {
            // alert(response.data);
            if (response.data === true) {
              // alert("중복입니다.");
              this.useNickname = "중복";
            } else {
              // alert("사용할수있습니다.");
              this.useNickname = "가능";
            }
          })
          .catch(error => {
            this.error = error;
            console.log(error);
            // this.moveList();
          });
      }
    },
    isEmail() {
      if (this.email) {
        const request = new FormData();
        request.append("email", this.email);
        axios
          .post(
            process.env.VUE_APP_SPRING_API_SERVER_URL + "member/email/",
            request
          )
          .then(response => {
            if (response.data === true) {
              this.useEmail = "이메일중복";
              // alert("이메일 중복입니다.");
            } else {
              this.useEmail = "가능";
              // alert("사용할수있습니다.");
            }
          })
          .catch(error => {
            this.error = error;
            console.log(error);
            // this.moveList();
          });
      }
    },
    createHandler() {
      // alert('123');
      // this.moveList();
      axios
        .post(process.env.VUE_APP_SPRING_API_SERVER_URL + "member/", {
          email: this.email,
          nickname: this.nickName,
          password: this.password
        })
        .then(response => {
          alert("등록이 완료되었습니다.");
          this.moveList();
        })
        .catch(error => {
          this.error = error;
          console.log(error);
          // this.moveList();
        })
        .finally(() => {});
    },
    moveList() {
      // alert('123');
      this.$router.push("/login");
    },
    fn_compare_pwd() {
      // alert('123');
      var pwd1 = this.password;
      var pwd2 = this.passwordConfirm;
      var regExpPwd = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d$@$!%*#?&]{8,}$/i;
      var result = document.getElementById("pwd_compare");

      if (pwd1.match(regExpPwd) != null) {
        if (pwd1 == pwd2) {
          result.innerText = "일치";
        } else {
          result.innerText = "불일치";
        }
      } else {
        result.innerText = "영어/한글 포함 8자 이상";
      }
    },
    verifyEmail() {
      var emailVal = this.email;
      var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      var result = document.getElementById("email_compare");
      if (emailVal.match(regExp) != null) {
        this.isEmail();
      } else {
        this.useEmail = "잘못된형식";
      }
    },
    onSuccess(res) {
      console.log(res);
      this.$emit("kakao-login", res);
    },
    onFailure(err) {
      console.log(err);
    }
  }
};
</script>

<style lang="css">
.md-field .md-error {
  left: unset !important;
}
.md-field .md-count,
.md-field .md-error,
.md-field .md-helper-text {
  right: 0px;
}
</style>
