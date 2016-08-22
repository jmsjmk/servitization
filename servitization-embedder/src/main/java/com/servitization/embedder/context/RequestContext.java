package com.servitization.embedder.context;

import java.util.List;
import java.util.Map;

/**
 * 请求级上下文
 * <p>
 * 和单次请求生命周期相同
 */
public interface RequestContext {

    /**
     * 获取全局上下文
     *
     * @return
     */
    GlobalContext getGlobalContext();

    /**
     * 获得已产生的错误列表
     *
     * @return
     */
    List<ErrorEntity> getErrorList();

    /**
     * 添加一个错误到上下文，没有具体异常栈，自动取当前handler名，默认errorCode和message
     */
    void addError();

    /**
     * 添加一个错误到上下文，包含具体异常栈，自动取当前handler名
     *
     * @param e
     */
    void addError(Exception e);

    /**
     * 添加一个错误到上下文，没有具体异常栈，自动取当前handler名, 指定errorCode和message
     */
    void addError(String errorCode, String errorMsg);

    /**
     * 添加一个错误到上下文，没有具体异常栈，自动取当前handler名, 指定errorCode、message和customErrorEntity
     */
    void addError(String errorCode, String errorMsg, Map<String, String> customErrorEntity);

    /**
     * 获得处理链上的索引
     *
     * @return
     */
    int getProcessIndex();
}
