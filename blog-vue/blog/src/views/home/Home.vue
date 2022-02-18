<template>
  <div>
    <!-- banner -->
    <div class="home-banner"
         :style="cover">
         
      <div class="banner-container">
      <!-- 联系方式 -->

        <h1 class="blog-title animated zoomIn" style="color: #42bfac">
          {{ blogInfo.websiteConfig.websiteName }}
        </h1>
     
        
        <!-- 一言 -->
        <div class="blog-intro" style="color: #cc0033">
          {{ obj.output }} <span class="typed-cursor">|</span>
        </div>
        <!-- 联系方式 -->
        <div class="blog-contact">
          <a v-if="isShowSocial('qq')"
             class="mr-2 ml-2 iconfont iconqq"
             target="_blank"
             :href="
              'http://wpa.qq.com/msgrd?v=3&uin=' +
                blogInfo.websiteConfig.qq +
                '&site=qq&menu=yes'
            " />
          <a v-if="isShowSocial('github')"
             target="_blank"
             :href="blogInfo.websiteConfig.github"
             class="mr-2 ml-2  iconfont icongithub" />
          <a v-if="isShowSocial('gitee')"
             target="_blank"
             :href="blogInfo.websiteConfig.gitee"
             class="iconfont icongitee-fill-round" />
        </div>
         <div class="recent-post-item"
             style="height:auto;width:100%;padding:0px;margin-top:20px;">
          <div id="categoryBar" style="padding-left: 15%;padding-right:15%">
            <ul class="categoryBar-list">
                <li  v-for="(item,index) in categoryList.slice(0, 6)"
                      :key="index"
                      style="background: url('');background-size:cover;width: 24%"
                    class="categoryBar-list-item">
                    
                    <a class="categoryBar-list-link">
                     <router-link :to="'/categories/' + item.id">
                    <v-icon color="#c0d6cb"
                            size="18"
                            class="mr-1">mdi-bookmark</v-icon>
                            <span style="color: #fff">  {{ item.categoryName }}</span>
                   
                     </router-link>
                  </a><span class="categoryBar-list-count"> {{ item.articleCount }} </span>
                 
                </li>
                <li 
                    class="categoryBar-list-item categoryBar-list-item1" style="background: url('');background-size:cover;width: 49%">
                    
                    <a class="categoryBar-list-link">
