package org.recap.model.usermanagement;

import org.junit.Test;
import org.recap.BaseTestCase;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 30/3/17.
 */
public class UserFormUT extends BaseTestCase{

    @Test
    public void testUserForm(){
        Set<String> permissions = new HashSet<>();
        permissions.add("admin");
        UserForm userForm = new UserForm();
        userForm.setUserId(1);
        userForm.setUsername("john");
        userForm.setPassword("test");
        userForm.setRememberMe(true);
        userForm.setWrongCredentials("test");
        userForm.setPasswordMatcher(true);
        userForm.setInstitution("PUL");
        userForm.setErrorMessage("test");
        userForm.setPermissions(permissions);

        assertNotNull(userForm.isPasswordMatcher());
        assertNotNull(userForm.getWrongCredentials());
        assertNotNull(userForm.getPermissions());
        assertNotNull(userForm.getInstitution());
        assertNotNull(userForm.getPassword());
        assertNotNull(userForm.isRememberMe());
        assertNotNull(userForm.getUserId());
        assertNotNull(userForm.getUsername());
        assertNotNull(userForm.getErrorMessage());
    }

}