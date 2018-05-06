package com.peaceful.cron.server.util;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by wangjun38 on 2018/5/5.
 */
public class CronUtil {

    /**
     * 时间精度:秒
     */
    public static Timestamp getNextExecutionTime(String cronExpression) {
        // Define your own cron: arbitrary fields are allowed and last field can be optional
        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);

        // Create a parser based on provided definition
        CronParser parser = new CronParser(cronDefinition);

        // Get date for last execution
        ZonedDateTime now = ZonedDateTime.now();
        // 每分钟执行一次
        ExecutionTime executionTime = ExecutionTime.forCron(parser.parse(cronExpression));
        ZonedDateTime time = executionTime.nextExecution(now).get();
        return new Timestamp(Date.from(time.toInstant()).getTime());
    }

}
