package com.warm.utils;

import com.warm.system.entity.*;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @param
 * @return
 */
public class WebConst {


    public static final String LOGINFLAG = "LOGINFLG";
    //注册邀请码
    public static final String CODE = "jiazhangjia";
    //session存放超级用户的key
    public static final String SUPERUSER = "superUser";
    public static final String TASKINFOKEY = "PersonalNoUserInfoSuff";
    public static final String TOCKEN = "PersonalNoCookieUserInfoSuff";
    public static final String SESSIONPRE = "PersonalNoSession";
    public static final String SESSSUFF = "InfoSuff";
    public static final String WECHATSTATUS = "封禁";

    private static int textNum = 0; //文字
    private static int photoNum = 0; //图片
    private static int cardNum = 0; //名片
    private static int urlNum = 0; // 链接
    private static int intoGroup = 0; // 邀请入群
    private static int smallProgramNum = 0; //小程序
    private static int voiceMessage =0;

    //做判断，生成格式：2（1条文字，1条邀请入群）
    private static void initNum() {
        textNum = 0; //文字
        photoNum = 0; //图片
        cardNum = 0; //名片
        urlNum = 0; // 链接
        intoGroup = 0; // 邀请入群
        smallProgramNum = 0; //小程序
        voiceMessage = 0;
    }
//    获取任务消息显示
    public static String getTaskContentShow(List<PersonalNoTaskMessageSendContent> personalNoTaskMessageSendContents) {

        StringBuffer temp = new StringBuffer();
        temp.append("(");
        if (!VerifyUtils.collectionIsEmpty(personalNoTaskMessageSendContents)) {
            initNum();
            for (PersonalNoTaskMessageSendContent personalNoTaskMessageSendContent : personalNoTaskMessageSendContents) {
                if ("文字".equals(personalNoTaskMessageSendContent.getContentType())) {
                    textNum += 1;
                    continue;
                } else if ("图片".equals(personalNoTaskMessageSendContent.getContentType())) {
                    photoNum += 1;
                    continue;
                } else if ("名片".equals(personalNoTaskMessageSendContent.getContentType())) {
                    cardNum += 1;
                    continue;
                } else if ("链接".equals(personalNoTaskMessageSendContent.getContentType())) {
                    urlNum += 1;
                    continue;
                } else if ("邀请入群".equals(personalNoTaskMessageSendContent.getContentType())) {
                    intoGroup += 1;
                    continue;
                } else if ("小程序".equals(personalNoTaskMessageSendContent.getContentType())) {
                    smallProgramNum += 1;
                    continue;
                } else if ("语音消息".equals(personalNoTaskMessageSendContent.getContentType())) {
                    voiceMessage += 1;
                    continue;
                }
            }
            boolean flag = false;
            if (textNum > 0) {
                temp.append(textNum + "条文字");
                flag = true;
            }
            if (photoNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(photoNum + "条图片");
                flag = true;
            }
            if (intoGroup > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(intoGroup + "条入群邀请");
                flag = true;
            }
            if (urlNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(urlNum + "条链接");
                flag = true;
            }
            if (smallProgramNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(smallProgramNum + "条小程序");
                flag = true;
            }
            if (cardNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(cardNum + "条名片");
                flag = true;
            }
            if (voiceMessage > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(voiceMessage + "条语音消息");
            }
        }
        temp.append(")");
        return temp.toString();
    }

