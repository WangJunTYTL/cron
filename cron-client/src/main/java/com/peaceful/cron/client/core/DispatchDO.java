package com.peaceful.cron.client.core;

import com.peaceful.cron.client.exception.CronException;
import com.peaceful.cron.client.exception.CronStatusCode;

/**
 * Created by wangjun38 on 2018/5/6.
 */
public class DispatchDO {

    private String jobName;
    private String dispatchId;

    public void validate() {
        if (jobName == null || jobName.trim().equalsIgnoreCase("")) {
            throw new CronException(CronStatusCode.ILLEGAL_REQUEST, "执行指令无效");
        }
        if (dispatchId == null || dispatchId.trim().equalsIgnoreCase("") || dispatchId.trim().equalsIgnoreCase("0")) {
            throw new CronException(CronStatusCode.ILLEGAL_REQUEST, "执行指令无效");
        }
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(String dispatchId) {
        this.dispatchId = dispatchId;
    }
}
