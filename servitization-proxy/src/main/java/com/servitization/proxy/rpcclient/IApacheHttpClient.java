package com.servitization.proxy.rpcclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

public interface IApacheHttpClient {

    CloseableHttpResponse sendRequest(HttpUriRequest request) throws IOException;

    byte[] sendRequestGetEntityBytes(HttpUriRequest request) throws IOException, NullPointerException,
            InterruptedException, ExecutionException;

    int sendRequest(HttpUriRequest request, OutputStream outputStream) throws IOException, NullPointerException,
            InterruptedException, ExecutionException;
}
