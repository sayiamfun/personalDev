webpackJsonp([25],{bxeC:function(e,t){},"er/J":function(e,t,s){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=s("T452"),i=s("y/jF"),o=s("Y8t/"),n={name:"firendFansNum",data:function(){return{blackHaoYou:!1,deleteHaoYou:!1,noLoading:!1,pagesize:20,total:0,currentPage:1,minilist:[],allFansChecked:!1,hao:[],hei:[],personNoNickName:"",personalNoCategoryName:"",friendsNum:"",equipmentStatus:"",equipmentId:""}},inject:["reload"],created:function(){var e=this;this.personNoNickName=this.$route.query.nickName,this.personalNoCategoryName=this.$route.query.personalNoCategoryName,this.friendsNum=this.$route.query.friendsNum,this.equipmentStatus=this.$route.query.equipmentStatus,this.equipmentId=this.$route.query.equipmentId,Object(o.b)("/personalNoFriends/"+this.$route.query.wxId+"/1/20000/").then(function(t){console.log(t),t.code===a.a?e.$message({type:"error",message:t.message}):(e.minilist=t.data,e.total=e.minilist.length)})},methods:{handleSizeChange:function(e){console.log(e),this.pagesize=e},handleCurrentChange:function(e){console.log("当前页: "+e),this.currentPage=e},deleteFriends:function(e){this.hao=e,this.deleteHaoYou=!this.deleteHaoYou},dialogDelete:function(){var e=this,t=[];t.push(this.hao),Object(o.c)("/personalNoFriends/delete/"+this.$route.query.wxId+"/",t).then(function(t){console.log(t),t.code===a.a?e.$message({type:"error",message:t.message}):(e.$message({type:"success",message:t.message}),e.deleteHaoYou=!e.deleteHaoYou,e.reload())})},blackListFriends:function(e){this.hei=e,this.blackHaoYou=!this.blackHaoYou},dialogBlack:function(){var e=this;Object(o.c)("/personalNoFriends/black/"+this.$route.query.wxId+"/",this.hei).then(function(t){console.log(t),t.code===a.a?e.$message({type:"error",message:t.message}):(e.$message({type:"success",message:t.message}),e.blackHaoYou=!e.blackHaoYou,e.reload())})}},components:{Loading:i.a}},l={render:function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"firendFansNum"},[s("div",{staticClass:"personIntr"},[s("div",{staticClass:"person-title"},[e._v("个人号简介")]),e._v(" "),s("ul",{staticClass:"personUl"},[s("li",[e._v("名称: "+e._s(e.personNoNickName))]),e._v(" "),s("li",[e._v("类别："+e._s(e.personalNoCategoryName))]),e._v(" "),s("li",[e._v("好友人数： "+e._s(e.friendsNum))]),e._v(" "),s("li",[e._v("工作情况："+e._s(e.equipmentStatus))])]),e._v(" "),s("ul",{staticClass:"task"},[s("li",{staticStyle:{flex:"3"}},[e._v("当前任务：L3.12脑力训练营")]),e._v(" "),s("li",{staticStyle:{flex:"1"}},[e._v("设备ID："+e._s(e.equipmentId))])])]),e._v(" "),e._m(0),e._v(" "),s("div",{staticClass:"friendsList"},[e.minilist.length>0?s("div",[e._m(1),e._v(" "),s("ul",{staticClass:"m-table-tbody"},e._l(e.minilist.slice((e.currentPage-1)*e.pagesize,e.currentPage*e.pagesize),function(t,a){return s("li",{key:a,staticClass:"body-li"},[s("ul",{staticClass:"m-table-row"},[s("li",[s("input",{attrs:{type:"checkbox"},domProps:{checked:e.allFansChecked}})]),e._v(" "),s("li",[s("img",{staticClass:"head-img",attrs:{src:t.headPortrait,alt:""}})]),e._v(" "),s("li",[e._v(e._s(t.nickName))]),e._v(" "),s("li",[e._v(e._s(t.createTime))]),e._v(" "),s("li",[s("div",{staticClass:"operate-list"},[s("span",{staticClass:"operate-btn",on:{click:function(s){return e.deleteFriends(t)}}},[s("em",[e._v("删除好友")])]),e._v(" "),s("span",{staticClass:"operate-btn",on:{click:function(s){return e.blackListFriends(t)}}},[s("em",[e._v("拉入黑名单")])])])])])])}),0)]):s("div",{staticClass:"noData"},[e._v("暂无数据")]),e._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:e.noLoading,expression:"noLoading"}]},[s("loading")],1)]),e._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:e.minilist.length,expression:"minilist.length"}],staticClass:"paging-wrapper"},[s("el-pagination",{attrs:{"current-page":e.currentPage,"page-sizes":[5,10,15,20,50,100],"page-size":e.pagesize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1),e._v(" "),s("el-dialog",{attrs:{title:"确定删除此好友吗",width:"30%",center:"",visible:e.deleteHaoYou},on:{"update:visible":function(t){e.deleteHaoYou=t}}},[s("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[s("el-button",{attrs:{type:"primary"},on:{click:e.dialogDelete}},[e._v("确 定")]),e._v(" "),s("el-button",{on:{click:function(t){e.deleteHaoYou=!1}}},[e._v("取 消")])],1)]),e._v(" "),s("el-dialog",{attrs:{title:"确定拉黑此好友吗",width:"30%",center:"",visible:e.blackHaoYou},on:{"update:visible":function(t){e.blackHaoYou=t}}},[s("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[s("el-button",{attrs:{type:"primary"},on:{click:e.dialogBlack}},[e._v("确 定")]),e._v(" "),s("el-button",{on:{click:function(t){e.blackHaoYou=!1}}},[e._v("取 消")])],1)])],1)},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"list"},[t("span",{staticStyle:{flex:"1"}},[this._v("好友列表")])])},function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"m-table-head"},[s("ul",{staticClass:"m-table-row"},[s("li",[s("input",{attrs:{type:"checkbox"}})]),e._v(" "),s("li",[e._v("头像")]),e._v(" "),s("li",[e._v("昵称")]),e._v(" "),s("li",[e._v("加好友时间")]),e._v(" "),s("li",[e._v("操作")])])])}]};var r=s("C7Lr")(n,l,!1,function(e){s("bxeC")},"data-v-313a602e",null);t.default=r.exports}});