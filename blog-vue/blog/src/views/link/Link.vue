<template>
  <div>
    <!-- banner -->
    <div class="banner" :style="cover">
      <h1 class="banner-title">休闲角 & 咖啡厅</h1>
    </div>
    <!-- 链接列表 -->
    <v-card class="blog-container">
      <div class="link-title mb-1">
        <v-icon color="blue">mdi-link-variant</v-icon> 推荐链接
      </div>
      <v-row class="link-container">
        <v-col
          class="link-wrapper"
          md="4"
          cols="12"
          v-for="item of friendLinkList"
          :key="item.id"
        >
          <a :href="item.linkAddress" target="_blank">
            <v-avatar size="65" class="link-avatar">
              <img :src="item.linkAvatar" />
            </v-avatar>
            <div style="width:100%;z-index:10;">
              <div class="link-name">{{ item.linkName }}</div>
              <div class="link-intro">{{ item.linkIntro }}</div>
            </div>
          </a>
        </v-col>
      </v-row>

      <blockquote class="mb-10">
        这里是休闲角~可以喝杯茶然后聊聊天
      </blockquote>
      <!-- 评论 -->
      <Comment
        :commentList="commentList"
        :count="count"
        @reloadComment="listComments"
      />
    </v-card>
  </div>
</template>

<script>
import Comment from "../../components/Comment";
export default {
  components: {
    Comment
  },
  created() {
    this.listFriendLink();
    this.listComments();
  },
  data: function() {
    return {
      friendLinkList: [],
      commentList: [],
      count: 0
    };
  },
  methods: {
    listFriendLink() {
      this.axios.get("/api/links").then(({ data }) => {
        this.friendLinkList = data.data;
      });
    },
    listComments() {
      this.axios
        .get("/api/comments", {
          params: { current: 1 }
        })
        .then(({ data }) => {
          this.commentList = data.data.recordList;
          this.count = data.data.count;
        });
    }
  },
  computed: {
    blogInfo() {
      return this.$store.state.blogInfo;
    },
    cover() {
      var cover = "";
      this.$store.state.blogInfo.pageList.forEach(item => {
        if (item.pageLabel == "links") {
          cover = item.pageCover;
        }
      });
      return "background: url(" + cover + ") center center / cover no-repeat";
    }
  }
};
</script>

<style scoped>
blockquote {
  line-height: 2;
  margin: 0;
  font-size: 15px;
  border-left: 0.2rem solid #49b1f5;
  padding: 10px 1rem !important;
  background-color: #ecf7fe;
  border-radius: 4px;
}
.link-title {
  color: #344c67;
  font-size: 21px;
  font-weight: bold;
  line-height: 2;
}
.link-container {
  margin: 10px 10px 0;
}
.link-wrapper {
  position: relative;
  transition: all 0.3s;
  border-radius: 8px;
}
.link-avatar {
  margin-top: 5px;
  margin-left: 10px;
  transition: all 0.5s;
}
@media (max-width: 759px) {
  .link-avatar {
    margin-left: 30px;
  }
}
.link-name {
  text-align: center;
  font-size: 1.25rem;
  font-weight: bold;
  z-index: 1000;
}
.link-intro {
  text-align: center;
  padding: 16px 10px;
  height: 50px;
  font-size: 13px;
  color: #1f2d3d;
  width: 100%;
}
.link-wrapper:hover a {
  color: #fff;
}
.link-wrapper:hover .link-intro {
  color: #fff;
}
.link-wrapper:hover .link-avatar {
  transform: rotate(360deg);
}
.link-wrapper a {
  color: #333;
  text-decoration: none;
  display: flex;
  height: 100%;
  width: 100%;
}
.link-wrapper:hover {
  box-shadow: 0 2px 20px #49b1f5;
}
.link-wrapper:hover:before {
  transform: scale(1);
}
.link-wrapper:before {
  position: absolute;
  border-radius: 8px;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  background: #49b1f5 !important;
  content: "";
  transition-timing-function: ease-out;
  transition-duration: 0.3s;
  transition-property: transform;
  transform: scale(0);
}
</style>
