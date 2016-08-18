package com.servitization.web.define.embedder;

import java.util.List;

public interface ServiceDefine {

    String getName();

    DeployModel getDeployModel();

    List<ChainElementDefine> getUpChainList();

    List<ChainElementDefine> getDownChainList();
}
