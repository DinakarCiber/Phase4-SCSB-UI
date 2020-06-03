package org.recap.model.schedule;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by rajeshbabuk on 5/4/17.
 */
@Setter
@Getter
public class ScheduleJobRequest {

    private Integer jobId;
    private String jobName;
    private String cronExpression;
    private String scheduleType;

}
