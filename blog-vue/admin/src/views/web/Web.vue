<template >

    <iframe scrolling=auto :src="webUrl" width="100%" :height="searchTableHeight" style="padding: 0px" frameborder="0"></iframe>

</template>

<script>
export default {
  created() {
    this.webUrl = window.location.pathname;
    //一定要路径后面加
    this.webUrl = this.webUrl.replace("/web/","");
    //alert(this.webUrl)
  },
  data: function() {
    return {
      webUrl: "",
      aboutContent: "",
      searchTableHeight: 0,
      searchTableWidth: 0
    };
  },
  mounted() {
    window.onresize = () => {
      this.widthHeight(); // 自适应高宽度
    };
    this.$nextTick(function () {
      this.widthHeight();
    });
  },
  methods: {
    widthHeight() {
      this.searchTableHeight = window.innerHeight - 125;
      this.searchTableWidth = window.innerWidth
    },
  },
  watch: {
    '$route': function () {
      // 监听路由变化
      this.webUrl = this.$route.params.url
    }
  }
};
</script>

<style scoped>
.edit-btn {
  float: right;
  margin: 1rem 0;
}
.el-card__body {
    padding: 0px;
}
</style>
