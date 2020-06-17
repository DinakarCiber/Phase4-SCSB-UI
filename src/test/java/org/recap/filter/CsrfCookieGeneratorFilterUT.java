package org.recap.filter;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;
import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by hemalathas on 30/3/17.
 */
public class CsrfCookieGeneratorFilterUT extends BaseTestCase{

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    FilterChain filterChain;

    @Mock
    CsrfCookieGeneratorFilter csrfCookieGeneratorFilter;

    @Mock
    CsrfToken csrfToken;

    @Test
    public void testDoFilterInternal() throws ServletException, IOException {
        Mockito.when((CsrfToken) request.getAttribute("_csrf")).thenReturn(csrfToken);
        Mockito.doCallRealMethod().when(csrfCookieGeneratorFilter).doFilterInternal(request,httpServletResponse,filterChain);
        csrfCookieGeneratorFilter.doFilterInternal(request,httpServletResponse,filterChain);
    }


}