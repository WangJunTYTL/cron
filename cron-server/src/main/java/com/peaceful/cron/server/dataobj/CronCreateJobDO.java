package com.peaceful.cron.server.dataobj;

import com.google.common.base.Preconditions;

import com.peaceful.cron.server.exception.CronResponseCode;
import com.peaceful.cron.server.exception.CronServerException;
import com.peaceful.cron.server.modal.CronJob;
import com.peaceful.cron.server.modal.JobStatus;

import org.apache.commons.lang.StringUtils;

/**
 * Created by wangjun38 on 2018/5/5.
 */
public class CronCreateJobDO {

    private String name;
    private String cronExpression;
    private JobStatus status;


    private void validate() {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(name), "Job名称不可以为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(cronExpression), "cron表达式不可以为空");
        } catch (IllegalArgumentException e) {
            throw new CronServerException(CronResponseCode.ILLEGAL_ARGUMENT,e.getMessage());
        }
    }

    public CronJob buildInsertRequest() {

        validate();

        CronJob job = new CronJob();
        job.setName(name);
        job.setCronExpression(cronExpression);
        job.setStatus(JobStatus.INIT);
        return job;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }
}
