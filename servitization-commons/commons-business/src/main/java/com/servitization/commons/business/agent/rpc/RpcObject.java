package com.servitization.commons.business.agent.rpc;

import com.servitization.commons.business.agent.AgentService;

import java.lang.reflect.Method;

/**
 * Rpc对象
 */
public class RpcObject {
    private AgentService agentService;
    private Method method;
    private Object[] args;
    private RpcConfig rpcConfig;

    public AgentService getAgentService() {
        return agentService;
    }

    public void setAgentService(AgentService agentService) {
        this.agentService = agentService;
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
