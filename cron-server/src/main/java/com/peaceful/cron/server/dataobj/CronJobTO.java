package com.peaceful.cron.server.dataobj;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Jun on 2018/5/12.
 */
public class CronJobTO {

    private String name;
    private long dispatchId;
    private int code;
    private String message;


    public static CronJobTO buildCronJobTO(HttpServletRequest request) {
        CronJobTO cronJobTO = new CronJobTO();

        String dispatchIdStr = request.getParameter("dispatchId");
        if (StringUtils.isNotBlank(dispatchIdStr)) {
            cronJobTO.dispatchId = Long.valueOf(dispatchIdStr);
        }
        String name = request.getParameter("name");
        if (StringUtils.isNotBlank(name)) {
            cronJobTO.name = name;
        }
        String codeStr = request.getParameter("code");
        if (StringUtils.isNotBlank(codeStr)) {
            cronJobTO.code = Integer.parseInt(codeStr);
        }
        String message = request.getParameter("message");
        if (StringUtils.isNotBlank(message)) {
            cronJobTO.message = message;
        }
        return cronJobTO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(long dispatchId) {
        this.dispatchId = dispatchId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "[" +
                "name='" + name + '\'' +
                ", dispatchId=" + dispatchId +
                ", code=" + code +
                ", message='" + message + '\'' +
                ']';
    }
}
