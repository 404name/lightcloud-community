<template>
  <div>
    <!-- 封面图 -->
    <div class="banner" :style="articleCover">
      <div class="article-info-container">
        <!-- 卡片标题 -->
        <div class="article-title">{{ article.articleTitle }}</div>
        <div class="article-info">
          <div class="first-line">
            <!-- 发表时间 -->
            <span>
              <i class="iconfont iconrili" />
              发表于 {{ article.createTime | date }}
            </span>
            <span class="separator">|</span>
            <!-- 发表时间 -->
            <span>
              <i class="iconfont icongengxinshijian" />
              更新于
              <template v-if="article.updateTime">
                {{ article.updateTime | date }}
              </template>
              <template v-else>
                {{ article.createTime | date }}
              </template>
            </span>
            <span class="separator">|</span>
            <!-- 卡片分类 -->
            <span class="article-category">
              <i class="iconfont iconfenlei1" />
              <router-link :to="'/categories/' + article.categoryId">
                {{ article.categoryName }}
              </router-link>
            </span>
          </div>
          <div class="second-line">
            <!-- 字数统计 -->
            <span>
              <i class="iconfont iconzishu" />
              字数统计: {{ wordNum | num }}
            </span>
            <span class="separator">|</span>
            <!-- 阅读时长 -->
            <span>
              <i class="iconfont iconshijian" />
              阅读时长: {{ readTime }}
            </span>
          </div>
          <div class="third-line">
            <span class="separator">|</span>
            <!-- 阅读量 -->
            <span>
              <i class="iconfont iconliulan" /> 阅读量: {{ article.viewsCount }}
            </span>
            <span class="separator">|</span>
            <!-- 评论量 -->
            <span>
              <i class="iconfont iconpinglunzu1" />评论数:
              <template v-if="count">{{ count }}</template>
              <template v-else>0</template>
            </span>
          </div>
        </div>
      </div>
    </div>
    <!-- 内容 -->
    <v-row class="article-container">
      <v-col md="9" cols="12">
        <v-card style = "padding: 10px">
          <!-- 图云和文章的介绍放前面 -->
          <article v-if="article.type == 1 || article.type == 3"
            id="write"
            class="article-content markdown-body"
            v-html="article.articleContent"
            ref="article"
          />
          <!-- 版权声明 -->
          <div class="aritcle-copyright msg-card-style">
            <div>
              <span>卡片内容：</span>
              {{ article.articleTitle }}
            </div>
            <div class="tag-container">
                <span>卡片信息：</span>
              <a :href="articleHref" target="_blank">{{ articleHref }} </a>
               <router-link to="/">
                  {{ blogInfo.websiteConfig.websiteAuthor }}
                </router-link>
              <router-link
                :to="article.originalUrl"
              >
                <span v-if="article.type == 1">动态</span>
                <span v-else-if="article.type == 2">转载</span>
                <span v-else-if="article.type == 3">图云</span>
                <span v-else>购物</span>
                
              </router-link>
            </div>
            <div>
            </div>
            <!-- 转载 -->
            <div v-if="article.type == 2">
              <iframe :src="article.originalUrl" frameborder="0" style="width:100%;height: 450px;" id="iframeBox"></iframe>
            </div>
            <!-- 图云 -->
            <div v-if="article.type == 3">
              <!-- 相册列表 -->
              <v-card class="">
                <div class="photo-wrap">
                  <img
                    v-for="(item, index) of photoList"
                    class="photo"
                    :key="index"
                    :src="item"
                    @click="preview(index)"
                  />
                </div>
                <!-- 无限加载 -->
                <infinite-loading @infinite="infiniteHandler">
                  <div slot="no-more" />
                  <div slot="no-results" />
                </infinite-loading>
              </v-card>
            </div>
            <!-- 购物 -->
            <div v-if="article.type == 4">
              <v-card
                class="animated zoomIn article-card"
                style="border-radius: 12px 8px 8px 12px"
              >
                <!-- 卡片封面图 -->
                <div :class="isRight(article.id)">
                  <router-link :to="'/articles/' + article.id">
                    <v-img
                      class="on-hover"
                      width="100%"
                      height="100%"
                      :src="article.articleCover"
                    />
                  </router-link>
                </div>
                <!-- 卡片信息 -->
                <div class="article-wrapper">
                  <H1> <v-icon size="14">mdi-calendar-month-outline</v-icon>
                    {{ article.createTime | date }}</H1>
                  <div style="line-height:1.4">
                    <!-- 是否置顶 -->
                    <span v-if="article.isTop == 1">
                      <span style="color:#ff7242">
                        <i class="iconfont iconzhiding" /> 推荐
                      </span>
                      <span class="separator">|</span>
                    </span>
                    <router-link :to="'/articles/' + article.id">
                      {{ article.articleTitle }}
                    </router-link>
                  </div>
                  <div class="article-info">
                    
                    <!-- 发表时间 -->
                    <v-icon size="14">mdi-calendar-month-outline</v-icon>
                    {{ article.createTime | date }}
                    <span class="separator">|</span>
                    <!-- 卡片分类 -->
                    <router-link :to="'/categories/' + article.categoryId">
                      <v-icon size="14">mdi-inbox-full</v-icon>
                      {{ article.categoryName }}
                    </router-link>
                    <span class="separator">|</span>
                    <!-- 卡片标签 -->
                    <router-link
                      style="display:inline-block"
                      :to="'/tags/' + tag.id"
                      class="mr-1"
                      v-for="tag of article.tagDTOList"
                      :key="tag.id"
                    >
                      <v-icon size="14">mdi-tag-multiple</v-icon>{{ tag.tagName }}
                    </router-link>
                  </div>

                  <a class="collection-btn"
                      @click="tip = true">
                    <v-icon color="#fff"
                            size="18"
                            class="mr-1">mdi-bookmark</v-icon>
                    <span style="color: #fff">线上咨询 - 0￥</span>
                  </a>
                   <a class="collection-btn"
                      @click="tip = true">
                    <v-icon color="#fff"
                            size="18"
                            class="mr-1">mdi-bookmark</v-icon>
                    <span style="color: #fff">线上指导 - 10￥</span>
                  </a>
                   <a class="collection-btn"
                      @click="tip = true">
                    <v-icon color="#fff"
                            size="18"
                            class="mr-1">mdi-bookmark</v-icon>
                    <span style="color: #fff">线下帮忙 - 30￥</span>
                  </a>
                </div>
                
              </v-card>
              <span>购后评价：</span>
               <router-link :to="'/user/' + msg.id"
                              class="tag-btn"
                              v-for="msg of this.feedback"
                              :key="msg.id">
                  <span style="color:#fff">{{ msg.content }}</span>
                </router-link>
                <router-link :to="'/user/' + msg.id"
                              class="tag-btn"
                              v-for="msg of this.feedback"
                              :key="msg.id">
                  <span style="color:#fff">{{ msg.content }}</span>
                </router-link>
                <router-link :to="'/user/'"
                              class="tag-btn"
                              >
                  <span style="color:#fff"> + 追加评论</span>
                </router-link>
            </div>
            <div>
              <span>版权声明：</span>轻云社区所有卡片除特别声明外，均采用
              <a
                href="https://creativecommons.org/licenses/by-nc-sa/4.0/"
                target="_blank"
              >
                CC BY-NC-SA 4.0
              </a>
              许可协议。转载请注明卡片出处。
            </div>
          </div>
           <!-- 转载和购物的介绍放后面 -->
          <article v-if="article.type == 2 || article.type == 4"
            id="write"
            class="article-content markdown-body"
            v-html="article.articleContent"
            ref="article"
          />
          <!-- 转发 -->
          <div class="article-operation">
            <div class="tag-container">
              <router-link
                v-for="item of article.tagDTOList"
                :key="item.id"
                :to="'/tags/' + item.id"
              >
                {{ item.tagName }}
              </router-link>
            </div>
            <share style="margin-left:auto" :config="config" />
          </div>
          <!-- 点赞打赏等 -->
          <div class="article-reward">
            <!-- 点赞按钮 -->
            <a :class="isLike" @click="like">
              <v-icon size="14" color="#fff">mdi-thumb-up</v-icon> 点赞
              <span v-show="article.likeCount > 0">{{
                article.likeCount
              }}</span>
            </a>
            <a class="reward-btn" v-if="blogInfo.websiteConfig.isReward == 1">
              <!-- 打赏按钮 -->
              <i class="iconfont iconerweima" /> 打赏
              <!-- 二维码 -->
              <div class="animated fadeInDown reward-main">
                <ul class="reward-all">
                  <li class="reward-item">
                    <img
                      class="reward-img"
                      :src="blogInfo.websiteConfig.weiXinQRCode"
                    />
                    <div class="reward-desc">微信</div>
                  </li>
                  <li class="reward-item">
                    <img
                      class="reward-img"
                      :src="blogInfo.websiteConfig.alipayQRCode"
                    />
                    <div class="reward-desc">支付宝</div>
                  </li>
                </ul>
              </div>
            </a>
          </div>
          <div class="pagination-post">
            <!-- 上一篇 -->
            <div
              :class="isFull(article.lastArticle.id)"
              v-if="article.lastArticle.id"
            >
              <router-link :to="'/articles/' + article.lastArticle.id">
                <img
                  class="post-cover"
                  :src="article.lastArticle.articleCover"
                />
                <div class="post-info">
                  <div class="label">上一篇</div>
                  <div class="post-title">
                    {{ article.lastArticle.articleTitle }}
                  </div>
                </div>
              </router-link>
            </div>
            <!-- 下一篇 -->
            <div
              :class="isFull(article.nextArticle.id)"
              v-if="article.nextArticle.id"
            >
              <router-link :to="'/articles/' + article.nextArticle.id">
                <img
                  class="post-cover"
                  :src="article.nextArticle.articleCover"
                />
                <div class="post-info" style="text-align: right">
                  <div class="label">下一篇</div>
                  <div class="post-title">
                    {{ article.nextArticle.articleTitle }}
                  </div>
                </div>
              </router-link>
            </div>
          </div>
          <!-- 推荐卡片 -->
          <div
            class="recommend-container"
            v-if="article.recommendArticleList.length"
          >
            <div class="recommend-title">
              <v-icon size="20" color="#4c4948">mdi-thumb-up</v-icon> 相关推荐
            </div>
            <div class="recommend-list">
              <div
                class="recommend-item"
                v-for="item of article.recommendArticleList"
                :key="item.id"
              >
                <router-link :to="'/articles/' + item.id">
                  <img class="recommend-cover" :src="item.articleCover" />
                  <div class="recommend-info">
                    <div class="recommend-date">
                      <i class="iconfont iconrili" />
                      {{ item.createTime | date }}
                    </div>
                    <div>{{ item.articleTitle }}</div>
                  </div>
                </router-link>
              </div>
            </div>
          </div>
          <!-- 分割线 -->
          <hr />
          <!-- 评论 -->
          <comment
            :commentList="commentList"
            :count="count"
            @reloadComment="listComment"
          />
        </v-card>
      </v-col>
      <!-- 侧边功能 -->
      <v-col md="3" cols="12" class="d-md-block d-none">
        <div style="position: sticky;top: 20px;">

          <!-- 卡片目录 -->
          <v-card class="right-container">
            <div class="right-title">
              <i class="iconfont iconhanbao" style="font-size:16.8px" />
              <span style="margin-left:10px">目录</span>
            </div>
            <div id="toc" />
          </v-card>
          <!-- 回复卡片 -->
          <v-card class="right-container" style="margin-top:10px;max-height:500px;overflow: scroll">
            <comment
              :commentList="commentList"
              :count="count"
              @reloadComment="listComment"
            />
          </v-card>
          <!-- 最新卡片 -->
          <v-card class="right-container" style="margin-top:20px">
            <div class="right-title">
              <i class="iconfont icongengxinshijian" style="font-size:16.8px" />
              <span style="margin-left:10px">最新卡片</span>
            </div>
            <div class="article-list">
              <div
                class="article-item"
                v-for="item of article.newestArticleList"
                :key="item.id"
              >
                <router-link :to="'/articles/' + item.id" class="content-cover">
                  <img :src="item.articleCover" />
                </router-link>
                <div class="content">
                  <div class="content-title">
                    <router-link :to="'/articles/' + item.id">
                      {{ item.articleTitle }}
                    </router-link>
                  </div>
                  <div class="content-time">{{ item.createTime | date }}</div>
                </div>
              </div>
            </div>
          </v-card>
        </div>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import Clipboard from "clipboard";
