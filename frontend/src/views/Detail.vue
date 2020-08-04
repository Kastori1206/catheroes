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
                    :src="catinfo.image"
                    alt="Circle Image"
                    class="img-raised rounded-circle img-fluid"
                  />
                </div>
                <div class="name">
                  <h3 class="title">이름(별명): {{ this.catinfo.nickname }}</h3>
                  <md-button
                    v-if="isFollow"
                    @click="follow()"
                    class="md-just-icon md-simple md-fa-heart"
                  >
                    <i class="fa fa-heart"></i>
                  </md-button>
                  <md-button
                    v-if="!isFollow"
                    @click="follow()"
                    class="md-just-icon md-simple md-fa-heart"
                  >
                    <i class="far fa-heart"></i>
                  </md-button>
                </div>
              </div>
            </div>
          </div>
          <div class="profile-tabs">
            <tabs
              :tab-name="['Detail', 'Post', 'Follower']"
              :tab-icon="['camera', 'palette', 'favorite']"
              plain
              nav-pills-icons
              color-button="success"
            >
              <!-- here you can add your content for tab-content -->
              <template slot="tab-pane-1">
                <div class="description text-center">
                  <h2>나이(추정) : {{ this.catinfo.age }}</h2>
                  <h2>품종(추정) : {{ this.catinfo.breed }}</h2>
                  <h2>사는 지역 : {{ this.catinfo.location }}</h2>
                  <h2>특징 : {{ this.catinfo.attr }}</h2>
                  <h2>근황 : {{ this.catinfo.conditions }}</h2>
                </div>
              </template>
              <template slot="tab-pane-2">
                <div v-for="(post, index) in posts" :key="index + '_posts'">
                  <div class="md-layout">
                    <md-card style="width: 50vw;">
                      <md-card-header>
                        <md-avatar style="margin-left: 10px;">
                          <img src="@/assets/img/faces/avatar.jpg" style="margin-bottom: 0px;" />
                        </md-avatar>

                        <div class="md-title">{{post.title}}</div>
                        <div class="md-subhead">{{writers[index]}}</div>
                      </md-card-header>

                      <md-card-media>
                        <img src="@/assets/img/examples/mariya-georgieva.jpg" />
                      </md-card-media>

                      <md-card-content>{{post.content}}</md-card-content>
                      <!-- 댓글 더 보기 -->
                      <button v-if="!post.isclick" @click="commentTest(index)">댓글 더 보기</button>
                      <button v-if="post.isclick" @click="commentTest(index)">댓글 닫기</button>
                      <div v-if="post.isclick">
                        <div
                          v-for="(comment, index2) in comments[index]"
                          :key="index2 + '_comments'"
                        >
                          <md-card-content>{{comment.comment}} - 글쓴사람 : {{comment.writer}}</md-card-content>
                        </div>
                        <div>
                          <textarea v-model="comment"></textarea>
                          <button
                            @click="saveComment(post.articleid, comment, userinfo.nickname, index)"
                          >등록</button>
                        </div>
                      </div>
                    </md-card>
                  </div>
                  <!--  -->
                  <!-- <md-card  style="width: 80vw;" >
                    <md-card-header>
                      <md-avatar style="margin-left: 10px;">
                        <img src="@/assets/img/faces/avatar.jpg" style="margin-bottom: 0px;"/>
                      </md-avatar>

                      <div class="md-title">Article Title</div>
                      <div class="md-subhead">Writter</div>
                    </md-card-header>

                    <md-card-media>
                      <img src="@/assets/img/examples/mariya-georgieva.jpg"/>
                    </md-card-media>

                    <md-card-content>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Optio itaque ea nostrum.</md-card-content>
                  </md-card>-->
                  <!--  -->
                </div>
              </template>
              <template slot="tab-pane-3">
                <!-- <div class="wrapper"> -->
                <!-- <div class="main main-raised"> -->
                <!-- <div class="section"> -->
                <div class="container">
                  <div class="features text-center">
                    <div class="md-layout">
                      <!--  -->
                      <div v-for="(user, index) in items" :key="index + '_items'">
                        <div class="md-card-new">
                          <md-card-header>
                            <md-card-header-text>
                              <div class="md-title">{{user.nickName}}</div>
                            </md-card-header-text>
                            <div style="width: 85px; height: 50px; margin: 10px;">
                              <img :src="tabPane2[3].image" alt="NewsImage" />
                            </div>
                          </md-card-header>
                        </div>
                      </div>
                      <!--  -->
                    </div>
                  </div>
                </div>
                <!-- </div> -->
                <!-- </div> -->
                <!-- </div> -->
              </template>
            </tabs>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Tabs } from "@/components";
