package com.servitization.commons.business.agent.business;

import com.alibaba.fastjson.JSON;
import com.servitization.commons.business.agent.AgentSupport;
import org.apache.commons.lang3.StringUtils;

public class AreaCodeUtil {

    private AreaCodeUtil() {
    }

    private static CheckPhoneNumberResp validMobile(String mobile) {

        AgentSupport agentSupport = new AgentSupport();
        agentSupport.setProperties(AreaCodeConfig.getProperties());
        AreaCodeAgent areaCode = agentSupport.createBean(AreaCodeAgent.class);

        CheckPhoneNumberReq checkPhoneNumberReq = new CheckPhoneNumberReq();
        checkPhoneNumberReq.setBusinessType("6");
        checkPhoneNumberReq.setLanguage(1);
        checkPhoneNumberReq.setPhoneNumber(mobile);
        String request = "data=" + JSON.toJSONString(checkPhoneNumberReq);
        CheckPhoneNumberResp checkPhoneNumberResp = areaCode.checkPhoneNumber(request);

        return checkPhoneNumberResp;
    }

    /**
     * 验证手机号是否符合规则 true:符合 false:不符合
     *
     * @param phoneNo 手机号
     * @return 布尔值
     */
    public static boolean checkPhoneNo(String phoneNo) {
        if (StringUtils.isBlank(phoneNo)) {
            return false;
        }
        CheckPhoneNumberResp checkPhoneNumberResp = validMobile(phoneNo);

        boolean result = false;

        if (StringUtils.equals("on", AreaCodeConfig.getValue("AreaCodeConfig", "on"))) {
            if (checkPhoneNumberResp == null || checkPhoneNumberResp.getSuccess() == 1) {
                result = true;
            } else {
                result = false;
            }
        } else {
            result = true;
        }

        return result;
    }
}
