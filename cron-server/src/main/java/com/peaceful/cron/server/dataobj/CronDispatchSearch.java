package com.peaceful.cron.server.dataobj;

import com.peaceful.cron.server.modal.DispatchStatus;

import java.sql.Timestamp;

/**
 * Created by Jun on 2018/5/9.
 */
public class CronDispatchSearch {

    private String name;
    private DispatchStatus status;
    private Timestamp dispatchTimeStart;
    private Timestamp dispatchTimeEnd;
    private Timestamp completeTimeStart;
    private Timestamp completeTimeEnd;
    private int pageNum;
    private int pageSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DispatchStatus getStatus() {
        return status;
    }

    public void setStatus(DispatchStatus status) {
        this.status = status;
    }

    public Timestamp getDispatchTimeStart() {
        return dispatchTimeStart;
    }

    public void setDispatchTimeStart(Timestamp dispatchTimeStart) {
        this.dispatchTimeStart = dispatchTimeStart;
    }

    public Timestamp getDispatchTimeEnd() {
        return dispatchTimeEnd;
    }

    public void setDispatchTimeEnd(Timestamp dispatchTimeEnd) {
        this.dispatchTimeEnd = dispatchTimeEnd;
    }

    public Timestamp getCompleteTimeStart() {
        return completeTimeStart;
    }

    public void setCompleteTimeStart(Timestamp completeTimeStart) {
        this.completeTimeStart = completeTimeStart;
    }

    public Timestamp getCompleteTimeEnd() {
        return completeTimeEnd;
    }

    public void setCompleteTimeEnd(Timestamp completeTimeEnd) {
        this.completeTimeEnd = completeTimeEnd;
    }

    public int getPageNum() {
        if (pageNum <= 0) pageNum = 1;
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        if (pageSize <= 0) pageSize = 10;
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
