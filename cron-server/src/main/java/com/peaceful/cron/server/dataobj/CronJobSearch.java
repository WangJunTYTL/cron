package com.peaceful.cron.server.dataobj;

import com.peaceful.cron.server.modal.enums.JobStatus;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created by Jun on 2018/5/6.
 */
@Data
public class CronJobSearch {

    private long serviceId;
    private String name;
    private JobStatus status;
    private Timestamp nextExecutionTimeStart;
    private Timestamp nextExecutionTimeEnd;
    private int pageNo;
    private int pageSize;


    public int getPageNo() {
        if (pageNo <= 0) pageNo = 1;
        return pageNo;
    }


    public int getPageSize() {
        if (pageSize <= 0) pageSize = 10;
        return pageSize;
    }

}