import axios from "axios";

export default {
  components: {
    Tabs
  },
  bodyClass: "profile-page",
  data() {
    return {
      isFollow: false,
      isclick: false,
      isclicks: [],
      items: [],
      posts: [],
      writers: [],
      comments: [],
      tabPane1: [],
      tabPane2: [
        { image: require("@/assets/img/bg.jpg") },
        { image: require("@/assets/img/bg2.jpg") },
        { image: require("@/assets/img/bg3.jpg") },
        { image: require("@/assets/img/bg7.jpg") },
        { image: require("@/assets/img/profile_city.jpg") },
        { image: require("@/assets/img/profile_city.jpg") },
        { image: require("@/assets/img/profile_city.jpg") }
      ],
      tabPane3: [
        { image: require("@/assets/img/examples/mariya-georgieva.jpg") },
        { image: require("@/assets/img/examples/studio-3.jpg") },
        { image: require("@/assets/img/examples/clem-onojeghuo.jpg") },
        { image: require("@/assets/img/examples/olu-eletu.jpg") },
        { image: require("@/assets/img/examples/studio-1.jpg") }
      ],
      userinfo: {
        email: null,
        nickname: null
      },
      catinfo: {
        nickname: null,
        age: null,
        breed: null,
        location: null,
        attr: null,
        food: null,
        hospital: null,
        family: null,
        neutered: null,
        conditions: null,
        image: null,
        lat: null,
        lng: null,
        catid: null
      }
    };
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/profile_city.jpg")
    },
    img: {
      type: String,
      default: require("@/assets/img/vue-mk-header.jpg")
    },
    isLoggedIn: Boolean
  },
  methods: {
    saveComment(articleid, comment, writer, index) {
      axios
        .post(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "article/saveComment/",
          {
            articleid,
            comment,
            writer
          }
        )
        .then(res => {
          if (res.data.data === "success") {
            alert("댓글 등록이 완료되었습니다.");
            this.comments[index].push({ articleid, comment, writer });
          }
        })
        .catch(err => {
          console.log(err);
        })
        .finally(() => {
          this.comment = "";
        });
    },
    retrieveUserInfo() {
      console.log("유저정보받아오라고했다");
      const token = this.$cookies.get("auth-token");
      axios
        .post(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "account/info/",
          null,
          {
            headers: { Authorization: `${token}` }
          }
        )
        .then(res => {
          console.log("user정보 출력");
          console.log(res.data);
          this.userinfo.email = res.data.email;
          this.userinfo.nickname = res.data.nickName;
        })
        .catch(error => {
          console.log(error);
        });
    },
    retrieveCatInfo() {
      // console.log("고양이정보받아오라고했다");
      // const number = 1;
      const catid = this.$route.params.catid;
      axios
        .get(process.env.VUE_APP_SPRING_API_SERVER_URL + "cat/detail/" + catid)
        .then(res => {
          // console.log(res.data);
          this.catinfo.nickname = res.data.nickName;
          this.catinfo.age = res.data.age;
          this.catinfo.breed = res.data.breed;
          this.catinfo.location = res.data.location;
          this.catinfo.attr = res.data.attr;
          this.catinfo.conditions = res.data.conditions;
          this.catinfo.lat = res.data.lat;
          this.catinfo.lng = res.data.lng;
          this.catinfo.catid = this.$route.params.catid;
          this.catinfo.image =
            process.env.VUE_APP_IMAGE_SERVER +
            "static/profile/cat/" +
            catid +
            ".jpg";
        })
        .catch(error => {
          console.log(error);
        });
    },
    CatFollow() {
      // console.log("고양이팔로워받아오라고했다");
      // const number = 1;
      const catid = this.$route.params.catid;
      // console.log(catid);
      const formData = new FormData();
      formData.append("catid", catid);
      axios
        .post(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "follow/findByCatId",
          formData
        )
        .then(res => {
          // console.log(res.data.length);
          for (var i = 0; i < res.data.length; i++) {
            // console.log("follow userid = " + res.data[i].userid);
            const formData = new FormData();
            formData.append("uid", res.data[i].userid);

            axios
              .post(
                process.env.VUE_APP_SPRING_API_SERVER_URL + "account/findByUid",
                formData
              )
              .then(res => {
                // console.log(res.data);
                ////
                this.items.push(res.data);
                ////
              })
              .catch(error => {
                console.log(error);
              });
          }
          // console.log(this.items);
        })
        .catch(error => {
          console.log(error);
        });
    },
    CatPost() {
      // console.log("고양이포스트받아오라고했다");
      const catid = this.$route.params.catid;
      const formData = new FormData();
      formData.append("catid", catid);
      axios
        .post(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "article/findByCatId",
          formData
        )
        .then(res => {
          for (var i = 0; i < res.data.length; i++) {
            console.log("왜안됨?" + res.data[i].userid);
            const formData = new FormData();
            formData.append("uid", res.data[i].userid);

            axios
              .post(
                process.env.VUE_APP_SPRING_API_SERVER_URL + "account/findByUid",
                formData
              )
              .then(res2 => {
                // console.log(res2.data.nickName);
                ////
                this.writers.push(res2.data.nickName);
                ////
              })
              .catch(error => {
                console.log(error);
              });

            formData = new FormData();
            formData.append("articleid", res.data[i].articleid);

            axios
              .post(
                process.env.VUE_APP_SPRING_API_SERVER_URL +
                  "article/findCommentByArticleId",
                formData
              )
              .then(res2 => {
                console.log(res2.data);
                this.comments.push(res2.data);
              })
              .catch(error => {
                console.log(error);
              });
            // console.log("이제 res.data[i]에 isclick변수를 넣을거야");
            res.data[i].isclick = false;
            console.log(res.data[i]);
            this.posts.push(res.data[i]);
          }
        })
        .catch(error => {
          console.log(error);
        });
      console.log(this.comments);
    },
    follow() {
      const token = this.$cookies.get("auth-token");

      axios
        .post(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "account/info/",
          null,
          {
            headers: { Authorization: `${token}` }
          }
        )
        .then(res => {
          console.log(res.data);
          this.userinfo.uid = res.data.uid;

          const uid = this.userinfo.uid;
          const catid = this.$route.params.catid;

          const formData = new FormData();
          formData.append("catid", catid);
          formData.append("userid", uid);

          axios
            .post(
              process.env.VUE_APP_SPRING_API_SERVER_URL + "follow/followCat",
              formData
            )
            .then(res => {
              // console.log(res.data);
              this.isFollow = res.data;
            })
            .catch(error => {
              console.log(error);
            });
        })
        .catch(error => {
          console.log(error);
        });
    },
    follow2() {
      const token = this.$cookies.get("auth-token");

      axios
        .post(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "account/info/",
          null,
          {
            headers: { Authorization: `${token}` }
          }
        )
        .then(res => {
          console.log(res.data);
          this.userinfo.uid = res.data.uid;

          const uid = this.userinfo.uid;
          const catid = this.$route.params.catid;

          const formData = new FormData();
          formData.append("catid", catid);
          formData.append("userid", uid);

          axios
            .post(
              process.env.VUE_APP_SPRING_API_SERVER_URL + "follow/followCheck",
              formData
            )
            .then(res => {
              // console.log(res.data);
              console.log(res.data);
              this.isFollow = res.data;
            })
            .catch(error => {
              console.log(error);
            });
        })
        .catch(error => {
          console.log(error);
        });
    },
    commentTest(index) {
      this.posts[index].isclick
        ? (this.posts[index].isclick = false)
        : (this.posts[index].isclick = true);
    }
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`
      };
    }
  },
  mounted() {
    // this.retrieveCatInfo();
    this.follow2();
  },
  updated() {},
  created() {
    this.retrieveUserInfo();
    this.retrieveCatInfo();
    this.CatFollow();
    this.CatPost();
  }
};
</script>

<style lang="scss" scoped>
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
  .md-card-actions.text-center {
    display: flex;
    justify-content: center !important;
  }
  .contact-form {
    margin-top: 30px;
  }

  .md-has-textarea + .md-layout {
    margin-top: 15px;
  }
}
</style>
