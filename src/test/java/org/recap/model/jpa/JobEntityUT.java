package org.recap.model.jpa;

import org.junit.Test;
import org.recap.BaseTestCase;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 17/7/17.
 */
public class JobEntityUT extends BaseTestCase{

    @Test
    public void testJobEntity(){
        JobEntity jobEntity = new JobEntity();
        jobEntity.setId(1);
        jobEntity.setJobName("Test");
        jobEntity.setCronExpression("0 53 19 1/1 * ? *");
        jobEntity.setJobDescription("Test");
        jobEntity.setLastExecutedTime(new Date());
        jobEntity.setStatus("Success");
        jobEntity.setNextRunTime(new Date());
        assertNotNull(jobEntity.getNextRunTime());
        assertNotNull(jobEntity.getCronExpression());
        assertNotNull(jobEntity.getJobDescription());
        assertNotNull(jobEntity.getId());
        assertNotNull(jobEntity.getJobName());
        assertNotNull(jobEntity.getLastExecutedTime());
        assertNotNull(jobEntity.getStatus());
    }

}