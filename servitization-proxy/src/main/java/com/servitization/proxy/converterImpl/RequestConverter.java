package com.servitization.proxy.converterImpl;

import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.metadata.common.CustomHeaderEnum;
import com.servitization.metadata.define.proxy.ServicePool;
import com.servitization.metadata.define.proxy.TargetService;
import com.servitization.proxy.IRequestConvert;
import com.servitization.proxy.check.ProxyCheck;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class RequestConverter implements IRequestConvert {

    private static CustomHeaderEnum[] ches = CustomHeaderEnum.values();

    public HttpUriRequest convert2HttpUriRequest(
            ImmobileRequest request,
            TargetService targetService, ServicePool servicePool)
            throws URISyntaxException, IllegalAccessException {
        String httpMethod = targetService.getMethod();

        List<NameValuePair> params = null;
        Map<String, String[]> paremterMap = request
                .getParameterMap();
        if (paremterMap != null) {
            params = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String[]> e : paremterMap.entrySet()) {
                if (e.getValue() != null && e.getValue().length > 0) {
                    params.add(new BasicNameValuePair(e.getKey(),
                            e.getValue()[0]));
                } else {
                    params.add(new BasicNameValuePair(e.getKey(), ""));
                }
            }
        }
        
        URI uri = null;
        String integratedUrl = ProxyCheck.servicePoolUrlCheck(servicePool
                .getUrl())
                + ProxyCheck.targetServiceNameCheck(targetService
                .getServiceName());
        if (httpMethod.equals("POST")) {
            uri = new URI(integratedUrl);
        } else {
            if (params != null && !params.isEmpty()) {
                uri = new URI(String.format("%s?%s", integratedUrl,
                        URLEncodedUtils.format(params, Consts.UTF_8)));
            } else {
                uri = new URI(integratedUrl);
            }
        }

        HttpRequestBase requestBase = getHttpRequest(uri, httpMethod, params);

        Enumeration<String> headerkeys = request.getHeaderNames();
        if (headerkeys != null) {
            CustomHeaderEnum che = null;
            String headerValue = null;
            for (int i = 0; i < ches.length; i++) {
                che = ches[i];
                headerValue = request.getHeader(che.headerName());
                if (headerValue != null) {
                    requestBase.addHeader(che.headerName(), headerValue);
                }
            }
        }
        requestBase.setHeader(CustomHeaderEnum.CLIENTIP.headerName(),
                request.getRemoteIP());

        RequestConfig config = RequestConfig
                .custom()
                .setConnectTimeout(servicePool.getConnectTimeout())
                .setSocketTimeout(targetService.getSocketTimeout())
                .setConnectionRequestTimeout(
                        servicePool.getConnectTimeout()
                                + targetService.getSocketTimeout() + 1000)
                .build();
        requestBase.setConfig(config);

        return requestBase;
    }

    private HttpRequestBase getHttpRequest(URI uri, String httpMethod,
                                           List<NameValuePair> params) {
        HttpRequestBase requestBase = null;
        switch (httpMethod) {
            case "GET":
                HttpGet httpGet = new HttpGet(uri);
                requestBase = httpGet;
                break;
            case "POST":
                HttpPost httpPost = new HttpPost(uri);
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
                        Consts.UTF_8);
                httpPost.setEntity(entity);
                requestBase = httpPost;
                break;
            case "PUT":
                HttpPut httpPut = new HttpPut(uri);
                requestBase = httpPut;
                break;
            case "OPTIONS":
                HttpOptions httpOptions = new HttpOptions(uri);
                requestBase = httpOptions;
                break;
            case "DELETE":
                HttpDelete httpDelete = new HttpDelete(uri);
                requestBase = httpDelete;
                break;
            case "TRACE":
                HttpTrace httpTrace = new HttpTrace(uri);
                requestBase = httpTrace;
                break;
            case "HEAD":
                HttpHead httpHead = new HttpHead(uri);
                requestBase = httpHead;
                break;
        }
        return requestBase;
    }
}