<!--                      <router-link :to="'/tags/'"> -->
                    <v-icon color="#c0d6cb"
                            size="18"
                            class="mr-1">mdi-bookmark</v-icon>
                    <span style="color: #fff" @click="scrollDown"> 点击进入</span>
                   <!--  </router-link> -->
                  </a><span class="categoryBar-list-count"> 0V0 </span>
                  
                </li>
            </ul>
          </div>
        </div>
        <!-- 自适应5个快 -->
        <!-- <div class="cover-wrapper featured"
             id="full">
          <div class="cover-backstretch"></div>
          <div class="cover-body">
            <div class="bottom">
              <div class="menu navigation">
                <div class="list-h"><a id="home"
                     href="/"
                     data-pjax-state=""><img class="lazyload placeholder loaded"
                         src="https://cdn-1.nesxc.com/img/index/5.webp"
                         srcset="https://cdn-1.nesxc.com/img/index/5.webp"
                         data-ll-status="loaded">
                    <p>主页</p>
                  </a><a id="archives"
                     href="/archives/"
                     data-pjax-state="load"><img class="lazyload placeholder loaded"
                         src="https://cdn-1.nesxc.com/img/index/2.webp"
                         srcset="https://cdn-1.nesxc.com/img/index/2.webp"
                         data-ll-status="loaded">
                    <p>归档</p>
                  </a><a id="links"
                     href="/link/"
                     data-pjax-state="load"><img class="lazyload placeholder loaded"
                         src="https://cdn-1.nesxc.com/img/index/1.webp"
                         srcset="https://cdn-1.nesxc.com/img/index/1.webp"
                         data-ll-status="loaded">
                    <p>朋友</p>
                  </a><a id="comments"
                     href="/messageboard/"
                     data-pjax-state=""><img class="lazyload placeholder loaded"
                         src="https://cdn-1.nesxc.com/img/index/4.webp"
                         srcset="https://cdn-1.nesxc.com/img/index/4.webp"
                         data-ll-status="loaded">
                    <p>留言</p>
                  </a><a id="about"
                     href="/about/"
                     data-pjax-state="load"><img class="lazyload placeholder loaded"
                         src="https://cdn-1.nesxc.com/img/index/3.webp"
                         srcset="https://cdn-1.nesxc.com/img/index/3.webp"
                         data-ll-status="loaded">
                    <p>关于</p>
                  </a></div>
              </div>
            </div>
          </div>
        </div> -->
        
      </div>
      <!-- 向下滚动 -->
      <div class="scroll-down"
           @click="scrollDown">
        <v-icon color="#fff"
                class="scroll-down-effects">
          mdi-chevron-down
        </v-icon>
      </div>
    </div>
    <!-- 主页文章 -->

    <v-row class="home-container">
     
      <v-col md="9"
             cols="12">
        <div class="recent-post-item"
             style="height:auto;width:100%;padding:0px;margin-top:20px;">
          <div id="categoryBar">
            <ul class="categoryBar-list">
              
                <li  v-for="(item,index) in this.cardPageList.slice(0, 5)"
                      :key="index"
                      :style="'background: url(' + item.pageCover + ');width: 24%'"
                    class="categoryBar-list-item">
                    <router-link :to="'' + item.url">
                    <a class="categoryBar-list-link">
                     
                    <v-icon color="#c0d6cb"
                            size="18"
                            class="mr-1">mdi-bookmark</v-icon>
                    {{ item.pageName }}
                  </a><span class="categoryBar-list-count"> {{ item.pageLabel }} </span>
                  </router-link>
                </li>
                <li  
                      :style="'background: url(' + this.cardPageList[5].pageCover + ');width: 24%'"
                    class="categoryBar-list-item">
      
                    <a class="categoryBar-list-link" :href="this.cardPageList[5].url">
                     
                    <v-icon color="#c0d6cb"
                            size="18"
                            class="mr-1">mdi-bookmark</v-icon>
                    {{ this.cardPageList[5].pageName }}
                  </a><span class="categoryBar-list-count"> {{ this.cardPageList[5].pageLabel }} </span>
                </li>
                <li 
                    class="categoryBar-list-item categoryBar-list-item1" style="background: url('https://minio.404name.top/test/config/d64c2bea70ea3a9c567dc90ce7ef2b46.jpg');background-size:cover;width: 49%">
                    <router-link :to="'/recommend'">
                    <a class="categoryBar-list-link">
                     
                    <v-icon color="#c0d6cb"
                            size="18"
                            class="mr-1">mdi-bookmark</v-icon>
                    热门榜
                  </a><span class="categoryBar-list-count"> 0V0 </span><span> test </span>
                  </router-link>
                </li>
            </ul>
          </div>
        </div>
         <!-- 文章主体部分 -->
        <div class="">
          <v-card class="">
            <div class="photo-wrap">
              <div v-for="item of articleList"
                   :key="item.id"
                   class="album-item photo">
                <img class="photo album-cover"
                     width="100%"
                     :key="item.id"
                     :src="item.articleCover"
                     @click="preview(item.id)" />
                <router-link :to="'/articles/' + item.id"
                             class="album-wrapper">

                  <div class="album-name">
                    <span v-if="item.isTop == 1">
                      <span style="color:#ff7242">
                        <i class="iconfont iconzhiding" /> 置顶
                      </span>
                      <span class="separator">|</span>
                    </span>{{ item.articleTitle }}
                    <router-link :to="'/categories/' + item.categoryId"
                                 class="tag-btn">
                      <span> {{ item.categoryName }}</span>
                    </router-link>

                    <br />
                    

                  </div>
                  <div class="album-desc">
                    发布于:<v-icon size="20"
                            style="color: #fff">mdi-clock-outline</v-icon> {{ item.createTime | date }}
                    <router-link :to="'/tags/' + tag.id"
                                 class="tag-btn"
                                 v-for="tag of item.tagDTOList"
                                 :key="tag.id">
                      {{ tag.tagName }}
                    </router-link>
                  </div>

                </router-link>
              </div>
            </div>
            <!-- 无限加载 -->
            <infinite-loading @infinite="infiniteHandler">
              <div slot="no-more" />
              <div slot="no-results" />
            </infinite-loading>
          </v-card>

        </div>
      </v-col>
      <!-- 博主信息 -->
      <v-col md="3"
             cols="12"
             class="d-md-block d-none">
        <div class="blog-wrapper">
          <v-card class="animated zoomIn blog-card mt-5">
            <div class="author-wrapper">
              <!-- 博主头像 -->
              <v-avatar size="110">
                <img class="author-avatar"
                     :src="blogInfo.websiteConfig.websiteAvatar" />
              </v-avatar>
              <div style="font-size: 1.375rem">
                {{ blogInfo.websiteConfig.websiteAuthor }}
              </div>
              <div style="font-size: 0.875rem;">
                {{ blogInfo.websiteConfig.websiteIntro }}
              </div>
            </div>
            <!-- 博客信息 -->
            <div class="blog-info-wrapper">
              <div class="blog-info-data">
                <router-link to="/archives">
                  <div style="font-size: 0.875rem">文章</div>
                  <div style="font-size: 1.25rem">
                    {{ blogInfo.articleCount }}
                  </div>
                </router-link>
              </div>
              <div class="blog-info-data">
                <router-link to="/categories">
                  <div style="font-size: 0.875rem">分类</div>
                  <div style="font-size: 1.25rem">
                    {{ blogInfo.categoryCount }}
                  </div>
                </router-link>
              </div>
              <div class="blog-info-data">
                <router-link to="/tags">
                  <div style="font-size: 0.875rem">标签</div>
                  <div style="font-size: 1.25rem">{{ blogInfo.tagCount }}</div>
                </router-link>
              </div>
            </div>
            <!-- 收藏按钮 -->
            <a class="collection-btn"
               @click="tip = true">
              <v-icon color="#fff"
                      size="18"
                      class="mr-1">mdi-bookmark</v-icon>
              版本信息
            </a>
            <!-- 收藏按钮 -->
            <a class="collection-btn"  href ="https://yuque.com/lightcloud" style="margin-top:5px;margin-bottom:5px">
              <v-icon color="#fff"
                      size="18"
                      class="mr-1">mdi-bookmark</v-icon>
              轻云社区官网
            </a>
            <!-- 收藏按钮 -->
            <a class="collection-btn" href ="https://www.pgyer.com/lightcloud">
              <v-icon color="#fff"
                      size="18"
                      class="mr-1">mdi-bookmark</v-icon>
              轻云APP下载
            </a>
            <!-- 社交信息 -->
            <div class="card-info-social">
              <a v-if="isShowSocial('qq')"
                 class="mr-5 iconfont iconqq"
                 target="_blank"
                 :href="
                  'http://wpa.qq.com/msgrd?v=3&uin=' +
                    blogInfo.websiteConfig.qq +
                    '&site=qq&menu=yes'
                " />
              <a v-if="isShowSocial('github')"
                 target="_blank"
                 :href="blogInfo.websiteConfig.github"
                 class="mr-5 iconfont icongithub" />
              <a v-if="isShowSocial('gitee')"
                 target="_blank"
                 :href="blogInfo.websiteConfig.gitee"
                 class="iconfont icongitee-fill-round" />
            </div>
          </v-card>
          <!-- 网站信息 -->
          <v-card class="blog-card animated zoomIn mt-5 big">
            <div class="web-info-title">
              <v-icon size="18">mdi-bell</v-icon>
              公告
            </div>
            <div style="font-size:0.875rem">
              {{ blogInfo.websiteConfig.websiteNotice }}
            </div>
          </v-card>

          <!-- 社交圈 -->
          <v-card class="blog-card animated zoomIn mt-5 big">
            <div class="web-info-title">
              <v-icon size="18">mdi-user-add</v-icon>
              新加入的朋友
            </div>
            <div style="font-size:0.875rem">
              <router-link :to="'/user/' + user.id"
                           class="tag-btn"
                           v-for="user of userList"
                           :key="user.id">
                <span style="color: #fffae8">{{ user.username }}</span>
              </router-link>
            </div>
          </v-card>
          <!-- 网站信息 -->
          <v-card class="blog-card animated zoomIn mt-5">
            <div class="web-info-title">
              <v-icon size="18">mdi-chart-line</v-icon>
              网站资讯
            </div>
            <div class="web-info">
              <div style="padding:4px 0 0">
                运行时间:<span class="float-right">{{ time }}</span>
              </div>
              <div style="padding:4px 0 0">
                总访问量:<span class="float-right">
                  {{ blogInfo.viewsCount }}
                </span>
              </div>
            </div>
          </v-card>
        </div>
      </v-col>
    </v-row>
    <!-- 提示消息 -->
    <v-snackbar v-model="tip"
                top
                color="#49b1f5"
                :timeout="2000">
      当前版本 {{this.blogInfo.websiteConfig.version | 1.1.0}}; 后期即将接入商城系统
    </v-snackbar>
  </div>
