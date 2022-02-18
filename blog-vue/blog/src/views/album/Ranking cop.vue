<template>
  <div>
    <!-- banner -->
    <div class="banner" :style="cover">
      <h1 class="banner-title">{{ photoAlbumName }} </h1>
    </div>
    <!-- 相册列表 -->
    <div class="blog-container">
      <div class="photo-wrap" >
       <!--  <img
          v-for="(item, index) of photoList"
          class="photo"
          :key="index"
          :src="item"
          @click="preview(index)"
        /> -->
        <v-card class="animated zoomIn article-item-card photo" v-for="item of photoList" :key="item.id">
            <div class="article-item-cover ">
              <router-link :to="'/articles/' + 1">
                <!-- 缩略图 -->
                <v-img
                  class="on-hover"
                  :key="index"
                  :src="item"
                  @click="preview(index)"
                />
              </router-link>
            </div>
            <div class="article-item-info ">
              <!-- 卡片标题 -->
              <div>
                <router-link :to="'/articles/' + item.id">
                  【轻云官方】关于轻云社区的一些想法
                </router-link>
              </div>
              <div style="margin-top:0.375rem">
                <!-- 发表时间 -->
                <v-icon size="20">mdi-clock-outline</v-icon>
                 2021-12-07
                <!-- 卡片分类 -->
                <router-link
                  :to="'/categories/' + item.categoryId"
                  class="float-right"
                >
                  <v-icon>mdi-bookmark</v-icon>卡片动态
                </router-link>
              </div>
            </div>
            <!-- 分割线 -->
            <v-divider></v-divider>
            <!-- 卡片标签 -->
            <div class="tag-wrapper">
              <router-link
                :to="'/tags/' + tag.id"
                class="tag-btn"
                v-for="tag of item.tagDTOList"
                :key="tag.id"
              >
                官方
              </router-link>
            </div>
        </v-card>
      </div>
      <!-- 无限加载 -->
      <infinite-loading @infinite="infiniteHandler">
        <div slot="no-more" />
        <div slot="no-results" />
      </infinite-loading>
    </div>
  </div>
</template>

<script>
export default {
  data: function() {
    return {
      photoAlbumName: "",
      photoAlbumCover: "",
      photoList: [],
      current: 1,
      size: 10
    };
  },
  methods: {
    preview(index) {
      this.$imagePreview({
        images: this.photoList,
        index: index
      });
    },
    infiniteHandler($state) {
      this.axios
        .get("/api/albums/" + this.$route.params.albumId + "/photos", {
          params: {
            current: this.current,
            size: this.size
          }
        })
        .then(({ data }) => {
            console.log(data.data);
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
  },
  computed: {
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
.photo-wrap {
  display: flex;
  flex-wrap: wrap;
}
.photo {
  margin: 3px;
  cursor: pointer;
  flex-grow: 1;
  object-fit: cover;
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
  height: 220px;
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
  display: inline-block;
  font-size: 0.725rem;
  line-height: 22px;
  height: 22px;
  border-radius: 10px;
  padding: 0 12px !important;
  background: linear-gradient(to right, #bf4643 0%, #6c9d8f 100%);
  opacity: 0.6;
  margin-right: 0.5rem;
}

</style>
