<template>
  <div class="wrapper">
    <div class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <!-- 입력 폼 사이즈 조정하는 곳 -->
          <div
            class="md-layout-item md-size-66 md-small-size-100 md-xsmall-size-100 md-medium-size-66 mx-auto"
          >
            <login-card header-color="green">
              <h4 slot="title" class="card-title">길냥이 정보 입력</h4>
              <p slot="description" class="description">무슨말을해야하나</p>

              <md-field class="md-form-group" slot="inputs">
                <md-icon style="z-index:-1;">
                  <i class="fas fa-cat"></i>
                </md-icon>
                <label>고양이 이름</label>
                <md-input v-model="nickname" id="nickname" ref="nickname"></md-input>
              </md-field>

              <md-field class="md-form-group" slot="inputs">
                <md-icon style="z-index:-1;">
                  <i class="fas fa-paw"></i>
                </md-icon>
                <label>길냥이 종류</label>
                <md-input v-model="breed" id="breed" ref="breed"></md-input>
                <span class="md-helper-text">예상 종류를 입력해주세요</span>
              </md-field>

              <md-field slot="inputs">
                <label for>이미지입력</label>
                <md-file accept="image/*" @change="onChangeImages" />
              </md-field>

              <md-field slot="inputs" v-if="imgpreview">
                <img :src="imgpreview" />
              </md-field>

              <div class="md-layout" slot="inputs">
                <div class="md-layout-item md-size-33">
                  <md-button class="md-success md-block" @click="classicModal = true">
                    <md-icon>library_books</md-icon>위치선택
                  </md-button>
                  <!-- 모달창 -->
                  <modal v-if="classicModal" @close="classicModalHide">
                    <template slot="header">
                      <h4 class="modal-title">Modal Title</h4>
                      <!-- <md-button class="md-simple md-just-icon md-round modal-default-button" @click="classicModalHide">
										<md-icon>clear</md-icon>
                      </md-button>-->
                    </template>

                    <template slot="body">
                      <div class="section section-map" style="padding:0px">
                        <div class="container" style="margin: auto;">
                          <Map2 :iscreate="iscreate" @send-data="getdata" @send-dong="getdong"></Map2>
                        </div>
                      </div>
                    </template>

                    <template slot="footer">
                      <md-button class="md-simple">Nice Button</md-button>
                      <md-button class="md-danger md-simple" @click="classicModalHide">Close</md-button>
                    </template>
                  </modal>
                </div>
              </div>
              <md-button @click="checkCat" slot="footer" class="md-simple md-success md-lg">
                <!-- Get Started -->
                길냥이 등록!
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
import { Modal } from "@/components";
import Map2 from "./components/Map2.vue";

export default {
  components: {
    LoginCard,
    Modal,
    Map2
  },
  bodyClass: "login-page",
  data() {
    return {
      // 데이터
      dong: null,
      nickname: null,
      breed: null,
      lat: null,
      lng: null,
      image: null,
      classicModal: false,
      imgpreview: null,
      iscreate: true
    };
  },
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
    getdata(mymarker) {
      this.lng = mymarker.Ga;
      this.lat = mymarker.Ha;
      this.classicModalHide();
      //   console.log(this.lat);
      //   console.log(this.lng);
    },
    getdong(dong) {
      this.dong = dong;
      //   console.log(dong);
    },
    onChangeImages(e) {
      console.log(e.target.files);
      const file = e.target.files[0];
      this.image = file;
      this.imgpreview = URL.createObjectURL(file);
      console.log(this.imgpreview);
    },
    checkCat() {
      let err = true;
      let msg = "";
      err && !this.nickname && ((msg = "이름을 입력해주세요."), (err = false));
      err && !this.breed && ((msg = "품종을 입력해주세요."), (err = false));
      // err && !this.image && ((msg = '비밀번호를 입력해주세요.'), (err = false));
      // err && !this. && ((msg = '비밀번호를 입력해주세요.'), (err = false));

      if (!err) alert(msg);
      else this.createHandler();
    },
    createHandler() {
      const request = new FormData();
      request.append("image", this.image);
      request.append("nickname", this.nickname);
      request.append("lat", this.lat);
      request.append("lng", this.lng);
      request.append("location", this.dong);
      request.append("imgpath", "/");
      request.append("breed", "breed");

      axios
        .post(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "cat/regist",
          request,
          {
            headers: {
              "Content-Type": "multipart/form-data"
            }
          }
        )
        .then(response => {
          console.log(response);
          if (response.data.data === "success") {
            alert("등록이 완료되었습니다.");
            this.$router.push("/");
          } else {
            alert("error");
          }
        })
        .catch(error => {
          this.error = error;
          console.log(error);
        })
        .finally(() => {});
    },
    classicModalHide() {
      this.classicModal = false;
    }
  }
};
</script>

<style lang="css"></style>