</template>

<script>
import EasyTyper from 'easy-typer-js'
export default {
  created() {
    this.init()
    this.listCategories()
    this.timer = setInterval(this.runTime, 1000)
  },
  data: function () {
    return {
      tip: false,
      time: '',
      obj: {
        output: '',
        isEnd: false,
        speed: 300,
        singleBack: false,
        sleep: 0,
        type: 'rollback',
        backSpeed: 40,
        sentencePause: true,
      },
      articleList: [],
      current: 1,
      categoryList: [],
      count: 0,
      //mooc
      cardPageList:[
        {
          "pageCover": "https://minio.404name.top/test/config/953df91d18981f8276eafbd2641d15a1.jpg",
        "id": 2,
        "pageName": "归档",
        "pageLabel": "archives"
        },
        {
          "pageCover": "https://minio.404name.top/test/config/953df91d18981f8276eafbd2641d15a1.jpg",
        "id": 2,
        "pageName": "归档",
        "pageLabel": "archives"
        },
        {
          "pageCover": "https://minio.404name.top/test/config/953df91d18981f8276eafbd2641d15a1.jpg",
        "id": 2,
        "pageName": "归档",
        "pageLabel": "archives"
        },
        {
          "pageCover": "https://minio.404name.top/test/config/953df91d18981f8276eafbd2641d15a1.jpg",
        "id": 2,
        "pageName": "归档",
        "pageLabel": "archives"
        },
        {
          "pageCover": "https://minio.404name.top/test/config/953df91d18981f8276eafbd2641d15a1.jpg",
        "id": 2,
        "pageName": "归档",
        "pageLabel": "archives"
        },
        {
          "pageCover": "https://www.pgyer.com/app/qrcode/lightcloud",
          "id": 1500,
          "pageName": "APP下载",
          "pageLabel": " ",
          "url":"https://www.pgyer.com/lightcloud"
        },
      ],
      userList: [
        { id: 1, username: 'frank' },
        { id: 2, username: '绝伦N' },
        { id: 3, username: '小王' },
        { id: 4, username: 'the Shy' },
        { id: 5, username: '特朗普' },
        { id: 6, username: '404name' },
        { id: 7, username: '嘿嘿' },
      ],
    }
  },
  methods: {
    // 初始化
    init() {
      for(var i = 0; i < this.blogInfo.pageList.length; i++){
        if(this.blogInfo.pageList[i].pageLabel == "recommend"){
          this.cardPageList[0] = this.blogInfo.pageList[i];
           this.cardPageList[0].url = '/' + this.cardPageList[0].pageLabel;
        }else if(this.blogInfo.pageList[i].pageLabel == "archives"){
          this.cardPageList[1] = this.blogInfo.pageList[i];
           this.cardPageList[1].url = '/' + this.cardPageList[1].pageLabel;
        }else if(this.blogInfo.pageList[i].pageLabel == "links"){
          this.cardPageList[2] = this.blogInfo.pageList[i];
           this.cardPageList[2].url = '/' + this.cardPageList[2].pageLabel;
        }else if(this.blogInfo.pageList[i].pageLabel == "message"){
          this.cardPageList[3] = this.blogInfo.pageList[i];
           this.cardPageList[3].url = '/' + this.cardPageList[3].pageLabel;
        }else if(this.blogInfo.pageList[i].pageLabel == "user"){
          this.cardPageList[4] = this.blogInfo.pageList[i];
           this.cardPageList[4].url = '/' + this.cardPageList[4].pageLabel;
        }
      }

      
      document.title = this.blogInfo.websiteConfig.websiteName
      // 一言Api进行打字机循环输出效果
      fetch('https://v1.hitokoto.cn?c=i')
        .then((res) => {
          return res.json()
        })
        .then(({ hitokoto }) => {
          this.initTyped(hitokoto)
        })
    },
    initTyped(input, fn, hooks) {
      const obj = this.obj
      // eslint-disable-next-line no-unused-vars
      const typed = new EasyTyper(obj, input, fn, hooks)
    },
    scrollDown() {
      window.scrollTo({
        behavior: 'smooth',
        top: document.documentElement.clientHeight,
      })
    },
    runTime() {
      var timeold =
        new Date().getTime() -
        new Date(this.blogInfo.websiteConfig.websiteCreateTime).getTime()
      var msPerDay = 24 * 60 * 60 * 1000
      var daysold = Math.floor(timeold / msPerDay)
      var str = ''
      var day = new Date()
      str += daysold + '天'
      str += day.getHours() + '时'
      str += day.getMinutes() + '分'
      str += day.getSeconds() + '秒'
      this.time = str
    },
    listCategories() {
      this.axios.get('/api/categories').then(({ data }) => {
        this.categoryList = data.data.recordList
        this.count = data.data.count
      })
    },
    infiniteHandler($state) {
      let md = require('markdown-it')()
      this.axios
        .get('/api/articles', {
          params: {
            current: this.current,
          },
        })
        .then(({ data }) => {
          if (data.data.length) {
            // 去除markdown标签
            data.data.forEach((item) => {
              item.articleContent = md
                .render(item.articleContent)
                .replace(/<\/?[^>]*>/g, '')
                .replace(/[|]*\n/, '')
                .replace(/&npsp;/gi, '')
            })
            this.articleList.push(...data.data)
            this.current++
            $state.loaded()
          } else {
            $state.complete()
          }
        })
    },
  },
  computed: {
    isRight() {
      return function (index) {
        if (index % 2 == 0) {
          return 'article-cover left-radius'
        }
        return 'article-cover right-radius'
      }
    },
    blogInfo() {
      
      return this.$store.state.blogInfo
    },
    isShowSocial() {
      return function (social) {
        return this.blogInfo.websiteConfig.socialUrlList.indexOf(social) != -1
      }
    },
    cover() {
      var cover = ''
      this.$store.state.blogInfo.pageList.forEach((item) => {
        if (item.pageLabel == '/') {
          cover = item.pageCover
        }
      })
      return 'background: url(' + cover + ') center center / cover no-repeat'
    },
  },
}
</script>

