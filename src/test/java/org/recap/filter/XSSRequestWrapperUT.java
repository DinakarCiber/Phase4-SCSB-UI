package org.recap.filter;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

import static org.junit.Assert.assertNotNull;

public class XSSRequestWrapperUT extends BaseTestCase {

    @Mock
    XSSRequestWrapper xssRequestWrapper;

    @Mock
    HttpSession httpSession;

    @Test
    public void testGetParameterValues() throws Exception{
        String[] values;
        String parameter = "+-0123456789#";
        Mockito.doCallRealMethod().when(xssRequestWrapper).getParameterValues(parameter);
        /*values = xssRequestWrapper.getParameterValues(parameter);
        assertNotNull(values);*/
    }

    @Test
    public void testGetHeaders() throws Exception{
        String name = "test";
        Mockito.doCallRealMethod().when(xssRequestWrapper).getHeader(name);
        /*String value = xssRequestWrapper.getHeader(name);
        assertNotNull(value);*/
    }

    @Test
    public void testGetParameter() throws Exception{
        String parameter = "test";
        Mockito.doCallRealMethod().when(xssRequestWrapper).getParameter(parameter);
        /*String value = xssRequestWrapper.getParameter(parameter);
        assertNotNull(value);*/
    }

}
