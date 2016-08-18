package com.servitization.webms.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UserAuthenticateFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hpptRequest = (HttpServletRequest) request;
        Object object = hpptRequest.getSession().getAttribute("user");
        if (null == object) {
            hpptRequest.getRequestDispatcher("login.jsp").forward(hpptRequest, response);
        } else {
            chain.doFilter(hpptRequest, response);
        }
    }

    @Override
    public void destroy() {

    }
}
