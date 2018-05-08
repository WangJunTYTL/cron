package com.peaceful.cron.server.util;


import com.peaceful.cron.server.exception.CronResponseCode;
import com.peaceful.cron.server.exception.CronServerException;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.Map;

/**
 * Created by Juna on 2018/4/12.
 */
public class PageResult {

    private static Logger logger = LoggerFactory.getLogger(PageResult.class);

    static {
        Velocity.setProperty(org.apache.velocity.runtime.RuntimeConstants.RESOURCE_LOADER, "classpath");
        Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        Velocity.init();
    }


    public static String render(String template, Map<String, Object> data) {
        VelocityContext context = new VelocityContext();
        if (data != null) {
            data.forEach((k, v) -> context.put(k, v));
        }
        StringWriter writer = new StringWriter();
        Velocity.mergeTemplate(template, "UTF-8", context, writer);
        return writer.toString();
    }

    public static String render(String template) {
        return render(template, null);
    }

    public static String FAIL(Exception e) {
        if (e instanceof CronServerException) {
            CronServerException exception = (CronServerException) e;
            return "code:" + exception.getCode() + "\nmessage:" + e.getMessage();
        }
        logger.error("系统错误", e);
        return CronResponseCode.SYSTEM_ERROR.getMessage();
    }
}
