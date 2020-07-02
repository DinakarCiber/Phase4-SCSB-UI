package org.recap.controller;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.RecapConstants;
import org.recap.model.jpa.JobEntity;
import org.recap.model.schedule.ScheduleJobResponse;
import org.recap.model.search.ScheduleJobsForm;
import org.recap.model.usermanagement.UserDetailsForm;
import org.recap.repository.jpa.JobDetailsRepository;
import org.recap.service.RestHeaderService;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Date;

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
    @Autowired
    ScheduleJobsController scheduleJobsController1;

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

    @Mock
    JobDetailsRepository jobDetailsRepository;

    @Autowired
    RestHeaderService restHeaderService;

    @Mock
    BindingResult bindingResult;

    @Mock
    JobEntity jobEntity;

    @Mock
    ScheduleJobResponse scheduleJobResponse;

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
        UserDetailsForm userDetailsForm = new UserDetailsForm();
        userDetailsForm.setSuperAdmin(false);
        userDetailsForm.setRecapPermissionAllowed(true);
        Mockito.when(scheduleJobsController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(getUserAuthUtil().getUserDetails(session, RecapConstants.BARCODE_RESTRICTED_PRIVILEGE)).thenReturn(userDetailsForm);
        when(jobDetailsRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        Mockito.when(scheduleJobsController.displayJobs(model, request)).thenCallRealMethod();
        String viewName = scheduleJobsController.displayJobs(model, request);
        assertNotNull(viewName);
    }

    @Test
    public void checkGetterMethod(){

        Mockito.doCallRealMethod().when(scheduleJobsController).setUserAuthUtil(userAuthUtil);
        scheduleJobsController.setUserAuthUtil(userAuthUtil);
        Mockito.when(scheduleJobsController.getUserAuthUtil()).thenCallRealMethod();
        Mockito.when(scheduleJobsController.getRestTemplate()).thenCallRealMethod();
    }

    @Test
    public void testScheduleJob(){
        ScheduleJobResponse scheduleJobResponse = new ScheduleJobResponse();
        ScheduleJobsForm scheduleJobsForm = getScheduleJobsForm();
        scheduleJobResponse.setMessage("SUCCESS");
        scheduleJobResponse.setNextRunTime(new Date());
        scheduleJobsController1.scheduleJob(scheduleJobsForm,bindingResult,model);
    }
    private ScheduleJobsForm getScheduleJobsForm(){
        ScheduleJobsForm scheduleJobsForm = new ScheduleJobsForm();
        scheduleJobsForm.setCronExpression("cron");
        scheduleJobsForm.setErrorMessage("error");
        scheduleJobsForm.setJobDescription("description");
        scheduleJobsForm.setJobId(1);
        scheduleJobsForm.setJobName("SCHEDULEDER");
        scheduleJobsForm.setMessage("SUCCESS");
        scheduleJobsForm.setScheduleType("UNSCHEDULE");
        return scheduleJobsForm;
    }

}
