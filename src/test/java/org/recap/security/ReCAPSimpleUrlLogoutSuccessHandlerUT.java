package org.recap.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.recap.BaseTestCase;
import org.recap.RecapConstants;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertNotNull;


public class ReCAPSimpleUrlLogoutSuccessHandlerUT extends BaseTestCase {
    @InjectMocks
    ReCAPSimpleUrlLogoutSuccessHandler reCAPSimpleUrlLogoutSuccessHandler;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpSession session;

    @Mock
    Authentication authentication;

    @Test
    public void onLogoutSuccess() throws Exception{
        Mockito.when(httpServletRequest.getSession()).thenReturn(session);
        Mockito.when(httpServletRequest.getAttribute(RecapConstants.USER_TOKEN)).thenReturn("token");
        Mockito.doCallRealMethod().when(reCAPSimpleUrlLogoutSuccessHandler).onLogoutSuccess(httpServletRequest,httpServletResponse,authentication);
       // reCAPSimpleUrlLogoutSuccessHandler.onLogoutSuccess(httpServletRequest,httpServletResponse,authentication);
    }
    @Test
    public void determineTargetUrl(){
        String result =  reCAPSimpleUrlLogoutSuccessHandler.determineTargetUrl(httpServletRequest,httpServletResponse);
        assertNotNull(result);
    }
}
