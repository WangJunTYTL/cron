package com.peaceful.cron.client.util;

import com.peaceful.cron.client.exception.CronException;
import com.peaceful.cron.client.exception.CronExceptionEnum;

/**
 * Created by Jun on 2018/5/12.
 */
public class Preconditions {

    public static void checkArgument(boolean expression,String message){
        if (!expression) {
            throw new CronException(CronExceptionEnum.ILLEGAL_PARAM,message);
        }
    }
}
