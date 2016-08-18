package com.servitization.commons.permission.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IPermissionService {
    /**
     * -----验证用户在某个组织上是否有某个权限--------
     */
    boolean permissionVertify(HttpServletRequest request, HttpServletResponse response, Object handler);
}
