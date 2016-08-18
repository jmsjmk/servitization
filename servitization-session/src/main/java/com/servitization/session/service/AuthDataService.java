package com.servitization.session.service;

import com.alibaba.fastjson.JSON;
import com.servitization.commons.user.remote.helper.SessionAuthHelper;
import com.servitization.commons.user.remote.request.ValidateSessionTokenReq;
import com.servitization.commons.user.remote.result.ValidateSessionTokenResp;
import com.servitization.session.strategy.AuthResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthDataService {
    private static final Logger LOG = LoggerFactory.getLogger(AuthDataService.class);

    private static SessionAuthHelper sessionAuthHelper;

    //private static SessionAuthHttpHelper sessionAuthHttpHelper;


    public static void init(SessionAuthHelper sessionAuthHelper) {
        AuthDataService.sessionAuthHelper = sessionAuthHelper;
    }

    public static AuthResult validateToken(String token) {
        try {
            ValidateSessionTokenReq req = new ValidateSessionTokenReq();
            req.setToken(token);
            ValidateSessionTokenResp resp = JSON.parseObject(
                    sessionAuthHelper.validateSessionToken(req),
                    ValidateSessionTokenResp.class);
            if (resp == null)
                return null;
            AuthResult result = new AuthResult();
            result.setErrorCode(resp.getErrorCode());
            result.setErrorMessage(resp.getErrorMessage());
            result.setIsError(resp.getIsError());
            if (resp.getSessionInfo() != null) {
                result.setAuthNo(resp.getSessionInfo().getAuthNo());
                result.setCardNo(resp.getSessionInfo().getCardNo());
                result.setType(resp.getSessionInfo().getType());
            }
            return result;
        } catch (Exception e) {
            LOG.error("validateSessionToken exception", e);
            return null;
        }
    }

    /**
     * 添加验证方式按照http方式进行验证 <br/>
     *
     * @param token
     * @return
     */
    public static AuthResult validateTokenByHttp(String token) {
        try {
            ValidateSessionTokenReq req = new ValidateSessionTokenReq();
            req.setToken(token);
            ValidateSessionTokenResp resp = JSON.parseObject(
                    sessionAuthHelper.validateSessionToken(req),
                    ValidateSessionTokenResp.class);
            if (resp == null)
                return null;
            AuthResult result = new AuthResult();
            result.setErrorCode(resp.getErrorCode());
            result.setErrorMessage(resp.getErrorMessage());
            result.setIsError(resp.getIsError());
            if (resp.getSessionInfo() != null) {
                result.setAuthNo(resp.getSessionInfo().getAuthNo());
                result.setCardNo(resp.getSessionInfo().getCardNo());
                result.setType(resp.getSessionInfo().getType());
            }
            return result;
        } catch (Exception e) {
            LOG.error("validateSessionToken exception", e);
            return null;
        }
    }
}
