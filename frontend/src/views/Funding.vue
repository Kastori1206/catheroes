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
            <div class="md-layout">
              <!-- start funding button -->
              <button @click="onPayment">펀딩하기</button>
              <!-- end funding button -->
            </div>
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
      default: require("@/assets/img/bg7.jpg")
    },
  },
  data() {
    return {
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
      IMP.init('imp80887593');

      /* 2. 결제 데이터 정의하기 */
      const data = {
        pg: 'html5_inicis',                           // PG사
        pay_method: 'card',                           // 결제수단
        merchant_uid: `mid_${new Date().getTime()}`,   // 주문번호
        amount: 1000,                                 // 결제금액
        name: '아임포트 결제 데이터 분석',                  // 주문명
        buyer_name: '홍길동',                           // 구매자 이름
        buyer_tel: '01012341234',                     // 구매자 전화번호
        buyer_email: 'example@example',               // 구매자 이메일
        buyer_addr: '신사동 661-16',                    // 구매자 주소
        buyer_postcode: '06018',                      // 구매자 우편번호
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
        error_msg,
        // ...
      } = response;

      if (success) {
        alert('결제 성공');
      } else {
        alert(`결제 실패: ${error_msg}`);
      }
    },
  },
  created() {
  }
};
</script>

<style lang="scss" scoped>

</style>