import Comment from "../../components/Comment";
import tocbot from "tocbot";
export default {
  components: {
    Comment
  },
  created() {
    this.getArticle();
    this.listComment();
  },
  destroyed() {
    this.clipboard.destroy();
    tocbot.destroy();
  },
  data: function() {
    return {
      feedback: [
        {
          id: 1,
          content: "内容不错"
        },
        {
          id: 2,
          content: "收获很大，非常感谢"
        },
        {
          id: 3,
          content: "好评~~太棒啦"
        },
        {
          id: 4,
          content: "我觉得不错"
        },
        {
          id: 5,
          content: "伟大伟大大碗大碗大碗大碗大碗"
        },
      ],
      config: {
        sites: ["qzone", "wechat", "weibo", "qq"]
      },
      imgList: [],
      article: {
        articleTitle: "正在加载中~",
        nextArticle: {
          id: 0,
          articleCover: ""
        },
        lastArticle: {
          id: 0,
          articleCover: ""
        },
        recommendArticleList: [],
        newestArticleList: []
      },
      commentList: [],
      count: 0,
      wordNum: "",
      readTime: "",
      articleHref: window.location.href,
      clipboard: null,
      //相册相关
      photoAlbumName: "",
      photoAlbumCover: "",
      photoList: [],
      current: 1,
      size: 10,
      //购物相关
    };
  },
  methods: {
    getArticle() {
      const that = this;
      //查询卡片
      this.axios.get("/api" + this.$route.path).then(({ data }) => {
        document.title = data.data.articleTitle;
        //将markdown转换为Html
        var temp = data.data;
        this.markdownToHtml(temp);
        this.$nextTick(() => {
          // 统计卡片字数
          this.wordNum = this.deleteHTMLTag(this.article.articleContent).length;
          // 计算阅读时间
          this.readTime = Math.round(this.wordNum / 400) + "分钟";
          // 添加代码复制功能
          this.clipboard = new Clipboard(".copy-btn");
          this.clipboard.on("success", () => {
            this.$toast({ type: "success", message: "复制成功" });
          });
          // 添加卡片生成目录功能
          let nodes = this.$refs.article.children;
          if (nodes.length) {
            for (let i = 0; i < nodes.length; i++) {
              let node = nodes[i];
              let reg = /^H[1-4]{1}$/;
              if (reg.exec(node.tagName)) {
                node.id = i;
              }
            }
          }
          tocbot.init({
            tocSelector: "#toc", //要把目录添加元素位置，支持选择器
            contentSelector: ".article-content", //获取html的元素
            headingSelector: "h1, h2, h3", //要显示的id的目录
            hasInnerContainers: true,
            onClick: function(e) {
              e.preventDefault();
            }
          });
          // 添加图片预览功能
          const imgList = this.$refs.article.getElementsByTagName("img");
          for (var i = 0; i < imgList.length; i++) {
            this.imgList.push(imgList[i].src);
            imgList[i].addEventListener("click", function(e) {
              that.previewImg(e.target.currentSrc);
            });
          }
        });
      });
    },
    listComment() {
      const path = this.$route.path;
      const arr = path.split("/");
      const articleId = arr[arr.length - 1];
      this.axios
        .get("/api/comments", {
          params: { current: 1, articleId: articleId }
        })
        .then(({ data }) => {
          this.commentList = data.data.recordList;
          this.count = data.data.count;
        });
    },
    like() {
      // 判断登录
      if (!this.$store.state.userId) {
        this.$store.state.loginFlag = true;
        return false;
      }
      //发送请求
      this.axios
        .post("/api/articles/" + this.article.id + "/like")
        .then(({ data }) => {
          if (data.flag) {
            //判断是否点赞
            if (
              this.$store.state.articleLikeSet.indexOf(this.article.id) != -1
            ) {
              this.$set(this.article, "likeCount", this.article.likeCount - 1);
            } else {
              this.$set(this.article, "likeCount", this.article.likeCount + 1);
            }
            this.$store.commit("articleLike", this.article.id);
          }
        });
    },
    markdownToHtml(article) {
      const MarkdownIt = require("markdown-it");
      const hljs = require("highlight.js");
      const md = new MarkdownIt({
        html: true,
        linkify: true,
        typographer: true,
        highlight: function(str, lang) {
          // 当前时间加随机数生成唯一的id标识
          var d = new Date().getTime();
          if (
            window.performance &&
            typeof window.performance.now === "function"
          ) {
            d += performance.now();
          }
          const codeIndex = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(
            /[xy]/g,
            function(c) {
              var r = (d + Math.random() * 16) % 16 | 0;
              d = Math.floor(d / 16);
              return (c == "x" ? r : (r & 0x3) | 0x8).toString(16);
            }
          );
          // 复制功能主要使用的是 clipboard.js
          let html = `<button class="copy-btn iconfont iconfuzhi" type="button" data-clipboard-action="copy" data-clipboard-target="#copy${codeIndex}"></button>`;
          const linesLength = str.split(/\n/).length - 1;
          // 生成行号
          let linesNum = '<span aria-hidden="true" class="line-numbers-rows">';
          for (let index = 0; index < linesLength; index++) {
            linesNum = linesNum + "<span></span>";
          }
          linesNum += "</span>";
          if (lang && hljs.getLanguage(lang)) {
            // highlight.js 高亮代码
            const preCode = hljs.highlight(lang, str, true).value;
            html = html + preCode;
            if (linesLength) {
              html += '<b class="name">' + lang + "</b>";
            }
            // 将代码包裹在 textarea 中，由于防止textarea渲染出现问题，这里将 "<" 用 "<" 代替，不影响复制功能
            return `<pre class="hljs"><code>${html}</code>${linesNum}</pre><textarea style="position: absolute;top: -9999px;left: -9999px;z-index: -9999;" id="copy${codeIndex}">${str.replace(
              /<\/textarea>/g,
              "</textarea>"
            )}</textarea>`;
          }
        }
      });
      // 将markdown替换为html标签
      if(article.type == 1)
      article.articleContent = md.render(article.articleContent + "\n# ⭐ 卡片详细");
      else article.articleContent = md.render(article.articleContent);
      this.article = article;
    },
    previewImg(img) {
      this.$imagePreview({
        images: this.imgList,
        index: this.imgList.indexOf(img)
      });
    },
    deleteHTMLTag(content) {
      return content
        .replace(/<\/?[^>]*>/g, "")
        .replace(/[|]*\n/, "")
        .replace(/&npsp;/gi, "");
    },
    //相册相关
    preview(index) {
      this.$imagePreview({
        images: this.photoList,
        index: index
      });
    },
    infiniteHandler($state) {
      if(this.article.type != 3) return;
      this.axios
        .get("/api/albums/" + parseInt(this.article.originalUrl) + "/photos", {
          params: {
            current: this.current,
            size: this.size
          }
        })
        .then(({ data }) => {
          this.photoAlbumCover = data.data.photoAlbumCover;
          this.photoAlbumName = data.data.photoAlbumName;
          if (data.data.photoList.length) {
            this.current++;
            this.photoList.push(...data.data.photoList);
            $state.loaded();
          } else {
            $state.complete();
          }
        });
    }
    //购物相关

  },
  computed: {
    isRight() {
      return function(index) {
        if (index % 2 == 0) {
          return "article-cover left-radius";
        }
        return "article-cover right-radius";
      };
    },
    blogInfo() {
      return this.$store.state.blogInfo;
    },
    articleCover() {
      return (
        "background: url(" +
        this.article.articleCover +
        ") center center / cover no-repeat"
      );
    },
    isLike() {
      var articleLikeSet = this.$store.state.articleLikeSet;
      return articleLikeSet.indexOf(this.article.id) != -1
        ? "like-btn-active"
        : "like-btn";
    },
    isFull() {
      return function(id) {
        return id ? "post full" : "post";
      };
    },
    //相册相关
    cover() {
      return (
        "background: url(" +
        this.photoAlbumCover +
        ") center center / cover no-repeat"
      );
    }
  }
};
</script>

