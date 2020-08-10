<template>
  <div class="wrapper">
    <parallax class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div class="md-layout-item md-size-50 md-small-size-70 md-xsmall-size-100">
            <h1 class="title">길냥이 펀딩</h1>
            <h4>도움이 필요한 길고양이에게 도움의 손길을 내밀어주세요.</h4>
          </div>
        </div>
      </div>
    </parallax>
    <div class="main main-raised">
      <div class="section">
        <div class="container">
          <div class="features text-center">
            <h2>전체보기</h2>
            <!-- start funding elements -->
            <md-card>
              <md-card-media>
                <img
                  @click="onPayment"
                  src="@/assets/img/funsam1.jpg"
                  style="height:140px"
                  alt="People"
                />
              </md-card-media>
              <md-card-header style="margin:0; padding:0; background-color:#ffffff">
                <div class="md-title" style="font-size:14px">말랑이 중성화수술 비용 펀딩</div>
                <md-progress-bar
                  md-mode="determinate"
                  :md-value="amount"
                  style="width:80%;margin:0 auto"
                ></md-progress-bar>
                <div class="md-subhead">30% 58,300원</div>
              </md-card-header>
            </md-card>
            <md-card>
              <md-card-media @click="onPayment">
                <img
                  @click="onPayment"
                  src="@/assets/img/funsam2.jpg"
                  style="height:140px"
                  alt="People"
                />
              </md-card-media>
              <md-card-header style="margin:0; padding:0; background-color:#ffffff">
                <div class="md-title" style="font-size:14px">김치 중성화수술 비용 펀딩</div>
                <md-progress-bar
                  md-mode="determinate"
                  :md-value="amount2"
                  style="width:80%;margin:0 auto"
                ></md-progress-bar>
                <div class="md-subhead">60% 106,500원</div>
              </md-card-header>
            </md-card>
            <!-- end funding elements -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Media",
  bodyClass: "landing-page",
  props: {
    header: {
      type: String,
      default: require("@/assets/img/cat-2536662_1920.jpg")
    }
  },
  data() {
    return {
      amount: 30,
      amount2: 60,
      items: []
    };
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`
      };
    }
  },
  methods: {
    onPayment() {
      /* 1. 가맹점 식별하기 */
      const { IMP } = window;
      IMP.init("imp80887593");

      /* 2. 결제 데이터 정의하기 */
      const data = {
        pg: "html5_inicis", // PG사
        pay_method: "card", // 결제수단
        merchant_uid: `mid_${new Date().getTime()}`, // 주문번호
        amount: 1000, // 결제금액
        name: "아임포트 결제 데이터 분석", // 주문명
        buyer_name: "홍길동", // 구매자 이름
        buyer_tel: "01012341234", // 구매자 전화번호
        buyer_email: "example@example", // 구매자 이메일
        buyer_addr: "신사동 661-16", // 구매자 주소
        buyer_postcode: "06018" // 구매자 우편번호
        // ...
      };

      /* 4. 결제 창 호출하기 */
      IMP.request_pay(data, this.callback);
    },
    callback(response) {
      /* 3. 콜백 함수 정의하기 */
      const {
        success,
        merchant_uid,
        error_msg
        // ...
      } = response;

      if (success) {
        alert("결제 성공");
      } else {
        alert(`결제 실패: ${error_msg}`);
      }
    }
  },
  created() {}
};
</script>

<style lang="scss" scoped>
.card-expansion {
  height: 480px;
}

.md-card {
  width: 320px;
  margin: 4px;
  display: inline-block;
  vertical-align: top;
}
</style>