(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-be377388"],{9851:function(t,e,a){"use strict";a("9c13")},"9c13":function(t,e,a){},bb51:function(t,e,a){"use strict";a.r(e);var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("div",{staticClass:"banner",style:t.cover},[a("h1",{staticClass:"banner-title"},[t._v("分类")])]),a("v-card",{staticClass:"blog-container"},[a("div",{staticClass:"category-title"},[t._v("分类 - "+t._s(t.count))]),a("ul",{staticClass:"category-list"},t._l(t.categoryList,(function(e){return a("li",{key:e.id,staticClass:"category-list-item"},[a("router-link",{attrs:{to:"/categories/"+e.id}},[t._v(" "+t._s(e.categoryName)+" "),a("span",{staticClass:"category-count"},[t._v("("+t._s(e.articleCount)+")")])])],1)})),0)])],1)},c=[],i=(a("d8ae"),a("3abb"),{created:function(){this.listCategories()},data:function(){return{categoryList:[],count:0}},methods:{listCategories:function(){var t=this;this.axios.get("/api/categories").then((function(e){var a=e.data;t.categoryList=a.data.recordList,t.count=a.data.count}))}},computed:{cover:function(){var t="";return this.$store.state.blogInfo.pageList.forEach((function(e){"categories"==e.pageLabel&&(t=e.pageCover)})),"background: url("+t+") center center / cover no-repeat"}}}),n=i,r=(a("9851"),a("cba8")),o=a("72e9"),u=a.n(o),l=a("9748"),d=Object(r["a"])(n,s,c,!1,null,"398e29af",null);e["default"]=d.exports;u()(d,{VCard:l["a"]})}}]);