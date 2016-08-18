package com.servitization.commons.business.agent.rpc;

import com.servitization.commons.business.agent.enums.RPCTypeEnum;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RpcClient {
    private static final Lock lock = new ReentrantLock();
    private static IRpc httpRpc;

    public static IRpc getRpcInstance(RPCTypeEnum rpcTypeEnum) {

        if (rpcTypeEnum == null) {
            return null;
        }
        switch (rpcTypeEnum) {
            case HTTP:
                return getHttpRpc();
            case TCP:
                break;
            case THRIFT:
                return getThriftRpc();
            default:
                break;
        }
        return null;
    }

    /**
     * 单例http请求对象
     *
     * @return
     */
    private static IRpc getHttpRpc() {
        if (httpRpc == null) {
            lock.lock();
            try {
                if (httpRpc == null) {
                    httpRpc = new HttpRpc();
                }

                return httpRpc;
            } finally {
                lock.unlock();
            }
        } else {
            return httpRpc;
        }
    }

    private static IRpc getThriftRpc() {
        return null;
    }
}
