package org.recap.controller;

import org.junit.Test;
import org.mockito.Mock;
import org.recap.BaseTestCase;
import org.recap.service.RestHeaderService;
import org.recap.util.UserAuthUtil;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

public class AbstractControllerUT extends BaseTestCase {

    @Mock
    UserAuthUtil userAuthUtil;
    @Mock
    HttpSession session;


    @Test
    public void getRestHeaderService(){
        AbstractController abstractController = new AbstractController();
        RestHeaderService restHeaderService = abstractController.getRestHeaderService();
    }
    @Test
    public void getRestTemplate(){
        AbstractController abstractController = new AbstractController();
        RestTemplate restTemplate = abstractController.getRestTemplate();
    }

          /*  @Test
    public void isAuthenticated(){
        String roleUrl = "http://localhost:9093/search/roles";
        Mockito.doCallRealMethod().when(authenticationController).isAuthenticated(session,roleUrl);
        authenticationController.isAuthenticated(session,roleUrl);
    }
    @Test
    public void getUserDetails(){
        String privilege = RecapConstants.REQUEST_PRIVILEGE;
        Mockito.doCallRealMethod().when(authenticationController).getUserDetails(session,privilege);
        authenticationController.getUserDetails(session,privilege);
    }
    @Test
    public void getRoles(){
        UserDetailsForm userDetailsForm = new UserDetailsForm();
        userDetailsForm.setRecapPermissionAllowed(true);
        userDetailsForm.setLoginInstitutionId(1);
        userDetailsForm.setSuperAdmin(true);
        Mockito.doCallRealMethod().when(authenticationController).getRoles(userDetailsForm);
        List<Object> result = authenticationController.getRoles(userDetailsForm);
    }
    @Test
    public void getInstitutions(){
        UserDetailsForm userDetailsForm = new UserDetailsForm();
        userDetailsForm.setRecapPermissionAllowed(true);
        userDetailsForm.setLoginInstitutionId(1);
        userDetailsForm.setSuperAdmin(true);
        Mockito.doCallRealMethod().when(authenticationController).getInstitutions(userDetailsForm);
        List<Object> result = authenticationController.getInstitutions(userDetailsForm);
    }*/
}
