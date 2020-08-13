<template>
  <div class="wrapper">
    <parallax class="section page-header header-filter" :style="headerStyle"></parallax>
    <div class="main main-raised">
      <div class="section profile-content">
        <div class="container">
          <div class="md-layout">
            <div class="md-layout-item md-size-50 mx-auto">
              <div class="profile">
                <div class="avatar">
                  <img
                    :src="memberinfo.image"
                    alt="Circle Image"
                    class="img-raised rounded-circle img-flmid"
                    @click="classicModal = true"
                  />
                </div>
                <!-- 프로필 이미지 수정 모달창 -->
                <modal v-if="classicModal" @close="classicModalHide">
                  <template slot="header">
                    <h4 class="modal-title">이미지 수정</h4>
                    <!-- <md-button class="md-simple md-just-icon md-round modal-default-button" @click="classicModalHide">
                              <md-icon>clear</md-icon>
                    </md-button>-->
                  </template>

                  <template slot="body">
                    <!-- <md-field v-if="imgpreview">
                      <img :src="imgpreview" />
                    </md-field>-->
                    <md-field>
                      <label>Only images</label>
                      <md-file @change="onChangeImages" accept="image/*" />
                    </md-field>
                  </template>

                  <template slot="footer">
                    <md-button @click="changeImage" class="md-simple md-success md-lg">이미지 수정</md-button>
                    <md-button class="md-danger md-simple" @click="classicModalHide">Close</md-button>
                  </template>
                </modal>

                <div class="name">
                  <h3 class="title">닉네임: {{ memberinfo.nickname }}</h3>
                </div>
              </div>
            </div>
          </div>
          <div class="profile-tabs">
            <tabs
              :tab-name="['Follow', 'Post', 'Settings']"
              :tab-icon="['favorite', 'palette', 'camera']"
              plain
              nav-pills-icons
              color-button="success"
            >
              <!-- here you can add your content for tab-content -->
              <template slot="tab-pane-1">
                <div class="md-layout">
                  <!-- start print follow list -->
                    <md-list class="md-follow-list" v-if="follows.length" style="padding:0">
                      <div v-for="(follow, index) in follows" :key="index + '_items'">
                      <md-list-item>
                        <md-avatar>
                          <img style="margin-bottom:0; float:left" :src=follow.image alt="People">
                        </md-avatar>

                        <span style="position:relative" class="md-list-item-text">{{ follow.nickname }}</span>

                        <md-button style="background-color:#4B89DC !important" class="md-icon-button">
                          팔로우
                        </md-button>
                      </md-list-item>
                      <md-divider class="md-inset"></md-divider>
                      </div>
                    </md-list>
                  <!-- end print follow list -->
                </div>
              </template>
              <template slot="tab-pane-2">
                <!-- -->
                <div v-for="(post, index) in posts" :key="index + '_posts'">
                  <div class="md-layout">
                    <md-card style="width: 50vw;">
                      <md-card-header>
                        <md-avatar style="margin-left: 10px;">
                          <img :src="memberinfo.image" style="margin-bottom: 0px;" />
                        </md-avatar>

                        <div class="md-title">{{ post.title }}</div>
                        <div class="md-subhead">{{ memberinfo.nickname }}</div>
                      </md-card-header>

                      <md-card-media>
                        <img :src=post.image />
                      </md-card-media>

                      <md-card-content>{{ post.content }}</md-card-content>
                    </md-card>
                  </div>
                </div>
                <!-- -->
              </template>
              <template slot="tab-pane-3">
                <div class="md-layout">
                  <!-- -->
                  <div class="md-layout-item md-size-55">
                    <md-button class="md-success md-block" @click="userUpdateModal = true">회원정보 수정</md-button>
                    <modal v-if="userUpdateModal" @close="userUpdateModalHide">
                      <template slot="header">
                        <h4 class="modal-title">회원정보 수정</h4>
                        <md-button
                          class="md-simple md-just-icon md-round modal-default-button"
                          @click="userUpdateModalHide"
                        >
                          <md-icon>clear</md-icon>
                        </md-button>
                      </template>

                      <template slot="body">
                        <!-- -->
                        <md-field class="md-form-group" slot="inputs">
                          <md-icon>face</md-icon>
                          <label>Nickname...</label>
                          <md-input
                            v-model="memberinfo.nickname"
                            id="nickName"
                            ref="nickName"
                            type="text"
                          ></md-input>
                        </md-field>
                        <md-field class="md-form-group" slot="inputs">
                          <md-icon>email</md-icon>
                          <label>Email...</label>
                          <md-input
                            v-model="memberinfo.email"
                            id="email"
                            ref="email"
                            type="email"
                            readonly="true"
                            v-on:keyup="verifyEmail"
                          ></md-input>
                          <span :class="{ active: passwordConfirmType === 'text' }">
                            <i id="email_compare" ref="email_compare">일치</i>
                          </span>
                        </md-field>
                        <md-field class="md-form-group" slot="inputs">
                          <md-icon>lock_outline</md-icon>
                          <label>New Password...</label>
                          <md-input
                            v-model="password"
                            id="password"
                            ref="password"
                            :type="passwordType"
                            v-on:keyup="fn_compare_pwd"
                          ></md-input>
                          <span
                            :class="{ active: passwordConfirmType === 'text' }"
                            style="display:None"
                          >
                            <i id="pwd_compare" ref="pwd_compare" class="fas fa-eye">불일치</i>
                          </span>
                        </md-field>
                        <md-field class="md-form-group" slot="inputs">
                          <md-icon>lock_outline</md-icon>
                          <label>New Password check...</label>
                          <md-input
                            v-model="passwordConfirm"
                            id="passwordConfirm"
                            ref="passwordConfirm"
                            :type="passwordConfirmType"
                            v-on:keyup="fn_compare_pwd"
                          ></md-input>
                          <span
                            :class="{ active: passwordConfirmType === 'text' }"
                            style="display:None"
                          >
                            <i class="fas fa-eye"></i>
                          </span>
                        </md-field>
                        <!-- -->
                      </template>

                      <template slot="footer">
                        <md-button class="md-simple" @click="checkHandler">수정</md-button>
                        <md-button class="md-danger md-simple" @click="userUpdateModalHide">닫기</md-button>
                      </template>
                    </modal>
                  </div>
                  <div class="md-layout-item md-size-55">
                    <md-button class="md-success md-block" @click="userDeleteModal = true">회원 탈퇴</md-button>
                    <modal v-if="userDeleteModal" @close="userDeleteModalHide">
                      <template slot="header">
                        <h4 class="modal-title">회원 탈퇴</h4>
                        <md-button
                          class="md-simple md-just-icon md-round modal-default-button"
                          @click="userDeleteModalHide"
                        >
                          <md-icon>clear</md-icon>
                        </md-button>
                      </template>

                      <template slot="body">
                        <p>개인정보의 보호를 위해 "회원탈퇴"를 입력 해 주세요.</p>
                        <md-field>
                          <md-input v-model="deleteConfirm" placeholder></md-input>
                        </md-field>
                      </template>

                      <template slot="footer">
                        <md-button class="md-danger md-simple" @click="checkHandler2">탈퇴하기</md-button>
                      </template>
                    </modal>
                  </div>
                  <!-- -->
                </div>
              </template>
            </tabs>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Pagination } from "@/components";
