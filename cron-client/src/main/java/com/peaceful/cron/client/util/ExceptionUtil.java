package com.peaceful.cron.client.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Jun on 2018/5/16.
 */
public class ExceptionUtil {

    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
