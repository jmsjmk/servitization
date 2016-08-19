package com.servitization.yoke.chain;

import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.handler.ChainHandler;
import com.servitization.embedder.handler.HandleResult;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.common.CommonsUtil;
import com.servitization.metadata.common.Constants;
import com.servitization.metadata.common.CustomHeaderEnum;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.encrypt.impl.EncryptDefineImpl;
import com.servitization.yoke.dao.DBAccess;
import com.servitization.yoke.service.AesService;
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

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 请求解密服务
 */
public class AESChainHandler implements ChainHandler {

    private AesService aesService;

    private Set<String> whiteList;

    private CloseableHttpClient httpclient;

    private String baseUrl;

    @Override
    public void init(ChainElementDefine eleDefine, GlobalContext context) {
        EncryptDefineImpl eDefine = (EncryptDefineImpl) eleDefine;
        whiteList = eDefine.getIPWhiteList();
        aesService = new AesService(new DBAccess((DataSource) context
                .getEmcfContext().getBean("EMBDataSource")));
        httpclient = generateHttpClient();
        baseUrl = CommonsUtil.CONFIG_PROVIDAR
                .getProperty("SysConfig.QueryAESkeyServiceUrl");
    }

    @Override
    public HandleResult handle(ImmobileRequest request,
                               ImmobileResponse response, RequestContext context) {
        if (whiteList != null && whiteList.contains(request.getRemoteIP()))
            return HandleResult.CONTINUE;
        String req = request.getParameter(Constants.REQ_PARAM_NAME);
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
            try {
                String value = aesService.decryptByKey(aesKey, req);
                request.setParameter(Constants.REQ_PARAM_NAME, value);
            } catch (Exception e) {
                Exception e1 = new Exception(new StringBuilder()
                        .append("AESChainHandler error#")
                        .append("RSA:" + sessionKey).append(',').append(req)
                        .toString(), e);
                context.addError(e1);
                return HandleResult.STOP;
            }
        } else {
            String clientKey = null;
            try {
                String clientType = request
                        .getHeader(CustomHeaderEnum.CLIENTTYPE.headerName());
                String version = request.getHeader(CustomHeaderEnum.VERSION
                        .headerName());
                clientKey = String.format("%s.%s", clientType, version);
                String value = aesService.decryptByClientKey(clientKey, req);
                request.setParameter(Constants.REQ_PARAM_NAME, value);
            } catch (Exception e) {
                Exception e1 = new Exception(new StringBuilder()
                        .append("AESChainHandler error#")
                        .append("AES:" + clientKey).append(',').append(req)
                        .toString(), e);
                context.addError(e1);
                return HandleResult.STOP;
            }
        }
        return HandleResult.CONTINUE;
    }

    @Override
    public void destroy(GlobalContext context) {
        aesService.destroy();
        aesService = null;
        whiteList = null;
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
