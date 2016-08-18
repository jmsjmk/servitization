package com.servitization.web.define.embedder;

import java.util.List;

public interface ChainGroupDefine extends ChainElementDefine {

    List<ChainElementDefine> getChainElementDefineList();

    int getGroupProcessTimeout();

    int getGroupSize();

    GroupPolicy getGroupPolicy();
}
