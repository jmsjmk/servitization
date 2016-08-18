package com.servitization.proxy.rpcclient;

import com.servitization.proxy.CommonLogger;
import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.CodingErrorAction;

public class CustomHttpClient implements IApacheHttpClient {

    CloseableHttpClient httpClient;

    public CustomHttpClient() {
        Registry<ConnectionSocketFactory> socketFactory = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register(
                        "https",
                        new SSLConnectionSocketFactory(SSLContexts
                                .createSystemDefault())).build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
                socketFactory);

        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true)
                .setSoKeepAlive(true).build();
        connManager.setDefaultSocketConfig(socketConfig);

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setCharset(Consts.UTF_8)
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE).build();
        connManager.setDefaultConnectionConfig(connectionConfig);

        connManager.setMaxTotal(65535);
        connManager.setDefaultMaxPerRoute(65535);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000).setSocketTimeout(3000)
                .setConnectionRequestTimeout(5000)
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
        httpClient = HttpClients.custom().setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig).build();

        String info = new StringBuilder().append("socketFactory:")
                .append(socketFactory.toString()).append("socketConfig:")
                .append(socketConfig.toString()).append('\t')
                .append("connectionConfig:")
                .append(connectionConfig.toString()).append('\t')
                .append("requestConfig:").append(requestConfig.toString())
                .toString();
        CommonLogger.getLogger().info(
                String.format("httpclient inited %s", info));
    }

    public CloseableHttpResponse sendRequest(HttpUriRequest request)
            throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        response = httpClient.execute(request);
        return response;
    }

    public byte[] sendRequestGetEntityBytes(HttpUriRequest request)
            throws ClientProtocolException, IOException, NullPointerException {
        CloseableHttpResponse response = null;
        byte[] bytes = null;
        try {
            response = httpClient.execute(request);
            if (response == null) {
                throw new NullPointerException("http get a empty response");
            }
            if (response.getEntity() != null) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    bytes = EntityUtils.toByteArray(response.getEntity());
                } else {
                    CommonLogger.getLogger().error(
                            response.getStatusLine().getReasonPhrase()
                                    + ",code:"
                                    + response.getStatusLine().getStatusCode());
                }
            }
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            request.abort();
        }
        return bytes;
    }

    public int sendRequest(HttpUriRequest request, OutputStream outputStream)
            throws ClientProtocolException, IOException, NullPointerException {
        int resultCode = HttpStatus.SC_OK;
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
            if (response == null) {
                throw new NullPointerException("http got a null response");
            }
            resultCode = response.getStatusLine().getStatusCode();
            if (response.getEntity() != null) {
                if (resultCode == HttpStatus.SC_OK || resultCode == HttpStatus.SC_NOT_MODIFIED) {
                    // add by jiamingku //  debug
                    CommonLogger.getLogger().info(
                            String.format(
                                    "http result path[%s]. code[%s]. contentType[%s]. length[%d]",
                                    request.getURI().getPath(),
                                    resultCode,
                                    response.getEntity().getContentType(),
                                    response.getEntity().getContentLength()));
                    response.getEntity().writeTo(outputStream);

                    /**
                     String s = response.getEntity().toString();
                     String s1 = null;

                     s1 = EntityUtils.toString(response.getEntity(),"utf-8");
                     System.out.println("response.getEntity().toString();:"+s);
                     System.out.println("response.getEntity().toString();:"+s1);

                     outputStream.write(s1.getBytes());

                     System.out.println(response.getEntity().getClass());
                     HttpEntity e = response.getEntity();
                     e.getContent();
                     Header[] headers =  response.getAllHeaders();
                     for (Header h: headers) {
                     System.out.println(h.getName() + ":" + h.getValue());
                     }
                     */

                } else {
                    CommonLogger.getLogger().error(
                            String.format("http result path[%s]. reasonphrase[%s]. code[%s]. ",
                                    request.getURI().getPath(),
                                    response.getStatusLine().getReasonPhrase(),
                                    response.getStatusLine().getStatusCode()));
                }
            } else {
                CommonLogger.getLogger().warn(
                        String.format("httpclient got a repsonse with empty entity;%d", resultCode));
            }
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            request.abort();
        }
        return resultCode;
    }

}