<style scoped>
.banner:before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}
.article-info i {
  font-size: 14px;
}
.article-info {
  font-size: 14px;
  line-height: 1.9;
  display: inline-block;
}
@media (min-width: 760px) {
  .banner {
    color: #eee !important;
  }
  .article-info span {
    font-size: 95%;
  }
  .article-info-container {
    position: absolute;
    bottom: 6.25rem;
    padding: 0 8%;
    width: 100%;
    text-align: center;
  }
  .second-line,
  .third-line {
    display: inline;
  }
  .article-title {
    font-size: 35px;
    margin: 20px 0 8px;
  }
  .pagination-post {
    display: flex;
  }
  .post {
    width: 50%;
  }
  .recommend-item {
    position: relative;
    display: inline-block;
    overflow: hidden;
    margin: 3px;
    width: calc(33.333% - 6px);
    height: 200px;
    background: #000;
    vertical-align: bottom;
  }
}
@media (max-width: 759px) {
  .banner {
    color: #eee !important;
    height: 360px;
  }
  .article-info span {
    font-size: 90%;
  }
  .separator:first-child {
    display: none;
  }
  .blog-container {
    margin: 0px 0px 0 0px;
  }
  .article-info-container {
    position: absolute;
    bottom: 1.3rem;
    padding: 0 5%;
    width: 100%;
    color: #eee;
    text-align: left;
  }
  .article-title {
    font-size: 1.5rem;
    margin-bottom: 0.4rem;
  }
  .post {
    width: 100%;
  }
  .pagination-post {
    display: block;
  }
  .recommend-item {
    position: relative;
    display: inline-block;
    overflow: hidden;
    margin: 3px;
    width: calc(100% - 4px);
    height: 150px;
    margin: 2px;
    background: #000;
    vertical-align: bottom;
  }
}
.article-content {
  word-break: break-word;
  font-size: 14px;
  line-height: 2;
}
.article-operation {
  display: flex;
  align-items: center;
}
.article-category a {
  color: #fff !important;
}
.tag-container a {
  display: inline-block;
  margin: 0.5rem 0.5rem 0.5rem 0;
  padding: 0 0.75rem;
  width: fit-content;
  border: 1px solid #49b1f5;
  border-radius: 1rem;
  color: #49b1f5 !important;
  font-size: 12px;
  line-height: 2;
}
.tag-container a:hover {
  color: #fff !important;
  background: #49b1f5;
  transition: all 0.5s;
}
.aritcle-copyright {
  color:#1e1e1e;
  position: relative;
  margin-top: 0px;
  margin-bottom: 10px;
  font-size: 0.875rem;
  line-height: 2;
  padding: 0.625rem 1rem;
  border: 1px solid #eee;
}
.aritcle-copyright span {
  color: #49b1f5;
  font-weight: bold;
}
.aritcle-copyright a {
    margin-top: 8px;
  text-decoration: underline !important;
  color: #99a9bf !important;
}
.aritcle-copyright:before {
  position: absolute;
  top: 0.7rem;
  right: 0.7rem;
  width: 1rem;
  height: 1rem;
  border-radius: 1rem;
  background: #49b1f5;
  content: "";
}
.aritcle-copyright:after {
  position: absolute;
  top: 0.95rem;
  right: 0.95rem;
  width: 0.5rem;
  height: 0.5rem;
  border-radius: 0.5em;
  background: #fff;
  content: "";
}
.article-reward {
  margin-top: 2rem;
  display: flex;
  justify-content: center;
  align-items: center;
}
.reward-btn {
  position: relative;
  display: inline-block;
  width: 100px;
  background: #49b1f5;
  margin: 0 1rem;
  color: #fff !important;
  text-align: center;
  line-height: 36px;
  font-size: 0.875rem;
}
.reward-btn:hover .reward-main {
  display: block;
}
.reward-main {
  display: none;
  position: absolute;
  bottom: 40px;
  left: 0;
  margin: 0;
  padding: 0 0 15px;
  width: 100%;
}
.reward-all {
  display: inline-block;
  margin: 0 0 0 -110px;
  padding: 20px 10px 8px !important;
  width: 320px;
  border-radius: 4px;
  background: #f5f5f5;
}
.reward-all:before {
  position: absolute;
  bottom: -10px;
  left: 0;
  width: 100%;
  height: 20px;
  content: "";
}
.reward-all:after {
  content: "";
  position: absolute;
  right: 0;
  bottom: 2px;
  left: 0;
  margin: 0 auto;
  width: 0;
  height: 0;
  border-top: 13px solid #f5f5f5;
  border-right: 13px solid transparent;
  border-left: 13px solid transparent;
}
.reward-item {
  display: inline-block;
  padding: 0 8px;
  list-style-type: none;
}
.reward-img {
  width: 130px;
  height: 130px;
  display: block;
}
.reward-desc {
  margin: -5px 0;
  color: #858585;
  text-align: center;
}
.like-btn {
  display: inline-block;
  width: 100px;
  background: #969696;
  color: #fff !important;
  text-align: center;
  line-height: 36px;
  font-size: 0.875rem;
}
.like-btn-active {
  display: inline-block;
  width: 100px;
  background: #ec7259;
  color: #fff !important;
  text-align: center;
  line-height: 36px;
  font-size: 0.875rem;
}
.pagination-post {
  margin-top: 40px;
  overflow: hidden;
  width: 100%;
  background: #000;
}
.post {
  position: relative;
  height: 150px;
  overflow: hidden;
}
.post-info {
  position: absolute;
  top: 50%;
  padding: 20px 40px;
  width: 100%;
  transform: translate(0, -50%);
  line-height: 2;
  font-size: 14px;
}
.post-cover {
  position: absolute;
  width: 100%;
  height: 100%;
  opacity: 0.4;
  transition: all 0.6s;
  object-fit: cover;
}
.post a {
  position: relative;
  display: block;
  overflow: hidden;
  height: 150px;
}
.post:hover .post-cover {
  opacity: 0.8;
  transform: scale(1.1);
}
.label {
  font-size: 90%;
  color: #eee;
}
.post-title {
  font-weight: 500;
  color: #fff;
}
hr {
  position: relative;
  margin: 40px auto;
  border: 2px dashed #d2ebfd;
  width: calc(100% - 4px);
}
.full {
  width: 100% !important;
}
.right-container {
  padding: 20px 24px;
  font-size: 14px;
}
.right-title {
  display: flex;
  align-items: center;
  line-height: 2;
  font-size: 16.8px;
  margin-bottom: 6px;
}
.right-title i {
  font-weight: bold;
}
.recommend-container {
  margin-top: 40px;
}
.recommend-title {
  font-size: 20px;
  line-height: 2;
  font-weight: bold;
  margin-bottom: 5px;
}
.recommend-cover {
  width: 100%;
  height: 100%;
  opacity: 0.4;
  transition: all 0.6s;
  object-fit: cover;
}
.recommend-info {
  line-height: 2;
  color: #fff;
  position: absolute;
  top: 50%;
  padding: 0 20px;
  width: 100%;
  transform: translate(0, -50%);
  text-align: center;
  font-size: 14px;
}
.recommend-date {
  font-size: 90%;
}
.recommend-item:hover .recommend-cover {
  opacity: 0.8;
  transform: scale(1.1);
}
.article-item {
  display: flex;
  align-items: center;
  padding: 6px 0;
}
.article-item:first-child {
  padding-top: 0;
}
.article-item:last-child {
  padding-bottom: 0;
}
.article-item:not(:last-child) {
  border-bottom: 1px dashed #f5f5f5;
}
.article-item img {
  width: 100%;
  height: 100%;
  transition: all 0.6s;
  object-fit: cover;
}
.article-item img:hover {
  transform: scale(1.1);
}
.content {
  flex: 1;
  padding-left: 10px;
  word-break: break-all;
  display: -webkit-box;
  overflow: hidden;
  -webkit-box-orient: vertical;
}
.content-cover {
  width: 58.8px;
  height: 58.8px;
  overflow: hidden;
}
.content-title a {
  transition: all 0.2s;
  font-size: 95%;
}
.content-title a:hover {
  color: #2ba1d1;
}
.content-time {
  color: #858585;
  font-size: 85%;
  line-height: 2;
}
</style>

