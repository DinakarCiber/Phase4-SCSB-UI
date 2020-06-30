package org.recap.security;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.ThrowableAnalyzer;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ReCAPExceptionTranslationFilterUT  extends BaseTestCase {

    @Mock
    ReCAPExceptionTranslationFilter reCAPExceptionTranslationFilter;

    @Mock
    ThrowableAnalyzer throwableAnalyzer;
    @Mock
    AuthenticationTrustResolver authenticationTrustResolver;
    @Mock
    ServletRequest request;
    @Mock
    ServletResponse response;
    @Mock
    FilterChain filterChain;
    @Mock
    AccessDeniedHandler accessDeniedHandler;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    AuthenticationException authenticationException;

    @Test
    public void afterPropertiesSet(){
        reCAPExceptionTranslationFilter.afterPropertiesSet();
    }
    @Test
    public void getAuthenticationEntryPoint(){
        Mockito.doCallRealMethod().when(reCAPExceptionTranslationFilter).getAuthenticationEntryPoint();
        reCAPExceptionTranslationFilter.getAuthenticationEntryPoint();
   }
    @Test
    public void getAuthenticationTrustResolver(){
        Mockito.doCallRealMethod().when(reCAPExceptionTranslationFilter).getAuthenticationTrustResolver();
        reCAPExceptionTranslationFilter.getAuthenticationTrustResolver();
    }
    /*@Test
    public void sendStartAuthentication() throws Exception{
        Mockito.doCallRealMethod().when(reCAPExceptionTranslationFilter).sendStartAuthentication(httpServletRequest,httpServletResponse,filterChain,authenticationException);
        reCAPExceptionTranslationFilter.sendStartAuthentication(httpServletRequest,httpServletResponse,filterChain,authenticationException);
    }*/
    @Test
    public void setAccessDeniedHandler(){
        Mockito.doCallRealMethod().when(reCAPExceptionTranslationFilter).setAccessDeniedHandler(accessDeniedHandler);
        reCAPExceptionTranslationFilter.setAccessDeniedHandler(accessDeniedHandler);
    }

    @Test
    public void setAuthenticationTrustResolver(){
        Mockito.doCallRealMethod().when(reCAPExceptionTranslationFilter).getAuthenticationTrustResolver();
        reCAPExceptionTranslationFilter.setAuthenticationTrustResolver(authenticationTrustResolver);
    }
    @Test
    public void setThrowableAnalyzer(){
        Mockito.doCallRealMethod().when(reCAPExceptionTranslationFilter).setThrowableAnalyzer(throwableAnalyzer);
        reCAPExceptionTranslationFilter.setThrowableAnalyzer(throwableAnalyzer);
    }

}
