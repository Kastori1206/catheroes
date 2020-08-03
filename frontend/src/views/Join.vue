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
             
              <p slot="description" class="description">Or Be Classical</p>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>face</md-icon>
                <label>Nickname...</label>
                <md-input v-model="nickName"
                            id="nickName"
                            ref="nickName"
                            
                            type="text"></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>email</md-icon>
                <label>Email...</label>
                <md-input v-model="email" 
                            id="email"
                            ref="email"
                            
                            type="email"
                            v-on:keyup="verifyEmail"></md-input>
                            <span :class="{active : passwordConfirmType==='text'}">
                                <i id="email_compare" ref="email_compare">불일치</i> 
                            </span>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>Password...</label>
                <md-input v-model="password"
                            id="password"
                            ref="password" 
                            :type="passwordType"
                            
                            v-on:keyup="fn_compare_pwd"></md-input>
                            <span :class="{active : passwordConfirmType==='text'}" style="display:None">
                                <i id="pwd_compare" ref="pwd_compare" class="fas fa-eye">불일치</i> 
                            </span>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>Password check...</label>
                <md-input v-model="passwordConfirm" 
                            id="passwordConfirm"
                            ref="passwordConfirm"
                            :type="passwordConfirmType"
                            
                            v-on:keyup="fn_compare_pwd"></md-input>
                            <span :class="{active : passwordConfirmType==='text'}" style="display:None">
                            <i class="fas fa-eye"></i> 
                        </span>
              </md-field>
              <md-button @click="checkHandler" slot="footer" class="md-simple md-success md-lg">
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
import axios from 'axios';
import KakaoLogin from "vue-kakao-login";

export default {
  components: {
    LoginCard,
    KakaoLogin,
  },
  bodyClass: "login-page",
  data() {
    return {
        email: '',
        nickName: '',
        uid: '',
        password: '',
        passwordConfirm: '',
        isTerm: true,
        passwordType:"password",
        passwordConfirmType:"password",
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
    }
  },
  methods: {
    checkHandler() {
        // alert('123');
        let err = true;
        let msg = '';
        err && !this.nickName && ((msg = '닉네임을 입력해주세요.'), (err = false));
        err && !this.email && ((msg = '이메일을 입력해주세요.'), (err = false));
        err && (document.getElementById('email_compare').innerHTML =='불일치') && ((msg = '이메일을 확인해주세요.'), (err = false));
        err && !this.password && ((msg = '비밀번호를 입력해주세요.'), (err = false));
        err && !this.passwordConfirm && ((msg = '비밀번호확인을 입력해주세요.'), (err = false));
        // err && (this.password != this.passwordConfirm) && ((msg = '비밀번호를 다시 확인해주세요'), (err = false), this.$refs.password.focus());
        err && !(document.getElementById('pwd_compare').innerHTML =='일치') && ((msg = '비밀번호를 다시 확인해주세요.'), (err = false));

        err && !this.isTerm && ((msg = '약관에 동의해주세요.'), (err = false));



        if(!err) alert(msg);
        else this.createHandler();
    },
    createHandler() {
        // alert('123');
        // this.moveList();
        axios
            .post('http://localhost:8080/account/signup', {
                email: this.email,
                nickname: this.nickName,
                password: this.password,
            })
            .then((response) => {
                console.log(response);
                if(response.data.data === 'success') {
                    alert('등록이 완료되었습니다.');
                    this.moveList();
                } else {
                    if(response.data.data ==='email'){
                        alert('email이 중복입니다.');
                    }else if(response.data.data ==='nickname'){
                        alert('nickname이 중복입니다.');
                    }
                }
                
            })
            .catch((error) => {
                this.error = error;
                console.log(error);
                // this.moveList();
            })
            .finally(() => {});
    },
    moveList() {
        // alert('123');
        this.$router.push('/login');
    },
    fn_compare_pwd() {
        // alert('123');
        var pwd1 = this.password;
        var pwd2 = this.passwordConfirm;
        var regExpPwd = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d$@$!%*#?&]{8,}$/i;
        var result = document.getElementById('pwd_compare');
        
        if (pwd1.match(regExpPwd) != null) {
            if(pwd1 == pwd2) {
                result.innerText = '일치';
            } else {
                result.innerText = '불일치';      
            }
        } else {
            result.innerText = '영어/한글 포함 8자 이상';
        }

    },
    verifyEmail() {
        var emailVal = this.email;
        var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        var result = document.getElementById('email_compare');
        if (emailVal.match(regExp) != null) {
            result.innerText = '일치';
        } else {
            result.innerText = '불일치';
        }
    },
    onSuccess(res) {
        console.log(res);
        this.$emit("kakao-login", res);
    },
    onFailure(err) {
      console.log(err);
    },
  }
};
</script>

<style lang="css"></style>
