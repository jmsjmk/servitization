package com.servitization.embedder.handler;

import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.embedder.ChainElementDefine;

public interface ChainElement {

    /**
     * 初始化方法，在实例化后调用
     * 调用时机有两个：
     * 1、在第一次容器启动加载的时候
     * 2、配置更新后，热加载过程中
     *
     * @param context
     */
    void init(ChainElementDefine eleDefine, GlobalContext context);

    /**
     * 链条处理方法
     *
     * @param request
     * @param response
     * @param context
     * @return
     */
    HandleResult handle(ImmobileRequest request, ImmobileResponse response, RequestContext context);

    /**
     * 销毁释放相关资源
     * 调用时机有两个：
     * 1、在filter释放的时候，也就是容器销毁时
     * 2、在热加载过程中，新的worker已经生成，作为老的处理器销毁
     *
     * @param context
     */
    void destroy(GlobalContext context);
}
