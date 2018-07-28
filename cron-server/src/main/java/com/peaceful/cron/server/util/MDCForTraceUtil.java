package com.peaceful.cron.server.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;

import java.util.Random;

/**
 * Created by Jun on 2018/5/20.
 */
public class MDCForTraceUtil {

    private static final String TRACE_KEY = "cronTraceId";

    public static void put() {
        if (StringUtils.isBlank(MDC.get(TRACE_KEY))) {
            MDC.put(TRACE_KEY, System.currentTimeMillis() + "-" + Thread.currentThread().getId() + "-" + randomNumStr(6));
        }
    }

    public static void clear(){
        MDC.remove(TRACE_KEY);
    }

    private static String randomNumStr(int length) {
        Random random = new Random();
        String str = "";
        for (int i = 0; i < length; i++) {
            str += random.nextInt(10);
        }
        return str;
    }
}
