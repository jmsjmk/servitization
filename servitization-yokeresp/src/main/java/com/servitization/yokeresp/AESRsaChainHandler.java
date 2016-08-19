package com.servitization.yokeresp;

import com.servitization.commons.util.AesEncryUtil;
import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.handler.ChainHandler;
import com.servitization.embedder.handler.HandleResult;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.common.CommonsUtil;
import com.servitization.metadata.common.CustomHeaderEnum;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;

public class AESRsaChainHandler implements ChainHandler {

    private static final String encryptRespValue = "aes";

    private static final Logger LOG = LoggerFactory
            .getLogger(AESRsaChainHandler.class);

    private CloseableHttpClient httpclient;

    private String baseUrl;

    @Override
    public void init(ChainElementDefine eleDefine, GlobalContext context) {
        httpclient = generateHttpClient();
        baseUrl = CommonsUtil.CONFIG_PROVIDAR
                .getProperty("SysConfig.QueryAESkeyServiceUrl");
    }

    @Override
    public HandleResult handle(ImmobileRequest request,
                               ImmobileResponse response, RequestContext context) {
        String encryptResp = request.getHeader(CustomHeaderEnum.ENCRYPTRESP
                .headerName());
        if (encryptResp != null && encryptResp.length() > 0
                && encryptResp.indexOf(encryptRespValue) != -1) {
            String sessionKey = request.getHeader(CustomHeaderEnum.SESSIONKEY
                    .headerName());
            if (sessionKey != null && sessionKey.length() > 0) {
                String aesKey = null;
                try {
                    aesKey = sendHttpAesQuery(sessionKey);
                } catch (Exception e) {
                    context.addError(e);
                }
                if (aesKey == null || aesKey.length() == 0) {
                    context.addError("799", "密钥已过期，请重新协商！");
                    return HandleResult.STOP;
                }
                byte[] data = response.getContent();
                if (data != null && data.length > 0) {
                    try {
                        String encryptContent = AesEncryUtil.encryptByKey(
                                new String(data), aesKey);
                        response.resetContent();
                        response.getOutputStream().write(
                                encryptContent.getBytes());
                    } catch (Exception e) {
                        LOG.error(context.getGlobalContext().getServiceDefine()
                                .getName()
                                + "#Error occurs when encrypt the response!", e);
                    }
                }
            }

        }
        return HandleResult.CONTINUE;
    }

    @Override
    public void destroy(GlobalContext context) {
        if (httpclient != null)
            try {
                httpclient.close();
            } catch (IOException e) {
            }
        httpclient = null;
    }

    /**
     * 生成http客户端
     *
     * @return
     */
    private CloseableHttpClient generateHttpClient() {
        Registry<ConnectionSocketFactory> socketFactory = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .build();
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
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig).build();
        return httpClient;
    }

    private String sendHttpAesQuery(String sessionkey)
            throws URISyntaxException, IOException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(CustomHeaderEnum.SESSIONKEY
                .headerName(), sessionkey));
        URI uri = new URI(String.format("%s?%s", baseUrl,
                URLEncodedUtils.format(params, Consts.UTF_8)));
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        InputStream is = response.getEntity().getContent();
        if (is.available() == 0)
            return null;
        byte[] b = new byte[is.available()];
        is.read(b);
        response.close();
        return new String(b);
    }
}
