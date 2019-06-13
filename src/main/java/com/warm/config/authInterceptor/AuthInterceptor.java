package com.warm.config.authInterceptor;

import com.warm.entity.DB;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoLogInfo;
import com.warm.system.entity.PersonalNoSuperuesr;
import com.warm.utils.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    private static Log log = LogFactory.getLog(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            log.info("*********************************************");
            StringBuffer requestURL = request.getRequestURL();
            String ip = request.getRemoteHost();
            log.info(requestURL);
            String cookieValue = CookieUtil.getCookieValue(request, WebConst.TOCKEN, true);
            if(!VerifyUtils.isEmpty(cookieValue)){
                Object superUser = SessionUtil.getSession(request, WebConst.SESSIONPRE+cookieValue+WebConst.SESSSUFF);
                if(!VerifyUtils.isEmpty(superUser)){
                    //添加日志
                    PersonalNoSuperuesr personalNoSuperuesr = JsonObjectUtils.jsonToPojo(superUser.toString(), PersonalNoSuperuesr.class);
                    PersonalNoLogInfo logInfo = new PersonalNoLogInfo();
                    logInfo.setRequestIp(ip);
                    logInfo.setDate(new Date());
                    logInfo.setRequestUrl(requestURL.toString());
                    logInfo.setSuperUserId(Integer.parseInt(cookieValue));
                    logInfo.setSuperUserName(personalNoSuperuesr.getSuperName());
                    logInfo.setDb(DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_log_info));
                    String s1 = HttpClientUtil.sendPost(G.ADDLOGADDRESS, JsonObjectUtils.objectToJson(logInfo));
                    request.setAttribute(WebConst.SUPERUSER,personalNoSuperuesr);
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("拦截器出错");
        }
        CookieUtil.setCookie(request,response,WebConst.LOGINFLAG,"1",3600,true);
        return false;
    }



}

