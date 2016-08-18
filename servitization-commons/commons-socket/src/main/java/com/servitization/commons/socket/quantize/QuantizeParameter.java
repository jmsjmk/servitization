package com.servitization.commons.socket.quantize;

import com.servitization.commons.socket.enums.CharsetEnum;
import com.servitization.commons.socket.enums.CompressEnum;
import com.servitization.commons.socket.enums.EncryptEnum;
import com.servitization.commons.socket.enums.ProtocolEnum;
import com.servitization.commons.socket.remote.DefaultType;
import com.servitization.commons.socket.remote.adapter.ConvertAdapter;
import com.servitization.commons.socket.remote.adapter.DefaultAdapter;

public class QuantizeParameter {

    private String serviceName;
    private String serviceVersion = "";
    private String serviceType;
    private CharsetEnum charset = CharsetEnum.UTF8;
    private CompressEnum compress = CompressEnum.NON;
    private EncryptEnum encrypt = EncryptEnum.NON;
    private ProtocolEnum protocol = ProtocolEnum.STRING;
    private boolean async = false;
    private long timeOut = 15000;
    private Class<?> resultType = DefaultType.class;
    private Class<? extends ConvertAdapter> resultAdapter = DefaultAdapter.class;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public CharsetEnum getCharset() {
        return charset;
    }

    public void setCharset(CharsetEnum charset) {
        this.charset = charset;
    }

    public CompressEnum getCompress() {
        return compress;
    }

    public void setCompress(CompressEnum compress) {
        this.compress = compress;
    }

    public EncryptEnum getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(EncryptEnum encrypt) {
        this.encrypt = encrypt;
    }

    public ProtocolEnum getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolEnum protocol) {
        this.protocol = protocol;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(Class<?> resultType) {
        this.resultType = resultType;
    }

    public Class<? extends ConvertAdapter> getResultAdapter() {
        return resultAdapter;
    }

    public void setResultAdapter(Class<? extends ConvertAdapter> resultAdapter) {
        this.resultAdapter = resultAdapter;
    }
}
