package org.recap.security;


import org.junit.Test;
import org.mockito.Mock;
import org.recap.BaseTestCase;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 30/3/17.
 */
public class UserManagementServiceUT extends BaseTestCase{

    @Autowired
    HttpSession httpSession;

    @Mock
    Logger logger;

    @Autowired
    UserManagementService userManagementService;

    @Test
    public void testSuperAdminRoleId(){
        Integer response = userManagementService.getSuperAdminRoleId();
        assertNotNull(response);
    }

    @Test
    public void testUnAuthorizedUser(){
        String response = userManagementService.unAuthorizedUser(httpSession,"test",logger);
        assertNotNull(response);
        assertEquals(response,"redirect:/");
    }

}