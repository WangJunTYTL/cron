package com.peaceful.cron.client.exception;

/**
 * Created by Jun on 2018/5/6.
 */
public class CronException extends RuntimeException {

    public int code;
    public String message;

    public CronException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public CronException(CronExceptionEnum exceptionEnum){
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    public CronException(CronExceptionEnum exceptionEnum,Exception e){
        super(exceptionEnum.getMessage(),e);
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    public CronException(CronExceptionEnum exceptionEnum,String message){
        super(message);
        this.code = exceptionEnum.getCode();
        this.message = message;
    }

    @Override
    public String toString() {
        return "[CronException]code:" + code + ", message:" + message + "";
    }


}
