package org.recap.filter;

import org.recap.controller.RequestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by angelind on 27/9/17.
 */
public class XSSFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("init method called");
    }

    @Override
    public void destroy() {
        logger.info("Destroyed");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
    }

}
