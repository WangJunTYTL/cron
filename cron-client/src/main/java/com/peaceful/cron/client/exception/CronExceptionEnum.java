package com.peaceful.cron.client.exception;

/**
 * Created by Jun on 2018/5/12.
 */
public enum CronExceptionEnum {

    ILLEGAL_PARAM(1, "param error!"),
    CRON_CLIENT_ERROR(500, "Cron Client Start Error"),
    CRON_SERVER_ERROR(501, "Cron Server Response Error!");

    private int code;
    private String message;

    CronExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
