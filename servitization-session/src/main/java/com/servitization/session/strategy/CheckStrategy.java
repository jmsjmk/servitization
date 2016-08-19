package com.servitization.session.strategy;

import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.session.StrategyEntry;

public interface CheckStrategy {

    AuthResult checkSession(ImmobileRequest request,
                            ImmobileResponse response, RequestContext context);

    void setStrategyEntry(StrategyEntry se);
}
