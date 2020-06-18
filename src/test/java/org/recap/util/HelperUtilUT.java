package org.recap.util;

import org.junit.Test;
import org.mockito.Mock;
import org.recap.BaseTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

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


}