<style lang="stylus">
.typed-cursor {
  opacity: 1;
  animation: blink 0.7s infinite;
}

@keyframes blink {
  0% {
    opacity: 1;
  }

  50% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}
</style>

<style scoped>
.home-banner {
  position: absolute;
  top: -60px;
  left: 0;
  right: 0;
  height: 100vh;
  background-attachment: fixed;
  text-align: center;
  color: #fff !important;
  animation: header-effect 1s;
}
.banner-container {
  margin-top: 15vh;
  line-height: 1.5;
  color: #eee;
}
.blog-contact a {
  color: #fff !important;
}
.card-info-social {
  line-height: 40px;
  text-align: center;
  margin: 6px 0 -6px;
}
.card-info-social a {
  font-size: 1.5rem;
}
.left-radius {
  border-radius: 8px 0 0 8px !important;
  order: 0;
}
.right-radius {
  border-radius: 0 8px 8px 0 !important;
  order: 1;
}
.article-wrapper {
  font-size: 14px;
}
@media (min-width: 760px) {
  .blog-title {
    font-size: 2.5rem;
  }
  .blog-intro {
    font-size: 1.5rem;
  }
  .blog-contact {
    display: none;
  }
  .home-container {
    max-width: 1200px;
    margin: calc(100vh - 48px) auto 28px auto;
    padding: 0 5px;
  }
  .article-card {
    display: flex;
    align-items: center;
    height: 280px;
    width: 100%;
    margin-top: 20px;
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
  .blog-title {
    font-size: 26px;
  }
  .blog-contact {
    font-size: 1.25rem;
    line-height: 2;
  }
  .home-container {
    width: 100%;
    margin: calc(100vh - 66px) auto 0 auto;
  }
  .article-card {
    margin-top: 1rem;
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
.scroll-down {
  cursor: pointer;
  position: absolute;
  bottom: 0;
  width: 100%;
}
.scroll-down i {
  font-size: 2rem;
}
.article-wrapper a:hover {
  color: #8e8cd8;
}
.article-info {
  font-size: 95%;
  color: #858585;
  line-height: 2;
  margin: 0.375rem 0;
}
.article-info a {
  font-size: 95%;
  color: #858585 !important;
}
.article-content {
  line-height: 2;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
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
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: -1;
  background: #ff7242;
  content: '';
  transition-timing-function: ease-out;
  transition-duration: 0.5s;
  transition-property: transform;
  transform: scaleX(0);
  transform-origin: 0 50%;
}
.collection-btn:hover:before {
  transition-timing-function: cubic-bezier(0.45, 1.64, 0.47, 0.66);
  transform: scaleX(1);
}
.author-avatar {
  transition: all 0.5s;
}
.author-avatar:hover {
  transform: rotate(360deg);
}
.web-info {
  padding: 0.25rem;
  font-size: 0.875rem;
}
.scroll-down-effects {
  color: #eee !important;
  text-align: center;
  text-shadow: 0.1rem 0.1rem 0.2rem rgba(0, 0, 0, 0.15);
  line-height: 1.5;
  display: inline-block;
  text-rendering: auto;
  -webkit-font-smoothing: antialiased;
  animation: scroll-down-effect 1.5s infinite;
}
@keyframes scroll-down-effect {
  0% {
    top: 0;
    opacity: 0.4;
    filter: alpha(opacity=40);
  }
  50% {
    top: -16px;
    opacity: 1;
    filter: none;
  }
  100% {
    top: 0;
    opacity: 0.4;
    filter: alpha(opacity=40);
  }
}
.big i {
  color: #f00;
  animation: big 0.8s linear infinite;
}
@keyframes big {
  0%,
  100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.2);
  }
}

@media (min-width: 760px) {
  .article-list-wrapper {
    max-width: 1106px;
    margin: 370px auto 1rem auto;
  }
  .article-item-card:hover {
    transition: all 0.3s;
    box-shadow: 0 4px 12px 12px rgba(7, 17, 27, 0.15);
  }
  .article-item-card:not(:hover) {
    transition: all 0.3s;
  }
  .article-item-card:hover .on-hover {
    transition: all 0.6s;
    transform: scale(1.1);
  }
  .article-item-card:not(:hover) .on-hover {
    transition: all 0.6s;
  }
  .article-item-info {
    line-height: 1.7;
    padding: 15px 15px 12px 18px;
    font-size: 15px;
  }
}
@media (max-width: 759px) {
  .article-list-wrapper {
    margin-top: 230px;
    padding: 0 12px;
  }
  .article-item-info {
    line-height: 1.7;
    padding: 15px 15px 12px 18px;
  }
}
.article-item-card {
  border-radius: 8px !important;
  box-shadow: 0 4px 8px 6px rgba(7, 17, 27, 0.06);
}
.article-item-card a {
  transition: all 0.3s;
}
.article-item-cover {
  height: 150px;
  overflow: hidden;
}
.article-item-card a:hover {
  color: #8e8cd8;
}
.tag-wrapper {
  padding: 10px 15px 10px 18px;
}
.tag-wrapper a {
  color: #fff !important;
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
.album-item {
  overflow: hidden;
  position: relative;
  cursor: pointer;
  background: #000;
  border-radius: 0.5rem !important;
}
.album-cover {
  position: relative;
  max-width: none;
  width: calc(100% + 1.25rem);
  height: 250px;
  opacity: 0.8;
  transition: opacity 0.35s, transform 0.35s;
  transform: translate3d(-10px, 0, 0);
  object-fit: cover;
}
.album-wrapper {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 1.8rem 2rem;
  color: #fff !important;
}
.album-item:hover .album-cover {
  transform: translate3d(0, 0, 0);
  opacity: 0.4;
}
.album-item:hover .album-name:after {
  transform: translate3d(0, 0, 0);
}
.album-item:hover .album-desc {
  opacity: 1;
  filter: none;
  transform: translate3d(0, 0, 0);
}
.album-name {
  font-weight: bold;
  font-size: 1.25rem;
  overflow: hidden;
  position: relative;
}
.album-name:after {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 2px;
  background: #fff;
  content: '';
  transition: transform 0.35s;
  transform: translate3d(-101%, 0, 0);
}
.album-desc {
  bottom: 0;
  margin: 0;
  padding: 0.4rem 0 0;
  line-height: 1.5;
  opacity: 0;
  transition: opacity 0.35s, transform 0.35s;
  transform: translate3d(100%, 0, 0);
}

.card-icon-container {
  display: inline-block;
  font-size: 3rem;
}
.card-desc {
  font-weight: bold;
  float: right;
}
.card-title {
  margin-top: 0.3rem;
  line-height: 18px;
  color: rgba(255, 250, 232, 0.45);
  font-size: 1rem;
}
.card-count {
  margin-top: 0.75rem;
  color: #666;
  font-size: 1.25rem;
}

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
  content: '';
  display: block;
  flex-grow: 9999;
}
@media (max-width: 759px) {
  .photo {
    width: 100%;
  }
}
.album-item {
  overflow: hidden;
  position: relative;
  cursor: pointer;
  background: #000;
  border-radius: 0.5rem !important;
}
.album-cover {
  position: relative;
  max-width: none;
  width: calc(100% + 1.25rem);
  height: 250px;
  opacity: 0.8;
  transition: opacity 0.35s, transform 0.35s;
  transform: translate3d(-10px, 0, 0);
  object-fit: cover;
}
.album-wrapper {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 0.2rem 0.8rem;
  color: #fff !important;
}
.album-item:hover .album-cover {
  transform: translate3d(0, 0, 0);
  opacity: 0.4;
}
.album-item:hover .album-name:after {
  transform: translate3d(0, 0, 0);
}
.album-item:hover .album-desc {
  opacity: 1;
  filter: none;
  transform: translate3d(0, 0, 0);
}
.album-name {
  font-weight: bold;
  font-size: 1.25rem;
  overflow: hidden;
  padding: 0.7rem 0;
  position: relative;
}
.album-name:after {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 2px;
  background: #fff;
  content: '';
  transition: transform 0.35s;
  transform: translate3d(-101%, 0, 0);
}
.album-desc {
  margin: 0;
  padding: 0.4rem 0 0;
  line-height: 1.5;
  opacity: 0;
  transition: opacity 0.35s, transform 0.35s;
  transform: translate3d(100%, 0, 0);
}
.cover-wrapper .cover-bg {
  position: absolute;
  width: 100%;
  height: 100%;
  background-position: center;
  background-size: cover;
}
.cover-wrapper {
  max-width: 100%;
}
.cover-wrapper .cover-body,
.cover-wrapper .cover-body .bottom,
.cover-wrapper .cover-body .top {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 100%;
}
.cover-wrapper .cover-body {
  margin-bottom: 100px;
}
.cover-wrapper .cover-body {
  z-index: 1;
  position: relative;
  height: 30%;
}
.cover-wrapper .cover-body .bottom {
  margin-top: 32px;
}
.cover-wrapper .cover-body,
.cover-wrapper .cover-body .bottom,
.cover-wrapper .cover-body .top {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 100%;
}
.cover-wrapper.dock .menu,
.cover-wrapper.featured .menu,
.cover-wrapper.focus .menu {
  border-radius: 6px;
}
.cover-wrapper.featured .menu .list-h {
  margin: -2px;
}
.cover-wrapper .list-h {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: stretch;
  border-radius: 4px;
  -moz-user-select: none;
  -ms-user-select: none;
  -webkit-user-select: none;
  user-select: none;
}
.list-h {
  border-radius: 12px;
}

.cover-wrapper.featured .menu .list-h a {
  background: rgba(255, 255, 255, 0.5);
  backdrop-filter: saturate(200%) blur(20px);
}
.cover-wrapper.featured .menu .list-h a {
  margin: 2px;
  background: rgba(255, 255, 255, 0.5);
}

.cover-wrapper.dock .menu .list-h a,
.cover-wrapper.featured .menu .list-h a,
.cover-wrapper.focus .menu .list-h a {
  padding: 12px 8px;
}
.cover-wrapper.dock .menu .list-h a,
.cover-wrapper.featured .menu .list-h a,
.cover-wrapper.focus .menu .list-h a {
  flex-direction: column;
  align-items: center;
  padding: 12px;
  line-height: 1px;
  border-radius: 4px;
  border-bottom: none;
  text-align: center;
  align-content: flex-end;
  color: rgba(68, 68, 68, 0.7);
  font-size: 1.5rem;
}
.cover-wrapper .list-h a {
  flex: 1;
  display: flex;
  font-weight: 600;
}
a {
  color: #ff786e;
  text-decoration: none;
  word-wrap: break-word;
  -webkit-transition: all 0.2s;
  -moz-transition: all 0.2s;
  -o-transition: all 0.2s;
  -ms-transition: all 0.2s;
  transition: all 0.2s;
  overflow-wrap: break-word;
}
.cover-wrapper .list-h a img {
  margin: 2px 4px;
  min-width: 32px;
  max-width: 36px;
}

.cover-wrapper .list-h a img {
  min-width: 60px;
  max-width: 80px;
}
.cover-wrapper .list-h a img {
  display: block;
  border-radius: 2px;
  margin: 4px;
  margin-bottom: 15px;
  min-width: 60px;
  max-width: 80px;
}
img {
  max-width: 100%;
  -webkit-transition: all 0.2s;
  -moz-transition: all 0.2s;
  -o-transition: all 0.2s;
  -ms-transition: all 0.2s;
  transition: all 0.2s;
}
img {
  border-style: none;
}
#recent-posts > .recent-post-item:not(:first-child) {
  margin-top: 1rem;
}
#recent-posts > .recent-post-item {
  display: -webkit-box;
  display: -moz-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: box;
  display: flex;
  -webkit-box-orient: horizontal;
  -moz-box-orient: horizontal;
  -o-box-orient: horizontal;
  -webkit-flex-direction: row;
  -ms-flex-direction: row;
  flex-direction: row;
  -webkit-box-align: center;
  -moz-box-align: center;
  -o-box-align: center;
  -ms-flex-align: center;
  -webkit-align-items: center;
  align-items: center;
  height: 20em;
  border-radius: 12px 8px 8px 12px;
  background: var(--card-bg);
  -webkit-box-shadow: var(--card-box-shadow);
  box-shadow: var(--card-box-shadow);
  -webkit-transition: all 0.3s;
  -moz-transition: all 0.3s;
  -o-transition: all 0.3s;
  -ms-transition: all 0.3s;
  transition: all 0.3s;
}
@media screen and (max-width: 768px) {
  #recent-posts > .recent-post-item {
    border-radius: 12px 12px 8px 8px;
  }
}
#recent-posts > .recent-post-item:hover {
  -webkit-box-shadow: var(--card-hover-box-shadow);
  box-shadow: var(--card-hover-box-shadow);
}
#recent-posts > .recent-post-item:hover img.post_bg {
  -webkit-transform: scale(1.1);
  -moz-transform: scale(1.1);
  -o-transform: scale(1.1);
  -ms-transform: scale(1.1);
  transform: scale(1.1);
}
#recent-posts > .recent-post-item .left_radius {
  border-radius: 8px 0 0 8px;
}
#recent-posts > .recent-post-item .right_radius {
  -webkit-box-ordinal-group: 2;
  -moz-box-ordinal-group: 2;
  -o-box-ordinal-group: 2;
  -ms-flex-order: 2;
  -webkit-order: 2;
  order: 2;
  border-radius: 0 8px 8px 0;
}
#recent-posts > .recent-post-item.ads-wrap {
  display: block !important;
  height: auto !important;
}
#recent-posts > .recent-post-item .post_cover {
  overflow: hidden;
  width: 45%;
  height: 100%;
  -webkit-mask-image: -webkit-radial-gradient(#fff, #000);
}
#recent-posts > .recent-post-item .post_cover img.post_bg {
  width: 100%;
  height: 100%;
  -webkit-transition: all 0.6s;
  -moz-transition: all 0.6s;
  -o-transition: all 0.6s;
  -ms-transition: all 0.6s;
  transition: all 0.6s;
  object-fit: cover;
}
#recent-posts > .recent-post-item .post_cover img.post_bg:hover {
  -webkit-transform: scale(1.1);
  -moz-transform: scale(1.1);
  -o-transform: scale(1.1);
  -ms-transform: scale(1.1);
  transform: scale(1.1);
}
#recent-posts > .recent-post-item > .recent-post-info {
  display: inline-block;
  overflow: hidden;
  padding: 0 40px;
  width: 55%;
}
#recent-posts > .recent-post-item > .recent-post-info.no-cover {
  width: 100%;
}
#recent-posts > .recent-post-item > .recent-post-info > .article-title {
  margin-bottom: 0.3rem;
  color: var(--text-highlight-color);
  font-size: 1.72em;
  line-height: 1.4;
  -webkit-transition: all 0.2s ease-in-out;
  -moz-transition: all 0.2s ease-in-out;
  -o-transition: all 0.2s ease-in-out;
  -ms-transition: all 0.2s ease-in-out;
  transition: all 0.2s ease-in-out;
  -webkit-line-clamp: 2;
}
#recent-posts > .recent-post-item > .recent-post-info > .article-title:hover {
  color: #49b1f5;
}
#recent-posts > .recent-post-item > .recent-post-info > .article-meta-wrap {
  color: #858585;
  font-size: 90%;
}
#recent-posts
  > .recent-post-item
  > .recent-post-info
  > .article-meta-wrap
  > .post-meta-date {
  cursor: default;
}
#recent-posts
  > .recent-post-item
  > .recent-post-info
  > .article-meta-wrap
  .sticky {
  color: #ff7242;
}
#recent-posts > .recent-post-item > .recent-post-info > .article-meta-wrap i {
  margin: 0 0.2rem 0 0;
}
#recent-posts
  > .recent-post-item
  > .recent-post-info
  > .article-meta-wrap
  .article-meta-label {
  padding-right: 0.2rem;
}
#recent-posts
  > .recent-post-item
  > .recent-post-info
  > .article-meta-wrap
  .article-meta__separator {
  margin: 0 0.3rem;
}
#recent-posts
  > .recent-post-item
  > .recent-post-info
  > .article-meta-wrap
  .article-meta__link {
  margin: 0 0.2rem;
}
#recent-posts
  > .recent-post-item
  > .recent-post-info
  > .article-meta-wrap
  .fa-angle-right {
  margin: 0 0.2rem;
}
#recent-posts > .recent-post-item > .recent-post-info > .article-meta-wrap a {
  color: #858585;
}
#recent-posts
  > .recent-post-item
  > .recent-post-info
  > .article-meta-wrap
  a:hover {
  color: #49b1f5;
  text-decoration: underline;
}
#recent-posts > .recent-post-item > .recent-post-info > .content {
  margin-top: 0.3rem;
  -webkit-line-clamp: 3;
}
@media screen and (max-width: 768px) {
  #recent-posts .recent-post-item {
    -webkit-box-orient: vertical;
    -moz-box-orient: vertical;
    -o-box-orient: vertical;
    -webkit-flex-direction: column;
    -ms-flex-direction: column;
    flex-direction: column;
    height: auto !important;
  }
  #recent-posts .recent-post-item .post_cover {
    -webkit-box-ordinal-group: 1 !important;
    -moz-box-ordinal-group: 1 !important;
    -o-box-ordinal-group: 1 !important;
    -ms-flex-order: 1 !important;
    -webkit-order: 1 !important;
    order: 1 !important;
    width: 100%;
    height: 230px;
    border-radius: 8px 8px 0 0;
  }
  #recent-posts .recent-post-item .recent-post-info {
    -webkit-box-ordinal-group: 2 !important;
    -moz-box-ordinal-group: 2 !important;
    -o-box-ordinal-group: 2 !important;
    -ms-flex-order: 2 !important;
    -webkit-order: 2 !important;
    order: 2 !important;
    padding: 1rem 1rem 1.5rem;
    width: 100%;
  }
  #recent-posts .recent-post-item .recent-post-info.no-cover {
    padding: 1.5rem 1rem;
  }
  #recent-posts .recent-post-item .recent-post-info .article-title {
    font-size: 1.43em;
  }
  #recent-posts .recent-post-item .recent-post-info .content {
    height: auto;
  }
}
#categoryBar {
  width: 100% !important;
}
ul.categoryBar-list {
  margin: 0px 0px 0 0px !important;
  padding: 0 !important;
}
li.categoryBar-list-item {
  font-weight: bold;
  display: inline-block;
  height: 180px !important;
  margin: 5px 0.5% 0 0.5% !important;
  background-image: -webkit-linear-gradient(
    rgba(0, 0, 0, 0.4) 25%,
    rgba(16, 16, 16, 0) 100%
  );
  background-image: -moz-linear-gradient(
    rgba(0, 0, 0, 0.4) 25%,
    rgba(16, 16, 16, 0) 100%
  );
  background-image: -o-linear-gradient(
    rgba(0, 0, 0, 0.4) 25%,
    rgba(16, 16, 16, 0) 100%
  );
  background-image: -ms-linear-gradient(
    rgba(0, 0, 0, 0.4) 25%,
    rgba(16, 16, 16, 0) 100%
  );
  background-image: linear-gradient(
    rgba(0, 0, 0, 0.4) 25%,
    rgba(16, 16, 16, 0) 100%
  );
  border-radius: 10px;
  padding: 25px 0 25px 25px !important;
  -webkit-box-shadow: rgba(50, 50, 50, 0.3) 50px 50px 50px 50px inset;
  box-shadow: rgba(50, 50, 50, 0.3) 50px 50px 50px 50px inset;
  overflow: hidden;
  -webkit-background-size: cover !important;
  -moz-background-size:  cover !important;
  background-size: cover !important;
  background-position: center !important;
}
li.categoryBar-list-item:hover {
  -webkit-background-size: fit !important;
  -moz-background-size: fit !important;
  background-size: fit !important;
  -webkit-box-shadow: inset 500px 50px 50px 50px rgba(50, 50, 50, 0.6);
  box-shadow: inset 500px 50px 50px 50px rgba(50, 50, 50, 0.6);
}
li.categoryBar-list-item:hover span.categoryBar-list-count::after {
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -o-transition: all 0.5s;
  -ms-transition: all 0.5s;
  transition: all 0.5s;
  -webkit-transform: translate(-100%, 0);
  -moz-transform: translate(-100%, 0);
  -o-transform: translate(-100%, 0);
  -ms-transform: translate(-100%, 0);
  transform: translate(-100%, 0);
}
a.categoryBar-list-link {
  color: #fff !important;
  font-size: 25px !important;
}
a.categoryBar-list-link::before {
  content: '|' !important;
  color: #fff !important;
  font-size: 20px !important;
}
a.categoryBar-list-link:after {
  content: '';
  position: relative;
  width: 0;
  bottom: 0;
  display: block;
  height: 3px;
  border-radius: 3px;
  background-color: #fff;
}
a.categoryBar-list-link:hover:after {
  width: 90%;
  left: 1%;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -o-transition: all 0.5s;
  -ms-transition: all 0.5s;
  transition: all 0.5s;
}
span.categoryBar-list-count {
  display: block !important;
  color: #fff !important;
  font-size: 20px !important;
}
span.categoryBar-list-count::before {
  padding-right: 15px !important;
}
span.categoryBar-list-count::after {
  padding: 5px;
  display: block !important;
  color: #fff !important;
  font-size: 20px !important;
  position: relative;
  right: -100%;
}

