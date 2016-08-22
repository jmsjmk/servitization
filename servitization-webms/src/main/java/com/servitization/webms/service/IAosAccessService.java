package com.servitization.webms.service;

import com.servitization.webms.entity.AosReturnDataEntity;

public interface IAosAccessService {
    AosReturnDataEntity getLoginInfoByUserNameAndPasswd(String user, String password);

    AosReturnDataEntity vertifyTokenToAosPlat(String token);
}
