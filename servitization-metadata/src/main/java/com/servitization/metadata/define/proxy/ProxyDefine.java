package com.servitization.metadata.define.proxy;

import com.servitization.metadata.define.embedder.ChainHandlerDefine;

import java.util.List;
import java.util.Set;

public interface ProxyDefine extends ChainHandlerDefine {

    List<ServiceModule> getServiceModuleList();

    List<ServicePool> getServicePoolList();

    Set<String> getResConvertServiceNames();
}