li.categoryBar-list-item:nth-child(9) {
  background: #abcdef;
}
li.categoryBar-list-item:nth-child(10) {
  background: rgba(45,67,89,0.7);
}
li.categoryBar-list-item:nth-child(11) {
  background: -webkit-linear-gradient(rgba(0, 0, 0, 0.4) 25%, rgba(200,16 , 16, 0) 100%);
  background: -moz-linear-gradient(rgba(0, 0, 0, 0.4) 25%, rgba(200,16 , 16, 0) 100%);
  background: -o-linear-gradient(rgba(0, 0, 0, 0.4) 25%, rgba(200,16 , 16, 0) 100%);
  background: -ms-linear-gradient(rgba(0, 0, 0, 0.4) 25%, rgba(200,16 , 16, 0) 100%);
  background: linear-gradient(rgba(0, 0, 0, 0.4) 25%, rgba(200,16 , 16, 0) 100%);
}
li.categoryBar-list-item:nth-child(1)>span::after {
  content: '青石落晚巷' !important;
}
li.categoryBar-list-item:nth-child(2)>span::after {
  content: '两行隔山茶' !important;
}
li.categoryBar-list-item:nth-child(3)>span::after {
  content: '棋子敲不响' !important;
}
li.categoryBar-list-item:nth-child(4)>span::after {
  content: '故人未还乡' !important;
}
li.categoryBar-list-item:nth-child(5)>span::after {
  content: '空山不见人' !important;
}
li.categoryBar-list-item:nth-child(6)>span::after {
  content: '但闻人语响' !important;
}
li.categoryBar-list-item:nth-child(7)>span::after {
  content: '返景入深林' !important;
}
li.categoryBar-list-item:nth-child(8)>span::after {
  content: '复照青苔上' !important;
}

@media screen and (max-width: 650px) {
  li.categoryBar-list-item {
    width: 48% !important;
    height: 150px !important;
    margin: 5px 1% 0 1% !important;
  }
  li.categoryBar-list-item1 {
    width: 98% !important;
    height: 150px !important;
    margin: 5px 1% 0 1% !important;
  }
}
.categoryBar-list {
  max-height: 380px;
  overflow: auto;
}
.categoryBar-list::-webkit-scrollbar {
  width: 0 !important;
}
@media screen and (max-width: 650px) {
  .categoryBar-list {
    max-height: 320px;
  }
}

</style>
