package org.recap.service;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertNotNull;


/**
 * Created by hemalathas on 30/3/17.
 */
public class CustomUserDetailsServiceUT extends BaseTestCase{

    @Mock
    Assertion assertion;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    AttributePrincipal principal;

    @Test
    public void testLoadUserDetails() throws Exception{

        Set<String> admins = new HashSet<>();
        admins.add("admin");
        CustomUserDetailsService customUserDetailsService1 = new CustomUserDetailsService(admins);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("a", Arrays.asList("CN=role_a1,OU=roles,DC=spring,DC=io", "CN=role_a2,OU=roles,DC=spring,DC=io"));
        attributes.put("b", "b");
        attributes.put("c", "c");
        attributes.put("d", null);
        attributes.put("someother", "unused");
        Mockito.when(assertion.getPrincipal()).thenReturn(principal);
        Mockito.when(principal.getAttributes()).thenReturn(attributes);
        Mockito.when(principal.getName()).thenReturn("admin");
        CasAssertionAuthenticationToken token = new CasAssertionAuthenticationToken(assertion, "ticket");
        UserDetails userDetails = customUserDetailsService.loadUserDetails(token);
        assertNotNull(userDetails);
    }


}