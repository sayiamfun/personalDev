package com.warm.system.controller;

import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.requre.GetNoEntity;
import com.warm.entity.robot.G;
import com.warm.entity.robot.WxResponseInfo2;
import com.warm.entity.robot.WxResponseInfo3;
import com.warm.entity.robot.responseInfo.SunApiResponse;
import com.warm.system.entity.*;
import com.warm.system.service.db1.*;
import com.warm.system.service.db2.PersonalNoOperationStockWechatAccountService;
import com.warm.utils.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Transactional
@CrossOrigin //跨域
@Controller
public class PageController extends SpringBootServletInitializer {

    protected SpringApplicationBuilder config(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(PageController.class);//该类就是controller层下的各个类名，看自己在那个类下写的就用那个类
    }

    public static Log log = LogFactory.getLog(PageController.class);

    @Autowired
    private PersonalNoUserService userService;
    @Autowired
    private PersonalNoAccessTockenService accessTockenService;
    @Autowired
    private PassageVisitorRecordService passageVisitorRecordService;
    @Autowired
    private ShortUrlService shortUrlService;
    @Autowired
    private PersonalNoOperationStockWechatAccountService wechatAccountService;
    @Autowired
    private PersonalNoTaskService noTaskService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;

    private String ZCDBPVR = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.passage_visitor_record);
    private String ZCDBAccessTocken = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_access_tocken);
    private String DBTask = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task);
    private String DBShortUrl = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.short_url);
    private String DBUser = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_user);
    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_request_exception);


    @ResponseBody
    @GetMapping("getAccId")
    public R getId(HttpServletRequest request){
        try {
            PersonalNoAccessTocken personalNoAccessTocken = new PersonalNoAccessTocken();
            personalNoAccessTocken.setFlag(0);
            personalNoAccessTocken.setDeleted(0);
            personalNoAccessTocken.setDb(ZCDBAccessTocken);
            accessTockenService.add(personalNoAccessTocken);
            return R.ok().data(personalNoAccessTocken.getId().toString());
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "", "获取登录id失败", "网页走丢了，请刷新。。。", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ResponseBody
    @GetMapping("wxShortUrl")
    public R getWxShortUrl(HttpServletRequest request) {
        try {
            //flag为0   id为通道id
            //flag为1   id为任务id
            //flag为2   id为任务渠道id
            //flag为3   id为通道渠道id
            Map<String, String> map = new HashMap<>();
            String roadId = request.getParameter("roadId");
            String flag = request.getParameter("flag");
            String id = request.getParameter("id");
            String shortUrl = null;
            if("0".equals(flag)){
                shortUrl = getRoadShortUrl("0",id);
            }else if("1".equals(flag)){
                shortUrl = getTaskShortUrl("0",id);
            }else if("2".equals(flag)){
               shortUrl = getTaskShortUrl(id, roadId);
            }else if("3".equals(flag)) {
                shortUrl = getRoadShortUrl(id, roadId);
            }
            map.put("url",shortUrl);
            return R.ok().data(map);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "roadId:"+request.getParameter("roadId")+" flag:"+request.getParameter("flag")+" id"+request.getParameter("id"), "获取短链失败", "网页走丢了，请刷新。。。", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }
    //根据条件生成不同的短链请求
    private String getTaskShortUrl(String id,String taskId){
        String redirect_uri_encode = "";
        String targetUrl = G.ms_SERVER_PORT + "/taskCallback?param="+"taskId="+taskId+"=channelId="+id;
        String getSql = DaoGetSql.getSql("SELECT * from "+DBShortUrl+" where org_url = ? limit 0,1",targetUrl);
        ShortUrl shortUrl = shortUrlService.getBySql(new Sql(getSql));
        String s = null;
        if(VerifyUtils.isEmpty(shortUrl) || VerifyUtils.isEmpty(shortUrl.getShortUrl())) {
            s = insertShorturl(shortUrl, id, taskId, redirect_uri_encode, targetUrl,1);
        }else {
            s = shortUrl.getShortUrl();
        }
        return s;
    }
    private String getRoadShortUrl(String id,String taskId){
        String redirect_uri_encode = "";
        String targetUrl = G.ms_SERVER_PORT + "/callback?param="+"taskId="+taskId+"=channelId="+id;
        String getSql = DaoGetSql.getSql("SELECT * from "+DBShortUrl+" where org_url = ? limit 0,1",targetUrl);
        ShortUrl shortUrl = shortUrlService.getBySql(new Sql(getSql));
        String s = null;
        if(VerifyUtils.isEmpty(shortUrl)) {
            s = insertShorturl(shortUrl, id, taskId, redirect_uri_encode, targetUrl,0);
        }else {
            s = shortUrl.getShortUrl();
        }
        return s;
    }
    //生成新的短链并添加到数据库
    private String insertShorturl(ShortUrl shortUrl, String id, String taskId, String redirect_uri_encode, String targetUrl, int i) {
        String s;
        s = getString(redirect_uri_encode, targetUrl);
        if(VerifyUtils.isEmpty(shortUrl)) {
            shortUrl = new ShortUrl();
        }
        if(i == 0) {
            shortUrl.setPassageId(Integer.parseInt(taskId));
        }else if(i == 1){
            shortUrl.setTaskId(Integer.parseInt(taskId));
        }
        shortUrl.setChannelId(Integer.parseInt(id));
        shortUrl.setOrgUrl(targetUrl);
        shortUrl.setShortUrl(s);
        shortUrl.setCreateTime(new Date());
        shortUrl.setDb(DBShortUrl);
        shortUrlService.add(shortUrl);
        return s;
    }
    private String getString(String redirect_uri_encode, String targetUrl) {
        String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + G.WX_APPID + "&redirect_uri=" + targetUrl + "&response_type=code&scope=snsapi_userinfo&state=0&connect_redirect=1#wechat_redirect";
        try {
            redirect_uri_encode = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return HttpClientUtil.sendGet("https://12i.cn/api.ashx?format=txt&userId=4590&key=AF5A09C5C41AF4625087B47224381808&url="+redirect_uri_encode);
    }
    //通道
    @GetMapping("callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取任务id
            String param = request.getParameter("param");
            String[] split = param.split("=");
            Map<String, String> map = new HashMap<>();
            try {
                WxResponseInfo3 wxResponseInfo3 = getWxResponseInfo3(request,0);
                map.put("openId", wxResponseInfo3.openid);
                map.put("nickName", wxResponseInfo3.nickname);
                map.put("unionId", wxResponseInfo3.unionid);
            }catch (Exception e){
            }
            log.info("将数据放入cookie");
            map.put("roadId", split[1]);
            CookieUtil.setCookie(request,response, WebConst.TASKINFOKEY, JsonObjectUtils.objectToJson(map), 60,true);
        } catch (Exception e) {
            G.requestException(DBRequestException, requestExceptionService, request, "param:"+ request.getParameter("param"), "通道回调异常", "", 1,e);
        }
        try {
            response.sendRedirect("task.html");
        } catch (IOException e) {
            G.requestException(DBRequestException, requestExceptionService, request, "param:"+ request.getParameter("param"), "通道回调跳转页面异常", "", 1,e);
        }
    }

    @GetMapping("personal")
    public ModelAndView personal(HttpServletRequest request, HttpServletResponse response,ModelAndView mv){
        try {
            System.err.println("personal.jsp");
        } catch (Exception e) {

        }
        mv.setViewName("personal");
        return mv;
    }

    @ResponseBody
    @GetMapping("testError")
    public R getError(HttpServletRequest request){
        try {
            int i = 2/0;
            return R.error().message("给你个报错");
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "", "测试异常", "", 1,e);
            return R.error().message("网页走丢了。。。请返回重试");
        }
    }

    @GetMapping("taskCallback")
    public ModelAndView taskCallback(HttpServletRequest request, HttpServletResponse response,ModelAndView mv) {
        try {
            log.info("将数据放入cookie");
            String param = request.getParameter("param");
            String[] split = param.split("=");
            int p_id = Integer.parseInt(split[1]);
            GetNoEntity noEntity = new GetNoEntity();
            try {
                WxResponseInfo3 wxResponseInfo3 = getWxResponseInfo3(request,1);
                noEntity.setOpenId(wxResponseInfo3.openid);
                noEntity.setUnionId(wxResponseInfo3.unionid);
                noEntity.setNickName(wxResponseInfo3.nickname);
            }catch (Exception e){
                e.printStackTrace();
            }
            String getSql = DaoGetSql.getSql("SELECT * from "+DBTask+" where id = ? and deleted = 0", p_id);
            PersonalNoTask task = noTaskService.getBySql(new Sql(getSql));
            if(task.getActivityType()==1){
                mv.addObject("flag","2");
            }else {
                noEntity.setTaskId(p_id);
                Map<String, Object> personalByTaskId = wechatAccountService.getPersonalByTaskId(noEntity);
                PersonalNoTask personalNoTask = (PersonalNoTask) personalByTaskId.get("task");
                PersonalNoOperationStockWechatAccount wechatAccount = (PersonalNoOperationStockWechatAccount) personalByTaskId.get("wechat");
                if (!VerifyUtils.isEmpty(personalNoTask)) {
                    int index = new Random().nextInt(personalNoTask.getRecommendedReasonsList().size());
                    mv.addObject("recommendedReasons", personalNoTask.getRecommendedReasonsList().get(index).getContent());
                    mv.addObject("taskTheme", personalNoTask.getTaskTheme());
                }
                if (!VerifyUtils.isEmpty(wechatAccount)) {
                    mv.addObject("headPortraitUrl", wechatAccount.getAvatar());
                    mv.addObject("Nickname", wechatAccount.getNickName());
                    mv.addObject("QrCode", wechatAccount.getQrCode());
                    mv.addObject("flag", "0");
                }else {
                    mv.addObject("flag","2");
                }
            }
        } catch (Exception e) {
            mv.addObject("flag","1");
            G.requestException(DBRequestException, requestExceptionService, request, "param:"+ request.getParameter("param"), "任务回调异常", "", 1,e);
        }
        mv.setViewName("personal");
        return mv;
    }

    private WxResponseInfo3 getWxResponseInfo3(HttpServletRequest request, Integer i) throws IOException {
        String p_code = request.getParameter("code");
        if (null == p_code) {
            G.i(-3, "OAuthController.callback", "没有获取到code参数。没有使用微信重定向链接？");
            throw new RuntimeException("没有获取到code参数。没有使用微信重定向链接？");
        }
        if (p_code.equals("-300")) {
            throw new RuntimeException("网路测试");
        }
        WxResponseInfo2 wxResponseInfo2 = HttpClientUtil.getWxResponseInfo2(p_code);
        String url3 = "https://api.weixin.qq.com/sns/userinfo?access_token=" + wxResponseInfo2.access_token + "&openid=" + wxResponseInfo2.openid + "&lang=zh_CN";
        String retStr3 = HttpClientUtil.sendGet(url3);
        WxResponseInfo3 wxResponseInfo3 = G.ms_om.readValue(retStr3, WxResponseInfo3.class);
        log.info("将用户信息插入到用户表");
        String getSql = DaoGetSql.getSql("SELECT * FROM "+DBUser+" WHERE unionid = ? order by create_time desc  limit 0,1",wxResponseInfo3.unionid);
        Sql sql = new Sql(getSql);
        PersonalNoUser user = userService.getBySql(sql);
        if(VerifyUtils.isEmpty(user)){
            getSql = DaoGetSql.getSql("SELECT * FROM "+DBUser+" WHERE openid = ? order by create_time desc limit 0,1",wxResponseInfo3.openid);
            sql.setSql(getSql);
            user = userService.getBySql(sql);
            if(VerifyUtils.isEmpty(user)) {
                user = new PersonalNoUser();
            }
        }
        user.setUnionid(wxResponseInfo3.unionid);
        user.setOpenid(wxResponseInfo3.openid);
        user.setAddress(wxResponseInfo3.country+wxResponseInfo3.province+wxResponseInfo3.city);
        user.setHeadPortrait(wxResponseInfo3.headimgurl);
        user.setGender(wxResponseInfo3.sex);
        user.setNickName(wxResponseInfo3.nickname);
        user.setCreateTime(new Date());
        user.setDb(DBUser);
        user.setDeleted(0);
        userService.add(user);
        if (wxResponseInfo3.errcode != null) {
            G.i(-3, "OAuthController.callback， 微信服务器返回错误 retStr3=", retStr3);
            throw new RuntimeException(retStr3);
        }
        //获取任务id
        String param = request.getParameter("param");
        String[] split = param.split("=");
        int p_id = Integer.parseInt(split[1]);
        int channelId = Integer.parseInt(split[3]);
        //TODO
        log.info("将渠道和用户对应关系插入到数据库");
        PassageVisitorRecord passageVisitorRecord = null;
        log.info("0：通道   1：任务");
        if(i == 0) {
            getSql = DaoGetSql.getSql("SELECT * FROM " + ZCDBPVR + " where passage_id = ? and channel_id = ? and union_id = ? order by id desc limit 0,1", p_id, channelId, wxResponseInfo3.unionid);
            sql.setSql(getSql);
            passageVisitorRecord = passageVisitorRecordService.getBySql(sql);
            if (VerifyUtils.isEmpty(passageVisitorRecord)) {
                passageVisitorRecord = new PassageVisitorRecord();
                passageVisitorRecord.setPassageId(p_id);
                passageVisitorRecord.setChannelId(channelId);
            }
        }else if(i == 1){
            getSql = DaoGetSql.getSql("SELECT * FROM " + ZCDBPVR + " where task_id = ? and channel_id = ? and union_id = ? order by id desc limit 0,1", p_id, channelId, wxResponseInfo3.unionid);
            sql.setSql(getSql);
            passageVisitorRecord = passageVisitorRecordService.getBySql(sql);
            if (VerifyUtils.isEmpty(passageVisitorRecord)) {
                passageVisitorRecord = new PassageVisitorRecord();
                passageVisitorRecord.setTaskId(p_id);
                passageVisitorRecord.setChannelId(channelId);
            }
        }
        if(!VerifyUtils.isEmpty(user.getWxId())){
            passageVisitorRecord.setUserWxId(user.getWxId());
        }
        passageVisitorRecord.setCity(wxResponseInfo3.city);
        passageVisitorRecord.setProvince(wxResponseInfo3.province);
        passageVisitorRecord.setCountry(wxResponseInfo3.country);
        passageVisitorRecord.setCreateTime(new Date());
        passageVisitorRecord.setCurrentIp(request.getRemoteHost());
        passageVisitorRecord.setHeadImage(wxResponseInfo3.headimgurl);
        passageVisitorRecord.setSex(wxResponseInfo3.sex);
        passageVisitorRecord.setNickName(wxResponseInfo3.nickname);
        passageVisitorRecord.setOpenId(wxResponseInfo3.openid);
        passageVisitorRecord.setUnionId(wxResponseInfo3.unionid);
        passageVisitorRecord.setPrivilege(wxResponseInfo3.privilege.toString());
        passageVisitorRecord.setScanQrcodeTime(new Date());
        passageVisitorRecord.setDb(ZCDBPVR);
        passageVisitorRecordService.add(passageVisitorRecord);
        return wxResponseInfo3;
    }


}
