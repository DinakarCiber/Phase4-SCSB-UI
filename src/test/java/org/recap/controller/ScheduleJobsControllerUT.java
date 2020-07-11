package org.recap.controller;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.recap.model.jpa.JobEntity;
import org.recap.model.jpa.RequestItemEntity;
import org.recap.model.request.ItemRequestInformation;
import org.recap.model.request.ItemResponseInformation;
import org.recap.model.request.ReplaceRequest;
import org.recap.model.schedule.ScheduleJobRequest;
import org.recap.model.schedule.ScheduleJobResponse;
import org.recap.model.search.ScheduleJobsForm;
import org.recap.model.usermanagement.UserDetailsForm;
import org.recap.repository.jpa.JobDetailsRepository;
import org.recap.service.RestHeaderService;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.*;

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
    @Autowired
    ScheduleJobsController scheduleJobsController1;

    @Mock
    HttpEntity httpEntity;

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

    public RestHeaderService getRestHeaderService() {
        return restHeaderService;
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
        Mockito.when(scheduleJobsController.getJobDetailsRepository()).thenReturn(jobDetailsRepository);
        Mockito.when(getUserAuthUtil().getUserDetails(session, RecapConstants.BARCODE_RESTRICTED_PRIVILEGE)).thenReturn(userDetailsForm);
        Mockito.when(scheduleJobsController.getJobDetailsRepository().findAll()).thenReturn(Collections.EMPTY_LIST);
        Mockito.when(scheduleJobsController.displayJobs(model, request)).thenCallRealMethod();
        String viewName = scheduleJobsController.displayJobs(model, request);
        assertNotNull(viewName);
    }
    @Test
    public void testDisplayJobsForSuperAdmin() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        UserDetailsForm userDetailsForm = new UserDetailsForm();
        userDetailsForm.setSuperAdmin(true);
        userDetailsForm.setRecapPermissionAllowed(true);
        Mockito.when(scheduleJobsController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(getUserAuthUtil().getUserDetails(session, RecapConstants.BARCODE_RESTRICTED_PRIVILEGE)).thenReturn(userDetailsForm);
        Mockito.when(scheduleJobsController.getJobDetailsRepository()).thenReturn(jobDetailsRepository);
        Mockito.when(scheduleJobsController.getJobDetailsRepository().findAll()).thenReturn(Collections.EMPTY_LIST);
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
    public void testScheduleJobException(){
        ScheduleJobRequest scheduleJobRequest = new ScheduleJobRequest();
        scheduleJobRequest.setJobId(1);
        scheduleJobRequest.setJobName("SCHEDULE");
        ScheduleJobResponse scheduleJobResponse = new ScheduleJobResponse();
        ScheduleJobsForm scheduleJobsForm = getScheduleJobsForm();
        scheduleJobResponse.setMessage("SUCCESS");
        scheduleJobResponse.setNextRunTime(new Date());
        when(scheduleJobsController.getRestTemplate()).thenReturn(restTemplate);
        ResponseEntity responseEntity1 = new ResponseEntity<ScheduleJobResponse>(scheduleJobResponse,HttpStatus.OK);
        Mockito.when(scheduleJobsController.getRestTemplate().exchange(getScsbUrl() + RecapCommonConstants.URL_SCHEDULE_JOBS, HttpMethod.POST, httpEntity, ScheduleJobResponse.class)).thenReturn(responseEntity1);
        Mockito.doCallRealMethod().when(scheduleJobsController).scheduleJob(scheduleJobsForm,bindingResult,model);
        scheduleJobsController.scheduleJob(scheduleJobsForm,bindingResult,model);
    }
   @Test
   public void testScheduleJob(){
       ScheduleJobRequest scheduleJobRequest = new ScheduleJobRequest();
       scheduleJobRequest.setJobId(1);
       scheduleJobRequest.setJobName("SCHEDULE");
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
