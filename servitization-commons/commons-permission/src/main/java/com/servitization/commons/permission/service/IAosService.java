package com.servitization.commons.permission.service;

import com.servitization.commons.permission.http.entity.AosReturnDataEntity;
import com.servitization.commons.permission.http.entity.PermissionEntity;

public interface IAosService {
    /**
     * --------------验证token是否合法过期------------
     */
    AosReturnDataEntity vertifyTokenToAosPlat(String token, String subSystem);

    /**
     * ---解票并且只能解票一次在一次会话过程中--
     */
    String decodeTicket(String ticket, String subSystem);


    /**
     * -----查询用户对应系统下组织的存在的所有权限---------
     */
    PermissionEntity[] permissionRelation(String userid, String dimension);
}
