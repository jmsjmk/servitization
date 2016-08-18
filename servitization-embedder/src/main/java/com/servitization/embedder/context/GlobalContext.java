package com.servitization.embedder.context;

import com.servitization.metadata.define.embedder.ServiceDefine;
import org.springframework.context.ApplicationContext;

/**
 * 全局上下文
 * <p>
 * 整体运行期环境
 * <p>
 * 与worker同生命周期
 */
public interface GlobalContext {

    /**
     * 获取元数据定义
     */
    ServiceDefine getServiceDefine();

    /**
     * 获取emcf的spring上下文
     */
    ApplicationContext getEmcfContext();

}
