package com.servitization.commons.permission.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.servitization.commons.business.agent.entity.CustomizeParameterEntity;
import com.servitization.commons.permission.http.AosProxy;
import com.servitization.commons.permission.http.entity.AosReturnDataEntity;
import com.servitization.commons.permission.http.entity.PermissionEntity;
import com.servitization.commons.permission.http.entity.PermissionOrganization;
import com.servitization.commons.permission.service.IAosService;
import com.servitization.commons.permission.util.Constant;
import com.servitization.commons.permission.util.UrlUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AosService implements IAosService {
    private static final Logger logger = Logger.getLogger(AosService.class);
    @Resource
    private PropertiesReader propertiesReader;

    @Resource
    private AosProxy aosProxy;

    @Override
    public AosReturnDataEntity vertifyTokenToAosPlat(String token, String subSystem) {

        CustomizeParameterEntity entity = new CustomizeParameterEntity();

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("token", token);
        paramMap.put("subsystem", subSystem);
        String param = UrlUtil.getUrlParam(Constant.VERTIFY_TOKEN_URL, paramMap, entity, propertiesReader);
        AosReturnDataEntity aosEntitry = aosProxy.vertifyTokenToAosPlat(param, entity);
        return aosEntitry;
    }

    @Override
    public String decodeTicket(String ticket, String subSystem) {
        try {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("ticket", ticket);
            paramMap.put("subsystem", subSystem);
            CustomizeParameterEntity entity = new CustomizeParameterEntity();
            String param = UrlUtil.getUrlParam(Constant.DECODE_TICKET_URL, paramMap, entity, propertiesReader);
            AosReturnDataEntity aosEntitry = aosProxy.decodeTicket(param, entity);
            if (null != aosEntitry && 200 == aosEntitry.getCode()) {
                return aosEntitry.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("decode ticket is error");
        }
        return null;
    }

    @Override
    public PermissionEntity[] permissionRelation(String userid, String dimension) {
        try {
            AosReturnDataEntity entity = null;
            PermissionEntity[] permissionArr = null;

            Map<String, String> paramMap = new HashMap<String, String>();
            CustomizeParameterEntity customize = new CustomizeParameterEntity();
            paramMap.put("userId", userid);
            paramMap.put("dimension", dimension);
            String param = UrlUtil.getUrlParam(Constant.PERMISSION_RELATION_URL, paramMap, customize, propertiesReader);
            entity = aosProxy.permissionRelation(param, customize);
            if (null != entity && 200 == entity.getCode()) {
                List<PermissionOrganization> list = JSONArray.parseArray(entity.getData(),
                        PermissionOrganization.class);
                if (null != list && list.size() > 0) {
                    permissionArr = JSON.parseObject(list.get(0).getPerGroup(), PermissionEntity[].class);
                }
                return permissionArr;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("gain permission Relation error");
        }
        return null;
    }
}
