package com.servitization.session.strategy.impl;

import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.session.StrategyEntry;
import com.servitization.session.strategy.AuthResult;
import com.servitization.session.strategy.CheckStrategy;

public class ForceUnCheckStrategy implements CheckStrategy {

    private final AuthResult authResult = new AuthResult();

    @Override
    public AuthResult checkSession(ImmobileRequest request,
                                   ImmobileResponse response, RequestContext context) {
        return authResult;
    }

	@Override
	public void setStrategyEntry(StrategyEntry se) {
		// TODO Auto-generated method stub
		
	}

}
