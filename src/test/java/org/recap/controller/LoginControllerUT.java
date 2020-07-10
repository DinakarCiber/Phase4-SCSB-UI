package org.recap.controller;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.recap.RecapConstants;
import org.recap.model.usermanagement.UserForm;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * Created by hemalathas on 1/2/17.
 */
public class LoginControllerUT extends BaseControllerUT{

    @Autowired
    LoginController loginController;
    @Mock
    LoginController loginControllerMocked;

    @Mock
    HttpSession session;

    @Mock
    javax.servlet.http.HttpServletRequest request;

    @Mock
    private UserAuthUtil userAuthUtil;

    @Mock
    Model model;

    @Mock
    BindingResult error;

    @Mock
    private Authentication auth;

    @Mock
    private UsernamePasswordToken token;


    @Mock
    SecurityContext securityContext;

    @Mock
    private RestTemplate restTemplate;

    @Value("${scsb.shiro}")
    private String scsbShiro;

    @Mock
    private HttpEntity requestEntity;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void loginScreenTest(){
        String response = loginController.loginScreen(request,model,new UserForm());
        assertNotNull(response);
        assertEquals(response,"login");
    }
    @Test
    public void home(){
        String response = loginController.home(request,model,new UserForm());
        assertNotNull(response);
        assertEquals(response,"login");
    }

    @Test
    public void logOutTest() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        usersSessionAttributes();
        String response = loginController.logoutUser(request);
        assertNotNull(response);
    }

    @Test
    public void createSessionTest() throws Exception{
        UserForm userForm = getUserForm();
        UsernamePasswordToken token=new UsernamePasswordToken(userForm.getUsername()+ RecapConstants.TOKEN_SPLITER +userForm.getInstitution(),userForm.getPassword(),true);
        when(userAuthUtil.doAuthentication(token)).thenCallRealMethod();
        when(restTemplate.postForObject(scsbShiro + RecapConstants.SCSB_SHIRO_AUTHENTICATE_URL, requestEntity, HashMap.class)).thenThrow(new RestClientException("Exception occured"));
        String response = loginController.createSession(userForm,request,model,error);
        assertNotNull(response);
        assertEquals(response,"login");
    }
    @Test
    public void createSessionTestEmptyUserForm() throws Exception{
        UserForm userForm = new UserForm();
//        UsernamePasswordToken token=new UsernamePasswordToken(userForm.getUsername()+ RecapConstants.TOKEN_SPLITER +userForm.getInstitution(),userForm.getPassword(),true);
        //when(userAuthUtil.doAuthentication(token)).thenCallRealMethod();
       // when(restTemplate.postForObject(scsbShiro + RecapConstants.SCSB_SHIRO_AUTHENTICATE_URL, requestEntity, HashMap.class)).thenThrow(new RestClientException("Exception occured"));
        String response = loginController.createSession(userForm,request,model,error);
        assertNotNull(response);
        assertEquals(response,"login");
    }

    @Test
    public void createSessionAuthenticationException() throws Exception{
        UserForm userForm = getUserForm();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("isAuthenticated",true);
        UsernamePasswordToken token=new UsernamePasswordToken(userForm.getUsername()+ RecapConstants.TOKEN_SPLITER +userForm.getInstitution(),userForm.getPassword(),true);
        when(loginControllerMocked.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(loginControllerMocked.getUserAuthUtil().doAuthentication(token)).thenThrow(new Exception());
        //when(restTemplate.postForObject(scsbShiro + RecapConstants.SCSB_SHIRO_AUTHENTICATE_URL, requestEntity, HashMap.class)).thenThrow(new RestClientException("Exception occured"));
        Mockito.doCallRealMethod().when(loginControllerMocked).createSession(userForm,request,model,error);
//        String response = loginControllerMocked.createSession(userForm,request,model,error);
  //      assertNotNull(response);
    //    assertEquals(response,"login");
    }
    @Test
    public void testLogin(){
        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
        when(request.getSession(true)).thenReturn(session);
        Mockito.when(request.getSession().getId()).thenReturn("66");
//        when(securityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("john");
        UserForm userForm = new UserForm();
        userForm.setInstitution("CUL");
        userForm.setUsername("john");
        String response = loginController.login(userForm,request,model,error);
        assertNotNull(response);
    }
    @Test
    public void testLoginException(){
        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
        when(request.getSession(true)).thenReturn(session);
        Mockito.when(request.getSession().getId()).thenReturn("23");
        Mockito.when(auth.getName()).thenReturn("john");
        UserForm userForm = new UserForm();
        userForm.setInstitution("PUL");
        userForm.setUsername("john");
        String response = loginController.login(userForm,request,model,error);
        assertNotNull(response);
    }

    private void usersSessionAttributes() throws Exception {
        when(request.getSession()).thenReturn(session);
        UserForm userForm = new UserForm();
        userForm.setUsername("SuperAdmin");
        userForm.setInstitution("1");
        userForm.setPassword("12345");
        UsernamePasswordToken token=new UsernamePasswordToken(userForm.getUsername()+ RecapConstants.TOKEN_SPLITER +userForm.getInstitution(),userForm.getPassword(),true);
        userAuthUtil.doAuthentication(token);
        when(session.getAttribute(RecapConstants.USER_TOKEN)).thenReturn(token);
        when(session.getAttribute(RecapConstants.USER_ID)).thenReturn(3);
        when(session.getAttribute(RecapConstants.SUPER_ADMIN_USER)).thenReturn(false);
        when(session.getAttribute(RecapConstants.BARCODE_RESTRICTED_PRIVILEGE)).thenReturn(false);
        when(session.getAttribute(RecapConstants.REQUEST_ITEM_PRIVILEGE)).thenReturn(false);
        when(session.getAttribute(RecapConstants.USER_INSTITUTION)).thenReturn(1);
        when(session.getAttribute(RecapConstants.REQUEST_ALL_PRIVILEGE)).thenReturn(false);
        userAuthUtil.getUserDetails(session,RecapConstants.BARCODE_RESTRICTED_PRIVILEGE);
    }

    private UserForm getUserForm(){
        Set<String> permissions = new HashSet<>();
        permissions.add("admin");
        UserForm userForm = new UserForm();
        userForm.setUserId(1);
        userForm.setUsername("john");
        userForm.setPassword("john");
        userForm.setRememberMe(true);
        userForm.setWrongCredentials("test");
        userForm.setPasswordMatcher(true);
        userForm.setInstitution("PUL");
        userForm.setErrorMessage("test");
        userForm.setPermissions(permissions);
        userForm.setPasswordMatcher(true);
        return userForm;
    }

}