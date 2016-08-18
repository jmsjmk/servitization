package com.servitization.web.define.proxy;

import com.yammer.metrics.core.Timer;

public class TargetService {
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServicePath() {
        return servicePath;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public byte getCharset() {
        return charset;
    }

    public void setCharset(byte charset) {
        this.charset = charset;
    }

    public byte getCompress() {
        return compress;
    }

    public void setCompress(byte compress) {
        this.compress = compress;
    }

    public byte getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(byte encrypt) {
        this.encrypt = encrypt;
    }

    public boolean isAsyn() {
        return asyn;
    }

    public void setAsyn(boolean asyn) {
        this.asyn = asyn;
    }

    private byte encrypt;
    private boolean asyn;
    private String host;
    private int port;
    private String servicePath;
    private String url;
    private String method;
    private int connectTimeout;
    private int socketTimeout;
    private int serviceType;
    private byte charset;
    private byte compress;
    private int threshold;
    private IValveController valve;
    private Timer timer;
    static final char COM = ',';

    public String toString() {
        return new StringBuilder().append(host).append(COM).append(port).append(COM).append(servicePath).append(COM)
                .append(method).toString();
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public IValveController getValve() {
        return valve;
    }

    public void setValve(IValveController valve) {
        this.valve = valve;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
