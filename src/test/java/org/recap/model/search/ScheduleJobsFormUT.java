package org.recap.model.search;

import org.junit.Test;
import org.recap.BaseTestCase;

import static org.junit.Assert.assertNotNull;

public class ScheduleJobsFormUT extends BaseTestCase {
    @Test
    public void testScheduleJobsForm(){
        ScheduleJobsForm scheduleJobsForm = new ScheduleJobsForm();

        scheduleJobsForm.setCronExpression("test");
        scheduleJobsForm.setErrorMessage("test");
        scheduleJobsForm.setJobDescription("test");
        scheduleJobsForm.setJobId(1);
        scheduleJobsForm.setJobName("test");
        scheduleJobsForm.setMessage("test");
        scheduleJobsForm.setScheduleType("test");

        assertNotNull(scheduleJobsForm.getMessage());
        assertNotNull(scheduleJobsForm.getErrorMessage());
        assertNotNull(scheduleJobsForm.getCronExpression());
        assertNotNull(scheduleJobsForm.getJobDescription());
        assertNotNull(scheduleJobsForm.getJobEntities());
        assertNotNull(scheduleJobsForm.getJobId());
        assertNotNull(scheduleJobsForm.getJobName());
        assertNotNull(scheduleJobsForm.getScheduleType());
    }
}