    //做判断，生成格式：2（1条文字，1条邀请入群）
//    获取标签消息发送显示
    public static String getLableContentShow(List<PersonalNoLableMessageSendContent> personalNoLableMessageSendContentList) {

        StringBuffer temp = new StringBuffer();
        temp.append("(");
        if (!VerifyUtils.collectionIsEmpty(personalNoLableMessageSendContentList)) {
            initNum();
            for (PersonalNoLableMessageSendContent personalNoLableMessageSendContent : personalNoLableMessageSendContentList) {
                if ("文字".equals(personalNoLableMessageSendContent.getContentType())) {
                    textNum += 1;
                    continue;
                } else if ("图片".equals(personalNoLableMessageSendContent.getContentType())) {
                    photoNum += 1;
                    continue;
                } else if ("链接".equals(personalNoLableMessageSendContent.getContentType())) {
                    urlNum += 1;
                    continue;
                } else if ("小程序".equals(personalNoLableMessageSendContent.getContentType())) {
                    smallProgramNum += 1;
                    continue;
                } else if ("邀请入群".equals(personalNoLableMessageSendContent.getContentType())) {
                    intoGroup += 1;
                    continue;
                }
                else if ("语音消息".equals(personalNoLableMessageSendContent.getContentType())) {
                    voiceMessage += 1;
                    continue;
                }else if ("名片".equals(personalNoLableMessageSendContent.getContentType())) {
                    cardNum += 1;
                    continue;
                }
            }
            boolean flag = false;
            if (textNum > 0) {
                temp.append(textNum + "条文字");
                flag = true;
            }
            if (photoNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(photoNum + "条图片");
                flag = true;
            }
            if (intoGroup > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(intoGroup + "条入群邀请");
                flag = true;
            }
            if (urlNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(urlNum + "条链接");
                flag = true;
            }
            if (smallProgramNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(smallProgramNum + "条小程序");
                flag = true;
            }
            if (cardNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(cardNum + "条名片");
                flag = true;
            }
            if (voiceMessage > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(voiceMessage + "条语音消息");
            }
        }
        temp.append(")");
        return temp.toString();
    }

    //做判断，生成格式：2（1条文字，1条邀请入群）
//    获取关键词消息显示
    public static String getKeyWordContentShow(List<PersonalNoKeywordContent> personalNoKeywordContentList) {

        StringBuffer temp = new StringBuffer();
        temp.append("(");
        if (!VerifyUtils.collectionIsEmpty(personalNoKeywordContentList)) {
            initNum();
            for (PersonalNoKeywordContent personalNoLableMessageSendContent : personalNoKeywordContentList) {
                if ("小程序".equals(personalNoLableMessageSendContent.getContentType())) {
                    smallProgramNum += 1;
                    continue;
                }else if ("图片".equals(personalNoLableMessageSendContent.getContentType())) {
                    photoNum += 1;
                    continue;
                } else if ("名片".equals(personalNoLableMessageSendContent.getContentType())) {
                    cardNum += 1;
                    continue;
                } else if ("语音消息".equals(personalNoLableMessageSendContent.getContentType())) {
                    voiceMessage += 1;
                    continue;
                }else if ("链接".equals(personalNoLableMessageSendContent.getContentType())) {
                    urlNum += 1;
                    continue;
                } else if ("邀请入群".equals(personalNoLableMessageSendContent.getContentType())) {
                    intoGroup += 1;
                    continue;
                }if ("文字".equals(personalNoLableMessageSendContent.getContentType())) {
                    textNum += 1;
                    continue;
                }
            }
            boolean flag = false;
            if (textNum > 0) {
                temp.append(textNum + "条文字");
                flag = true;
            }
            if (photoNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(photoNum + "条图片");
                flag = true;
            }
            if (intoGroup > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(intoGroup + "条入群邀请");
                flag = true;
            }
            if (urlNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(urlNum + "条链接");
                flag = true;
            }
            if (smallProgramNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(smallProgramNum + "条小程序");
                flag = true;
            }
            if (cardNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(cardNum + "条名片");
                flag = true;
            }
            if (voiceMessage > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(voiceMessage + "条语音消息");
            }
        }
        temp.append(")");
        return temp.toString();
    }

