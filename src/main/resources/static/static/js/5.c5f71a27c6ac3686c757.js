webpackJsonp([5],{GiWH:function(e,t){},ORkd:function(e,t,s){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=s("IHPB"),i=s.n(n),a=s("T452"),o=s("Y8t/"),l={name:"copyTagMessageSend",data:function(){return{dragging:[],relapyContentCategoryName:"",groupV:"",groupCategory:[],groupSet:"",groupCategorySet:[],stopAddGroup:!0,fssionIndex:0,fssion:"",fssionList:[{id:0,name:"1号后台"},{id:1,name:"2号后台"}],startOrEndTime:"",isicon:!0,quDaoVal:"请选择类别",list:[],setFindGroupName:"",firstShow:!1,biaoqian:"",checkAll1:!1,checkedCities1:[],isIndeterminate1:!0,checkedCities:[],personHe:"",personNum:"",setList:[],noPersonClass:!1,checkedCitiesLabel1:[],setLabel1:[],setLabel:[],listLableArr:[],kouweiSpan:0,checkedCitiesLabel:[],personLabel:"",personLabelNum:"",noPersonLabel:!1,tagRadio:"根据标签",agree:!1,reContentVal:[{content:"",contentType:"文字"}],loading:!1,sendTiems:"立即发送",groupNei:"群发送",closeShow:!0,closeId:!1,closeIgs:!1,closeLinks:!1,checkedIn:!1,checkedAdd:!1,yulanEWM:!1,yulanEWMs:!1,Dshow:!1,dada1:"",titi1:"",upLoadVal:"",upLoadVals:"",replayNeiRong:"",editDate2:"",editTimeXuan2:"",src:"",srcs:"",firstShowLabel:!1,mingCard:"",addLinks:"",channel:[],noLabel:[],geRen:[],startTime:"",endTime:"",geLeiBie:"根据标签",fuGai:0}},inject:["reload"],created:function(){var e=this;Object(o.b)("/personalLableCategoryManager").then(function(t){t=t.data,Object(o.b)("/lableManager/getLableByCategory/"+t[0].categoryName+"/").then(function(t){e.setLabel=t.data})}),1===this.$route.query.code?this.goP=!1:this.goP=!0,1===this.$route.query.code&&Object(o.c)("/personalNoLableMessageSend/"+this.$route.query.id+"/").then(function(t){console.log(t),e.reContentVal=t.data.personalNoLableMessageSendContentList;for(var s=0;s<e.reContentVal.length;s++){if("邀请入群"===e.reContentVal[s].contentType){e.goP=!1;break}e.goP=!0}t.data.sendTime&&(e.geLeiBie="根据个人号",e.tagRadio="根据个人号",e.sendTiems="定时发送",e.Dshow=!0,e.editDate2=t.data.sendTime,e.editTimeXuan2=t.data.sendTime,e.relapyContentCategoryName=t.data.groupName)})},methods:{handleDragStart:function(e,t){console.log(e),console.log(t),this.dragging=t,console.log(this.dragging)},handleDragEnd:function(e,t){console.log(e),console.log(t),this.dragging=null},handleDragOver:function(e){console.log(e),e.dataTransfer.dropEffect="move"},handleDragEnter:function(e,t){if(console.log(e),console.log(t),e.dataTransfer.effectAllowed="move",t!==this.dragging){var s=[].concat(i()(this.reContentVal));console.log(s);var n=s.indexOf(this.dragging),a=s.indexOf(t);s.splice.apply(s,[a,0].concat(i()(s.splice(n,1)))),this.reContentVal=s}},selectGroupSet:function(e){var t=this;console.log(e),Object(o.b)("/groupCategory/"+e+"/"+this.fssionIndex+"/").then(function(e){console.log(e),e.code===a.a?t.$message({type:"error",message:"暂时没有群类别哦，创建一个群类别再来添加群哦"}):t.groupCategory=e.data})},inputChangeLittle:function(e,t,s){console.log(s),this.$set(this.reContentVal,s,{content:t,contentType:"小程序"})},uploadVoiceValShow:function(e,t){this.$set(this.reContentVal,t,{content:e,contentType:"语音消息"})},selectGroup:function(e,t){console.log(e),console.log(t);var s=e;this.$set(this.reContentVal,t,{content:this.fssionIndex+"/"+s,contentType:"邀请入群"}),console.log(this.reContentVal)},fssionChange:function(e){var t=this;0===e?(this.fssionIndex=0,this.fssion="1号后台"):(this.fssionIndex=1,this.fssion="2号后台"),Object(o.b)("/groupCategorySet/"+this.fssionIndex+"/").then(function(e){console.log(e),e.code===a.a?t.$message({type:"error",message:"暂时没有可用的群哦"}):t.groupCategorySet=e.data})},startEndTime:function(e){console.log(e),this.startTime=e[0],this.endTime=e[1]},changeInput:function(e){console.log(e),this.isicon=!this.isicon},handleCheckAllChange1:function(e){console.log(e),!0===e?(this.quDaoVal="已选择"+this.channel.length+"个",this.checkedCities1=this.channel):(this.checkedCities1=[],this.quDaoVal="已选择0个"),this.isIndeterminate1=!1},setGroupSure:function(){var e=this;Object(o.c)("/lableManager/listByPersonal",this.checkedCities).then(function(t){console.log(t),e.channel=t.data});var t={lableNameList:this.checkedCities1,noList:this.checkedCities};Object(o.c)("/personalNoLableMessageSend/getPeopleNum",t).then(function(t){console.log(t),t.code===a.a?e.fuGai=0:e.fuGai=t.data}),this.firstShow=!this.firstShow,this.noPersonClass=!0},gen:function(e){var t=e.personalNoName;this.geRen.push(t)},handleCheckedCitiesChange:function(e){console.log(e),this.checkedCities=e;var t=e.length;this.personNum=t,this.checkAll=t===this.setList.length,this.isIndeterminate=t>0&&t<this.setList.length},findWantGroupVal:function(){},handleCheckedCitiesChange1:function(e){var t=this;console.log(e),this.checkedCities1=e,1===e.length?this.quDaoVal=e[0]:this.quDaoVal="已选择"+e.length+"个";var s=e.length;this.checkAll1=s===this.channel.length,this.isIndeterminate1=s>0&&s<this.channel.length;var n={lableNameList:this.checkedCities1,noList:this.checkedCities};Object(o.c)("/personalNoLableMessageSend/getPeopleNum",n).then(function(e){console.log(e),e.code===a.a?t.fuGai=0:t.fuGai=e.data})},selectPersonTask:function(e){var t=this;1===e&&(this.checkedCities=[]),Object(o.b)("/personalCategoryManager").then(function(e){console.log(e),t.list=e.data,Object(o.b)("/personalManager/getByCategory/"+t.list[0].personalNoCategory+"/1/3/").then(function(e){console.log(e),t.setList=e.data})}),this.firstShow=!this.firstShow},setGroupSureLabel:function(){var e=this;this.firstShowLabel=!this.firstShowLabel,this.noPersonLabel=!0,this.showPP=!0;var t={lableNameList:this.noLabel,noList:this.checkedCitiesLabel1};Object(o.c)("/personalNoLableMessageSend/getPeopleNum",t).then(function(t){console.log(t),t.code===a.a?e.fuGai=0:e.fuGai=t.data})},leftMenuTask:function(e,t){var s=this;console.log(e),console.log(t),this.kouweiSpan=t,this.userIndex=t,Object(o.b)("/personalManager/getByCategory/"+e.personalNoCategory+"/1/3/").then(function(e){console.log(e),s.setList=e.data})},gai:function(e){var t=this;console.log(e);var s=e.lableName;this.noLabel.push(s),console.log(this.noLabel),Object(o.b)("/personalManager/"+e.lableId+"/").then(function(e){console.log(e),t.setLabel1=e.data})},handleCheckedCitiesChangeLabel:function(e){},handleCheckedCitiesChangeLabel1:function(e){console.log(e),this.checkedCitiesLabel1=e,this.biaoqian=this.checkedCitiesLabel1[0].nickname;var t=e.length;this.personLabelNum=1===t?e[0].lableName:this.quDaoVal="已选择"+t+"个",this.checkAll=t===this.setLabel.length,this.isIndeterminate=t>0&&t<this.setLabel1.length},leftMenuTaskLabel:function(e,t){var s=this;console.log(e);var n=e.categoryName;console.log(t),this.kouweiSpan=t,this.userIndex=t,Object(o.b)("/lableManager/getLableByCategory/"+n+"/").then(function(e){console.log(e),s.setLabel=e.data})},selectPersonTaskLabel:function(e){var t=this;1===e&&(this.checkedCitiesLabel=[]),Object(o.b)("/personalLableCategoryManager/1/").then(function(e){console.log(e),t.listLableArr=e.data}),this.firstShowLabel=!this.firstShowLabel},tagTaskChange:function(e){console.log(e),this.geLeiBie=e},editSelectDate:function(e){console.log(e);var t=new Date(e),s=t.getFullYear(),n=t.getMonth()+1;n=n<10?"0"+n:n;var i=t.getDate()<10?"0"+t.getDate():t.getDate();console.log(s+"-"+n+"-"+i),this.dada1=s+"-"+n+"-"+i},editSelectTimes:function(e){console.log(e);var t=new Date(e),s=t.getHours()<10?"0"+t.getHours():t.getHours(),n=t.getMinutes()<10?"0"+t.getMinutes():t.getMinutes(),i=t.getSeconds()<10?"0"+t.getSeconds():t.getSeconds();console.log(s+":"+n+":"+i),this.titi1=s+":"+n+":"+i},timeChange:function(e){console.log(e),this.sendTiems=e,"定时发送"===e?this.Dshow=!0:(this.Dshow=!1,this.dada1="",this.titi1="")},getFile:function(e){var t=this,s=e.target.files[0];if(console.log(this.$refs.file),this.upLoadVal=s.name,e&&window.FileReader){var n=new FileReader;n.readAsDataURL(s),n.onloadend=function(){var e=this;t.src=this.result,console.log(t.src),t.$message({showClose:!0,message:"图片压缩中.....",duration:0}),Object(o.c)("/upload/imageUpload",t.src).then(function(s){console.log(s),s.code===a.a?e.$message({type:"error",message:"图片上传"+s.message}):(t.$message.closeAll(),t.$message({type:"success",message:"图片上传成功"}),t.$set(t.reContentVal,t.indexTime,{content:s.data,contentType:"图片"}))})}}},uploadImgs:function(e){console.log(e),this.indexTime=e},yulan:function(e){console.log(e),this.yulanEWM=!this.yulanEWM},getFiles:function(e){var t=this,s=e.target.files[0];if(console.log(s),e&&window.FileReader){var n=new FileReader;n.readAsDataURL(s),n.onloadend=function(){t.srcs=this.result;var e=t.srcs.slice(22,-1);localStorage.setItem("imageSrcs",e),localStorage.setItem("imageNames",s.name)}}},yulans:function(e){console.log(e),this.yulanEWMs=!this.yulanEWMs},NeiSong:function(e){console.log(e),this.groupNei=e},inputChange:function(e,t,s){console.log(s),this.$set(this.reContentVal,s,{content:t,contentType:"文字"})},word:function(){this.closeShow=!0,this.reContentVal.length>4?this.$message({type:"error",message:"每次发送最多五条消息"}):this.reContentVal.push({content:"",contentType:"文字",imageName:""}),this.$refs.newBottom.style.paddingTop=a.b+"px",this.$refs.newBottom.style.paddingBottom=a.b+"px"},busCard:function(){this.closeId=!0},showImg:function(){this.closeIgs=!0,this.reContentVal.length>4?this.$message({type:"error",message:"每次发送最多五条消息"}):this.reContentVal.push({content:"",contentType:"图片",imageName:""})},link:function(){this.closeLinks=!0},music:function(){!1===this.stopAddGroup?this.$message({type:"error",message:"邀请入群只能为一个"}):(this.reContentVal.length>4?this.$message({type:"error",message:"每次发送最多五条消息"}):this.reContentVal.push({content:"",contentType:"邀请入群"}),this.$refs.newBottom.style.paddingTop=a.b+"px",this.$refs.newBottom.style.paddingBottom=a.b+"px")},miniPro:function(){this.reContentVal.length>4?this.$message({type:"error",message:"每次发送最多五条消息"}):this.reContentVal.push({content:"",contentType:"小程序"}),this.$refs.newBottom.style.paddingTop=a.b+"px",this.$refs.newBottom.style.paddingBottom=a.b+"px"},voiceInfo:function(){this.reContentVal.length>4?this.$message({type:"error",message:"每次发送最多五条消息"}):this.reContentVal.push({content:"",contentType:"语音消息"})},close:function(e){this.closeShow=!this.closeShow,this.reContentVal.splice(e,1),this.$refs.newBottom.style.paddingTop=a.a+"px",this.$refs.newBottom.style.paddingBottom=a.a+"px"},closeCard:function(){this.closeId=!this.closeId},closeImg:function(){this.closeIgs=!this.closeIgs},closeLink:function(){this.closeLinks=!this.closeLinks},send:function(){var e=this;this.loading=!0,this.agree=!0;var t="",s={};t=""===this.dada1&&""===this.titi1?"":this.dada1+" "+this.titi1,s="根据标签"===this.geLeiBie?{sendTime:t,personalNoLableMessageSendContentList:this.reContentVal,noList:this.checkedCitiesLabel1,lableList:this.noLabel,startTime:this.startTime,endTime:this.endTime}:{sendTime:t,personalNoLableMessageSendContentList:this.reContentVal,noList:this.checkedCities,lableList:this.checkedCities1,startTime:this.startTime,endTime:this.endTime},console.log(this.$route.query.code),0===this.$route.query.code&&Object(o.c)("/personalNoLableMessageSend/lableMessageSend",s).then(function(t){e.loading=!1,console.log(t),t.code===a.a?(e.$message({type:"error",message:t.message}),e.agree=!1,e.reload()):(e.$message({type:"success",message:t.message}),e.agree=!1,e.$router.push({path:"/tagMessageSend"}),e.reload())})},sendCancel:function(){this.$router.push({path:"/tagMessageSend"}),this.reload()},detailsCancel:function(){this.firstShow=!this.firstShow}}},c={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"copyTagMessageSend"},[n("div",{staticClass:"adver-set"},[e._v("新增消息")]),e._v(" "),n("div",{staticClass:"adverWrapper"},[n("div",{staticClass:"adverBorder"},[n("el-form",{staticClass:"form"},[n("div",{staticClass:"send-wrapper"},[n("el-form-item",{attrs:{label:"群发任务:","label-width":"200px"}},[n("el-radio-group",{on:{change:e.tagTaskChange},model:{value:e.tagRadio,callback:function(t){e.tagRadio=t},expression:"tagRadio"}},[n("el-radio",{attrs:{label:"根据标签"}},[e._v("根据标签")]),e._v(" "),n("el-radio",{attrs:{label:"根据个人号"}},[e._v("根据个人号")])],1)],1),e._v(" "),n("el-form-item",{attrs:{label:"开始时间&结束时间:","label-width":"200px"}},[n("el-date-picker",{attrs:{type:"datetimerange","value-format":"yyyy-MM-dd HH:mm:ss","range-separator":"至","default-time":["00:00:00","23:59:59"],"start-placeholder":"开始日期","end-placeholder":"结束日期"},on:{change:e.startEndTime},model:{value:e.startOrEndTime,callback:function(t){e.startOrEndTime=t},expression:"startOrEndTime"}})],1),e._v(" "),"根据标签"===e.tagRadio?n("div",{staticClass:"biaoqian"},[n("el-form-item",{attrs:{label:"","label-width":"100px"}},[!1===e.noPersonLabel?n("span",{staticClass:"selectPer",staticStyle:{width:"150px",display:"inline-block","text-align":"center",border:"1px solid #ccc"},on:{click:function(t){return e.selectPersonTaskLabel(0)}}},[n("i",{staticClass:"iconfont icon-liebiao"}),e._v("选择粉丝标签")]):n("div",{staticStyle:{display:"flex","flex-direction":"row"}},[n("span",{staticStyle:{"margin-right":"20px"}},[e._v(e._s(e.personLabelNum))]),e._v(" "),n("span",{staticClass:"selectPer",on:{click:function(t){return e.selectPersonTaskLabel(1)}}},[n("i",{staticClass:"iconfont icon-liebiao"}),e._v("修改")]),e._v(" "),n("el-select",{directives:[{name:"show",rawName:"v-show",value:this.checkedCitiesLabel1.length>0,expression:"this.checkedCitiesLabel1.length > 0"}],staticStyle:{"margin-left":"20px"},attrs:{placeholder:e.biaoqian},model:{value:e.personLabel,callback:function(t){e.personLabel=t},expression:"personLabel"}},e._l(e.checkedCitiesLabel1,function(t){return n("el-option",{key:t.nickname,attrs:{disabled:e.showPP,label:t.nickname,value:t.nickname}})}),1)],1),e._v(" "),n("span",{staticStyle:{"margin-left":"10px"}},[e._v("覆盖"+e._s(e.fuGai)+"人")])])],1):n("div",[!1===e.noPersonClass?n("span",{staticClass:"selectPer",on:{click:function(t){return e.selectPersonTask(0)}}},[n("i",{staticClass:"iconfont icon-liebiao"}),e._v("选择个人号")]):n("div",{staticStyle:{display:"flex","flex-direction":"row"}},[n("span",{staticClass:"selectPer selectHover",staticStyle:{width:"210px"},on:{click:function(t){return e.selectPersonTask(1)}}},[e._v("个人号已选择 "+e._s(e.personNum)+" 个")]),e._v(" "),n("span",{staticClass:"tuiguang",on:{click:e.changeInput}},[e._v(e._s(e.quDaoVal)),n("i",{class:!0===e.isicon?"el-icon-arrow-down":"el-icon-arrow-up"})]),e._v(" "),n("div",{staticStyle:{position:"relative",top:"40px",left:"-320px"}},[n("div",{directives:[{name:"show",rawName:"v-show",value:!1===e.isicon,expression:"isicon === false"}],staticClass:"triangle_border_up"},[n("span")]),e._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:!1===e.isicon,expression:"isicon === false"}],staticClass:"proChannel"},[n("div",[n("el-checkbox",{attrs:{indeterminate:e.isIndeterminate1},on:{change:e.handleCheckAllChange1},model:{value:e.checkAll1,callback:function(t){e.checkAll1=t},expression:"checkAll1"}},[e._v("全选")])],1),e._v(" "),n("el-checkbox-group",{on:{change:e.handleCheckedCitiesChange1},model:{value:e.checkedCities1,callback:function(t){e.checkedCities1=t},expression:"checkedCities1"}},e._l(e.channel,function(t,s){return n("el-checkbox",{key:s,attrs:{title:t.channelName,label:t}},[e._v(e._s(t))])}),1)],1)]),e._v(" "),n("span",{staticStyle:{"margin-left":"10px","line-height":"42px"}},[e._v("覆盖"+e._s(e.fuGai)+"人")])])])],1),e._v(" "),n("div",{staticClass:"sendTs"},[n("el-form-item",{attrs:{label:"时间设置:","label-width":"200px"}},[n("el-radio-group",{on:{change:e.timeChange},model:{value:e.sendTiems,callback:function(t){e.sendTiems=t},expression:"sendTiems"}},[n("el-radio",{attrs:{label:"立即发送"}},[e._v("立即发送")]),e._v(" "),n("el-radio",{attrs:{label:"定时发送"}},[e._v("定时发送")])],1)],1),e._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:e.Dshow,expression:"Dshow"}]},[n("el-date-picker",{attrs:{type:"date",placeholder:"选择日期"},on:{change:e.editSelectDate},model:{value:e.editDate2,callback:function(t){e.editDate2=t},expression:"editDate2"}}),e._v(" "),n("el-time-picker",{attrs:{format:"HH:mm:ss",placeholder:"选择时间"},on:{change:e.editSelectTimes},model:{value:e.editTimeXuan2,callback:function(t){e.editTimeXuan2=t},expression:"editTimeXuan2"}})],1)],1),e._v(" "),n("div",{staticClass:"invateGoGroup"},[n("el-form-item",{attrs:{label:"群裂变后台：","label-width":"150px"}},[n("el-select",{attrs:{placeholder:"请选择裂变后台"},on:{change:e.fssionChange},model:{value:e.fssion,callback:function(t){e.fssion=t},expression:"fssion"}},e._l(e.fssionList,function(e){return n("el-option",{key:e.name,attrs:{label:e.name,value:e.id}})}),1),e._v(" "),n("p",[e._v("*仅限邀请入群选择")])],1)],1),e._v(" "),n("div",{staticClass:"groupSendC"},[n("el-form-item",{attrs:{label:"发送内容:","label-width":"200px"}},[n("div",{staticClass:"newBigCon"},[n("div",{staticClass:"newTop"},[n("ul",[n("li",{staticStyle:{width:"105px"},on:{click:e.word}},[e._v("文字")]),e._v(" "),n("li",{on:{click:e.showImg}},[e._v("图片")]),e._v(" "),n("li",{directives:[{name:"show",rawName:"v-show",value:!0===e.goP,expression:"goP === true"}],on:{"~click":function(t){return e.music(t)}}},[e._v("邀请入群")]),e._v(" "),n("li",{directives:[{name:"show",rawName:"v-show",value:!1===e.goP,expression:"goP === false"}]},[e._v("邀请入群")]),e._v(" "),n("li",{on:{click:e.miniPro}},[e._v("小程序")]),e._v(" "),n("li",{on:{click:e.voiceInfo}},[e._v("语音消息")])])]),e._v(" "),n("div",{ref:"newBottom",staticClass:"newBottom"},[e._l(e.reContentVal,function(t,s){return n("div",{key:s,attrs:{draggable:"true"},on:{dragstart:function(s){return e.handleDragStart(s,t)},dragover:function(s){return s.preventDefault(),e.handleDragOver(s,t)},dragenter:function(s){return e.handleDragEnter(s,t)},dragend:function(s){return e.handleDragEnd(s,t)}}},["文字"===t.contentType?n("div",{ref:"word",refInFor:!0,staticClass:"newWords"},[n("i",{staticClass:"el-icon-edit"}),e._v(" "),n("el-input",{attrs:{type:"textarea",maxlength:"800",autosize:{minRows:10,maxRows:10},placeholder:"请输入回复内容，不得超过800字。添加“#####”可在回复时@对应成员。"},on:{change:function(n){return e.inputChange(t,n,s)}},model:{value:t.content,callback:function(s){e.$set(t,"content",s)},expression:"item.content"}}),e._v(" "),n("i",{staticClass:"el-icon-circle-close relative",on:{click:function(t){return e.close(s)}}})],1):e._e(),e._v(" "),"图片"===t.contentType?n("div",{ref:"image",refInFor:!0,staticClass:"newImg"},[n("i",{staticClass:"el-icon-picture-outline"}),e._v(" "),n("div",{staticClass:"uploadImg"},[n("span",[e._v(e._s(t.content))]),e._v(" "),n("input",{ref:"file",refInFor:!0,attrs:{type:"file",id:"file"},on:{change:function(t){return e.getFile(t)}}}),e._v(" "),n("label",{staticClass:"upload",attrs:{for:"file"},on:{click:function(t){return e.uploadImgs(s)}}},[e._v("上传文件")]),e._v(" "),n("label",{staticClass:"yulan",on:{click:function(s){return e.yulan(t.content)}}},[e._v("预览")])]),e._v(" "),n("el-dialog",{attrs:{visible:e.yulanEWM,width:"20%","append-to-body":""},on:{"update:visible":function(t){e.yulanEWM=t}}},[n("img",{staticClass:"imgWEM",attrs:{src:t.content}})]),e._v(" "),n("i",{staticClass:"el-icon-circle-close relative",on:{click:function(t){return e.close(s)}}})],1):e._e(),e._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:!1===e.goP,expression:"goP === false"}],staticStyle:{"margin-bottom":"10px"}},["邀请入群"===t.contentType?n("span",{ref:"goGroup",refInFor:!0,staticClass:"goGroup"},[n("i",{staticClass:"iconfont icon-yonghu-tianchong",staticStyle:{"padding-right":"10px"}}),e._v(e._s(e.relapyContentCategoryName))]):e._e()]),e._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:e.goP,expression:"goP"}],staticStyle:{"margin-bottom":"10px"}},["邀请入群"===t.contentType?n("div",{ref:"goGroup",refInFor:!0,staticClass:"goGroup"},[n("i",{staticClass:"iconfont icon-yonghu-tianchong"}),e._v(" "),n("div",{staticClass:"groupList",staticStyle:{"margin-left":"20px"}},[n("el-select",{attrs:{placeholder:"请选择群类别集合"},on:{change:e.selectGroupSet},model:{value:e.groupSet,callback:function(t){e.groupSet=t},expression:"groupSet"}},e._l(e.groupCategorySet,function(e){return n("el-option",{key:e.sname,attrs:{label:e.sname,value:e.id}})}),1),e._v(" "),n("el-select",{attrs:{placeholder:"请选择群类别"},on:{change:function(t){return e.selectGroup(t,s)}},model:{value:e.groupV,callback:function(t){e.groupV=t},expression:"groupV"}},e._l(e.groupCategory,function(e){return n("el-option",{key:e.cname,attrs:{label:e.cname,value:e.id}})}),1)],1),e._v(" "),n("i",{staticClass:"el-icon-circle-close relative",on:{click:function(t){return e.close(s)}}})]):e._e()]),e._v(" "),"小程序"===t.contentType?n("div",{ref:"little",refInFor:!0,staticClass:"newWords little",staticStyle:{height:"60px"}},[n("i",{staticClass:"el-icon-edit"}),e._v(" "),n("el-input",{staticStyle:{border:"none"},attrs:{placeholder:"请输入小程序链接地址"},on:{change:function(n){return e.inputChangeLittle(t,n,s)}},model:{value:t.content,callback:function(s){e.$set(t,"content",s)},expression:"item.content"}}),e._v(" "),n("i",{staticClass:"el-icon-circle-close relative",on:{click:function(t){return e.close(s)}}})],1):e._e(),e._v(" "),"语音消息"===t.contentType?n("div",{ref:"voice",refInFor:!0,staticClass:"newWords",staticStyle:{height:"60px"}},[n("i",{staticClass:"iconfont icon-yuyin"}),e._v(" "),n("div",{staticClass:"uploadVoice",staticStyle:{display:"flex","flex-direction":"row"}},[n("el-input",{attrs:{placeholder:"请输入个人号转过来的链接..."},on:{change:function(n){return e.uploadVoiceValShow(t,n,s)}},model:{value:t.content,callback:function(s){e.$set(t,"content",s)},expression:"item.content"}})],1),e._v(" "),n("i",{staticClass:"el-icon-circle-close relative",staticStyle:{right:"-20px"},on:{click:function(t){return e.close(s)}}})]):e._e()])}),e._v(" "),n("div",{staticClass:"fission"}),e._v(" "),n("span",{staticStyle:{color:"red"}},[e._v("每次发送不能超过5条消息")])],2)])])],1),e._v(" "),n("div",{staticClass:"submit"},[n("el-button",{staticClass:"subSpa1",attrs:{disabled:e.agree},on:{click:e.send}},[n("img",{directives:[{name:"show",rawName:"v-show",value:e.loading,expression:"loading"}],staticStyle:{width:"16px",height:"16px","vertical-align":"middle"},attrs:{src:s("G/2H")}}),e._v("\n            发 送")]),e._v(" "),n("el-button",{staticClass:"subSpa2",on:{click:e.sendCancel}},[e._v("取 消")])],1)])],1)]),e._v(" "),n("el-dialog",{attrs:{title:"选择粉丝标签",center:"",visible:e.firstShowLabel,width:"50%"},on:{"update:visible":function(t){e.firstShowLabel=t}}},[n("div",{staticClass:"selectDia"},[n("div",{staticClass:"GroupList"},[n("div",{staticClass:"groupLeft"},[n("ul",{staticClass:"leftUl"},[n("li",{staticStyle:{"text-align":"center","background-color":"#fff"}},[e._v("标签类别")]),e._v(" "),e._l(e.listLableArr,function(t,s){return n("li",{key:s,class:{on:s==e.kouweiSpan},on:{click:function(n){return e.leftMenuTaskLabel(t,s)}}},[n("i",{staticClass:"iconfont icon-guanliyuan",class:{on:s==e.kouweiSpan}}),e._v(e._s(t.categoryName))])})],2)]),e._v(" "),n("div",{staticClass:"groupRight"},[n("div",{staticClass:"groupcName"},[e._v("标签名称")]),e._v(" "),n("div",{staticClass:"rightRadio"},[n("el-checkbox-group",{on:{change:e.handleCheckedCitiesChangeLabel},model:{value:e.checkedCitiesLabel,callback:function(t){e.checkedCitiesLabel=t},expression:"checkedCitiesLabel"}},e._l(e.setLabel,function(t,s){return n("el-checkbox",{key:s,attrs:{label:t},on:{change:function(s){return e.gai(t)}}},[e._v(e._s(t.lableName))])}),1)],1)]),e._v(" "),n("div",{staticClass:"groupRight",staticStyle:{"border-left":"none"}},[n("div",{staticClass:"groupcName"},[e._v("个人号昵称")]),e._v(" "),n("div",{staticClass:"rightRadio"},[n("el-checkbox-group",{on:{change:e.handleCheckedCitiesChangeLabel1},model:{value:e.checkedCitiesLabel1,callback:function(t){e.checkedCitiesLabel1=t},expression:"checkedCitiesLabel1"}},e._l(e.setLabel1,function(t,s){return n("el-checkbox",{key:s,attrs:{label:t}},[e._v(e._s(t.nickname))])}),1)],1)])])]),e._v(" "),n("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{attrs:{type:"primary"},on:{click:e.setGroupSureLabel}},[e._v("确 定")]),e._v(" "),n("el-button",{on:{click:function(t){e.firstShowLabel=!1}}},[e._v("取 消")])],1)]),e._v(" "),n("el-dialog",{attrs:{title:"选择个人号",center:"",visible:e.firstShow,width:"50%"},on:{"update:visible":function(t){e.firstShow=t}}},[n("div",{staticClass:"selectDia"},[n("div",{staticClass:"GroupList"},[n("div",{staticClass:"groupLeft"},[n("ul",{staticClass:"leftUl"},[n("li",{staticStyle:{"text-align":"center","background-color":"#fff"}},[e._v("个人号类别")]),e._v(" "),e._l(e.list,function(t,s){return n("li",{key:s,class:{on:s==e.kouweiSpan},on:{click:function(n){return e.leftMenuTask(t,s)}}},[n("i",{staticClass:"iconfont icon-guanliyuan",class:{on:s==e.kouweiSpan}}),e._v(e._s(t.personalNoCategory))])})],2)]),e._v(" "),n("div",{staticClass:"groupRight",staticStyle:{width:"60%"}},[n("div",{staticClass:"groupcName"},[e._v("个人号昵称")]),e._v(" "),n("div",{staticClass:"rightRadio"},[n("el-checkbox-group",{on:{change:e.handleCheckedCitiesChange},model:{value:e.checkedCities,callback:function(t){e.checkedCities=t},expression:"checkedCities"}},e._l(e.setList,function(t,s){return n("el-checkbox",{key:s,attrs:{label:t},on:{change:function(s){return e.gen(t)}}},[e._v(e._s(t.nickname))])}),1)],1)])])]),e._v(" "),n("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{attrs:{type:"primary"},on:{click:e.setGroupSure}},[e._v("确 定")]),e._v(" "),n("el-button",{on:{click:function(t){e.firstShow=!1}}},[e._v("取 消")])],1)])],1)},staticRenderFns:[]};var r=s("C7Lr")(l,c,!1,function(e){s("GiWH"),s("TgXA")},"data-v-daf13596",null);t.default=r.exports},TgXA:function(e,t){}});