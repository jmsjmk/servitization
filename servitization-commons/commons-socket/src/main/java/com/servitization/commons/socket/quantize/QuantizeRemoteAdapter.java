package com.servitization.commons.socket.quantize;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;
import com.servitization.commons.log.trace.Spanner;
import com.servitization.commons.log.trace.Tracer;
import com.servitization.commons.socket.client.Follower;
import com.servitization.commons.socket.client.RemoteClient;
import com.servitization.commons.socket.enums.*;
import com.servitization.commons.socket.message.SocketRequest;
import com.servitization.commons.socket.remote.DefaultType;
import com.servitization.commons.socket.remote.adapter.*;
import com.servitization.commons.socket.remote.entity.RemoteRequest;
import com.servitization.commons.socket.utils.ZLibUtils;

import java.lang.reflect.Method;

public class QuantizeRemoteAdapter {

    private RemoteClient remoteClient;

    public Object invoke(QuantizeParameter rs, Object[] args) throws Exception {
        if (this.remoteClient == null) {
            throw new RuntimeException("RemoteClient is null ");
        }

        if (rs == null) {
            throw new RuntimeException("Not found RemoteService.");
        }
        if (args == null || args.length < 1 || args[0] == null) {
            throw new RuntimeException(
                    "Remote request parameter can not be null.");
        }
        Object arg = args[0];

        String serviceName = rs.getServiceName();
        CharsetEnum charset = rs.getCharset();
        CompressEnum compress = rs.getCompress();
        EncryptEnum encrypt = rs.getEncrypt();
        ProtocolEnum protocol = rs.getProtocol();
        boolean async = rs.isAsync();
        String version = rs.getServiceVersion();
        long timeOut = rs.getTimeOut();
        Class<?> resultClazz = rs.getResultType();
        Class<?> adapterClass = rs.getResultAdapter();
        String serviceType = rs.getServiceType();
        byte[] request;
        ConvertAdapter adapter = null;

        // 判断返回结果数据类型
        if (adapterClass != DefaultAdapter.class) {
            adapter = (ConvertAdapter) adapterClass.newInstance();
        }

        if (adapter == null) {
            if (resultClazz == DefaultType.class || resultClazz == String.class) {// 判断放回结果位字符串
                adapter = new StringConvertAdapter();
            } else {
                Class<?>[] clazzs = resultClazz.getInterfaces();
                if (clazzs != null && clazzs.length > 0) {
                    for (Class<?> c : clazzs) {
                        if (c == MessageLite.class) {
                            adapter = new ProtobufConvertAdapter(resultClazz);
                            break;
                        }
                    }
                }
                if (adapter == null) {
                    // 判断返回结果是json对象
                    adapter = new JsonObjectConvertAdapter(resultClazz);
                }
            }
        }
        switch (protocol) {
            case STRING:
                request = ((String) arg).getBytes();
                break;
            case OBJECT:
                // 验证请求对象是否继承remoterequest
                if (!isRemoteRequest(arg.getClass())) {
                    throw new RuntimeException(
                            "req parameter is not extends RemoteRequest");
                }
                request = JSON.toJSONString(arg).getBytes();
                break;
            case GOOGLE:
                Class<?> clazz = arg.getClass();
                Method m = clazz.getMethod("toByteArray");
                request = (byte[]) m.invoke(arg);

                break;
            default:
                throw new RuntimeException(
                        "Only support JSON and google protobuf protocol，Temporarily does not support other protocol.");
        }

        if (charset.code() != CharsetEnum.UTF8.code()) {
            throw new RuntimeException(
                    "Only support UTF-8 charset，Temporarily does not support other charset.");
        }
        // 加密
        switch (encrypt) {
            case NON:
                break;
            default:
                throw new RuntimeException("Temporarily does not support encrypt.");

        }
        // 压缩
        switch (compress) {
            case NON:
                break;
            case ZLIB:
                request = ZLibUtils.compress(request);
                break;
            default:
                throw new RuntimeException(
                        "Only support NON and ZLIB compress，Temporarily does not support other compress.");
        }

        Spanner span = Tracer.getTracer().startRpcTrace();
        SocketRequest.Request srequest = SocketRequest.Request
                .newBuilder()
                .setCharset(charset.code())
                .setCompress(compress.code())
                .setEncrypt(encrypt.code())
                .setProtocol(protocol.code())
                .setServiceName(serviceName)
                .setServiceVersion(version)
                .setSocketVersion(SocketVersionEnum.INITIAL_VERSION.code())
                .setMarking(span.getRpcId())
                .setSpan(span.getLocation())
                .setTraceId(span.getTraceId())
                .setRequest(
                        request != null ? ByteString.copyFrom(request)
                                : ByteString.EMPTY).build();
        Follower follower = this.remoteClient.asyncClient(serviceType,
                srequest, timeOut, adapter);
        if (follower != null) {
            if (async) {
                return follower;
            } else {
                return follower.getResult();
            }
        } else {
            return null;
        }
    }

    public RemoteClient getRemoteClient() {
        return remoteClient;
    }

    public void setRemoteClient(RemoteClient remoteClient) {
        this.remoteClient = remoteClient;
    }

    private boolean isRemoteRequest(Class<?> clazz) {
        if (clazz == RemoteRequest.class) {
            return true;
        }
        if (clazz == Object.class) {
            return false;
        }
        return this.isRemoteRequest(clazz.getSuperclass());
    }
}
