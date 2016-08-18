package com.servitization.embedder;

import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;

import javax.servlet.FilterChain;

/**
 * 插线板 所有线程、处理任务的统一插线板
 * <p>
 * 控制配置更新 控制服务端通信 控制主工作对象
 */
public interface Embedder {

    /**
     * 构造时，1、启动配置更新监察线程 2、启动服务zk通信线程 3、GlobalContext管理
     */

    /**
     * 获取service定义 初始化一个worker
     */
    public void init();

    /**
     * 检查是否有配置更新 转发给worker进行处理
     *
     * @param request
     * @param response
     */
    public void process(ImmobileRequest request, ImmobileResponse response,
                        FilterChain chain);

    /**
     * 最终释放一些资源
     */
    public void destroy();
}
