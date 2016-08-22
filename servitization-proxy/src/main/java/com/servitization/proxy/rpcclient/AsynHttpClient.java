package com.servitization.proxy.rpcclient;

import com.servitization.proxy.CommonLogger;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.ManagedNHttpClientConnectionFactory;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.conn.ManagedNHttpClientConnection;
import org.apache.http.nio.conn.NHttpConnectionFactory;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.CodingErrorAction;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsynHttpClient implements IApacheHttpClient {

    private CloseableHttpAsyncClient closeableHttpAsyncClient;

    public AsynHttpClient() {
        NHttpConnectionFactory<ManagedNHttpClientConnection> connFactory = new ManagedNHttpClientConnectionFactory();
        Registry<SchemeIOSessionStrategy> seRegistry = RegistryBuilder
                .<SchemeIOSessionStrategy>create()
                .register("http", NoopIOSessionStrategy.INSTANCE)
                .register("https", new SSLIOSessionStrategy(SSLContexts.createSystemDefault())).build();
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setSoKeepAlive(true).setConnectTimeout(1000)
                .setSoTimeout(5000).setTcpNoDelay(true).setSelectInterval(500)
                .build();
        ConnectingIOReactor ioReactor = null;
        try {
            ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor, connFactory, seRegistry);
        MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200).build();
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8)
                .setMessageConstraints(messageConstraints).build();
        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setConnectionConfig(new HttpHost("somehost", 80),
                ConnectionConfig.DEFAULT);
        // Configure total max or per route limits for persistent connections
        // that can be kept in the pool or leased by the connection manager.
        connManager.setMaxTotal(65535);
        connManager.setDefaultMaxPerRoute(65535);
        // Create global request configuration
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                .setConnectTimeout(1000).setSocketTimeout(3000)
                .setConnectionRequestTimeout(4000).build();
        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(defaultRequestConfig).build();
        this.closeableHttpAsyncClient = httpAsyncClient;
        this.closeableHttpAsyncClient.start();
        String info = new StringBuilder().append("ioReactorConfig:")
                .append(ioReactorConfig.toString()).append('\t')
                .append("messageConstraintConfig:")
                .append(messageConstraints.toString()).append('\t')
                .append("connectionConfig:")
                .append(connectionConfig.toString()).append('\t')
                .append("requestConfig:")
                .append(defaultRequestConfig.toString()).toString();
        CommonLogger.getLogger().info(
                String.format("Asyn httpclient inited %s", info));
    }

    @Override
    public CloseableHttpResponse sendRequest(HttpUriRequest request)
            throws IOException {
        this.closeableHttpAsyncClient.execute(request, new ResultCallBack(request));
        return null;
    }

    public Future<HttpResponse> getResponse(HttpUriRequest request) {
        return this.closeableHttpAsyncClient.execute(request,
                new ResultCallBack(request));
    }

    public HttpResponse sendRequest4Wait(HttpUriRequest request)
            throws IOException, InterruptedException, ExecutionException {
        Future<HttpResponse> future = this.closeableHttpAsyncClient.execute(request, new ResultCallBack(request));
        return future.get();
    }

    @Override
    public byte[] sendRequestGetEntityBytes(HttpUriRequest request) throws IOException, NullPointerException,
            InterruptedException, ExecutionException {
        byte[] bytes = null;
        HttpResponse response = sendRequest4Wait(request);
        if (response == null) {
            throw new NullPointerException("http got a null response");
        }
        int resultCode = response.getStatusLine().getStatusCode();
        if (response.getEntity() != null) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                bytes = EntityUtils.toByteArray(response.getEntity());
            } else {
                CommonLogger.getLogger().error(response.getStatusLine().getReasonPhrase() + ",code:"
                                + response.getStatusLine().getStatusCode());
            }
        } else {
            CommonLogger.getLogger().warn(String.format("httpclient got a repsonse with empty entity;%d", resultCode));
        }
        return bytes;
    }

    @Override
    public int sendRequest(HttpUriRequest request, OutputStream outputStream)
            throws IOException, NullPointerException,
            InterruptedException, ExecutionException {
        HttpResponse response = sendRequest4Wait(request);
        if (response == null) {
            throw new NullPointerException("http got a null response");
        }
        int resultCode = response.getStatusLine().getStatusCode();
        if (response.getEntity() != null) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK
                    || resultCode == HttpStatus.SC_NOT_MODIFIED) {
                response.getEntity().writeTo(outputStream);
            } else {
                CommonLogger.getLogger().error(response.getStatusLine().getReasonPhrase() + ",code:"
                                + response.getStatusLine().getStatusCode());
            }
        } else {
            CommonLogger.getLogger().warn(String.format("httpclient got a repsonse with empty entity;%d", resultCode));
        }
        return resultCode;
    }

    public void stop() {
        try {
            this.closeableHttpAsyncClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ResultCallBack implements FutureCallback<HttpResponse> {

        HttpUriRequest req;
        URI uri;

        public ResultCallBack(HttpUriRequest req) {
            this.req = req;
            this.uri = req.getURI();
        }

        @Override
        public void completed(HttpResponse result) {
            CommonLogger.getLogger().debug("http request success" + uri);
            if (!req.isAborted()) {
                req.abort();
            }
        }

        @Override
        public void failed(Exception ex) {
            CommonLogger.getLogger().debug("http request falied#" + uri, ex);
            if (!req.isAborted()) {
                req.abort();
            }
        }

        @Override
        public void cancelled() {
            CommonLogger.getLogger().debug(uri + "cancelled");
            if (!req.isAborted()) {
                req.abort();
            }
        }
    }
}
