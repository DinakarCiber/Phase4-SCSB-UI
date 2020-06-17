package org.recap.service;

import org.jasig.cas.client.validation.Assertion;
import org.junit.Test;
import org.mockito.Mock;
import org.recap.BaseTestCase;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by hemalathas on 30/3/17.
 */
public class CustomUserDetailsServiceUT extends BaseTestCase{

    @Mock
    private Assertion assertion;

    @Mock
    private CustomUserDetailsService customUserDetailsService;


    @Test
    public void testLoadUserDetails(){
        Set<String> admins = new HashSet<>();
        admins.add("admin");
        CustomUserDetailsService customUserDetailsService2 = new CustomUserDetailsService();
        CustomUserDetailsService customUserDetailsService1 = new CustomUserDetailsService(admins);
    }


}