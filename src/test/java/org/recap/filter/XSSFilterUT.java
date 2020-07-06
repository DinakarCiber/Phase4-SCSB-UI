package org.recap.filter;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class XSSFilterUT extends BaseTestCase {

    @Mock
    ServletResponse servletResponse;

    @Mock
    FilterChain filterChain;

    @Mock
    XSSFilter xssFilter;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession httpSession;

    @Mock
    FilterConfig filterConfig;

    @Test
    public void testdoFilter() throws IOException, ServletException {
        Mockito.when(((HttpServletRequest)request).getSession()).thenReturn(httpSession);
        Mockito.doCallRealMethod().when(xssFilter).doFilter(request,servletResponse,filterChain);
        xssFilter.doFilter(request,servletResponse,filterChain);
    }

    @Test
    public void testDestroy(){
        Mockito.doCallRealMethod().when(xssFilter).destroy();
        xssFilter.destroy();
    }

    @Test
    public void testInit() throws ServletException{
        Mockito.doCallRealMethod().when(xssFilter).init(filterConfig);
        xssFilter.init(filterConfig);
    }
}
