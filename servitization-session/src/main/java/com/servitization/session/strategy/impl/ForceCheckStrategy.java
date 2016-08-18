package com.servitization.session.strategy.impl;

import com.servitization.commons.user.remote.common.EnumAuthResult;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.common.CustomHeaderEnum;
import com.servitization.metadata.define.session.StrategyEntry;
import com.servitization.session.service.AuthDataService;
import com.servitization.session.strategy.AuthResult;
import com.servitization.session.strategy.CheckStrategy;
import com.servitization.session.util.TokenValidateHelper;
import org.apache.commons.lang3.StringUtils;

public class ForceCheckStrategy implements CheckStrategy {

    @Override
    public AuthResult checkSession(ImmobileRequest request,
                                   ImmobileResponse response, RequestContext context) {
        String token = request.getHeader(CustomHeaderEnum.SESSIONTOKEN
                .headerName());
        AuthResult authResult;
        if (StringUtils.isBlank(token)) {
            authResult = new AuthResult();
            authResult.setResult(EnumAuthResult.NoPermission);
            return authResult;
        }

        if (!TokenValidateHelper.validate(token)) {
            authResult = new AuthResult();
            authResult.setResult(EnumAuthResult.NotValid);
            return authResult;
        }

        authResult = AuthDataService.validateToken(token);
        return authResult;
    }

    @Override
    public void setStrategyEntry(StrategyEntry se) {
    }
}
