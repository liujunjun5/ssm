package com.work.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    /**
     * 会话级cookie生成
     */
    public static void saveCookie(HttpServletResponse response,String key ,String token) {
        saveCookie(response, key, token,0, 0); // 调用持久级方法，但传递 0 以表示会话级
    }

    /**
     * 持久级cookie生成
     */
    public static void saveCookie(HttpServletResponse response, String key, String token, Integer constants, Integer count) {
        Cookie cookie = new Cookie(key, token);

        // 如果 constants 和 count 都大于 0，则设置持久化 Cookie
        if (constants > 0 && count > 0) {
            cookie.setMaxAge(constants * count);
        }
        try {
            if(constants<0 || count<0){
                throw new Exception("TTL不合法");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**ljz
     * 根据键名删除cookie，并返回被删除的value做进一步处理
     */
    public static void cleanCookie(HttpServletResponse response,
                                      HttpServletRequest request,
                                      String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    //stringRedisTemplate.delete("user:"+cookie.getValue());
//                    value = cookie.getValue();
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    break;
                }
            }
        }
//        return value;
    }

    public static String getCookie(HttpServletRequest request, String key) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)){
                    value = cookie.getValue();
                    return value;
                }
            }
        }
        return value;
    }

}
