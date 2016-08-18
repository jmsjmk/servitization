package com.servitization.commons.permission.interceptor;

import com.servitization.commons.permission.service.impl.LoginService;
import com.servitization.commons.permission.util.Constant;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor, InitializingBean {

    private String subSystem;
    private String cookieName;
    private String login_view_path;

    private boolean allIntercept;// 系统是否是全部的页面需要有登录，反之可以在任意类上面或者方法上面需要有登录

    @Resource
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        loginService.setCookieName(cookieName);
        loginService.setSubSystem(subSystem);
        loginService.setAllIntercept(allIntercept);
        loginService.setLogin_view_path(login_view_path);
        request.setAttribute(Constant.SUB_SYSTEM_NAME, subSystem);
        return loginService.loginVertify(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (cookieName == null) {
            throw new RuntimeException("cookie name is blank");
        }

        if (subSystem == null) {
            throw new RuntimeException("subSystem name is blank");
        }
        if (login_view_path == null) {
            throw new RuntimeException("login_view_path  is blank");
        }
    }

    public String getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getLogin_view_path() {
        return login_view_path;
    }

    public void setLogin_view_path(String login_view_path) {
        this.login_view_path = login_view_path;
    }


    public boolean getAllIntercept() {
        return allIntercept;
    }

    public void setAllIntercept(boolean allIntercept) {
        this.allIntercept = allIntercept;
    }

}
