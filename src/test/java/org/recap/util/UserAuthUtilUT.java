package org.recap.util;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;
import org.recap.RecapConstants;
import org.recap.model.usermanagement.UserForm;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by dharmendrag on 7/2/17.
 */
public class UserAuthUtilUT extends BaseTestCase {

    @Mock
    UserAuthUtil userAuthUtil;

    @Test
    public void testLoginAndAuthorization()throws Exception{
        boolean authorized=false;
        UserForm userForm=new UserForm();
        userForm.setUsername("john");
        userForm.setInstitution("CUL");
        userForm.setPassword("123");

        Map<String,Object> map = new HashMap<>();
        map.put("isAuthenticated",true);
        map.put("userName","john");
        map.put(RecapConstants.USER_ID,"CUL");
        UsernamePasswordToken token=new UsernamePasswordToken(userForm.getUsername()+ RecapConstants.TOKEN_SPLITER +"CUL",userForm.getPassword(),true);
        Mockito.when((Map<String,Object>)userAuthUtil.doAuthentication(token)).thenReturn(map);
        Map<String,Object> resultmap=(Map<String,Object>)userAuthUtil.doAuthentication(token);

        assertTrue((Boolean) resultmap.get("isAuthenticated"));
        assertEquals(userForm.getUsername(),resultmap.get("userName"));
        assertEquals(userForm.getInstitution(),resultmap.get(RecapConstants.USER_ID));
        Mockito.when(userAuthUtil.authorizedUser(RecapConstants.SCSB_SHIRO_COLLECTION_URL,token)).thenReturn(true);
        authorized=userAuthUtil.authorizedUser(RecapConstants.SCSB_SHIRO_COLLECTION_URL,token);

        assertTrue(authorized);

    }

}
