package com.servitization.embedder.core;

import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.context.impl.RequestContextImpl;
import com.servitization.embedder.handler.ChainElement;
import com.servitization.embedder.handler.HandleResult;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * 运行期链条类
 * <p>
 * 控制流转方向
 */
public class Chain {

    private static final Logger LOG = LoggerFactory.getLogger(Chain.class);

    private LinkedHashMap<String, ChainElement> chainElements = null;

    public void init(List<ChainElementDefine> eleDefines, GlobalContext context) {
        chainElements = new LinkedHashMap<>();
        if (null == eleDefines) {
            return;
        }
        for (ChainElementDefine eleDefine : eleDefines) {
            ChainElement chainElement = ChainElementFactory.factory(eleDefine);
            chainElement.init(eleDefine, context);
            chainElements.put(eleDefine.getName(), chainElement);
        }
        LOG.info("Chain Size [" + chainElements.size() + "] " +  chainElements.keySet());
    }

    public HandleResult exec(ImmobileRequest imHttpRequest, ImmobileResponse imHttpResponse,
                             RequestContextImpl reqContext) {
        HandleResult result = null;
        try {
            for (Entry<String, ChainElement> entry : chainElements.entrySet()) {
                // now handler and index
                reqContext.setCurrentHandlerName(entry.getKey());
                reqContext.incProcessIndex();
                result = entry.getValue().handle(imHttpRequest, imHttpResponse,
                        reqContext.getRequestContext());
                if (result == HandleResult.STOP) {
                    break;
                }
            }
        } catch (Exception e) {
            reqContext.getRequestContext().addError(e);
            result = HandleResult.STOP;
        }
        return result;
    }

    public void destory(GlobalContext context) {
        for (ChainElement ce : chainElements.values()) {
            ce.destroy(context);
        }
        chainElements = null;
    }

}
