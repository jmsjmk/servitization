package com.servitization.commons.permission.http;

import com.servitization.commons.business.agent.AgentService;
import com.servitization.commons.business.agent.entity.CustomizeParameterEntity;
import com.servitization.commons.business.agent.enums.DataTypeEnum;
import com.servitization.commons.business.agent.enums.MethodTypeEnum;
import com.servitization.commons.business.agent.enums.RPCTypeEnum;
import com.servitization.commons.business.agent.rpc.ContentType;
import com.servitization.commons.permission.http.entity.AosReturnDataEntity;

public interface AosProxy {
    @AgentService(name = "vertifyToken", rpcType = RPCTypeEnum.HTTP, methodType = MethodTypeEnum.POST, reqType = DataTypeEnum.STRING, contentType = ContentType.POST_KV, resultType = DataTypeEnum.JSON, customizeParameter = true)
    AosReturnDataEntity vertifyTokenToAosPlat(String param, CustomizeParameterEntity customEntity);

    @AgentService(name = "decodeTicket", rpcType = RPCTypeEnum.HTTP, methodType = MethodTypeEnum.POST, reqType = DataTypeEnum.STRING, contentType = ContentType.POST_KV, resultType = DataTypeEnum.JSON, customizeParameter = true)
    AosReturnDataEntity decodeTicket(String param, CustomizeParameterEntity customEntity);

    @AgentService(name = "organization", rpcType = RPCTypeEnum.HTTP, methodType = MethodTypeEnum.GET, reqType = DataTypeEnum.STRING, resultType = DataTypeEnum.JSON, customizeParameter = true)
    AosReturnDataEntity organization(String param, CustomizeParameterEntity customEntity);

    @AgentService(name = "permission_relation", rpcType = RPCTypeEnum.HTTP, methodType = MethodTypeEnum.GET, reqType = DataTypeEnum.STRING, resultType = DataTypeEnum.JSON, customizeParameter = true)
    AosReturnDataEntity permissionRelation(String param, CustomizeParameterEntity customEntity);
}