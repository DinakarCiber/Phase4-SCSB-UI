package org.recap.controller;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.RecapConstants;
import org.recap.model.usermanagement.UserDetailsForm;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by rajeshbabuk on 20/4/17.
 */
public class ScheduleJobsControllerUT extends BaseControllerUT {

    @Value("${scsb.url}")
    String scsbUrl;

    @Mock
    ScheduleJobsController scheduleJobsController;

    @Mock
    Model model;

    @Mock
    RestTemplate restTemplate;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession session;

    @Mock
    private UserAuthUtil userAuthUtil;

    public String getScsbUrl() {
        return scsbUrl;
    }

    public void setScsbUrl(String scsbUrl) {
        this.scsbUrl = scsbUrl;
    }

    public UserAuthUtil getUserAuthUtil() {
        return userAuthUtil;
    }

    public void setUserAuthUtil(UserAuthUtil userAuthUtil) {
        this.userAuthUtil = userAuthUtil;
    }

    @Test
    public void testDisplayJobs() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        Mockito.when(getUserAuthUtil().getUserDetails(session, RecapConstants.BARCODE_RESTRICTED_PRIVILEGE)).thenReturn(new UserDetailsForm());
        Mockito.when(scheduleJobsController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(scheduleJobsController.displayJobs(model, request)).thenCallRealMethod();
        String viewName = scheduleJobsController.displayJobs(model, request);
        assertNotNull(viewName);
    }

    @Test
    public void checkGetterMethod(){
        Mockito.doCallRealMethod().when(scheduleJobsController).setUserAuthUtil(userAuthUtil);
        Mockito.when(scheduleJobsController.getUserAuthUtil()).thenCallRealMethod();
        Mockito.when(scheduleJobsController.getRestTemplate()).thenCallRealMethod();
        assertNotEquals(scheduleJobsController.getUserAuthUtil(),userAuthUtil);
        assertNotEquals(scheduleJobsController.getRestTemplate(),restTemplate);
    }
}
