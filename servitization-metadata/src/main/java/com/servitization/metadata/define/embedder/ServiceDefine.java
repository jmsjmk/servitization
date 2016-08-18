package com.servitization.metadata.define.embedder;

import com.servitization.metadata.define.XmlSerializable;

import java.util.List;

public interface ServiceDefine extends XmlSerializable {

    String getName();

    String getVersion();

    DeployModel getDeployModel();

    List<ChainElementDefine> getUpChainList();

    List<ChainElementDefine> getDownChainList();

}
