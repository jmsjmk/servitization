package com.servitization.embedder.context.impl;

import com.servitization.embedder.context.ErrorEntity;
import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.context.RequestContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RequestContextImpl {

    private RequestContextImplNarrow narrow;

    public void setCurrentHandlerName(String name) {
        narrow.currentHandlerName = name;
    }

    public void incProcessIndex() {
        narrow.processIndex++;
    }

    public void createRequestContext(GlobalContext globalContext) {
        narrow = new RequestContextImplNarrow(globalContext);
    }

    public RequestContext getRequestContext() {
        return narrow;
    }

    public void restoreRequestContext(RequestContext context) {
        this.narrow = (RequestContextImplNarrow) context;
    }

    /**
     * narrow interface for handler
     */
    private class RequestContextImplNarrow implements RequestContext {

        private GlobalContext globalContext;

        private List<ErrorEntity> errorList;

        private String currentHandlerName;

        private int processIndex;

        private RequestContextImplNarrow(GlobalContext globalContext) {
            this.globalContext = globalContext;
            errorList = new LinkedList<>();
        }

        @Override
        public GlobalContext getGlobalContext() {
            return globalContext;
        }

        @Override
        public List<ErrorEntity> getErrorList() {
            return errorList;
        }

        @Override
        public void addError() {
            this.errorList.add(new ErrorEntity(currentHandlerName));
        }

        @Override
        public void addError(Exception e) {
            this.errorList.add(new ErrorEntity(currentHandlerName, e));
        }

        @Override
        public void addError(String errorCode, String errorMsg) {
            this.errorList.add(new ErrorEntity(currentHandlerName, errorCode, errorMsg));
        }

        @Override
        public void addError(String errorCode, String errorMsg, Map<String, String> customErrorEntity) {
            this.errorList.add(new ErrorEntity(currentHandlerName, errorCode, errorMsg, customErrorEntity));
        }

        @Override
        public int getProcessIndex() {
            return processIndex;
        }
    }
}
