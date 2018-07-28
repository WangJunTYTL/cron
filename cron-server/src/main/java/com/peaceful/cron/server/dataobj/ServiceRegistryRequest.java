package com.peaceful.cron.server.dataobj;

import com.google.common.base.Preconditions;

import com.peaceful.cron.server.exception.CronResponseCode;
import com.peaceful.cron.server.exception.CronServerException;
import com.peaceful.cron.server.modal.ServiceRegistry;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

/**
 * Created by Jun on 2018/5/26.
 */
@Data
public class ServiceRegistryRequest {

    private String name;
    private String comment;

    public void validate() {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(name), "服务名称不可以为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(comment), "服务概述不可以为空");
        } catch (IllegalArgumentException e) {
            throw new CronServerException(CronResponseCode.ILLEGAL_ARGUMENT, e.getMessage());
        }
    }

    public ServiceRegistry buildNewServiceRegistry() {
        validate();
        ServiceRegistry registry = new ServiceRegistry();
        registry.setName(name);
        registry.setComment(comment);
        return registry;
    }
}
