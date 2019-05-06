package com.warm.system.controller;


import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.requre.GetAccessTockenResult;
import com.warm.entity.robot.G;
import com.warm.entity.robot.WxResponseInfo2;
import com.warm.system.entity.PersonalNoAccessTocken;
import com.warm.system.entity.PersonalNoSuperuesr;
import com.warm.system.service.db1.PersonalNoAccessTockenService;
import com.warm.system.service.db1.PersonalNoSuperuesrService;
import com.warm.system.service.db1.PersonalNoValueTableService;
import com.warm.system.service.db1.UploadService;
import com.warm.utils.*;
import com.warm.utils.ImageUpload.Base64Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.codec.digest.DigestUtils;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@CrossOrigin //跨域
@Api("超级用户管理")
@RestController
@RequestMapping("/personalNoSuperuesr")
public class PersonalNoSuperuesrController {
    private static Log log = LogFactory.getLog(PersonalNoSuperuesrController.class);
    @Value("${fileServer.ms_SERVER_ROOT_URL}")
    String ms_SERVER_ROOT_URL;
    @Autowired
    private PersonalNoSuperuesrService personalNoSuperuesrService;
    @Autowired
    private PersonalNoAccessTockenService accessTockenService;
    @Autowired
    private PersonalNoValueTableService valueTableService;

