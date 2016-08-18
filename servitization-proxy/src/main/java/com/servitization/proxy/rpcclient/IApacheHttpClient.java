package com.servitization.proxy.rpcclient;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

public interface IApacheHttpClient {

    public CloseableHttpResponse sendRequest(HttpUriRequest request)
            throws ClientProtocolException, IOException;

    public byte[] sendRequestGetEntityBytes(HttpUriRequest request)
            throws ClientProtocolException, IOException, NullPointerException,
            InterruptedException, ExecutionException;

    public int sendRequest(HttpUriRequest request, OutputStream outputStream)
            throws ClientProtocolException, IOException, NullPointerException,
            InterruptedException, ExecutionException;
}
