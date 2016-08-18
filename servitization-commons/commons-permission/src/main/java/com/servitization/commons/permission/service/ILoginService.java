package com.servitization.commons.permission.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ILoginService {
    /**
     * -------------------------------验证登录------------------------
     */
    boolean loginVertify(HttpServletRequest request, HttpServletResponse response);
}
