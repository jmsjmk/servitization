package com.servitization.embedder.immobile.impl;

import com.servitization.embedder.immobile.ImmobileRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ImmobileRequestHttpImpl implements ImmobileRequest {

    private Map<String, String> customHeaders = null;
    private Map<String, String[]> customParameters = null;
    private String serviceName = null;
    private String remoteIP = null;
    private String method = "";

    public ImmobileRequestHttpImpl(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        customParameters = new HashMap<>(request.getParameterMap().size());
        customParameters.putAll(request.getParameterMap());
        customHeaders = new HashMap<>();
        Enumeration<String> headerKey = request.getHeaderNames();
        String key;
        while (headerKey.hasMoreElements()) {
            key = headerKey.nextElement();
            if (key != null)
                customHeaders.put(key, request.getHeader(key));
        }
        serviceName = request.getServletPath();
        method = request.getMethod();
        String ip = request.getHeader("RealIp");
        if (ip == null || ip.length() == 0)
            ip = request.getRemoteAddr();
        remoteIP = ip;
    }

    public String getParameter(String name) {
        String[] results = customParameters.get(name);
        if (results == null || results.length == 0)
            return null;
        if (results.length == 1)
            return results[0]; // most of time, return with here.
        return buildValue(results);
    }

    public Map<String, String[]> getParameterMap() {
        return customParameters;
    }

    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(customParameters.keySet());
    }

    public void setParameter(String key, String value) {
        customParameters.put(key, new String[]{value});
    }

    private String buildValue(String[] array) {
        int length = array.length;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(array[i]);
            if (i != length - 1)
                builder.append(",");
        }
        return builder.toString();
    }

    public String getHeader(String name) {
        if (name == null)
            return null;
        return customHeaders.get(name);
    }

    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(customHeaders.keySet());
    }

    public void setHeader(String key, String value) {
        if (key != null)
            customHeaders.put(key, value);
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    @Override
    public String getMethod() {
        return method;
    }
}
