package com.peaceful.cron.client.util;

import com.peaceful.cron.client.exception.CronException;
import com.peaceful.cron.client.exception.CronExceptionEnum;

/**
 * Created by Jun on 2018/5/16.
 */
public class IntegerUtil {

    public static Integer parseWithDefault(String value, int defaultValue) {
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        } else {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new CronException(CronExceptionEnum.ILLEGAL_PARAM, "value must is number format");
            }
        }
    }

    public static int parse(String value, String errorMsg) {
        if (StringUtils.isBlank(value)) {
            throw new CronException(CronExceptionEnum.ILLEGAL_PARAM, errorMsg);
        } else {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new CronException(CronExceptionEnum.ILLEGAL_PARAM, "value must is number format");
            }
        }
    }

    public static Integer parse(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        } else {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new CronException(CronExceptionEnum.ILLEGAL_PARAM, "value must is number format");
            }
        }
    }

}
