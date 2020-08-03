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
              <h4 slot="title" class="card-title">TEST</h4>
              <p slot="description" class="description">TEST</p>

              <md-field slot="inputs">
                <md-input ref="imageInput" type="file" hidden @change="onChangeImages"></md-input>
              </md-field>

              <md-field slot="inputs" v-if="imgpreview">
                <img :src="imgpreview" />
              </md-field>

              <md-button @click="test_image" slot="footer" class="md-simple md-success md-lg">
                <!-- Get Started -->
                TEST!!!
              </md-button>
            </login-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { LoginCard } from "@/components";

export default {
  components: {
    LoginCard
  },
  data() {
    return {
      image: null,
      imgpreview: null
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
    onChangeImages(e) {
      console.log(e.target.files);
      const file = e.target.files[0];
      this.image = file;
      this.imgpreview = URL.createObjectURL(file);
      console.log(this.imgpreview);
    },
    test_image() {
      const fd = new FormData();
      fd.append("image", this.image);
      axios
        .post("http://127.0.0.1:8000/keras/", fd, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>

<style lang="css"></style>