    public static String getMessageContentShow(List<PersonalNoMessageContent> messageContentList) {
        StringBuffer temp = new StringBuffer();
        temp.append("(");
        if (!VerifyUtils.collectionIsEmpty(messageContentList)) {
            initNum();
            for (PersonalNoMessageContent personalNoLableMessageSendContent : messageContentList) {
                if ("小程序".equals(personalNoLableMessageSendContent.getContentType())) {
                    smallProgramNum += 1;
                    continue;
                } else if ("名片".equals(personalNoLableMessageSendContent.getContentType())) {
                    cardNum += 1;
                    continue;
                } if ("文字".equals(personalNoLableMessageSendContent.getContentType())) {
                    textNum += 1;
                    continue;
                }else if ("语音消息".equals(personalNoLableMessageSendContent.getContentType())) {
                    voiceMessage += 1;
                    continue;
                }else if ("链接".equals(personalNoLableMessageSendContent.getContentType())) {
                    urlNum += 1;
                    continue;
                }else if ("图片".equals(personalNoLableMessageSendContent.getContentType())) {
                    photoNum += 1;
                    continue;
                } else if ("邀请入群".equals(personalNoLableMessageSendContent.getContentType())) {
                    intoGroup += 1;
                    continue;
                }
            }
            boolean flag = false;
            if (textNum > 0) {
                temp.append(textNum + "条文字");
                flag = true;
            }
            if (photoNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(photoNum + "条图片");
                flag = true;
            }
            if (intoGroup > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(intoGroup + "条入群邀请");
                flag = true;
            }
            if (urlNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(urlNum + "条链接");
                flag = true;
            }
            if (smallProgramNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(smallProgramNum + "条小程序");
                flag = true;
            }
            if (cardNum > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(cardNum + "条名片");
                flag = true;
            }
            if (voiceMessage > 0) {
                if (flag) {
                    temp.append(",");
                }
                temp.append(voiceMessage + "条语音消息");
            }
        }
        temp.append(")");
        return temp.toString();
    }
    /**
     * 将错误信息插入到数据库
     *
     * @param requestExceptionService
     * @param request
     * @param response
     * @param object
     * @return
     */
    public static boolean insertRequseException(PersonalNoRequestExceptionService requestExceptionService, HttpServletRequest request, HttpServletResponse response, Object object) {
        PersonalNoRequestException requestException = new PersonalNoRequestException();
        requestException.setCreateTime(new Date());
        requestException.setMethod(request.getMethod());
        requestException.setRequestBody(object.toString());
        requestException.setUrl(request.getRequestURL().toString());
        requestException.setRequestBody(object.toString());
        requestException.setStatusCode(response.getStatus());
        requestExceptionService.insert(requestException);
        return true;
    }

    /**
     * 时间格式转换带时分秒
     * Date  to   String
     * @return
     */
    public static String getNowDate(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    /**
     * 时间格式转换不带时分秒
     * Date   to   String
     * @return
     */
    public static String getNowDateNotHour(Date currentTime){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 时间格式转换不带时分秒
     * String   to   Date
     * @return
     */
    public static Date getDateByString(String date){
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    /**
     * 除法运算
     * @param d1
     * @param d2
     * @param len
     * @return
     */
    public static double div(double d1,double d2,int len) {
        if(d2==0){
            return 0;
        }
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2,len,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String getNames(Collection<?> list) {
        StringBuffer temp = new StringBuffer();
        boolean b = false;
        for (Object integer : list) {
            if(b){
                temp.append("|");
            }
            if(integer instanceof String){
                temp.append("'"+integer+"'");
            }else {
                temp.append(integer);
            }
            b = true;
        }
        return temp.toString();
    }

    public static String getLableNames(Collection<?> list) {
        StringBuffer temp = new StringBuffer();
        boolean b = false;
        temp.append("|");
        for (Object integer : list) {
            if(b){
                temp.append("|");
            }
            if(integer instanceof String){
                temp.append(""+integer+"");
            }else {
                temp.append(integer);
            }
            b = true;
        }
        temp.append("|");
        return temp.toString();
    }

}
