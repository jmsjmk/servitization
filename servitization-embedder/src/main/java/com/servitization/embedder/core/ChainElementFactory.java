package com.servitization.embedder.core;

import com.servitization.embedder.handler.ChainElement;
import com.servitization.embedder.handler.ChainHandler;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.embedder.ChainGroupDefine;
import com.servitization.metadata.define.embedder.ChainHandlerDefine;

/**
 * 链条元素工厂
 */
public class ChainElementFactory {

    public static ChainElement factory(ChainElementDefine define) {
        if (define instanceof ChainGroupDefine) {
            return new ChainGroupExecutor();
        } else if (define instanceof ChainHandlerDefine) {
            ChainHandlerDefine hDefine = (ChainHandlerDefine) define;
            try {
                Class<?> clazz = Class.forName(hDefine.getHandlerClazz());
                Object obj = clazz.newInstance();
                if (!(obj instanceof ChainHandler))
                    throw new RuntimeException("Wrong clazz type for "
                            + hDefine.getHandlerClazz()
                            + ", it must be ChainHandler!");
                return (ChainHandler) obj;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Unrecognized define type!");
        }
    }
}
