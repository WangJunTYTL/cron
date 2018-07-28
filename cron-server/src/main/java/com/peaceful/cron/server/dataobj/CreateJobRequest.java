package com.peaceful.cron.server.dataobj;

import com.google.common.base.Preconditions;

import com.peaceful.cron.server.exception.CronResponseCode;
import com.peaceful.cron.server.exception.CronServerException;
import com.peaceful.cron.server.modal.CronJob;
import com.peaceful.cron.server.modal.enums.JobStatus;
import com.peaceful.cron.server.util.CronUtil;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

/**
 * Created by Jun on 2018/5/5.
 */
@Data
public class CreateJobRequest {

    private long serviceId;
    private String name;
    private String cronExpression;
    private JobStatus status;


    private void validate() {
        try {
            Preconditions.checkArgument(serviceId > 0, "服务标识Id不可以为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(name), "Job名称不可以为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(cronExpression), "cron表达式不可以为空");
            CronUtil.getNextExecutionTime(cronExpression); // 这里只是为了校验下表达式是否正确
        } catch (IllegalArgumentException e) {
            throw new CronServerException(CronResponseCode.ILLEGAL_ARGUMENT, e.getMessage());
        }
    }

    public CronJob buildRequest() {

        validate();

        CronJob job = new CronJob();
        job.setServiceId(serviceId);
        job.setName(name);
        job.setCronExpression(cronExpression);
        job.setStatus(JobStatus.INIT);
        return job;
    }


}
