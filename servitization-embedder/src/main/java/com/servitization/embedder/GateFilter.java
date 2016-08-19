package com.servitization.embedder;

import com.servitization.embedder.core.EmbedderImpl;
import com.servitization.embedder.core.RequestValidator;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.embedder.immobile.impl.ImmobileRequestHttpImpl;
import com.servitization.embedder.immobile.impl.ImmobileResponseHttpImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 入口filter
 * <p>
 * 只会被容器初始化一次
 */
public class GateFilter implements Filter {

    private Embedder embedder = EmbedderImpl.getInstance();

    public void init(FilterConfig filterConfig) throws ServletException {
        embedder.init();
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (!RequestValidator.isValidatorRequest(request, response))
            return;
        ImmobileRequest imRequest = new ImmobileRequestHttpImpl((HttpServletRequest) request);
        ImmobileResponse imResponse = new ImmobileResponseHttpImpl((HttpServletResponse) response);
        embedder.process(imRequest, imResponse, chain);
    }

    public void destroy() {
        embedder.destroy();
    }
}
