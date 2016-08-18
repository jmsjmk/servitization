package com.servitization.request.vo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;

public class NewRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> newParameters = null;
    private Map<String, String> newHeaders = null;

    public NewRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public Map<String, String[]> getNewParameters() {
        return newParameters;
    }

    public void setNewParameters(Map<String, String[]> newParameters) {
        this.newParameters = newParameters;
    }

    public Map<String, String> getNewHeaders() {
        return newHeaders;
    }

    public void setNewHeaders(Map<String, String> newHeaders) {
        this.newHeaders = newHeaders;
    }
}
