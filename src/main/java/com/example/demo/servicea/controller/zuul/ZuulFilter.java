package com.example.demo.servicea.controller.zuul;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class ZuulFilter implements Filter {

    public static final String RELATION_ID = "relation_id";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String value = ((HttpServletRequest) request).getHeader(RELATION_ID);
        RelationIdContextHolder.set(value);
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
