webpackJsonp([10],{"/TMn":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=a("y/jF"),n=a("T452"),i=a("dybo"),o=a("8EyE"),l=a("Y8t/"),r={name:"personalManger",data:function(){return{current:1,editId:"",id:"",personInfo:"",relayManger:!1,newClassVal:"",innerPerson:!1,minilist:[],pagesize:20,total:0,currentPage:1,noData:!1,nickName:"",rootId:"",classManger:!1,options:[],opt:[{label:"所有",value:"所有"},{label:"线上",value:"线上"},{label:"加满好友",value:"加满好友"},{label:"暂停服务",value:"暂停服务"},{label:"断开",value:"断开"},{label:"封禁",value:"封禁"},{label:"下线",value:"下线"}],listOne:[{title:"头像"},{title:"设备ID"},{title:"昵称"},{title:"创建时间"},{title:"好友人数"},{title:"销售组"},{title:"待通过好友人数"},{title:"设备状态"},{title:"个人号类别"},{title:"操作"}],listDia:[{name:"家长家"},{name:"JZJ+no2"}],width:"width01",personClass:"",personRboot:"",newPerson:!1,avatar:"",userNickName:"",userRootId:"",listNum:[]}},inject:["reload"],created:function(){var e=this;this.noData=!0;Object(l.c)("/personalManager/1/20/",{}).then(function(t){t.code===n.a?e.$message({type:"error",message:"个人号列表请求"+t.message}):(e.noData=!1,e.minilist=t.data.records,e.total=t.data.total)}),Object(l.b)("/personalCategoryManager").then(function(t){console.log(t),e.options=t.data}),Object(l.b)("/personalManager/Num").then(function(t){t.code===n.a?e.$message({type:"error",message:t.message}):e.listNum=t.data})},methods:{go:function(e){var t=this;console.log(e);Object(l.c)("/personalManager/"+e+"/20/",{}).then(function(e){console.log(e),t.minilist=e.data.records})},pagechange:function(e){var t=this;console.log(e);Object(l.c)("/personalManager/"+e+"/20/",{}).then(function(e){console.log(e),t.minilist=e.data.records})},goFileName:function(e){this.$router.push({path:"/firendFansNum",query:{wxId:e.wxId,nickName:e.nickname,personalNoCategoryName:e.personalNoCategoryName,friendsNum:e.friendsNum,equipmentStatus:e.equipmentStatus,equipmentId:e.equipmentId}})},relayEidt:function(e){console.log(e),this.relayManger=!this.relayManger,this.avatar=e.headPortraitUrl,this.userNickName=e.nickname,this.userRootId=e.equipmentId,this.id=e.id,this.personInfo=e.personalNoCategory},relaySurePerson:function(){var e=this,t={headPortraitUrl:this.avatar,nickname:this.userNickName,equipmentId:this.userRootId,personalNoCategory:this.personInfo};Object(l.d)("/personalManager/updatePersonal/"+this.id+"/",t).then(function(t){console.log(t),t.code===n.a?e.$message({type:"error",message:"修改"+t.message}):(e.$message({type:"success",message:"修改"+t.message}),e.relayManger=!e.relayManger)})},PersonChangeInfo:function(e){console.log(e),this.personInfo=e},relayCancelPerson:function(){this.relayManger=!this.relayManger},rootChange:function(e){console.log(e),this.personRboot=e},personClassChange:function(e){this.personClass=e},personSea:function(){var e=this,t={nickname:this.nickName,equipmentId:this.rootId,equipmentStatus:this.personRboot,personalNoCategoryName:this.personClass};Object(l.c)("/personalManager/1/20/",t).then(function(t){console.log(t),t.code===n.a?e.$message({type:"error",message:"啊偶，没有查到数据👉"}):(console.log(t),e.minilist=t.data.records,e.total=t.data.total)})},personclassManger:function(){var e=this;this.classManger=!this.classManger,Object(l.b)("/personalCategoryManager").then(function(t){e.listDia=t.data}).catch(function(){e.$message({type:"error",message:"服务器出问题了!"})})},mangerNewAdd:function(){this.innerPerson=!this.innerPerson,this.changeVal=0,this.newClassVal=""},surePerson:function(){this.classManger=!this.classManger,this.reload()},cancelPerson:function(){this.classManger=!this.classManger},newSurePerson:function(){var e=this;this.innerPerson=!this.innerPerson;var t={};t=1===this.changeVal?{personalNoCategory:this.newClassVal,id:this.editId}:{superId:Object(o.b)("p_superId"),personalNoCategory:this.newClassVal,id:""},Object(l.c)("/personalCategoryManager/addCategory",t).then(function(t){t.code===n.a?e.$message({type:"error",message:"类别修改"+t.message}):(e.$message({type:"success",message:"类别修改"+t.message}),Object(l.b)("/personalCategoryManager").then(function(t){e.listDia=t.data}).catch(function(){e.$message({type:"error",message:"服务器出问题了!"})}))})},newCancelPerson:function(){this.innerPerson=!this.innerPerson,this.newClassVal=""},editperManger:function(e){this.changeVal=1,this.innerPerson=!this.innerPerson,this.editId=e.id,this.newClassVal=e.personalNoCategory},deleteManger:function(e){var t=this;Object(l.a)("/personalCategoryManager/"+e.id+"/").then(function(e){e.code===n.a?t.$message({type:"error",message:"删除类别"+e.message}):(t.$message({type:"success",message:"删除类别"+e.message}),Object(l.b)("/personalCategoryManager").then(function(e){t.listDia=e.data}).catch(function(){t.$message({type:"error",message:"服务器出问题了!"})}))})}},components:{Loading:s.a,"v-pagination":i.a}},c={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"personalManger"},[a("div",{staticClass:"personSearch",staticStyle:{"padding-bottom":"20px"}},[a("div",{staticClass:"person_nickName"},[a("label",[e._v("个人号昵称:")]),e._v(" "),a("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"输入个人号昵称查询"},model:{value:e.nickName,callback:function(t){e.nickName=t},expression:"nickName"}})],1),e._v(" "),a("div",{staticClass:"person_nickName"},[a("label",[e._v("设 备 I D:")]),e._v(" "),a("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"输入设备ID查询"},model:{value:e.rootId,callback:function(t){e.rootId=t},expression:"rootId"}})],1),e._v(" "),a("div",{staticClass:"personSS",on:{click:e.personSea}},[a("span",[e._v("搜索")])])]),e._v(" "),a("div",{staticClass:"personSearch"},[a("div",{staticClass:"person-class",staticStyle:{width:"40%"}},[a("label",{staticStyle:{"margin-right":"20px"}},[e._v("个人号类别:")]),e._v(" "),a("el-select",{attrs:{placeholder:"请选择"},on:{change:e.personClassChange},model:{value:e.personClass,callback:function(t){e.personClass=t},expression:"personClass"}},e._l(e.options,function(e){return a("el-option",{key:e.id,attrs:{label:e.personalNoCategory,value:e.personalNoCategory}})}),1)],1),e._v(" "),a("div",{staticClass:"person-class",staticStyle:{width:"40%"}},[a("label",{staticStyle:{"margin-right":"20px"}},[e._v("设备状态:")]),e._v(" "),a("el-select",{attrs:{placeholder:"请选择"},on:{change:e.rootChange},model:{value:e.personRboot,callback:function(t){e.personRboot=t},expression:"personRboot"}},e._l(e.opt,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}),1)],1)]),e._v(" "),a("div",{staticClass:"personInfo"},[a("div",{staticClass:"perinfo"},[e._v("个人号信息")]),e._v(" "),a("ul",{staticClass:"personUl"},[a("li",[e._v("个人号总是："+e._s(e.listNum.total)+" 个")]),e._v(" "),a("li",[e._v("合计好友总数："+e._s(e.listNum.friendsNum)+" 人")]),e._v(" "),a("li",[e._v("承担活动总数："+e._s(e.listNum.activityNum)+" 个")])])]),e._v(" "),a("div",{staticClass:"personData"},[a("span",{on:{click:e.personclassManger}},[e._v("类别管理")]),e._v(" "),a("span",[e._v("导出数据")])]),e._v(" "),a("div",{staticClass:"groupCategory"},[a("ul",{staticClass:"groupMingDan"},e._l(e.listOne,function(t,s){return a("li",{key:s,class:e.width},[e._v(e._s(t.title))])}),0)]),e._v(" "),e.minilist.length>0?a("div",[a("ul",{staticClass:"groupMin-wrapper"},e._l(e.minilist.slice((e.currentPage-1)*e.pagesize,e.currentPage*e.pagesize),function(t,s){return a("li",{key:s,staticClass:"groupMinLi-wrapper"},[a("ul",{staticClass:"groupMinDanCon"},[a("li",{staticClass:"groupFileName"},[a("img",{staticStyle:{width:"30px",height:"30px",margin:"0 auto","vertical-align":"middle"},attrs:{src:t.headPortraitUrl,alt:""}})]),e._v(" "),a("li",{staticClass:"hoverPerson",attrs:{title:t.equipmentId}},[e._v(e._s(t.equipmentId))]),e._v(" "),a("li",{staticClass:"hoverPerson",attrs:{title:t.nickname},on:{click:function(a){return e.goFileName(t)}}},[a("span",[e._v(e._s(t.nickname))]),e._v(" "),a("img",{staticClass:"img1",attrs:{src:t.qrCode}})]),e._v(" "),a("li",{attrs:{title:t.createTime}},[e._v(e._s(t.createTime))]),e._v(" "),a("li",[e._v(e._s(t.friendsNum))]),e._v(" "),t.salesGroup?a("li",[e._v(e._s(t.salesGroup))]):a("li",[e._v("--")]),e._v(" "),a("li",[e._v(e._s(t.waitingPassNum))]),e._v(" "),a("li",[e._v(e._s(t.equipmentStatus))]),e._v(" "),a("li",[e._v(e._s(t.personalNoCategory))]),e._v(" "),a("li",{staticClass:"miniHover"},[a("span",{staticClass:"miniSpa1",on:{click:function(a){return e.relayEidt(t)}}},[a("i",{staticClass:"iconfont icon-xiugai",staticStyle:{"vertical-align":"middle"}}),e._v("编辑\n                ")])])])])}),0),e._v(" "),a("v-pagination",{attrs:{total:e.total,"current-page":e.current,num:e.total},on:{pagechange:e.pagechange,go:e.go}})],1):a("div",{staticClass:"noData"},[e._v("暂无数据")]),e._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:e.noData,expression:"noData"}]},[a("loading")],1),e._v(" "),a("el-dialog",{attrs:{title:"更改个人号信息",width:"30%",center:"",visible:e.relayManger},on:{"update:visible":function(t){e.relayManger=t}}},[a("div",{staticClass:"changeCon"},[a("div",{staticClass:"changeHead"},[a("img",{attrs:{src:e.avatar}}),e._v(" "),a("div",{staticClass:"changeWord"},[a("span",[e._v(e._s(e.userNickName))]),e._v(" "),a("span",[e._v(e._s(e.userRootId))])])]),e._v(" "),a("el-form",{staticStyle:{"padding-top":"20px"}},[a("el-form-item",{attrs:{label:"类 别：","label-width":"100px"}},[a("el-select",{attrs:{placeholder:"请选择"},on:{change:e.PersonChangeInfo},model:{value:e.personInfo,callback:function(t){e.personInfo=t},expression:"personInfo"}},e._l(e.options,function(e){return a("el-option",{key:e.value,attrs:{label:e.personalNoCategory,value:e.personalNoCategory}})}),1)],1)],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{staticClass:"orange",attrs:{type:"primary"},on:{click:e.relaySurePerson}},[e._v("确 定")]),e._v(" "),a("el-button",{on:{click:e.relayCancelPerson}},[e._v("取 消")])],1)]),e._v(" "),a("el-dialog",{attrs:{title:"类别管理",center:"",visible:e.classManger},on:{"update:visible":function(t){e.classManger=t}}},[a("div",{staticClass:"classPersonAdd"},[a("span",{staticClass:"clNewAdd",on:{click:e.mangerNewAdd}},[e._v("新增")]),e._v(" "),a("ul",{staticClass:"cl-ul"},[a("li",[e._v("个人号类别")]),e._v(" "),a("li",[e._v("操作")])]),e._v(" "),a("ul",{staticClass:"groupMin-wrapper"},e._l(e.listDia,function(t,s){return a("li",{key:s,staticClass:"groupMinLi-wrapper"},[a("ul",{staticClass:"groupMinDanCon listPer"},[a("li",{staticClass:"groupFileName"},[e._v("\n                  "+e._s(t.personalNoCategory)+"\n                ")]),e._v(" "),a("li",{staticClass:"miniHover"},[a("span",{staticClass:"miniSpa1",on:{click:function(a){return e.editperManger(t)}}},[a("i",{staticClass:"iconfont icon-xiugai",staticStyle:{"vertical-align":"middle"}}),e._v("编辑\n                  ")]),e._v(" "),a("span",{staticClass:"miniSpa1",on:{click:function(a){return e.deleteManger(t)}}},[a("i",{staticClass:"iconfont icon-xiugai",staticStyle:{"vertical-align":"middle"}}),e._v("删除\n                  ")])])])])}),0)]),e._v(" "),a("el-dialog",{attrs:{width:"30%",title:"新增个人号类别",center:"",visible:e.innerPerson,"append-to-body":""},on:{"update:visible":function(t){e.innerPerson=t}}},[a("div",{staticClass:"xinzeng"},[a("label",{staticStyle:{width:"200px","line-height":"40px"}},[e._v("名称：")]),e._v(" "),a("el-input",{model:{value:e.newClassVal,callback:function(t){e.newClassVal=t},expression:"newClassVal"}})],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{staticClass:"orange",attrs:{type:"primary"},on:{click:e.newSurePerson}},[e._v("确 定")]),e._v(" "),a("el-button",{on:{click:e.newCancelPerson}},[e._v("取 消")])],1)]),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{staticClass:"orange",attrs:{type:"primary"},on:{click:e.surePerson}},[e._v("确 定")]),e._v(" "),a("el-button",{on:{click:e.cancelPerson}},[e._v("取 消")])],1)],1)],1)},staticRenderFns:[]};var p=a("C7Lr")(r,c,!1,function(e){a("M4oI"),a("m7MF")},"data-v-89c3de08",null);t.default=p.exports},M4oI:function(e,t){},m7MF:function(e,t){}});