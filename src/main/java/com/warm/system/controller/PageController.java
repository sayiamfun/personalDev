package com.warm.system.controller;

import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.requre.GetAccessTockenResult;
import com.warm.entity.requre.GetNoEntity;
import com.warm.entity.requre.WxUrlResult;
import com.warm.entity.robot.G;
import com.warm.entity.robot.WxResponseInfo2;
import com.warm.entity.robot.WxResponseInfo3;
import com.warm.system.entity.*;
import com.warm.system.service.db1.*;
import com.warm.utils.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    private PersonalNoUserService userService;
    @Autowired
    private PersonalNoRoadService roadService;
    @Autowired
    private PersonalNoAccessTockenService accessTockenService;
    @Autowired
    private PersonalNoTaskService noTaskService;
    @Autowired
    private PersonalNoService noService;
    @Autowired
    private PersonalNoTaskChannelService taskChannelService;

    @ResponseBody
    @GetMapping("getAccId")
    public R getId(){
        try {
            PersonalNoAccessTocken personalNoAccessTocken = new PersonalNoAccessTocken();
            personalNoAccessTocken.setFlag(0);
            personalNoAccessTocken.setDeleted(0);
            personalNoAccessTocken.setDb(DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_access_tocken));
            accessTockenService.add(personalNoAccessTocken);
            return R.ok().data(personalNoAccessTocken.getId().toString());
        }catch (Exception e){
            e.printStackTrace();
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

            if("0".equals(flag)){
                PersonalNoRoad personalNoRoad = roadService.selectById(id);
                if(VerifyUtils.isEmpty(personalNoRoad.getRoadUrl())) {
                    String shortUrl = getRoadShortUrl("0",id);
                    personalNoRoad.setRoadUrl(shortUrl);
                    roadService.updateById(personalNoRoad);
                }
                map.put("url", personalNoRoad.getRoadUrl());
            }else if("1".equals(flag)){
                PersonalNoTask personalNoTask = noTaskService.selectById(id);
                if(VerifyUtils.isEmpty(personalNoTask.getTaskUrl())) {
                    String shortUrl = getTaskShortUrl("0",id);
                    personalNoTask.setTaskUrl(shortUrl);
                    noTaskService.updateById(personalNoTask);
                }
                map.put("url", personalNoTask.getTaskUrl());
            }else if("2".equals(flag)){
                PersonalNoTaskChannel personalNoTaskChannel = taskChannelService.selectById(id);
                if(VerifyUtils.isEmpty(personalNoTaskChannel.getUrl())) {
                    String shortUrl = getTaskShortUrl(id, roadId);
                    personalNoTaskChannel.setUrl(shortUrl);
                    taskChannelService.updateById(personalNoTaskChannel);
                }
                map.put("url", personalNoTaskChannel.getUrl());
            }else if("3".equals(flag)){
                PersonalNoTaskChannel personalNoTaskChannel = taskChannelService.selectById(id);
                PersonalNoTaskChannel personalNoTaskChannel1 = taskChannelService.selectByTaskIdAndChannelId(roadId,personalNoTaskChannel.getChannelId(),1);
                if(!VerifyUtils.isEmpty(personalNoTaskChannel1)){
                    if(VerifyUtils.isEmpty(personalNoTaskChannel1.getUrl())) {
                        String shortUrl = getRoadShortUrl(id, roadId);
                        personalNoTaskChannel1.setUrl(shortUrl);
                        taskChannelService.updateById(personalNoTaskChannel1);
                    }
                }else {
                    personalNoTaskChannel1 = new PersonalNoTaskChannel();
                    personalNoTaskChannel1.setId(null);
                    personalNoTaskChannel1.setPersonalNoTaskId(Integer.parseInt(roadId));
                    personalNoTaskChannel1.setChannelName(personalNoTaskChannel.getChannelName());
                    personalNoTaskChannel1.setChannelId(personalNoTaskChannel.getChannelId());
                    String shortUrl = getRoadShortUrl("0",roadId);
                    personalNoTaskChannel1.setRoadOrTask("1");
                    personalNoTaskChannel1.setUrl(shortUrl);
                    taskChannelService.insert(personalNoTaskChannel1);
                }
                map.put("url",personalNoTaskChannel1.getUrl());
            }
            return R.ok().data(map);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }
    //根据条件生成不同的短链请求
    private String getTaskShortUrl(String id,String taskId){
        String redirect_uri_encode = "";
        String targetUrl = ms_SERVER_ROOT_URL + "/taskCallback?taskId="+taskId+"&channelId="+id;
        String s = getString(redirect_uri_encode, targetUrl);
        return s;
    }
    private String getRoadShortUrl(String id, String roadId){
        String redirect_uri_encode = "";
        String targetUrl = ms_SERVER_ROOT_URL + "/callback?roadId="+roadId+"&channelId="+id;
        String s = getString(redirect_uri_encode, targetUrl);
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

    @GetMapping("taskCallback")
    public void taskCallback(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取任务id
            String p_id = request.getParameter("taskId");
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
            if (wxResponseInfo3.errcode != null) {
                G.i(-3, "OAuthController.callback， 微信服务器返回错误 retStr3=", retStr3);
                throw new RuntimeException(retStr3);
            }
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
            //TODO
            log.info("将数据放入cookie");
            GetNoEntity noEntity = new GetNoEntity();
            noEntity.setOpenId(wxResponseInfo3.openid);
            noEntity.setUnionId(wxResponseInfo3.unionid);
            noEntity.setNickName(wxResponseInfo3.nickname);
            noEntity.setTaskId(Integer.parseInt(p_id));
            Map<String, Object> personalByTaskId = noService.getPersonalByTaskId(noEntity);
            Base64 base64 = new Base64();
            String base64Sign = base64.encodeToString(JsonObjectUtils.objectToJson(personalByTaskId).getBytes("UTF-8"));
            CookieUtil.setCookie(request,response, WebConst.TASKINFOKEY, base64Sign, 60,true);
            response.sendRedirect("personal.html");
        } catch (Exception e) {
            G.e(e);
        }
    }