import { LoginCard } from "@/components";
import { Modal } from "@/components";
import { Tabs } from "@/components";
import axios from "axios";

export default {
  components: {
    Pagination,
    LoginCard,
    Modal,
    Tabs
  },
  bodyClass: "profile-page",
  data() {
    return {
      file: null,
      // imgpreview: null,
      classicModal: false,
      deleteConfirm: null,
      userUpdateModal: false,
      userDeleteModal: false,
      follows: [],
      posts: [],
      tabPane1: [
        { image: require("@/assets/img/examples/studio-1.jpg") },
        { image: require("@/assets/img/examples/studio-2.jpg") },
        { image: require("@/assets/img/examples/studio-4.jpg") },
        { image: require("@/assets/img/examples/studio-5.jpg") }
      ],
      tabPane2: [
        { image: require("@/assets/img/examples/olu-eletu.jpg") },
        { image: require("@/assets/img/examples/clem-onojeghuo.jpg") },
        { image: require("@/assets/img/examples/cynthia-del-rio.jpg") },
        { image: require("@/assets/img/examples/mariya-georgieva.jpg") },
        { image: require("@/assets/img/examples/clem-onojegaw.jpg") }
      ],
      tabPane3: [
        { image: require("@/assets/img/examples/mariya-georgieva.jpg") },
        { image: require("@/assets/img/examples/studio-3.jpg") },
        { image: require("@/assets/img/examples/clem-onojeghuo.jpg") },
        { image: require("@/assets/img/examples/olu-eletu.jpg") },
        { image: require("@/assets/img/examples/studio-1.jpg") }
      ],
      prevPassword: "",
      password: "",
      passwordConfirm: "",
      passwordType: "password",
      passwordConfirmType: "password",

      memberinfo: {
        email: null,
        nickname: null,
        mid: null,
        password: null,
        image: null
      }
    };
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/kitty-2948404_1920.jpg")
    }
    // img: {
    //   type: String,
    //   default: require("@/assets/img/faces/christian.jpg")
    // }
  },
  methods: {
    // 프로필 이미지 바꾸는 곳
    changeImage(e) {
      if (!this.file) {
        alert("파일을 넣어주세영♥");
      } else {
        const request = new FormData();
        request.append("image", this.file);
        request.append("mid", this.memberinfo.mid);

        axios
          .post(
            process.env.VUE_APP_SPRING_API_SERVER_URL + "member/image",
            request,
            {
              headers: {
                "Content-Type": "multipart/form-data"
              }
            }
          )
          .then(res => {
            console.log(res.data)
            this.memberinfo.image = process.env.VUE_APP_IMAGE_SERVER + res.data;
            this.classicModalHide();
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    onChangeImages(e) {
      // console.log(e.target.files);
      this.file = e.target.files[0];

      // this.imgpreview = URL.createObjectURL(file);
      // console.log(this.imgpreview);
    },
    classicModalHide() {
      this.classicModal = false;
      this.file = null;
    },
    checkHandler2() {
      let err = true;
      let msg = "";
      err &&
        !(this.deleteConfirm === "회원탈퇴") &&
        ((msg = "다시 입력해주세요."), (err = false));

      if (!err) alert(msg);
      else this.deleteHandler();
    },
    deleteHandler() {
      axios
        .delete(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "member/" + this.memberinfo.mid)
        .then(res => {
          alert("회원탈퇴가 완료되었습니다.");
          // console.log(res);
          this.$cookies.remove("auth-token");
          this.$router.push("/");
        })
        .catch(error => {
          alert("회원탈퇴 중 오류가 발생하였습니다.");
          this.error = error;
          console.log(error);
        })
        .finally(() => {
          this.userDeleteModalHide();
        });
    },
    checkHandler() {
      let err = true;
      let msg = "";
      err &&
        !this.memberinfo.nickname &&
        ((msg = "닉네임을 입력해주세요."), (err = false));
      err &&
        !this.memberinfo.email &&
        ((msg = "이메일을 입력해주세요."), (err = false));
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

      if (!err) alert(msg);
      else this.updateHandler();
    },
    updateHandler() {
      const request = new FormData();
      request.append("mid", this.memberinfo.mid);
      request.append("email", this.memberinfo.email);
      request.append("nickname", this.memberinfo.nickname);
      request.append("password", this.password);
      axios
        .put(process.env.VUE_APP_SPRING_API_SERVER_URL + "member", request)
        .then(res => {
          // console.log(res);
          this.memberinfo.email = res.data.email;
          this.memberinfo.nickname = res.data.nickName;
          this.memberinfo.mid = res.data.mid;
          this.memberinfo.password = res.data.password;
          this.memberinfo.image = res.data.image;
          alert("수정이 완료되었습니다.");
          this.userUpdateModalHide();
        })
        .catch(error => {
          this.error = error;
          console.log(error);
        })
        .finally(() => {});
    },
    userDeleteModalHide() {
      this.userDeleteModal = false;
    },
    userUpdateModalHide() {
      this.userUpdateModal = false;
    },
    //유저 정보 확인
    retrievememberinfo() {
      // console.log("유저정보받아오라고했다");
      const token = this.$cookies.get("auth-token");
      // console.log(token)
      axios
        .post(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "member/info",
          null,
          {
            headers: { Authorization: `${token}` }
          }
        )
        .then(res => {     
          console.log(res)
          this.memberinfo.email = res.data.email;
          this.memberinfo.nickname = res.data.nickname;
          this.memberinfo.mid = res.data.mid;
          this.memberinfo.password = res.data.password;
          this.memberinfo.image =
          process.env.VUE_APP_IMAGE_SERVER + res.data.image;     
          this.UserFollow();
          this.UserPost();
        })
        .catch(error => {
          console.log(error);
        });
    },
    //해당 유저의 팔로우한 고양이
    UserFollow() {            
      axios
        .get(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "follow/member/"+this.memberinfo.mid,          
        )
        .then(res => {
          console.log(res.data);
          for (var i = 0; i < res.data.length; i++) {           
              res.data[i].cat.image = process.env.VUE_APP_IMAGE_SERVER + res.data[i].cat.image
              this.follows.push(res.data[i].cat);             
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    //유저 포스트 정보
    UserPost() {   
      console.log("포스트")
      axios
        .get(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "article/member/"+this.memberinfo.mid
        )
        .then(res => {
          console.log(res.data);
          for (var i = 0; i < res.data.length; i++) {
            // console.log(res.data[i]);
            res.data[i].image = process.env.VUE_APP_IMAGE_SERVER + res.data[i].image;
            this.posts.push(res.data[i]);
          }
        })
        .catch(error => {
          console.log(error);
        });
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
      var emailVal = this.memberinfo.email;
      var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      var result = document.getElementById("email_compare");
      if (emailVal.match(regExp) != null) {
        result.innerText = "일치";
      } else {
        result.innerText = "불일치";
      }
    }
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`
      };
    }
  },
  created() {
    this.retrievememberinfo();  
  }, 
};
</script>

<style lang="scss" scoped>
.md-card-header {
  margin: 0;
}
.section {
  padding: 0;
}

.profile-tabs::v-deep {
  .md-card-tabs .md-list {
    justify-content: center;
  }

  [class*="tab-pane-"] {
    margin-top: 3.213rem;
    padding-bottom: 50px;

    img {
      margin-bottom: 2.142rem;
    }
  }
}

  .md-follow-list {
    width: 320px;
    max-width: 100%;
    display: inline-block;
    vertical-align: top;
    border: 1px solid rgba(#000, .12);
  }
</style>