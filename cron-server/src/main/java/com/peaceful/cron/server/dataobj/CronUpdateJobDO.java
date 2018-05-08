package com.peaceful.cron.server.dataobj;

import com.google.common.base.Preconditions;

import com.peaceful.cron.server.exception.CronResponseCode;
import com.peaceful.cron.server.exception.CronServerException;
import com.peaceful.cron.server.modal.CronJob;
import com.peaceful.cron.server.modal.JobStatus;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

/**
 * Created by Jun on 2018/5/5.
 */
public class CronUpdateJobDO {

    private long id;
    private String name;
    private JobStatus jobStatus;
    private String cronExpression;
    private Timestamp nextExecutionTime;


    public void volidate() {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(name) || id > 0, "更新条件不足");
        } catch (IllegalArgumentException e) {
            throw new CronServerException(CronResponseCode.ILLEGAL_ARGUMENT, e.getMessage());
        }
    }

    public CronJob buildUpdateRequest() {
        volidate();

        CronJob cronJob = new CronJob();
        if (id > 0) {
            cronJob.setId(this.id);
        }
        if (StringUtils.isNotBlank(name)) {
            cronJob.setName(name);
        }
        if (jobStatus != null) {
            cronJob.setStatus(jobStatus);
        }
        if (StringUtils.isNotBlank(cronExpression)) {
            cronJob.setCronExpression(cronExpression);
        }
        if (nextExecutionTime != null) {
            cronJob.setNextExecutionTime(nextExecutionTime);
        }
        return cronJob;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Timestamp getNextExecutionTime() {
        return nextExecutionTime;
    }

    public void setNextExecutionTime(Timestamp nextExecutionTime) {
        this.nextExecutionTime = nextExecutionTime;
    }
}
