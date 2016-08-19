package com.servitization.commons.business.agent.rpc;

import com.servitization.commons.business.agent.AgentService;

import java.lang.reflect.Method;

/**
 * Rpc对象
 */
public class RpcObject {
    private AgentService agengService;
    private Method method;
    private Object[] args;
    private RpcConfig rpcConfig;

    public AgentService getAgengService() {
        return agengService;
    }

    public void setAgengService(AgentService agengService) {
        this.agengService = agengService;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public RpcConfig getRpcConfig() {
        return rpcConfig;
    }

    public void setRpcConfig(RpcConfig rpcConfig) {
        this.rpcConfig = rpcConfig;
    }
}
