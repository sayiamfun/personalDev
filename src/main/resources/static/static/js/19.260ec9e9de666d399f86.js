webpackJsonp([19],{LvlE:function(e,t){},PqcZ:function(e,t,s){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=s("y/jF"),a=s("T452"),i=s("dybo"),o=s("Y8t/"),l={name:"taskMessageSend",data:function(){return{current:1,seeCon:!1,pagesize:20,total:0,currentPage:1,minilist:[],noData:!1,listOne:[{title:"发送时间"},{title:"发送任务"},{title:"发送内容"},{title:"发送状态"},{title:"操作"}],width:"width01",con:"",reContent:[]}},created:function(){var e=this;Object(o.c)("/personalNoTaskMessageSend/1/20/",{}).then(function(t){console.log(t),t.code===a.a?e.$message({type:"error",message:t.message}):(e.minilist=t.data.records,e.total=t.data.total)})},components:{Loading:n.a,"v-pagination":i.a},methods:{go:function(e){var t=this;console.log(e);Object(o.c)("/personalNoTaskMessageSend/"+e+"/20/",{}).then(function(e){console.log(e),t.minilist=e.data.records})},pagechange:function(e){var t=this;console.log(e);Object(o.c)("/personalNoTaskMessageSend/"+e+"/20/",{}).then(function(e){console.log(e),t.minilist=e.data.records})},seeSendCon:function(e,t){console.log(e),console.log(t),this.seeCon=!this.seeCon,this.con=t,this.reContent=e},addSend:function(){this.$router.push({path:"/copyTaskSendMessage",query:{error:0}})},copyTaskSendMessage:function(e){this.$router.push({path:"/copyTaskSendMessage",query:{error:1,id:e.id}})},editDeletePublic:function(e){this.$router.push({path:"/copyTaskSendMessage",query:{error:2,id:e.id}})},handleSizeChange:function(e){this.pagesize=e},handleCurrentChange:function(e){console.log("当前页: "+e),this.currentPage=e}}},r={render:function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"taskMessageSend"},[s("div",{staticClass:"sendRecord"},[s("span",{staticClass:"sendRecordO"},[e._v("发送记录")]),e._v(" "),s("span",{staticClass:"addSendRecord",on:{click:e.addSend}},[e._v("增加")])]),e._v(" "),s("ul",{staticClass:"groupMingDan"},e._l(e.listOne,function(t,n){return s("li",{key:n,class:e.width},[e._v(e._s(t.title))])}),0),e._v(" "),e.minilist.length>0?s("div",{staticClass:"clearfix"},[s("ul",{ref:"groupMinWrapper",staticClass:"groupMin-wrapper"},e._l(e.minilist.slice((e.currentPage-1)*e.pagesize,e.currentPage*e.pagesize),function(t,n){return s("li",{key:n,staticClass:"groupMinLi-wrapper"},[s("ul",{staticClass:"groupMinDanCon"},[s("li",{staticClass:"groupFileName",attrs:{title:t.sendTime}},[e._v("\n                "+e._s(t.sendTime)+"\n              ")]),e._v(" "),s("li",[e._v(e._s(t.personalNoTaskMessageName)+".")]),e._v(" "),t.personalNoTaskMessageContentShow?s("li",{staticClass:"hoverCon",on:{click:function(s){return e.seeSendCon(t.personalNoTaskMessageSendContentList,t.personalNoTaskMessageContentShow)}}},[e._v(e._s(t.personalNoTaskMessageContentShow))]):s("li",[e._v("--")]),e._v(" "),t.personaNolTaskMessageStatus?s("li",[e._v(e._s(t.personaNolTaskMessageStatus))]):s("li",[e._v("--")]),e._v(" "),s("li",{staticClass:"miniHover"},[s("span",{staticClass:"miniSpa1",on:{click:function(s){return e.copyTaskSendMessage(t)}}},[e._v("\n                  复制\n                ")]),e._v(" "),s("span",{staticClass:"miniSpa1",on:{click:function(s){return e.editDeletePublic(t)}}},[e._v("\n                  修改\n                ")])])])])}),0),e._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:e.minilist.length,expression:"minilist.length"}],staticClass:"paging-wrapper"},[s("v-pagination",{directives:[{name:"show",rawName:"v-show",value:e.minilist.length,expression:"minilist.length"}],attrs:{total:e.total,"current-page":e.current,num:e.total},on:{pagechange:e.pagechange,go:e.go}})],1)]):s("div",{staticClass:"noData"},[e._v("暂无数据")]),e._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:e.noData,expression:"noData"}]},[s("loading")],1),e._v(" "),s("el-dialog",{attrs:{title:e.con,center:"",visible:e.seeCon,width:"30%"},on:{"update:visible":function(t){e.seeCon=t}}},[s("div",{staticClass:"diaSeeCon"},e._l(e.reContent,function(t,n){return s("div",{key:n},["文字"===t.contentType?s("div",{staticClass:"word"},[s("i",{staticClass:"el-icon-edit"}),e._v(" "),s("span",{staticStyle:{"padding-left":"20px","box-sizing":"border-box"}},[e._v(e._s(t.content))])]):"图片"===t.contentType?s("div",{staticClass:"img"},[s("i",{staticClass:"el-icon-picture-outline"}),e._v(" "),s("img",{staticStyle:{"padding-left":"20px","box-sizing":"border-box"},attrs:{src:t.content}})]):s("div",{staticClass:"group"},[s("i",{staticClass:"el-icon-tickets"}),e._v(" "),s("span",{staticStyle:{"padding-left":"20px","box-sizing":"border-box"}},[e._v("cy思维训练")])])])}),0)])],1)},staticRenderFns:[]};var c=s("C7Lr")(l,r,!1,function(e){s("LvlE"),s("vBR3")},"data-v-08d34150",null);t.default=c.exports},vBR3:function(e,t){}});