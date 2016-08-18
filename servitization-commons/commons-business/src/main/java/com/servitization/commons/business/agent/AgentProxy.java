package com.servitization.commons.business.agent;

import com.servitization.commons.business.agent.rpc.IRpc;
import com.servitization.commons.business.agent.rpc.RpcClient;
import com.servitization.commons.business.agent.rpc.RpcConfig;
import com.servitization.commons.business.agent.rpc.RpcObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;

public class AgentProxy implements InvocationHandler {

    private Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        AgentService agentService = method.getAnnotation(AgentService.class);

        if (agentService == null) {
            throw new RuntimeException("Not found AgentService. MethodName:" + method.getName());
        }
        if (args == null || args.length < 1 || args[0] == null) {
            throw new RuntimeException("Agent request parameter can not be null. MethodName:" + method.getName());
        }

        IRpc rpc = RpcClient.getRpcInstance(agentService.rpcType());
        if (rpc == null) {
            throw new RuntimeException("Agent RpcClient.getRpcInstance can not be null. MethodName:" + method.getName());
        }

        RpcObject rpcObject = new RpcObject();
        rpcObject.setAgengService(agentService);
        rpcObject.setMethod(method);
        rpcObject.setArgs(args);
        rpcObject.setRpcConfig(RpcConfig.getRpcConfig(properties));

        return rpc.execute(rpcObject);
    }

}
