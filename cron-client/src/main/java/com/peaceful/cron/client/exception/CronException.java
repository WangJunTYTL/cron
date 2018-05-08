package com.peaceful.cron.client.exception;

/**
 * Created by Juna on 2018/5/6.
 */
public class CronException extends RuntimeException {

    public CronStatusCode code;
    public String message;

    public CronException(CronStatusCode code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }


}