<style lang="scss">
pre.hljs {
  padding: 12px 2px 12px 40px !important;
  border-radius: 5px !important;
  position: relative;
  font-size: 14px !important;
  line-height: 22px !important;
  overflow: hidden !important;
  &:hover .copy-btn {
    display: flex;
    justify-content: center;
    align-items: center;
  }
  code {
    display: block !important;
    margin: 0 10px !important;
    overflow-x: auto !important;
    &::-webkit-scrollbar {
      z-index: 11;
      width: 6px;
    }
    &::-webkit-scrollbar:horizontal {
      height: 6px;
    }
    &::-webkit-scrollbar-thumb {
      border-radius: 5px;
      width: 6px;
      background: #666;
    }
    &::-webkit-scrollbar-corner,
    &::-webkit-scrollbar-track {
      background: #1e1e1e;
    }
    &::-webkit-scrollbar-track-piece {
      background: #1e1e1e;
      width: 6px;
    }
  }
  .line-numbers-rows {
    position: absolute;
    pointer-events: none;
    top: 12px;
    bottom: 12px;
    left: 0;
    font-size: 100%;
    width: 40px;
    text-align: center;
    letter-spacing: -1px;
    border-right: 1px solid rgba(0, 0, 0, 0.66);
    user-select: none;
    counter-reset: linenumber;
    span {
      pointer-events: none;
      display: block;
      counter-increment: linenumber;
      &:before {
        content: counter(linenumber);
        color: #999;
        display: block;
        text-align: center;
      }
    }
  }
  b.name {
    position: absolute;
    top: 7px;
    right: 45px;
    z-index: 1;
    color: #999;
    pointer-events: none;
  }
  .copy-btn {
    position: absolute;
    top: 6px;
    right: 6px;
    z-index: 1;
    color: #ccc;
    background-color: #525252;
    border-radius: 6px;
    display: none;
    font-size: 14px;
    width: 32px;
    height: 24px;
    outline: none;
  }
  
}

