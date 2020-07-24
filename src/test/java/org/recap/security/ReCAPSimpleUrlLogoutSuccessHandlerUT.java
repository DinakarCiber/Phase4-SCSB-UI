package org.recap.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.recap.BaseTestCase;
import org.recap.RecapConstants;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertNotNull;



public class ReCAPSimpleUrlLogoutSuccessHandlerUT extends BaseTestCase{


    @Autowired
    UserAuthUtil userAuthUtil;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    HttpServletRequest httpServletRequest;


    @Mock
    Authentication authentication;

    @Test
    public void onLogoutSuccess(){
        ReCAPSimpleUrlLogoutSuccessHandler reCAPSimpleUrlLogoutSuccessHandler = new ReCAPSimpleUrlLogoutSuccessHandler(userAuthUtil);
        try {
            reCAPSimpleUrlLogoutSuccessHandler.onLogoutSuccess(httpServletRequest, httpServletResponse, authentication);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
