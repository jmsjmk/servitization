package com.servitization.webms.service.impl;

import com.servitization.commons.business.agent.entity.CustomizeParameterEntity;
import com.servitization.webms.entity.AosReturnDataEntity;
import com.servitization.webms.http.AosAgent;
import com.servitization.webms.service.IAosAccessService;
import com.servitization.webms.service.IConfigProvider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AosAccessServiceImpl implements IAosAccessService {
    @Resource
    private IConfigProvider configProvider;

    @Resource
    private AosAgent aosAgent;

    @Override
    public AosReturnDataEntity getLoginInfoByUserNameAndPasswd(String user, String password) {
        StringBuilder param = new StringBuilder();
        param.append("user=").append(user).append("&").append("password=").append(password);
        String url = configProvider.Get("getLoginInfoByUserNameAndPasswd.url");
        CustomizeParameterEntity entity = new CustomizeParameterEntity();
        entity.setUrl(url);
        AosReturnDataEntity result = aosAgent.getLoginInfoByUserNameAndPasswd(param.toString(), entity);
        return result;
    }

    @Override
    public AosReturnDataEntity vertifyTokenToAosPlat(String token) {
        StringBuilder param = new StringBuilder();
        AosReturnDataEntity result;
        param.append("token=").append(token).append("&").append("subsystem=servicePlat");
        String url = configProvider.Get("vertifyTokenToAos.url");
        CustomizeParameterEntity entity = new CustomizeParameterEntity();
        entity.setUrl(url);
        result = aosAgent.vertifyTokenToAosPlat(param.toString(), entity);
        return result;
    }
}
