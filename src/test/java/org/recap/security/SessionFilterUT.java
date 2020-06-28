package org.recap.security;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;
import org.recap.util.UserAuthUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import static org.junit.Assert.assertNotNull;

public class SessionFilterUT extends BaseTestCase {
    @Mock
    SessionFilter sessionFilter;
    @Mock
    ServletRequest request;
    @Mock
    ServletResponse response;
    @Mock
    FilterChain filterChain;
    @Mock
    Authentication authentication;
    @Mock
    SecurityContext context;
    @Test
    public void doFilter() throws Exception{
        Mockito.when(context.getAuthentication()).thenReturn(authentication);
        Mockito.doCallRealMethod().when(sessionFilter).doFilter(request,response,filterChain);
        sessionFilter.doFilter(request,response,filterChain);
    }
    public void getUserAuthUtil(){
        UserAuthUtil userAuthUtil = sessionFilter.getUserAuthUtil();
        assertNotNull(userAuthUtil);
    }
}
