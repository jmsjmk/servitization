package com.servitization.metadata.define.pvUv;

import com.servitization.metadata.define.embedder.ChainHandlerDefine;

import java.util.List;

public interface PvUvDefine extends ChainHandlerDefine {
    /**
     * 得到需要记录pv、uv日志的接口列表
     */
    List<String> getPvUv();
}
