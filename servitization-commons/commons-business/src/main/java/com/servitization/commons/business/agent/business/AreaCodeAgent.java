package com.servitization.commons.business.agent.business;

import com.servitization.commons.business.agent.AgentService;
import com.servitization.commons.business.agent.enums.DataTypeEnum;
import com.servitization.commons.business.agent.enums.MethodTypeEnum;
import com.servitization.commons.business.agent.rpc.ContentType;

public interface AreaCodeAgent {

    @AgentService(name = "checkPhoneNumber", methodType = MethodTypeEnum.POST, contentType = ContentType.POST_KV, reqType = DataTypeEnum.STRING, resultType = DataTypeEnum.JSON)
    CheckPhoneNumberResp checkPhoneNumber(String mobile);
}
