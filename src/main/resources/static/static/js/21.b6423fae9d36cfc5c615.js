webpackJsonp([21],{"B/EL":function(e,t){},nibh:function(e,t){},y4ys:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=a("y/jF"),i=a("T452"),n={name:"tagChildrenManger",data:function(){return{deleteShow:!1,className:"",remark:"",newShow:!1,tagInputVal:"",pagesize:20,total:0,currentPage:1,minilist:[],noData:!1,listOne:[{title:"类别名称"},{title:"标签数量"},{title:"粉丝数量"},{title:"个人号数量"},{title:"备注"},{title:"操作"}],width:"width01",codeTag:"",newChildren:"",id:"",deleteId:""}},inject:["reload"],created:function(){var e=this;this.$http.get(i.d+"/personalLableCategoryManager/1/").then(function(t){console.log(t),e.minilist=t.body.data,e.total=e.minilist.length})},methods:{NewSureTagClass:function(){var e=this,t={};t=0===this.codeTag?{id:this.id,categoryName:this.className,remarks:this.remark}:{id:"",categoryName:this.className,remarks:this.remark},this.$http.post(i.d+"/personalLableCategoryManager/addLableCategory",t).then(function(t){console.log(t),t.body.code===i.a?e.$message({type:"error",message:t.body.message}):(e.$message({type:"success",message:t.body.message}),e.newChildren=!e.newChildren,e.reload())})},cancelTag:function(){this.newShow=!this.newShow,this.className="",this.remark=""},addTagClass:function(){this.codeTag=1,this.newShow=!this.newShow,this.newChildren="新增标签类别"},tagClassSou:function(){var e=this;this.minilist="",this.noData=!0;var t=this.tagInputVal;""===t&&(t=1),this.$http.get(i.d+"/personalLableCategoryManager/"+t+"/").then(function(t){console.log(t),t.body.code===i.a?(e.$message({type:"error",message:"数据加载失败，请过一会儿重试！"}),e.noData=!1,e.noData=!1):(e.minilist=t.body.data,e.total=e.minilist.length,e.noData=!1)})},editTagClass:function(e){console.log(e),this.codeTag=0,this.newShow=!this.newShow,this.className=e.categoryName,this.remark=e.remarks,this.newChildren="编辑标签类别",this.id=e.id},deleteTagClass:function(e){this.deleteShow=!this.deleteShow,this.deleteId=e.id},deleteSure:function(){var e=this;this.$http.delete(i.d+"/personalLableCategoryManager/deleteById/"+this.deleteId+"/").then(function(t){t.body.code===i.a?e.$message({type:"error",message:t.body.message}):(e.$message({type:"success",message:t.body.message}),e.deleteShow=!e.deleteShow,e.reload())})},cancelDelete:function(){this.deleteShow=!this.deleteShow},handleSizeChange:function(e){this.pagesize=e},handleCurrentChange:function(e){console.log("当前页: "+e),this.currentPage=e}},components:{Loading:s.a}},l={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"tagChildrenManger"},[a("div",{staticClass:"tagSerch"},[a("el-input",{staticStyle:{width:"300px"},attrs:{placeholder:"请输入标签类别名称"},model:{value:e.tagInputVal,callback:function(t){e.tagInputVal=t},expression:"tagInputVal"}}),e._v(" "),a("span",{staticClass:"sousuo",on:{click:e.tagClassSou}},[e._v("搜 索")])],1),e._v(" "),a("div",{staticClass:"sendRecord"},[a("span",{staticClass:"sendRecordO"},[e._v("标签类别列表")]),e._v(" "),a("span",{staticClass:"addSendRecord",on:{click:e.addTagClass}},[e._v("增加")])]),e._v(" "),a("ul",{staticClass:"groupMingDan"},e._l(e.listOne,function(t,s){return a("li",{key:s,class:e.width},[e._v(e._s(t.title))])}),0),e._v(" "),e.minilist.length>0?a("div",{staticClass:"clearfix"},[a("ul",{ref:"groupMinWrapper",staticClass:"groupMin-wrapper"},e._l(e.minilist.slice((e.currentPage-1)*e.pagesize,e.currentPage*e.pagesize),function(t,s){return a("li",{key:s,staticClass:"groupMinLi-wrapper"},[a("ul",{staticClass:"groupMinDanCon"},[a("li",{staticClass:"groupFileName",attrs:{title:t.categoryName}},[e._v("\n            "+e._s(t.categoryName)+"\n          ")]),e._v(" "),a("li",[e._v(e._s(t.lableNum))]),e._v(" "),a("li",[e._v(e._s(t.userNum))]),e._v(" "),t.personalNum?a("li",[e._v(e._s(t.personalNum))]):a("li",[e._v("0")]),e._v(" "),a("li",[e._v(e._s(t.remarks))]),e._v(" "),a("li",{staticClass:"miniHover"},[a("span",{staticClass:"miniSpa1",on:{click:function(a){return e.editTagClass(t)}}},[e._v("\n              编辑\n            ")]),e._v(" "),a("span",{staticClass:"miniSpa1",on:{click:function(a){return e.deleteTagClass(t)}}},[e._v("\n              删除\n            ")])])])])}),0),e._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:e.minilist.length,expression:"minilist.length"}],staticClass:"paging-wrapper"},[a("el-pagination",{attrs:{"current-page":e.currentPage,"page-sizes":[5,10,15,20],"page-size":e.pagesize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1)]):a("div",{staticClass:"noData"},[e._v("暂无数据")]),e._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:e.noData,expression:"noData"}]},[a("loading")],1),e._v(" "),a("el-dialog",{attrs:{title:e.newChildren,center:"",visible:e.newShow,width:"30%"},on:{"update:visible":function(t){e.newShow=t}}},[a("div",{staticClass:"newClass"},[a("el-form",[a("el-form-item",{attrs:{label:"类 别 名 称：","label-width":"120px"}},[a("el-input",{staticStyle:{width:"300px"},attrs:{placeholder:"请输入类别名称，8字以内"},model:{value:e.className,callback:function(t){e.className=t},expression:"className"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"备  注：","label-width":"120px"}},[a("el-input",{staticStyle:{width:"300px"},attrs:{placeholder:"请输入备注，20字以内",autosize:{minRows:3,maxRows:4},type:"textarea"},model:{value:e.remark,callback:function(t){e.remark=t},expression:"remark"}})],1)],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{staticClass:"goOut",attrs:{type:"primary"},on:{click:e.NewSureTagClass}},[e._v("确 定")]),e._v(" "),a("el-button",{on:{click:e.cancelTag}},[e._v("取 消")])],1)]),e._v(" "),a("el-dialog",{attrs:{title:"是否确认删除本条类别？",center:"",visible:e.deleteShow,width:"30%"},on:{"update:visible":function(t){e.deleteShow=t}}},[a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{staticClass:"goOut",attrs:{type:"primary"},on:{click:e.deleteSure}},[e._v("确 定")]),e._v(" "),a("el-button",{on:{click:e.cancelDelete}},[e._v("取 消")])],1)])],1)},staticRenderFns:[]};var o=a("C7Lr")(n,l,!1,function(e){a("B/EL"),a("nibh")},"data-v-0d463ca5",null);t.default=o.exports}});