//    @GetMapping("entry")
//    public void entry(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            String p_id = request.getParameter("roadId");
//            String targetUrl = ms_SERVER_ROOT_URL + "/callback?roadId=" + p_id;
//            String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + G.WX_APPID + "&redirect_uri=" + targetUrl + "&response_type=code&scope=snsapi_userinfo&state=0&connect_redirect=1#wechat_redirect";
//            log.info("统计点击通道人数");
//            clickUrl(request, Integer.parseInt(p_id));
//            try {
//                response.sendRedirect(redirectUrl);
//            } catch (IOException e) {
//                log.info("出错了");
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    //统计点击通道人数
//    private void clickUrl(HttpServletRequest request, Integer roadId) {
//        String ipAddress = request.getRemoteHost();
//        PersonalNoRoad byId = roadService.selectById(roadId);
//        byId.setClickRoadNum(byId.getClickRoadNum()==null?0:byId.getClickRoadNum()+1);
//        Date date = new Date();
//        String sql = DaoGetSql.getSql("SELECT id,passage_id,ip,request_info,click_time FROM " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_passage_click_record) + " where ip = ? and click_time between ? and ? LIMIT 0,1", ipAddress, WebConst.getNowDate(new Date(date.getTime() - 20 * 60 * 1000)), WebConst.getNowDate(new Date(date.getTime() + 60 * 1000)));
//        PersonalNoPassageClickRecord passageClickRecord =  passageClickRecordService.getOne(sql);
//        if(VerifyUtils.isEmpty(passageClickRecord)) {
//            log.info("根据ip判断实际点击任务");
//            byId.setLessClickRoadNum(byId.getLessClickRoadNum() == null ? 0 : byId.getLessClickRoadNum() + 1);
//        }
//        roadService.updateById(byId);
//        PersonalNoPassageClickRecord tempPCR = new PersonalNoPassageClickRecord();
//        tempPCR.setPassageId(roadId);
//        tempPCR.setIp(request.getRemoteHost());
//        tempPCR.setClickTime(new Date());
//        String requestHeaderInfo = request.getMethod() + " " + request.getRequestURL().toString() + " " + request.getProtocol() + "\r\n";
//        Enumeration<String> enumer = request.getHeaderNames();
//        while (enumer.hasMoreElements()) {
//            String key = enumer.nextElement();
//            requestHeaderInfo += key + ": " + request.getHeader(key) + "\r\n";
//        }
//        String requestContent = "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\r\n" + requestHeaderInfo + "\r\n" + G.ReadAsChars(request) + "\r\nvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv";
//        String calleeIP = request.getRemoteHost();
//        String outputInfo = "log_before==============================\r\n" + new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()) + "\t调用者ip地址:" + calleeIP + "\r\n请求体:\r\n" + requestContent + "\r\n=================================\r\n";
//        tempPCR.setRequestInfo(outputInfo);
//        tempPCR.setDb(DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_passage_click_record));
//        int save = passageClickRecordService.add(tempPCR);
//        if (save == 0) {
//            throw new RuntimeException("插入数据库出错");
//        }
//    }



