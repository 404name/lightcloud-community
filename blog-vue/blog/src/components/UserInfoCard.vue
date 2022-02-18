<template>
  <view class="hm-balance-card">
    <view class="box "
          :class="options"
          name="cardColorTwo"
          @tap="onCard">
      <view class="trimStrip"
            id="trimStrip"></view>
      <view class="">
        <slot name="header"></slot>
      </view>
      <view class="">
        <slot name="middle"></slot>
      </view>
      <view class="">
        <slot name="footer"></slot>
      </view>
    </view>
  </view>
</template>
<script>
export default {
  name: 'HmBalanceCard',
  props: {
    options: {
      type: String,
      default: 'cardColorNone'  ,//.cardColorOne | cardColorTwo | cardColorThree | cardColorNone
     }
  },
  data() {
    return {};
  },
  mounted() {
        this.$nextTick(function(){
            let dom = document.getElementById('trimStrip')
            setTimeout(()=>{
                dom.style.width='45px'
                dom.style.left="500px"
            },100)
        })
            // animation: reflectLight .6s ;
            // @keyframes reflectLight {
            //   from {
            //   left: -100px;
            //   width: 20px;
            //   }
            //   to {
            //    width: 45px;
            //    left: 500px;
            //   }
            // }
  },
  methods:{
      onCard(){
          this.$emit("on-card",true)
      }
  }
};
  
</script>
<style lang="less" scoped>
.hm-balance-card {
  padding: 10px 4%;
  .box {
    border-radius: 30upx;
    overflow: hidden;
    position: relative;
    background-size: cover;
    min-height: 176px;
  }
}
.cardColorOne {
  background: linear-gradient(left top, #668282 0%, #ccd6d0 100%);
}
.cardColorTwo {
  background: linear-gradient(left top, #bb8d09 0%, #ffd04c 100%);
}
.cardColorThree {
  background: url('http://www.eleve.cn/houminhua/zs.png');
  background-color: #000;
}
.cardColorNone {
  background: linear-gradient(left top, #c1c1c1 0%, #b7b7b7 100%);
}
.trimStrip {
  content: ' ';
  position: absolute;
  background: #fff;
  top: 0;
  height: 300px;
  width: 20px;
  left: -100px;
  opacity: 0.3;
  transition: all 0.6s ease-out;
  transform: skewX(-30deg);
}
</style>
