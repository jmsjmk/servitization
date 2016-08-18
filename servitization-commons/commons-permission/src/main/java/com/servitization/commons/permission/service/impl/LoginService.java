package com.servitization.commons.permission.service.impl;

import com.alibaba.fastjson.JSON;
import com.servitization.commons.permission.annotation.Login;
import com.servitization.commons.permission.http.entity.AosDataEntity;
import com.servitization.commons.permission.http.entity.AosReturnDataEntity;
import com.servitization.commons.permission.service.IAosService;
import com.servitization.commons.permission.service.IConfigProvider;
import com.servitization.commons.permission.util.Constant;
import com.servitization.commons.permission.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class LoginService {
    private static final Logger logger = Logger.getLogger(LoginService.class);
    @Resource
    private IAosService aosService;
    @Resource
    private IConfigProvider propertiesReader;
    private String subSystem;// 子系统的名称
    private String cookieName;// cookie的名称
    private String login_view_path;// 登录转发到登录界面的路径和页面
    private String organizationName;

    /**
     * ----系统是否是全部的页面需要有登录，反之可以在任意类上面或者方法上面需要有登录---- true:每个都需要登录后才能kand
     * <p>
     * ------
     */
    private boolean isAllIntercept;

    public boolean loginVertify(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String loginurl = propertiesReader.Get(Constant.LOGIN_URL);
        if (StringUtils.isBlank(loginurl)) {
            throw new RuntimeException("login url config is blank");
        }
        HttpSession loginSession = request.getSession();
        String ticket = request.getParameter("ticket");

        if (isAllIntercept) {
            return parseAnnotation(request, response, loginSession, loginurl);
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> clz = handlerMethod.getBeanType();
            Login classLogin = clz.getAnnotation(Login.class);
            Login methodLogin = handlerMethod.getMethodAnnotation(Login.class);
            /***
             * ---首先判断类上面有没有登录注解，如果有说明该类下面的所有方法都需要登录才能访问 ---
             */
            if (null != classLogin || null != methodLogin) {
                return parseAnnotation(request, response, loginSession, loginurl);
            }
        }
        if (!StringUtils.isBlank(ticket) && (null == loginSession.getAttribute("user"))) {
            decodeTicket(ticket, request, loginSession);
        }
        return true;
    }

    public boolean parseAnnotation(HttpServletRequest request, HttpServletResponse response, HttpSession loginSession,
                                   String loginurl) {

        boolean tokenVertify = false;
        try {
            if (null == loginSession || null == loginSession.getAttribute("user")) {
                String ticket = request.getParameter("ticket");
                if (!StringUtils.isBlank(ticket)) {
                    if (decodeTicket(ticket, request, loginSession)) {
                        return true;
                    }
                }
                Cookie cookie = CookieUtil.getCookieFromLocalByName(request, cookieName);
                /** -------验证token----- */
                if (null != cookie) {
                    String token = cookie.getValue();
                    if (StringUtils.isBlank(token)) {
                        request.getRequestDispatcher(login_view_path).forward(request, response);
                        return false;
                    }
                    tokenVertify = vertifyToken(token, request);
                    /** --验证token失败跳到登录- */
                    if (!tokenVertify) {
                        request.getRequestDispatcher(login_view_path).forward(request, response);
                        return false;
                    }
                    return tokenVertify;
                }
                request.getRequestDispatcher(login_view_path).forward(request, response);
                return false;
            }
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * --------解票并设置用户信息到session-------------
     */
    public boolean decodeTicket(String ticket, HttpServletRequest request, HttpSession loginSession) {

        String userInfo = aosService.decodeTicket(ticket, subSystem);
        if (!StringUtils.isBlank(userInfo)) {
            loginSession.setAttribute("user", userInfo);
            return true;
        }
        return false;
    }

    /**
     * 到单点服务器上面验证token是否过期合法
     **/
    public boolean vertifyToken(String token, HttpServletRequest request) {

        try {
            AosReturnDataEntity entitry = aosService.vertifyTokenToAosPlat(token, subSystem);
            if (null == entitry || 200 != entitry.getCode()) {
                return false;
            }
            AosDataEntity data = JSON.parseObject(entitry.getData(), AosDataEntity.class);
            decodeTicket(data.getTicket(), request, request.getSession());
        } catch (Exception e) {
            logger.error("vertify Token error");
            return false;
        }
        return true;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public boolean isAllIntercept() {
        return isAllIntercept;
    }

    public void setAllIntercept(boolean isAllIntercept) {
        this.isAllIntercept = isAllIntercept;
    }

    public void setLogin_view_path(String login_view_path) {
        this.login_view_path = login_view_path;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
