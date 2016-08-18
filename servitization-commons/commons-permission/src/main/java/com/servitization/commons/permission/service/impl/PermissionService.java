package com.servitization.commons.permission.service.impl;

import com.alibaba.fastjson.JSON;
import com.servitization.commons.permission.annotation.Permission;
import com.servitization.commons.permission.http.entity.PermissionEntity;
import com.servitization.commons.permission.http.entity.UserEntity;
import com.servitization.commons.permission.service.IAosService;
import com.servitization.commons.permission.service.IPermissionService;
import com.servitization.commons.permission.util.Constant;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class PermissionService implements IPermissionService {
    private static final Logger logger = Logger.getLogger(PermissionService.class);
    @Resource
    private IAosService aosservice;

    @Override
    public boolean permissionVertify(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Permission permission = handlerMethod.getMethodAnnotation(Permission.class);
                /** ----------------验证权限-----没有permission标记直接放行-------- */
                if (null != permission) {
                    HttpSession session = request.getSession();
                    Object userInfo = session.getAttribute("user");
                    UserEntity user = null;
                    String permissionName = null;
                    if (null != userInfo) {
                        permissionName = permission.name();
                        user = JSON.parseObject((String) userInfo, UserEntity.class);
                        boolean isPermission = auth(user.getId(),
                                (String) request.getAttribute(Constant.SUB_SYSTEM_NAME), permissionName);
                        if (!isPermission) {
                            response.setStatus(Constant.ERROR_CODE);
                        }
                        return isPermission;
                    }
                    // session 已经过期或者没有权限或者没有登录
                    response.setStatus(Constant.ERROR_CODE);
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("permission interceptor error");
            return false;
        }
        return true;
    }

    public boolean auth(String userid, String dimension, String permissionName) {
        try {
            PermissionEntity[] permissionEntity = aosservice.permissionRelation(userid, dimension);

            if (null == permissionEntity || permissionEntity.length <= 0) {
                return false;
            }
            for (int index = 0; index < permissionEntity.length; index++) {
                if (permissionName.equals(permissionEntity[index].getName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("auth error permissionName=" + permissionName);
        }
        return false;
    }

}
