package com.warm.config.authInterceptor;

import com.warm.system.entity.PersonalNoLogInfo;
import com.warm.utils.*;
import com.warm.utils.ImageUpload.Base64Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    private static Log log = LogFactory.getLog(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("*********************************************");
        StringBuffer requestURL = request.getRequestURL();
        String ip = request.getRemoteHost();
        log.info(requestURL);
        String cookieValue = CookieUtil.getCookieValue(request, WebConst.TOCKEN, false);
        System.err.println(cookieValue);
        if(!VerifyUtils.isEmpty(cookieValue)){
            String s = SymmetricEncoder.AESDncode(WebConst.ASC, cookieValue);
            Map map = JsonObjectUtils.jsonToPojo(s, Map.class);
            System.err.println(map);
            Object superUser = SessionUtil.getSession(request, WebConst.SESSIONPRE+map.get("id").toString()+WebConst.SESSSUFF);
            if(!VerifyUtils.isEmpty(superUser)){
                //添加日志
                PersonalNoLogInfo logInfo = new PersonalNoLogInfo();
                logInfo.setRequestIp(ip);
                logInfo.setDate(new Date());
                logInfo.setRequestUrl(requestURL.toString());
                logInfo.setSuperUserId(Integer.parseInt(map.get("id").toString()));
                logInfo.setSuperUserName(map.get("name").toString());
                logInfo.setDb("personal_zc_01.personal_no_log_info");
                String s1 = HttpClientUtil.sendPost(WebConst.ADDLOGADDRESS, JsonObjectUtils.objectToJson(logInfo));
                request.setAttribute(WebConst.SUPERUSERID,map.get("id"));
                return true;
            }
        }
        CookieUtil.setCookie(request,response,WebConst.LOGINFLAG,"1",3600,true);
        return false;
//        CookieUtil.setCookie(request,response,WebConst.LOGINFLAG,"0",3600,true);
//        return true;
    }

//    {
//        System.err.println("***************************************************************");
//        StringBuffer requestURL = request.getRequestURL();
//        System.err.println(requestURL);
//        Map<String,String> map = new HashMap<>();
//        map.put("id","123");
//        map.put("name","张三");
//        System.err.println(map);
//        SessionUtil.setSession(request,"Personal"+123+"suff",map);
//        System.err.println("*************************************************");
//        Object personal = SessionUtil.getSession(request, "Personal"+123+"suff");
//        System.err.println(personal);
//        System.err.println("*************************************************");
//        CookieUtil.setCookie(request,response,"Personal",JsonObjectUtils.objectToJson(map),60,true);
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//            System.err.println(cookie.getName());
//        }
//        System.err.println("*************************************************");
//        System.err.println(personal);
//        System.err.println("*******************************************");
//    }


}

