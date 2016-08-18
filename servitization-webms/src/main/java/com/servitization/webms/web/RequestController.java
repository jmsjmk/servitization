package com.servitization.webms.web;

import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.embedder.impl.ChainHandlerDefineImpl;
import com.servitization.webms.entity.MetadataModule;
import com.servitization.webms.util.adapter.BaseObserver;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RequestController extends BaseObserver {

    @Override
    public String getHandlerName() {

        return "RequestServiceChainHandler";
    }

    @Override
    public void builderXml(long metadataId, MetadataModule module, List<ChainElementDefine> chainList) {
        ChainHandlerDefineImpl chainHandlerDefine = new ChainHandlerDefineImpl();
        chainHandlerDefine.setName(module.getHandlerName());
        chainHandlerDefine.setHandlerClazz(module.getHandlerClazz());
        chainList.add(chainHandlerDefine);
    }
}
