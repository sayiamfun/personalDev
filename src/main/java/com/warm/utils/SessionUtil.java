package com.warm.utils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
    /*
     * 添加数据到session
     */
    public static void setSession(HttpServletRequest request , String key , Object value){
        HttpSession session = request.getSession();
        session.setAttribute(key , value);
    }
    /*
     * 得到session中的数据
     */
    public static Object getSession(HttpServletRequest request , String key){
        HttpSession session = request.getSession();
        Object value = session.getAttribute(key);
        return value;
    }
    /*
     * 删除session中的数据
     */
    public static void removeSession(HttpServletRequest request , String key){
        HttpSession session = request.getSession();
        session.removeAttribute(key);
    }
    /*
     * 清空所有的session
     */
    public static void invalidateSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
    }
}
