package com.servitization.metadata.define.encrypt;

import com.servitization.metadata.define.embedder.ChainHandlerDefine;

import java.util.Set;

public interface EncryptDefine extends ChainHandlerDefine {

    public Set<String> getIPWhiteList();

}
