package com.servitization.session.strategy;

import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.session.StrategyEntry;

public interface CheckStrategy {

    public AuthResult checkSession(ImmobileRequest request,
                                   ImmobileResponse response, RequestContext context);
    
    
    public void setStrategyEntry(StrategyEntry se);

}
