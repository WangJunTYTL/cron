package com.peaceful.cron.server.dataobj;

import com.peaceful.cron.server.modal.JobStatus;

import java.sql.Timestamp;

/**
 * Created by Juna on 2018/5/6.
 */
public class CronJobSearch {

    private String name;
    private JobStatus status;
    private Timestamp nextExecutionTimeStart;
    private Timestamp nextExecutionTimeEnd;
    private int pageNo;
    private int pageSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Timestamp getNextExecutionTimeStart() {
        return nextExecutionTimeStart;
    }

    public void setNextExecutionTimeStart(Timestamp nextExecutionTimeStart) {
        this.nextExecutionTimeStart = nextExecutionTimeStart;
    }

    public Timestamp getNextExecutionTimeEnd() {
        return nextExecutionTimeEnd;
    }

    public void setNextExecutionTimeEnd(Timestamp nextExecutionTimeEnd) {
        this.nextExecutionTimeEnd = nextExecutionTimeEnd;
    }

    public int getPageNo() {
        if (pageNo <= 0) pageNo = 0;
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        if (pageSize <= 0) pageSize = 10;
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
