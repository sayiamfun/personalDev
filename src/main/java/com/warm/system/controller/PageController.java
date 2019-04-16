package com.warm.system.controller;

import com.warm.entity.R;
import com.warm.entity.requre.GetAccessTockenResult;
import com.warm.entity.requre.WxUrlResult;
import com.warm.entity.robot.G;
import com.warm.entity.robot.WxResponseInfo2;
import com.warm.entity.robot.WxResponseInfo3;
import com.warm.system.entity.PersonalNoAccessTocken;
import com.warm.system.entity.PersonalNoPassageClickRecord;
import com.warm.system.entity.PersonalNoRoad;
import com.warm.system.entity.PersonalNoUser;
import com.warm.system.service.db1.*;
import com.warm.utils.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Transactional
@CrossOrigin //跨域
@Controller
public class PageController {

    public static Log log = LogFactory.getLog(PageController.class);
    @Value("${fileServer.ms_SERVER_ROOT_URL}")
    String ms_SERVER_ROOT_URL;
    @Autowired
    private PersonalNoPassageClickRecordService passageClickRecordService;
    @Autowired
    private PersonalNoWxUserService wxUserService;
    @Autowired
    private PersonalNoPeopleService peopleService;
    @Autowired
    private PersonalNoUserService userService;
    @Autowired
    private PersonalNoTaskPersonalService taskPersonalService;
    @Autowired
    private PersonalNoFriendsService friendsService;
    @Autowired
    private PersonalNoPhoneTaskGroupService taskGroupService;
    @Autowired
    private PersonalNoTaskService noTaskService;
    @Autowired
    private PersonalNoPhoneTaskService taskService;
    @Autowired
    private PersonalNoBlacklistService blacklistService;
    @Autowired
    private PersonalNoTaskDataService taskDataService;
    @Autowired
    private PersonalNoValueTableService valueTableService;
    @Autowired
    private PersonalNoRoadService roadService;
    @Autowired
    private PersonalNoAccessTockenService accessTockenService;

