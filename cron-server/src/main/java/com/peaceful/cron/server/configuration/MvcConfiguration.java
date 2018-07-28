package com.peaceful.cron.server.configuration;

import com.peaceful.cron.server.util.MDCForTraceUtil;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by Jun on 2018/5/20.
 */
@Configuration
public class MvcConfiguration {

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TraceFilter());//添加过滤器
        registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
//        registration.addInitParameter("name", "alue");//添加默认参数
        registration.setName("TraceFilter");//设置优先级
        registration.setOrder(1);//设置优先级
        return registration;
    }

    class TraceFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            MDCForTraceUtil.put();
            filterChain.doFilter(servletRequest,servletResponse);
            MDCForTraceUtil.clear();
        }

        @Override
        public void destroy() {

        }
    }

}
