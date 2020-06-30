package org.recap.util;

import org.junit.Test;
import org.mockito.Mock;
import org.recap.BaseTestCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by hemalathas on 31/3/17.
 */
public class HelperUtilUT extends BaseTestCase{

    @Autowired
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletRequest request;

    @Mock
    HelperUtil mockedHelperUtil;

    @Test
    public void testGetAttributeValueFromRequest(){
        HelperUtil helperUtil = new HelperUtil();
        String response = helperUtil.getAttributeValueFromRequest(httpServletRequest,"test");
        assertNull(response);
    }

    @Test
    public void testGetLogoutUrl() throws Exception{
        String  institutionCode = "NYPL";
        HelperUtil helperUtil = new HelperUtil();
        String casLogoutUrl = helperUtil.getLogoutUrl(institutionCode);
        assertNotNull(casLogoutUrl);
    }
    @Test
    public void testGetLogoutUrl2() throws Exception{
        String  institutionCode = "PUL";
        HelperUtil helperUtil = new HelperUtil();
        String casLogoutUrl = helperUtil.getLogoutUrl(institutionCode);
        assertNotNull(casLogoutUrl);
    }

    @Test
    public void testLogoutFromShiro() throws Exception{
        Object attribute = "recap" ;
        HelperUtil helperUtil = new HelperUtil();
        helperUtil.logoutFromShiro(attribute);

    }
   /* @Test
    public void testIsAnonymousUser() throws Exception{
        Authentication auth ;
        auth.setAuthenticated(true);
        HelperUtil helperUtil = new HelperUtil();
        boolean result=helperUtil.isAnonymousUser(auth);
        assertNotNull(result);
    }
*/
}