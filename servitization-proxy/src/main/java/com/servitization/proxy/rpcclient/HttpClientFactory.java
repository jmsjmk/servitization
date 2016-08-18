package com.servitization.proxy.rpcclient;

public class HttpClientFactory {

    static CustomHttpClient customHttpClientInstance;

    static {
        customHttpClientInstance = new CustomHttpClient();
    }

    public AsynHttpClient asynHttpClient() {
        return AsynHttpClientHolder.INSTANCE;
    }

    public IApacheHttpClient customHttpClient() {
        return customHttpClientInstance;
    }

    private static class AsynHttpClientHolder {
        public static AsynHttpClient INSTANCE = new AsynHttpClient();
    }
}
