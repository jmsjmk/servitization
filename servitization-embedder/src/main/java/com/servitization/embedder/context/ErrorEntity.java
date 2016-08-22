package com.servitization.embedder.context;

import java.util.Map;

/**
 * 错误实体对象
 */
public class ErrorEntity {
    // 出错的handler名称
    public final String handlerName;

    public final String errorCode;

    public final String errorMessage;

    public final Exception exception;

    public final Map<String, String> customErrorEntity;

    public ErrorEntity(String handlerName) {
        this.handlerName = handlerName;
        this.errorCode = null;
        this.errorMessage = null;
        this.exception = null;
        this.customErrorEntity = null;
    }

    public ErrorEntity(String handlerName, Exception exception) {
        this.handlerName = handlerName;
        this.errorCode = null;
        this.errorMessage = null;
        this.exception = exception;
        this.customErrorEntity = null;
    }

    public ErrorEntity(String handlerName, String errorCode, String errorMessage) {
        this.handlerName = handlerName;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.exception = null;
        this.customErrorEntity = null;
    }

    public ErrorEntity(String handlerName, String errorCode,
                       String errorMessage, Map<String, String> customErrorEntity) {
        this.handlerName = handlerName;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.exception = null;
        this.customErrorEntity = customErrorEntity;
    }
}