.msg-card-style {
    background-color: rgb(255 255 255 / 70%);
    border-radius: 10px;
}
//相册相关

.photo-wrap {
  display: flex;
  flex-wrap: wrap;
}
.photo {
  margin: 3px;
  cursor: pointer;
  flex-grow: 1;
  object-fit: cover;
  height: 200px;
}
.photo-wrap::after {
  content: "";
  display: block;
  flex-grow: 9999;
}
@media (max-width: 759px) {
  .photo {
    width: 100%;
  }
}




@media (min-width: 760px) {

  .article-card {
    display: flex;
    align-items: center;
    height: 280px;
    width: 100%;
    margin-top: 5px;
    margin-bottom: 5px;
  }
  .article-cover {
    overflow: hidden;
    height: 100%;
    width: 45%;
  }
  .on-hover {
    transition: all 0.6s;
  }
  .article-card:hover .on-hover {
    transform: scale(1.1);
  }
  .article-wrapper {
    padding: 0 2.5rem;
    width: 55%;
  }
  .article-wrapper a {
    font-size: 1.5rem;
    transition: all 0.3s;
  }
}
@media (max-width: 759px) {

  .article-card {
    margin-top: 0.3rem;
    margin-bottom: 0.3rem;
  }
  .article-cover {
    border-radius: 8px 8px 0 0 !important;
    height: 230px !important;
    width: 100%;
  }
  .article-cover div {
    border-radius: 8px 8px 0 0 !important;
  }
  .article-wrapper {
    padding: 1.25rem 1.25rem 1.875rem;
  }
  .article-wrapper a {
    font-size: 1.25rem;
    transition: all 0.3s;
  }
}
.article-wrapper a:hover {
  color: #8e8cd8;
}
.article-info {
  font-size: 95%;
  /* color: #858585; */
  line-height: 2;
  margin: 0.375rem 0;
}
.article-info a {
  font-size: 95%;
  /* color: #858585 !important; */
}

