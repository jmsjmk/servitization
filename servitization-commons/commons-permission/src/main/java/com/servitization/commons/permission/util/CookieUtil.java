package com.servitization.commons.permission.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    private final static int DEFAUL_COOKIE_MAXAGE = 30 * 24 * 60 * 60; // cookie默认生命周期是一个月。单位为秒

    private CookieUtil() {

    }

    public static void saveUserCookieToLocale(HttpServletResponse response, int maxAge, String cookieName,
                                              String token) {
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setPath("/");
        int tempCookieAge = maxAge <= 0 ? DEFAUL_COOKIE_MAXAGE : maxAge;
        cookie.setMaxAge(tempCookieAge);
        response.addCookie(cookie);
    }

    public static Cookie getCookieFromLocalByName(HttpServletRequest request, String cookieName) {
        if (StringUtils.isBlank(cookieName)) {
            return null;
        }
        Cookie[] cookieArry = request.getCookies();
        Cookie tempCookie = null;
        if (cookieArry != null) {
            for (int i = 0; i < cookieArry.length; i++) {
                tempCookie = cookieArry[i];

                if (StringUtils.equals(tempCookie.getName(), cookieName)) {
                    return tempCookie;
                }
            }
        }
        return null;
    }

    public static void cleanCookieByName(HttpServletRequest request, String cookieName) {
        Cookie cookie = getCookieFromLocalByName(request, cookieName);
        if (cookie == null) {
            return;
        }
        cookie.setMaxAge(-1);
    }
}
