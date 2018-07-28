package com.peaceful.cron.server.util;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jun on 2018/5/19.
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
}
)
public class PrintSqlPlugin implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String sql = mappedStatement.getBoundSql(invocation.getArgs()[1]).getSql();
        Object result = invocation.proceed();
        logger.info("sql[" + replaceBlankLeaveOne(sql) + "] cost:[" + (System.currentTimeMillis() - start) + "ms]");
        return result;
    }

    public Object plugin(Object target) {
        target = Plugin.wrap(target, this); // Plugin 是mybatis内置的封装类
        return target;
    }

    public void setProperties(Properties properties) {

    }

    //去除所有空格，留下一个
    public static String replaceBlankLeaveOne(String str) {
        String s = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s{2,}|\t|\r|\n");
            Matcher m = p.matcher(str);
            s = m.replaceAll(" ");
        }
        return s;
    }

}