.blog-wrapper {
  position: sticky;
  top: 10px;
}
.blog-card {
  line-height: 2;
  padding: 1.25rem 1.5rem;
}
.author-wrapper {
  text-align: center;
}
.blog-info-wrapper {
  display: flex;
  justify-self: center;
  padding: 0.875rem 0;
}
.blog-info-data {
  flex: 1;
  text-align: center;
}
.blog-info-data a {
  text-decoration: none;
}
.collection-btn {
  border-radius: 8px 8px 8px 8px !important;
  text-align: center;
  z-index: 1;
  font-size: 14px;
  position: relative;
  display: block;
  background-color: #49b1f5;
  color: #fff !important;
  height: 32px;
  line-height: 32px;
  transition-duration: 1s;
  transition-property: color;
}
.collection-btn:before {
  border-radius: 8px 8px 8px 8px !important;
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: -1;
  background: #2eeb47;
  content: '';
  transition-timing-function: ease-out;
  transition-duration: 0.5s;
  transition-property: transform;
  transform: scaleX(0);
  transform-origin: 0 50%;
}
.collection-btn:hover:before {
  border-radius: 8px 8px 8px 8px !important;
  transition-timing-function: cubic-bezier(0.45, 1.64, 0.47, 0.66);
  transform: scaleX(1);
}
.tag-btn {
  color: #fff !important;
  display: inline-block;
  font-size: 0.725rem;
  line-height: 22px;
  height: 22px;
  border-radius: 10px;
  padding: 0 12px !important;
  background: linear-gradient(to right, #bf4643 0%, #6c9d8f 100%);
  opacity: 1;
  margin-right: 0.5rem;
}
</style>
