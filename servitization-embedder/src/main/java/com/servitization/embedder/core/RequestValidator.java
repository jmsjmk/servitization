package com.servitization.embedder.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.servitization.metadata.zk.Constants;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求有效性验证器
 */
public class RequestValidator {

    private static final Logger LOG = LoggerFactory.getLogger(RequestValidator.class);

    private static boolean isHttpRequest(ServletRequest request, ServletResponse response) {
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            LOG.error("Can't accept non-http servlet request or response!");
            return false;
        }
        return true;
    }

    private static boolean isHeartBeat(HttpServletRequest request) {
        String path = request.getServletPath();
        if (path != null && path.equalsIgnoreCase(Constants.exclude_url))
            return true;
        return false;
    }

    public static boolean isValidatorRequest(ServletRequest request, ServletResponse response) {
        if (!isHttpRequest(request, response) || isHeartBeat((HttpServletRequest) request))
            return false;
        return true;
    }
}
