webpackJsonp([20],{"/PWI":function(e,t){},A2W3:function(e,t){},B0Ef:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=a("y/jF"),i=a("T452"),s=a("hUhi"),l={name:"channelManger",data:function(){return{deleteDio:!1,title:"",create_name:"jiazhangjia2",create_time:"",chaName:!1,remas:!1,pagesize:20,total:0,currentPage:1,listOne:[{title:"渠道名称"},{title:"创建人"},{title:"创建时间"},{title:"操作"}],newAddChannel:!1,minilist:[],noData:!1,width:"width01",channelName:"",remarks:"",diaShow:"",editId:"",deleteId:"",dateTime:"",superuserId:""}},inject:["reload"],created:function(){var e=this;this.noData=!0,this.$http.get(i.d+"/channelManger").then(function(t){console.log(t),0===t.body.code?(e.noData=!1,e.minilist=Object(s.a)(t,e).body.data,e.total=e.minilist.length):e.$message({type:"error",message:t.body.message})})},methods:{deleteSureChannel:function(){var e=this;this.$http.delete(i.d+"/channelManger/"+this.deleteId+"/").then(function(t){t.body.code===i.a?e.$message({type:"error",message:t.body.message}):(e.$message({type:"success",message:"删除"+t.body.message}),e.reload())})},deleteCancelChannel:function(){},channelNewAdd:function(){this.newAddChannel=!this.newAddChannel,this.diaShow=0,this.title="新增渠道管理";var e=(new Date).getTime(),t=new Date,a=t.getFullYear(),n=t.getMonth()+1,i=t.getDate(),s=t.getHours(),l=t.getMinutes(),r=t.getSeconds();n>=1&&n<=9&&(n="0"+n),i>=0&&i<=9&&(i="0"+i),this.create_time=a+"-"+n+"-"+i+" "+s+":"+l+":"+r,console.log("当前时间戳是："+e),console.log("当前时间是："+this.create_time),this.dateTime=e},handleSizeChange:function(e){console.log(e),this.pagesize=e},handleCurrentChange:function(e){console.log("当前页: "+e),this.currentPage=e},editChannel:function(e){console.log(e),this.channelName=e.channelName,this.remarks=e.remarks,this.editId=e.id,this.create_name=e.superName,this.create_time=e.createTime,this.newAddChannel=!this.newAddChannel,this.diaShow=1,this.title="修改渠道管理"},seeChannel:function(e){this.channelName=e.channelName,this.remarks=e.remarks,this.editId=e.id,this.create_name=e.superName,this.create_time=e.createTime,this.newAddChannel=!this.newAddChannel,this.diaShow=2,this.chaName=!0,this.remas=!0,this.title="查看渠道管理"},deleteChannel:function(e){this.deleteId=e.id,this.deleteDio=!this.deleteDio},sureChannel:function(){var e=this;if(0===this.diaShow){var t={channelName:this.channelName,remarks:this.remarks,superId:this.superuserId,superName:this.create_name,createTime:this.create_time,id:-1};this.$http.post(i.d+"/channelManger/addChannel",t).then(function(t){console.log(t),t.body.code===i.a?e.$message({type:"error",message:"新增渠道"+t.body.message}):(e.$message({type:"success",message:"新增渠道"+t.body.message}),e.newAddChannel=!e.newAddChannel,e.reload())})}else if(1===this.diaShow){var a={channelName:this.channelName,remarks:this.remarks,superId:4,superName:this.create_name,createTime:this.dateTime,id:this.editId};this.$http.post(i.d+"/channelManger/addChannel",a).then(function(t){console.log(t),t.code===i.a?e.$message({type:"error",message:t.body.message}):(e.$message({type:"success",message:t.body.message}),e.newAddChannel=!e.newAddChannel,e.reload())})}else this.diaShow},cancelChannel:function(){this.newAddChannel=!this.newAddChannel,this.channelName="",this.remarks="",this.chaName=!1,this.remas=!1}},components:{Loading:n.a},watch:{newAddChannel:function(e,t){!0===t&&(this.chaName=!1,this.remas=!1,this.channelName="",this.remarks="")}}},r={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"channelManger"},[a("div",{staticClass:"channelAdd"},[a("span",{staticClass:"adver-NewAdd",on:{click:e.channelNewAdd}},[a("i",{staticClass:"el-icon-plus"}),e._v("\n      新增")])]),e._v(" "),a("div",{staticClass:"groupCategory"},[a("ul",{staticClass:"groupMingDan"},e._l(e.listOne,function(t,n){return a("li",{key:n,class:e.width},[e._v(e._s(t.title))])}),0)]),e._v(" "),e.minilist.length>0?a("div",[a("ul",{staticClass:"groupMin-wrapper"},e._l(e.minilist.slice((e.currentPage-1)*e.pagesize,e.currentPage*e.pagesize),function(t,n){return a("li",{key:n,staticClass:"groupMinLi-wrapper"},[a("ul",{staticClass:"groupMinDanCon"},[a("li",{staticClass:"groupFileName",attrs:{title:t.channelName}},[e._v(e._s(t.channelName))]),e._v(" "),a("li",{staticStyle:{padding:"0px 20px","box-sizing":"border-box"},attrs:{title:t.superName}},[e._v(e._s(t.superName))]),e._v(" "),a("li",{staticClass:"hoverSeeCon",attrs:{title:t.createTime}},[e._v(e._s(t.createTime))]),e._v(" "),a("li",{staticClass:"miniHover"},[a("span",{staticClass:"miniSpa1",on:{click:function(a){return e.editChannel(t)}}},[a("i",{staticClass:"iconfont icon-xiugai",staticStyle:{"vertical-align":"middle"}}),e._v("修改\n            ")]),e._v(" "),a("span",{staticClass:"miniSpa1",on:{click:function(a){return e.seeChannel(t)}}},[a("i",{staticClass:"iconfont icon-yanjing",staticStyle:{"vertical-align":"middle"}}),e._v("查看\n            ")]),e._v(" "),a("span",{staticClass:"miniSpa1",on:{click:function(a){return e.deleteChannel(t)}}},[a("i",{staticClass:"iconfont icon-jiufuqianbaoicon05",staticStyle:{"vertical-align":"middle"}}),e._v("删除\n            ")])])])])}),0),e._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:e.minilist.length,expression:"minilist.length"}],staticClass:"paging-wrapper"},[a("el-pagination",{attrs:{"current-page":e.currentPage,"page-sizes":[5,10,15,20],"page-size":e.pagesize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1)]):a("div",{staticClass:"noData"},[e._v("暂无数据")]),e._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:e.noData,expression:"noData"}]},[a("loading")],1),e._v(" "),a("el-dialog",{attrs:{title:e.title,width:"30%",center:"",visible:e.newAddChannel},on:{"update:visible":function(t){e.newAddChannel=t}}},[a("div",{staticClass:"newdialog"},[a("el-form",[a("el-form-item",{attrs:{label:"渠道名称：","label-width":"100px"}},[a("el-input",{attrs:{"aria-placeholder":"渠道名称",disabled:e.chaName},model:{value:e.channelName,callback:function(t){e.channelName=t},expression:"channelName"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"创建人：","label-width":"100px"}},[a("span",[e._v(e._s(e.create_name))])]),e._v(" "),a("el-form-item",{attrs:{label:"创建时间：","label-width":"100px"}},[a("span",[e._v(e._s(e.create_time))])]),e._v(" "),a("el-form-item",{attrs:{label:"备注：","label-width":"100px"}},[a("el-input",{attrs:{disabled:e.remas},model:{value:e.remarks,callback:function(t){e.remarks=t},expression:"remarks"}})],1)],1)],1),e._v(" "),0===e.diaShow||1===e.diaShow?a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{staticClass:"orange",attrs:{type:"primary"},on:{click:e.sureChannel}},[e._v("确 定")]),e._v(" "),a("el-button",{on:{click:e.cancelChannel}},[e._v("取 消")])],1):a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:e.cancelChannel}},[e._v("取 消")])],1)]),e._v(" "),a("el-dialog",{attrs:{title:"是否删除本条渠道",width:"20%",center:"",visible:e.deleteDio},on:{"update:visible":function(t){e.deleteDio=t}}},[a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{staticClass:"orange",attrs:{type:"primary"},on:{click:e.deleteSureChannel}},[e._v("确 定")]),e._v(" "),a("el-button",{on:{click:e.deleteCancelChannel}},[e._v("取 消")])],1)])],1)},staticRenderFns:[]};var o=a("C7Lr")(l,r,!1,function(e){a("/PWI"),a("A2W3")},"data-v-14b10950",null);t.default=o.exports}});