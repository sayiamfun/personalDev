webpackJsonp([27],{c41N:function(e,t){},t1Ye:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=n("T452"),a=n("dybo"),i=n("Y8t/"),o={name:"keyWordSet",data:function(){return{qianThree:[],seeCon:!1,total:0,current:1,currentPage:1,TaskData:[],pagesize:20,reContent:[],wordNum:0,picNum:0,tempList:[],deleteId:"",deleteShow:!1}},components:{"v-pagination":a.a},inject:["reload"],created:function(){var e=this;Object(i.b)("/personalNoKeyword/-1/1/20/").then(function(t){console.log(t),0===t.code?(e.TaskData=t.data.records,e.total=t.data.total):e.$message({type:"error",message:"数据走丢了，请稍后重试！！"})}),Object(i.b)("/personalNoKeyword/getStart").then(function(t){0===t.code?e.qianThree=t.data:e.$message({type:"error",message:"数据走丢了，请稍后重试！！"})})},methods:{replyCon:function(e){this.seeCon=!this.seeCon,this.tempList=e.keywordContentList},newAddKeyWord:function(){this.$router.push({path:"/addKeyWord",query:{error:0}})},closeGan:function(e){var t=this;Object(i.b)("/personalNoKeyword/on/"+e.id).then(function(e){console.log(e),e.code===s.a?t.$message({type:"error",message:e.message}):(t.$message({type:"success",message:e.message}),t.reload())})},editCon:function(e){this.$router.push({path:"/addKeyWord",query:{id:e.id,error:1}})},closeKey:function(e){},deleteCon:function(e){this.deleteShow=!this.deleteShow,this.deleteId=e.id},deleteSure:function(){var e=this;Object(i.a)("/personalNoKeyword/"+this.deleteId).then(function(t){console.log(t),t.code===s.a?e.$message({type:"error",message:t.message}):(e.$message({type:"success",message:t.message}),e.reload())})},go:function(e){var t=this;console.log(e);Object(i.c)("/personalNoKeyword/-1/"+e+"/20/",{}).then(function(e){console.log(e),t.TaskData=e.records})},pagechange:function(e){var t=this;console.log(e);Object(i.c)("/personalNoKeyword/-1/"+e+"/20/",{}).then(function(e){console.log(e),t.TaskData=e.data.records})}}},l={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"keyWordSet"},[n("div",{staticClass:"golbalInfo-add",on:{click:e.newAddKeyWord}},[e._v("新   增")]),e._v(" "),n("div",{staticClass:"dataTable"},[e._m(0),e._v(" "),n("ul",{staticClass:"xunDataUl"},[e._l(e.qianThree,function(t,s){return n("li",{key:"info2-"+s},[n("ul",{staticClass:"xunDataUlChildren"},[n("li",{staticClass:"liKey",staticStyle:{"text-align":"left"},attrs:{title:t.keyword}},[e._v("‘"+e._s(t.keyword)+"’")]),e._v(" "),n("li",{staticClass:"conHover",staticStyle:{flex:"3","text-align":"left"},on:{click:function(n){return e.replyCon(t)}}},[e._v(e._s(t.keywordContentShow))]),e._v(" "),n("li",[e._v(e._s(t.nickName))]),e._v(" "),n("li"),e._v(" "),n("li"),e._v(" "),n("li"),e._v(" "),n("li",{staticClass:"opreate"},[n("span",{on:{click:function(n){return e.editCon(t)}}},[e._v("编辑")]),e._v(" "),1===t.deleted?n("span",{on:{click:function(n){return e.closeGan(t)}}},[e._v("开启")]):n("span",{on:{click:function(n){return e.closeGan(t)}}},[e._v("关闭")])])])])}),e._v(" "),e._l(e.TaskData.slice((e.currentPage-1)*e.pagesize,e.currentPage*e.pagesize),function(t,s){return e.TaskData.length?n("li",{key:s},[n("ul",{staticClass:"xunDataUlChildren"},[n("li",{staticClass:"liKey",staticStyle:{"text-align":"left"},attrs:{title:t.keyword}},[e._v("‘"+e._s(t.keyword)+"’")]),e._v(" "),n("li",{staticClass:"conHover",staticStyle:{flex:"3","text-align":"left"},on:{click:function(n){return e.replyCon(t)}}},[e._v(e._s(t.keywordContentShow))]),e._v(" "),n("li",[e._v(e._s(t.nickName))]),e._v(" "),n("li"),e._v(" "),n("li"),e._v(" "),n("li"),e._v(" "),n("li",{staticClass:"opreate"},[n("span",{on:{click:function(n){return e.editCon(t)}}},[e._v("编辑")]),e._v(" "),n("span",{on:{click:function(n){return e.deleteCon(t)}}},[e._v("删除")])])])]):n("span",{staticClass:"noData"},[e._v("暂无数据")])})],2),e._v(" "),n("v-pagination",{attrs:{total:e.total,"current-page":e.current,num:e.total},on:{pagechange:e.pagechange,go:e.go}})],1),e._v(" "),n("el-dialog",{attrs:{title:"回复内容",center:"",visible:e.seeCon,width:"30%"},on:{"update:visible":function(t){e.seeCon=t}}},[n("div",{staticClass:"diaSeeCon"},e._l(e.tempList,function(t,s){return n("div",{key:s},["文字"===t.contentType?n("div",{staticClass:"word"},[n("i",{staticClass:"el-icon-edit"}),e._v(" "),n("span",{staticStyle:{"padding-left":"20px","box-sizing":"border-box"}},[e._v(e._s(t.content))])]):"图片"===t.contentType?n("div",{staticClass:"img"},[n("i",{staticClass:"el-icon-picture-outline"}),e._v(" "),n("img",{staticStyle:{"padding-left":"20px","box-sizing":"border-box"},attrs:{src:t.content}})]):"邀请入群"===t.contentType?n("div",{staticClass:"group"},[n("i",{staticClass:"el-icon-tickets"}),e._v(" "),n("span",{staticStyle:{"padding-left":"20px","box-sizing":"border-box"}},[e._v(e._s(t.content))])]):"小程序"===t.contentType?n("div",{staticClass:"group",staticStyle:{width:"100%",overflow:"hidden"}},[n("i",{staticClass:"el-icon-tickets"}),e._v(" "),n("span",{staticStyle:{"padding-left":"20px","box-sizing":"border-box"}},[e._v(e._s(t.content))])]):e._e()])}),0)]),e._v(" "),n("el-dialog",{attrs:{title:"确定删除此关键词？",center:"",visible:e.deleteShow,width:"30%"},on:{"update:visible":function(t){e.deleteShow=t}}},[n("div",{staticClass:"footer"},[n("span",{staticClass:"faqi",on:{click:e.deleteSure}},[e._v("确 定")]),e._v(" "),n("span",{staticClass:"quxiao",on:{click:function(t){e.deleteShow}}},[e._v("取 消")])])])],1)},staticRenderFns:[function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("ul",{staticClass:"data-ul"},[n("li",{staticStyle:{flex:"2","text-align":"left"}},[e._v("关键词")]),e._v(" "),n("li",{staticStyle:{flex:"3","text-align":"left"}},[e._v("回复内容")]),e._v(" "),n("li",[e._v("个人号昵称")]),e._v(" "),n("li"),e._v(" "),n("li"),e._v(" "),n("li"),e._v(" "),n("li"),e._v(" "),n("li",[e._v("操作")])])}]};var c=n("C7Lr")(o,l,!1,function(e){n("c41N")},"data-v-0d98ab71",null);t.default=c.exports}});