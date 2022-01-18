package com.george.seckill.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description Cookie工具类
 */
public class CookieUtil {
    //从请求中读取cookie
    public static String readLoginToken(HttpServletRequest request,String cookieName){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck : cks){
                if(StringUtils.equals(ck.getName(), cookieName)){
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    //往响应中写cookie
    public static void writeLoginToken(HttpServletResponse response,String token,String cookieName){
        Cookie cookie = new Cookie(cookieName, token);
        //将cookie设置在根目录下面
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        //设置cookie的有效期，单位是秒(两小时)
        //如果这个maxage不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。
        cookie.setMaxAge(60*60*2);
        response.addCookie(cookie);
    }

    //删除cookie(从请求中读，往响应中写,已经删除完了的)
    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response,String cookieName){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(StringUtils.equals(cookie.getName(), cookieName)){
                    cookie.setDomain(cookieName);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }
}
