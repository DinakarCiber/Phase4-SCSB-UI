package org.recap.security;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;
import org.recap.RecapConstants;
import org.recap.util.HelperUtil;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

public class ReCAPHttpSessionEventPublisherUT extends BaseTestCase {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpSessionEvent httpSessionEvent;
    @Mock
    HttpSession session;
    @Autowired
    ReCAPHttpSessionEventPublisher reCAPHttpSessionEventPublisher;
    @Mock
    UserAuthUtil userAuthUtil;
    @Mock
    HelperUtil helperUtil;
    @Mock
    UsernamePasswordToken usernamePasswordToken;

    @Mock
    ServletContext servletContext;

    Map<String, Object> attributes = new HashMap<>();

    Object value = "recap";
    String attribute = RecapConstants.USER_TOKEN;
    @Test
    public void testSessionDestroyed() throws Exception{
       /*attributes.put("1","token");
       // when(userAuthUtil.authorizedUser(RecapConstants.SCSB_SHIRO_LOGOUT_URL,(UsernamePasswordToken)attributes)).thenReturn(true);
        when(session.getAttribute(RecapConstants.USER_TOKEN)).thenReturn(attributes);
        when(httpSessionEvent.getSession()).thenReturn(session);
        reCAPHttpSessionEventPublisher.sessionDestroyed(httpSessionEvent);*/
    }
}
