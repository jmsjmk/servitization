package com.servitization.embedder;

import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;

import javax.servlet.FilterChain;

/**
 * 主工作对象，
 * <p>
 * 处理所有请求 创建请求链条 异常处理 相关线程池管理
 */
public interface Worker {

    /**
     * 传入ServiceDefine、GlobalContext 初始化线程池、chain、handler
     *
     * @param context
     */
    void init(GlobalContext context);

    /**
     * 处理一条请求
     *
     * @param request
     * @param response
     * @param chain
     */
    void process(ImmobileRequest request, ImmobileResponse response,
                 FilterChain chain);

    /**
     * 销毁方法，会调用相关handler的销毁方法 时机有两个 1、容器销毁 2、热加载销毁
     */
    void destory();
}
