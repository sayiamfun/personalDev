package com.warm.system.controller;


import com.warm.entity.R;
import com.warm.entity.requre.GetAccessTockenResult;
import com.warm.entity.robot.G;
import com.warm.entity.robot.WxResponseInfo2;
import com.warm.system.entity.PersonalNoAccessTocken;
import com.warm.system.entity.PersonalNoSuperuesr;
import com.warm.system.service.db1.PersonalNoAccessTockenService;
import com.warm.system.service.db1.PersonalNoSuperuesrService;
import com.warm.system.service.db1.UploadService;
import com.warm.utils.HttpClientUtil;
import com.warm.utils.SessionUtil;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@CrossOrigin //跨域
@Api(description = "超级用户管理")
@RestController
@RequestMapping("/personalNoSuperuesr")
public class PersonalNoSuperuesrController {
    private static Log log = LogFactory.getLog(PersonalNoSuperuesrController.class);
    @Value("${fileServer.ms_SERVER_ROOT_URL}")
    String ms_SERVER_ROOT_URL;
    @Autowired
    private PersonalNoSuperuesrService personalNoSuperuesrService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private PersonalNoAccessTockenService accessTockenService;

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
    @PostMapping(value = "logout")
    public R logout(HttpServletRequest request){
        try {
            SessionUtil.invalidateSession(request);
            log.info("退出登录");
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "微信扫码登录方法")
    @GetMapping(value = "loginInWX")
    public void loginInWX(HttpServletResponse response){
        try {
            String targetUrl = ms_SERVER_ROOT_URL + "/personalNoSuperuesr/LoginCallback";
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
    public void loginCallBack(org.apache.catalina.servlet4preview.http.HttpServletRequest request, HttpServletResponse response){
        try {
            String p_code = request.getParameter("code");
            //获取code后，请求以下链接获取access_token：  https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
            log.info("WxResponseInfo2");
            WxResponseInfo2 wxResponseInfo2 = HttpClientUtil.getWxResponseInfo2(p_code);
            List<PersonalNoSuperuesr> list = personalNoSuperuesrService.selectList(null);
            boolean flag = true;
            for (PersonalNoSuperuesr personalNoSuperuesr : list) {
                if(wxResponseInfo2.unionid.equals(personalNoSuperuesr.getOpenid())){
                    accessTockenService.deleteByOpenIdId(wxResponseInfo2.openid);
                    PersonalNoAccessTocken personalNoAccessTocken = accessTockenService.getLast();
                    if(personalNoAccessTocken.getAccessToken()==null){
                        personalNoAccessTocken.setOpenid(wxResponseInfo2.unionid);
                        personalNoAccessTocken.setFlag(1);
                        accessTockenService.updateById(personalNoAccessTocken);
                    }
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
            @PathVariable("id") Integer id
    ){
        try {
            PersonalNoAccessTocken byId = accessTockenService.selectById(id);
            return R.ok().data(byId);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据id取得登录用户信息")
    @GetMapping("getByAccessTockenId/{id}")
    public R getByAccessTockenId(
            @ApiParam(name = "id", value = "唯一id", required = true)
            @PathVariable("id") Integer id
    ){
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + G.WX_APPID + "&secret=" + G.WX_SECRET;
            String retStr2 = HttpClientUtil.sendGet(url);
            GetAccessTockenResult getAccessTockenResult = G.ms_om.readValue(retStr2, GetAccessTockenResult.class);
            PersonalNoAccessTocken byId = accessTockenService.selectById(id);
            byId.setAccessToken(getAccessTockenResult.getAccess_token());
            accessTockenService.updateById(byId);
            PersonalNoSuperuesr superuesr = personalNoSuperuesrService.getByOpenIdId(byId.getOpenid());
            return R.ok().data(superuesr);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }
}

