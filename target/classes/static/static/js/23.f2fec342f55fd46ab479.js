webpackJsonp([23],{VK1r:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=i("T452"),a=i("SthE"),o=i("8EyE"),c=i("Y8t/"),r={name:"wxLogin",data:function(){return{clientHeight:"",timer:null,id:""}},created:function(){var e=this;this.clientHeight=""+document.documentElement.clientHeight,console.log(this.clientHeight),window.onresize=function(){this.clientHeight=""+document.documentElement.clientHeight},Object(c.b)("/getAccId").then(function(t){console.log(t.data);var i=n.c+"/personalNoSuperuesr/loginInWX?id=";e.id=t.data;var o=Object(a.a)({data:decodeURIComponent(i+t.data),size:300});document.getElementById("qrcode").innerHTML="",document.getElementById("qrcode").appendChild(o),e.timer=setInterval(e._getCookieVal,3e3)})},methods:{changeFixed:function(e){console.log(e),this.$refs.hv.style.height=e+"px"},_getCookieVal:function(){var e=this;Object(c.b)("/personalNoSuperuesr/veryById/"+e.id+"/").then(function(t){console.log(t),t.code===n.a?e.$message({type:"error",message:"数据请求失败..."}):1===t.data.flag&&t.data.openid&&Object(c.b)("/personalNoSuperuesr/getByAccessTockenId/"+t.data.id+"/").then(function(t){console.log(t),Object(o.c)("p_openid",t.data.openid,t.data.time),Object(o.c)("p_superId",t.data.superId,t.data.time),Object(o.c)("p_superName",t.data.superName,t.data.time),Object(o.c)("p_headPortrait",t.data.headPortrait,t.data.time),console.log(e.$route.query),e.$router.push({path:e.$route.query.redirect})})})}},watch:{clientHeight:function(){this.changeFixed(this.clientHeight)}},beforeDestroy:function(){this.timer&&clearInterval(this.timer)}},s={render:function(){var e=this.$createElement;return(this._self._c||e)("div",{ref:"hv",staticClass:"wxLogin",style:"height: "+this.clientHeight+"px"},[this._m(0)])},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"m-qrcode"},[t("div",{staticClass:"qrcodeStyle",attrs:{id:"qrcode"}}),this._v(" "),t("div",{staticClass:"title"},[this._v("打开微信扫描二维码登录系统")])])}]};var d=i("C7Lr")(r,s,!1,function(e){i("ffW4")},"data-v-4226a98f",null);t.default=d.exports},ffW4:function(e,t){}});