    @ApiOperation(value = "登录方法")
    @PostMapping(value = "login")
    public R login(
            @ApiParam(name = "personalNoSuperuesr", value = "登录账户信息", required = true)
            @RequestBody PersonalNoSuperuesr personalNoSuperuesr,
            HttpServletRequest request
    ){
        try {
            log.info("开始验证登录");
            if(VerifyUtils.isEmpty(personalNoSuperuesr)){
                log.info("登录信息为空");
                return R.error().message("用户名或密码为空");
            }
            String password = personalNoSuperuesr.getPassword();
            String pwd = DigestUtils.md5Hex(password);
            log.info("开始验证用户名");
            PersonalNoSuperuesr result = personalNoSuperuesrService.login(personalNoSuperuesr.getSuperName());
            if(VerifyUtils.isEmpty(result)){
                log.info("账户不存在");
                return R.error().message("账户不存在");
            }
            if(!result.getPassword().equals(pwd)){
                log.info("密码错误");
                return R.error().message("密码错误");
            }
            log.info("验证成功，将数据存入session");
            //将用户信息存入session，保证登录状态
            SessionUtil.setSession(request, WebConst.SUPERUSER, result);
            log.info("登录成功");
            return R.ok().data(result);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "注册方法")
    @PostMapping(value = "register")
    public R register(
            @ApiParam(name = "personalNoSuperuesr", value = "注册账户信息", required = true)
            @RequestBody PersonalNoSuperuesr personalNoSuperuesr
    ){
        try {
            log.info("开始注册任务");
            if(VerifyUtils.isEmpty(personalNoSuperuesr)){
                log.info("开始注册任务");
                return R.error().message("注册信息为空");
            }
            log.info("开始验证用户名");
            PersonalNoSuperuesr result = personalNoSuperuesrService.login(personalNoSuperuesr.getSuperName());
            if(!VerifyUtils.isEmpty(result)){
                return R.error().message("用户名已经存在");
            }
            log.info("开始验证专属码");
            if(!WebConst.CODE.equals(personalNoSuperuesr.getCode())){
                log.info("专属码匹配失败");
                return R.error().message("专属码匹配失败");
            }
            log.info("开始密码加密");
            String password = personalNoSuperuesr.getPassword();
            String pwd = DigestUtils.md5Hex(password);
            personalNoSuperuesr.setPassword(pwd);
            log.info("默认头像");
            personalNoSuperuesr.setHeadPortrait("http://120.79.207.156:8080/group1/M00/00/00/rBJid1xihWKAeq6rAAAfi2XiEkU661.jpg");
            boolean save = personalNoSuperuesrService.insert(personalNoSuperuesr);
            if(!save){
                log.info("添加用户导数据库失败");
                return R.error().message("注册失败");
            }
            log.info("注册成功");
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "注销方法")
    @GetMapping(value = "logout")
    public R logout(HttpServletRequest request, HttpServletResponse response){
        try {
            String id = request.getParameter("id");
            CookieUtil.deleteCookie(request,response,WebConst.TOCKEN);
            CookieUtil.deleteCookie(request,response,WebConst.LOGINFLAG);
            SessionUtil.removeSession(request,WebConst.SESSIONPRE+id+WebConst.SESSSUFF);
            log.info("退出登录");
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "微信扫码登录方法")
    @GetMapping(value = "loginInWX")
    public void loginInWX(HttpServletRequest request, HttpServletResponse response){
        try {
            String id = request.getParameter("id");
            String targetUrl = ms_SERVER_ROOT_URL + "/personalNoSuperuesr/LoginCallback?id="+id;
            String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + G.WX_APPID + "&redirect_uri=" + targetUrl + "&response_type=code&scope=snsapi_userinfo&state=0&connect_redirect=1#wechat_redirect";
            try {
                response.sendRedirect(redirectUrl);
            } catch (IOException e) {
                log.info("出错了");
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "微信扫码登录回调方法")
    @GetMapping(value = "LoginCallback")
    public void loginCallBack(HttpServletRequest request, HttpServletResponse response){
        try {
            String p_id = request.getParameter("id");
            String p_code = request.getParameter("code");
            //获取code后，请求以下链接获取access_token：  https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
            log.info("WxResponseInfo2");
            WxResponseInfo2 wxResponseInfo2 = HttpClientUtil.getWxResponseInfo2(p_code);
            List<PersonalNoSuperuesr> list = personalNoSuperuesrService.selectList(null);
            boolean flag = true;
            for (PersonalNoSuperuesr personalNoSuperuesr : list) {
                if(wxResponseInfo2.unionid.equals(personalNoSuperuesr.getOpenid())){
                    String sql = DaoGetSql.getSql("delete from "+DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_access_tocken)+" where openid = ?",wxResponseInfo2.openid);
                    accessTockenService.delete(sql);
                    String sql1 = DaoGetSql.getSql("select id,flag,access_token,openid,refreshtoken,deleted from "+DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_access_tocken)+" where id = ? and deleted = 0",p_id);
                    PersonalNoAccessTocken personalNoAccessTocken = accessTockenService.getOne(sql1);
                    personalNoAccessTocken.setOpenid(wxResponseInfo2.unionid);
                    personalNoAccessTocken.setFlag(1);
                    personalNoAccessTocken.setDb("personal_zc_01.personal_no_access_tocken");
                    accessTockenService.updateOneById(personalNoAccessTocken);
                    flag = false;
                    response.sendRedirect("/wxOath.html");
                }
            }
            if(flag){
                response.sendRedirect("/wxOathError.html");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @ApiOperation(value = "验证用户是否扫码")
    @GetMapping("veryById/{id}")
    public R veryById(
            @ApiParam(name = "id", value = "唯一id", required = true)
            @PathVariable("id") String id
    ){
        try {
            String sql = DaoGetSql.getSql("select id,flag,access_token,openid,refreshtoken,deleted from "+DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_access_tocken)+" where id = ? and deleted = 0", id);
            PersonalNoAccessTocken byId = accessTockenService.getOne(sql);
            return R.ok().data(byId);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据id取得登录用户信息")
    @GetMapping("getByAccessTockenId/{id}/")
    public R getByAccessTockenId(
            @ApiParam(name = "id", value = "唯一id", required = true)
            @PathVariable("id") String id,
            HttpServletRequest request,HttpServletResponse response
    ){
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + G.WX_APPID + "&secret=" + G.WX_SECRET;
            String retStr2 = HttpClientUtil.sendGet(url);
            GetAccessTockenResult getAccessTockenResult = G.ms_om.readValue(retStr2, GetAccessTockenResult.class);
            String sql = DaoGetSql.getSql("select id,flag,access_token,openid,refreshtoken,deleted from "+DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_access_tocken)+" where id = ? and deleted = 0", id);
            PersonalNoAccessTocken byId = accessTockenService.getOne(sql);
            byId.setAccessToken(getAccessTockenResult.getAccess_token());
            byId.setDb(DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_access_tocken));
            byId.setDeleted(1);
            accessTockenService.updateOneById(byId);
            PersonalNoSuperuesr superuesr = personalNoSuperuesrService.getByOpenIdId(byId.getOpenid());
            byId.setDb(DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_access_tocken));
            Map<String,String> map1 = new HashMap<>();
            map1.put("id",superuesr.getId().toString());
            map1.put("name",superuesr.getSuperName());
            String s = SymmetricEncoder.AESEncode(WebConst.ASC, JsonObjectUtils.objectToJson(map1));
            CookieUtil.setCookie(request,response,WebConst.TOCKEN,s,Integer.parseInt(valueTableService.selectById(6).getValue()),false);
            CookieUtil.setCookie(request,response,WebConst.LOGINFLAG,"0",Integer.parseInt(valueTableService.selectById(6).getValue()),false);
            SessionUtil.setSession(request,WebConst.SESSIONPRE+superuesr.getId().toString()+WebConst.SESSSUFF,superuesr);
            Map<String, String> map = new HashMap<>();
            map.put("superId",superuesr.getId().toString());
            map.put("openid",superuesr.getOpenid());
            map.put("headPortrait",superuesr.getHeadPortrait());
            map.put("superName", superuesr.getSuperName());
            map.put("time",valueTableService.selectById(6).getValue());
            return R.ok().data(map);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }
}

