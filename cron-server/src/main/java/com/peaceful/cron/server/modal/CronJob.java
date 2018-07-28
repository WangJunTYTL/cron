package com.peaceful.cron.server.modal;

import com.peaceful.cron.server.modal.enums.JobStatus;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created by Jun on 2018/5/5.
 */
@Data
public class CronJob {

    private long id;
    private long serviceId;
    private String name;
    private String cronExpression;
    private JobStatus status;
    private Timestamp nextExecutionTime;
    private Timestamp createTime;
    private Timestamp updateTime;


}
