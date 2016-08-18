package com.servitization.webms.service;

import com.servitization.webms.entity.AosReturnDataEntitry;

public interface IAosAccessService {
    AosReturnDataEntitry getLoginInfoByUserNameAndPasswd(String user, String password);

    AosReturnDataEntitry vertifyTokenToAosPlat(String token);
}
