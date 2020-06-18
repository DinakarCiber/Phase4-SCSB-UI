package org.recap.filter;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by hemalathas on 30/3/17.
 */
public class ReCAPLogoutFilterUT extends BaseTestCase {

    @Mock
    HttpServletRequest request;

    @Mock
    ServletResponse servletResponse;

    @Mock
    FilterChain filterChain;

    @Mock
    ReCAPLogoutFilter reCAPLogoutFilter;

    @Mock
    HttpSession httpSession;

    @Test
    public void testdoFilter() throws IOException, ServletException {
        Mockito.when(((HttpServletRequest)request).getSession()).thenReturn(httpSession);
        Mockito.doCallRealMethod().when(reCAPLogoutFilter).doFilter(request,servletResponse,filterChain);
        reCAPLogoutFilter.doFilter(request,servletResponse,filterChain);
    }

}