    @ResponseBody
    @GetMapping("longUrl")
    public R getShortUrl(HttpServletRequest request) {
        try {
            String p_targetUrl = request.getParameter("targetUrl");
            GetAccessTockenResult getAccessTockenResult = getGetAccessTockenResult();
            String cmd2 = "https://12i.cn/api.ashx?format=txt&userId=4590&key=AF5A09C5C41AF4625087B47224381808&url=" + p_targetUrl;
            String s = HttpClientUtil.sendGet(cmd2);
            PersonalNoAccessTocken personalNoAccessTocken = accessTockenService.getLast();
            if(personalNoAccessTocken.getFlag() == 1) {
                personalNoAccessTocken = new PersonalNoAccessTocken();
                personalNoAccessTocken.setFlag(0);
                accessTockenService.insert(personalNoAccessTocken);
            }
            Map<String, String> map = new HashMap<>();
            map.put("id", personalNoAccessTocken.getId().toString());
            map.put("url", s);
            return R.ok().data(map);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }
    @ResponseBody
    @GetMapping("wxShortUrl")
    public R getWxShortUrl(HttpServletRequest request) {
        try {
            String p_targetUrl = request.getParameter("targetUrl");
            String openid = request.getParameter("openid");
            String roadId = request.getParameter("roadId");
            PersonalNoRoad roadById = roadService.selectById(roadId);
            if(VerifyUtils.isEmpty(roadById.getRoadUrl())) {
                PersonalNoAccessTocken accessTocken = accessTockenService.getByOpenId(openid);
                log.info("获取短链");
                String s = getWxUrlResult(accessTocken, p_targetUrl);
                WxUrlResult wxUrlResult = G.ms_om.readValue(s, WxUrlResult.class);
                if (wxUrlResult.getErrcode() != 0) {
                    log.info("获取GetAccessTockenResult");
                    GetAccessTockenResult getAccessTockenResult = getGetAccessTockenResult();
                    accessTocken.setAccessToken(getAccessTockenResult.getAccess_token());
                    accessTockenService.updateById(accessTocken);
                    log.info("获取短链");
                    s = getWxUrlResult(accessTocken, p_targetUrl);
                    wxUrlResult = G.ms_om.readValue(s, WxUrlResult.class);
                }
                roadById.setRoadUrl(wxUrlResult.getShort_url());
                roadService.updateById(roadById);
            }
            Map<String, String> map = new HashMap<>();
            map.put("url", roadById.getRoadUrl());
            return R.ok().data(map);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @GetMapping("entry")
    public void entry(HttpServletRequest request, HttpServletResponse response) {
        try {
            String p_id = request.getParameter("roadId");
            String targetUrl = ms_SERVER_ROOT_URL + "/callback?roadId=" + p_id;
            String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + G.WX_APPID + "&redirect_uri=" + targetUrl + "&response_type=code&scope=snsapi_userinfo&state=0&connect_redirect=1#wechat_redirect";
            log.info("统计点击通道人数");
            clickUrl(request, Integer.parseInt(p_id));
            try {
                response.sendRedirect(redirectUrl);
            } catch (IOException e) {
                log.info("出错了");
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //统计点击通道人数
    private void clickUrl(HttpServletRequest request, Integer roadId) {
        String ipAddress = request.getRemoteHost();
        PersonalNoRoad byId = roadService.selectById(roadId);
        byId.setClickRoadNum(byId.getClickRoadNum()==null?0:byId.getClickRoadNum()+1);
        PersonalNoPassageClickRecord passageClickRecord =  passageClickRecordService.getByIpAndTime(ipAddress, new Date());
        if(VerifyUtils.isEmpty(passageClickRecord)) {
            log.info("根据ip判断实际点击任务");
            byId.setLessClickRoadNum(byId.getLessClickRoadNum() == null ? 0 : byId.getLessClickRoadNum() + 1);
        }
        roadService.updateById(byId);
        PersonalNoPassageClickRecord tempPCR = new PersonalNoPassageClickRecord();
        tempPCR.setPassageId(roadId);
        tempPCR.setIp(request.getRemoteHost());
        tempPCR.setClickTime(new Date());
        String requestHeaderInfo = request.getMethod() + " " + request.getRequestURL().toString() + " " + request.getProtocol() + "\r\n";
        Enumeration<String> enumer = request.getHeaderNames();
        while (enumer.hasMoreElements()) {
            String key = enumer.nextElement();
            requestHeaderInfo += key + ": " + request.getHeader(key) + "\r\n";
        }
        String requestContent = "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\r\n" + requestHeaderInfo + "\r\n" + G.ReadAsChars(request) + "\r\nvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv";
        String calleeIP = request.getRemoteHost();
        String outputInfo = "log_before==============================\r\n" + new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()) + "\t调用者ip地址:" + calleeIP + "\r\n请求体:\r\n" + requestContent + "\r\n=================================\r\n";
        tempPCR.setRequestInfo(outputInfo);
        boolean save = passageClickRecordService.insert(tempPCR);
        if (!save) {
            throw new RuntimeException("插入数据库出错");
        }
    }

    //通道
    @GetMapping("callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        try {
           //获取任务id
            String p_id = request.getParameter("roadId");
            String p_code = request.getParameter("code");

            if (null == p_code) {
                G.i(-3, "OAuthController.callback", "没有获取到code参数。没有使用微信重定向链接？");
                throw new RuntimeException("没有获取到code参数。没有使用微信重定向链接？");
            }

            if (p_code.equals("-300")) {
                throw new RuntimeException("网路测试");
            }
            //获取code后，请求以下链接获取access_token：  https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
            WxResponseInfo2 wxResponseInfo2 = HttpClientUtil.getWxResponseInfo2(p_code);
            String url3 = "https://api.weixin.qq.com/sns/userinfo?access_token=" + wxResponseInfo2.access_token + "&openid=" + wxResponseInfo2.openid + "&lang=zh_CN";
            String retStr3 = HttpClientUtil.sendGet(url3);
            WxResponseInfo3 wxResponseInfo3 = G.ms_om.readValue(retStr3, WxResponseInfo3.class);
            log.info("将用户信息插入到用户表");
            PersonalNoUser user = userService.getByUnionId(wxResponseInfo3.unionid);
            if(VerifyUtils.isEmpty(user)){
                user = userService.getByOpenid(wxResponseInfo3.openid);
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
            userService.insert(user);
            if (wxResponseInfo3.errcode != null) {
                G.i(-3, "OAuthController.callback， 微信服务器返回错误 retStr3=", retStr3);
                throw new RuntimeException(retStr3);
            }
            log.info("将数据放入cookie");
            Map<String, String> map = new HashMap<>();
            map.put("roadId", p_id);
            map.put("openId", wxResponseInfo3.openid);
            map.put("nickName", wxResponseInfo3.nickname);
            map.put("unionId", wxResponseInfo3.unionid);
            CookieUtil.setCookie(request,response, WebConst.TASKINFOKEY, JsonObjectUtils.objectToJson(map), 60,true);
            response.sendRedirect("task.html");
        } catch (Exception e) {
            G.e(e);
        }
    }

    private GetAccessTockenResult getGetAccessTockenResult() throws java.io.IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + G.WX_APPID + "&secret=" + G.WX_SECRET;
        String retStr2 = HttpClientUtil.sendGet(url);
        return G.ms_om.readValue(retStr2, GetAccessTockenResult.class);
    }

    private String getWxUrlResult(PersonalNoAccessTocken accessTocken, String longUrl){
        String url = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=" + accessTocken.getAccessToken();
        HashMap<String, String> tempHashMap1 = new HashMap<>();
        tempHashMap1.put("action", "long2short");
        tempHashMap1.put("long_url", longUrl);
        WxUrlResult wxUrlResult = new WxUrlResult();
        wxUrlResult.setErrcode(-1);
        String s = JsonObjectUtils.objectToJson(wxUrlResult);
        try {
            s = HttpClientUtil.sendPost(url, JsonObjectUtils.objectToJson(tempHashMap1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

}