//    @GetMapping("taskEntry")
//    public void taskEntry(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            String p_id = request.getParameter("taskId");
//            String targetUrl = ms_SERVER_ROOT_URL + "/taskCallback?taskId=" + p_id;
//            String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + G.WX_APPID + "&redirect_uri=" + targetUrl + "&response_type=code&scope=snsapi_userinfo&state=0&connect_redirect=1#wechat_redirect";
//            try {
//                response.sendRedirect(redirectUrl);
//                log.info("去重");
//            } catch (IOException e) {
//                log.info("出错了");
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



//    @ResponseBody
//    @GetMapping("longUrl")
//    public R getShortUrl(HttpServletRequest request) {
//        try {
//            String p_targetUrl = request.getParameter("targetUrl");
//            String cmd2 = "https://12i.cn/api.ashx?format=txt&userId=4590&key=AF5A09C5C41AF4625087B47224381808&url=" + p_targetUrl;
//            String s = HttpClientUtil.sendGet(cmd2);
//            Map<String, String> map = new HashMap<>();
//            map.put("url", s);
//            return R.ok().data(map);
//        }catch (Exception e){
//            e.printStackTrace();
//            return R.error().message("网页走丢了，请刷新。。。");
//        }
//    }

//    private GetAccessTockenResult getGetAccessTockenResult() throws java.io.IOException {
//        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + G.WX_APPID + "&secret=" + G.WX_SECRET;
//        String retStr2 = HttpClientUtil.sendGet(url);
//        return G.ms_om.readValue(retStr2, GetAccessTockenResult.class);
//    }

//    private String getWxUrlResult(PersonalNoAccessTocken accessTocken, String longUrl){
//        String url = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=" + accessTocken.getAccessToken();
//        HashMap<String, String> tempHashMap1 = new HashMap<>();
//        tempHashMap1.put("action", "long2short");
//        tempHashMap1.put("long_url", longUrl);
//        WxUrlResult wxUrlResult = new WxUrlResult();
//        wxUrlResult.setErrcode(-1);
//        String s = JsonObjectUtils.objectToJson(wxUrlResult);
//        try {
//            s = HttpClientUtil.sendPost(url, JsonObjectUtils.objectToJson(tempHashMap1));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return s;
//    }

//        private WxUrlResult getWxUrlResult(String p_targetUrl, String openid) throws IOException {
//        String sql = DaoGetSql.getSql("select id,flag,access_token,openid,refreshtoken,deleted from " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_access_tocken) + " where openid = ? order by id desc limit 0,1", openid);
//        PersonalNoAccessTocken accessTocken = accessTockenService.getOne(sql);
//        log.info("获取短链");
//        String s = getWxUrlResult(accessTocken, p_targetUrl);
//        WxUrlResult wxUrlResult = G.ms_om.readValue(s, WxUrlResult.class);
//        if (wxUrlResult.getErrcode() != 0) {
//            log.info("获取GetAccessTockenResult");
//            GetAccessTockenResult getAccessTockenResult = getGetAccessTockenResult();
//            accessTocken.setAccessToken(getAccessTockenResult.getAccess_token());
//            accessTocken.setDb(DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_access_tocken));
//            accessTockenService.updateOneById(accessTocken);
//            log.info("获取短链");
//            s = getWxUrlResult(accessTocken, p_targetUrl);
//            wxUrlResult = G.ms_om.readValue(s, WxUrlResult.class);
//        }
//        return wxUrlResult;
//    }

}
