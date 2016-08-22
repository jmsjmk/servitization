package com.servitization.webms.http;

import com.servitization.commons.business.agent.AgentService;
import com.servitization.commons.business.agent.entity.CustomizeParameterEntity;
import com.servitization.commons.business.agent.enums.DataTypeEnum;
import com.servitization.commons.business.agent.enums.MethodTypeEnum;
import com.servitization.commons.business.agent.enums.RPCTypeEnum;
import com.servitization.commons.business.agent.rpc.ContentType;
import com.servitization.webms.entity.AosReturnDataEntity;
import com.servitization.webms.http.entity.BatchGetAosNodeByMachineNameResp;
import com.servitization.webms.http.entity.GetAosMachineByNodeResp;
import com.servitization.webms.http.entity.GetAosNodeByIdResp;
import com.servitization.webms.http.entity.GetAosNodeByMachineNameResp;

public interface AosAgent {

    @AgentService(name = "getAosNodeById", rpcType = RPCTypeEnum.HTTP, methodType = MethodTypeEnum.GET, reqType = DataTypeEnum.STRING, resultType = DataTypeEnum.JSON, customizeParameter = true)
    GetAosNodeByIdResp getAosNodeById(String param, CustomizeParameterEntity entity);

    @AgentService(name = "getAosMachineByNode", rpcType = RPCTypeEnum.HTTP, methodType = MethodTypeEnum.GET, reqType = DataTypeEnum.STRING, resultType = DataTypeEnum.JSON, customizeParameter = true)
    GetAosMachineByNodeResp getAosMachineByNode(String param, CustomizeParameterEntity entity);

    @AgentService(name = "getAosNodeByMachineName", rpcType = RPCTypeEnum.HTTP, methodType = MethodTypeEnum.GET, reqType = DataTypeEnum.STRING, resultType = DataTypeEnum.JSON, customizeParameter = true)
    GetAosNodeByMachineNameResp getAosNodeByMachineName(String param, CustomizeParameterEntity entity);

    @AgentService(name = "batchGetAosNodeByMachineName", rpcType = RPCTypeEnum.HTTP, methodType = MethodTypeEnum.POST, reqType = DataTypeEnum.STRING, contentType = ContentType.POST_KV, resultType = DataTypeEnum.JSON, customizeParameter = true)
    BatchGetAosNodeByMachineNameResp batchGetAosNodeByMachineName(String param, CustomizeParameterEntity entity);

    @AgentService(name = "getLoginInfoByUserNameAndPasswd", rpcType = RPCTypeEnum.HTTP, methodType = MethodTypeEnum.POST, reqType = DataTypeEnum.STRING, contentType = ContentType.POST_KV, resultType = DataTypeEnum.JSON, customizeParameter = true)
    AosReturnDataEntity getLoginInfoByUserNameAndPasswd(String param, CustomizeParameterEntity entity);

    @AgentService(name = "vertifyTokenToAos", rpcType = RPCTypeEnum.HTTP, methodType = MethodTypeEnum.POST, reqType = DataTypeEnum.STRING, contentType = ContentType.POST_KV, resultType = DataTypeEnum.JSON, customizeParameter = true)
    AosReturnDataEntity vertifyTokenToAosPlat(String param, CustomizeParameterEntity entity);

}
