package org.recap.filter;

import org.junit.Test;
import org.mockito.Mock;
import org.recap.BaseTestCase;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hemalathas on 30/3/17.
 */
public class ReCAPInstitutionFilterUT extends BaseTestCase {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    FilterChain filterChain;

    @Test
    public void testDoFilterInternal() throws ServletException, IOException {
        ReCAPInstitutionFilter reCAPInstitutionFilter = new ReCAPInstitutionFilter();
       // reCAPInstitutionFilter.doFilterInternal(request,httpServletResponse,filterChain);
    }

}