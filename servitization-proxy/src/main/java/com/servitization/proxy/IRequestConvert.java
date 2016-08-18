package com.servitization.proxy;

import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.metadata.define.proxy.ServicePool;
import com.servitization.metadata.define.proxy.TargetService;
import org.apache.http.client.methods.HttpUriRequest;

import java.net.URISyntaxException;

public interface IRequestConvert {

    HttpUriRequest convert2HttpUriRequest(
            ImmobileRequest request, TargetService targetService,
            ServicePool servicePool) throws URISyntaxException, IllegalAccessException;

}
