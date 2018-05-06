package com.peaceful.cron.server.exception;

/**
 * Created by wangjun38 on 2018/5/5.
 */
public class CronServerException extends RuntimeException {
    private int code;
    private String message;

    public CronServerException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public CronServerException(CronResponseCode code, String message) {
        super(message);
        this.code = code.getCode();
        this.message = message;
    }

    public CronServerException(CronResponseCode code) {
        super(code.getMessage());
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
