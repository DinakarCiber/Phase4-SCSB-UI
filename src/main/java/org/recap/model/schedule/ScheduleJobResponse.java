package org.recap.model.schedule;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by rajeshbabuk on 5/4/17.
 */
@Setter
@Getter
public class ScheduleJobResponse {

    private String message;
    private Date nextRunTime;

}
