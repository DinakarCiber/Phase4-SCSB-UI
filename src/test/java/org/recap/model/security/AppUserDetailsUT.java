package org.recap.model.security;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.*;

public class AppUserDetailsUT extends BaseTestCase {

    AppUserDetails appUserDetails = new AppUserDetails();
    Collection<? extends GrantedAuthority> authorities = null;
    String userId = "1";
    //AppUserDetails appUserDetails1 = new AppUserDetails(userId,authorities);
    @Test
    public void testAppUser(){

        assertNull(appUserDetails.getUsername());
        assertNull(appUserDetails.getPassword());
        assertTrue(appUserDetails.isAccountNonExpired());
        assertTrue(appUserDetails.isCredentialsNonExpired());
        assertTrue(appUserDetails.isAccountNonLocked());
        assertTrue(appUserDetails.isEnabled());
    }

}
