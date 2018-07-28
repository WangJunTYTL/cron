package com.peaceful.cron.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peaceful.cron.server.dataobj.CreateJobRequest;
import com.peaceful.cron.server.dataobj.CronDispatchSearch;
import com.peaceful.cron.server.dataobj.CronJobSearch;
import com.peaceful.cron.server.dataobj.ServiceRegistryRequest;
import com.peaceful.cron.server.exception.CronResponseCode;
import com.peaceful.cron.server.exception.CronServerException;
import com.peaceful.cron.server.modal.CronDispatch;
import com.peaceful.cron.server.modal.ServiceRegistry;
import com.peaceful.cron.server.modal.mapper.CronDispatchMapper;
import com.peaceful.cron.server.modal.CronJob;
import com.peaceful.cron.server.modal.mapper.CronJobMapper;
import com.peaceful.cron.server.modal.mapper.ServiceRegistryMapper;
import com.peaceful.cron.server.service.CronManageService;
import com.peaceful.cron.server.service.ServerACK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jun on 2018/5/5.
 */
@Service
public class CronManageServiceImpl implements CronManageService {

    @Autowired
    private CronJobMapper cronJobMapper;

    @Autowired
    private CronDispatchMapper cronDispatchMapper;

    @Autowired

    private ServiceRegistryMapper serviceRegistryMapper;

    @Autowired
    private ServerACK ack;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void createCronJob(CreateJobRequest newCronJobDO) {
        try {
            CronJob cronJob = newCronJobDO.buildRequest();
            ServiceRegistry serviceRegistry = serviceRegistryMapper.selectById(cronJob.getServiceId());
            if (serviceRegistry == null) {
                throw new CronServerException(CronResponseCode.SERVICE_NOT_EXIST, "服务标识[" + cronJob.getServiceId() + "]不存在");
            }
            cronJobMapper.insert(cronJob);
        } catch (DuplicateKeyException e) {
            throw new CronServerException(CronResponseCode.SUCCESS, "该任务之前已创建");
        }
    }

    public PageInfo<CronJob> searchCronJob(CronJobSearch search) {
        PageHelper.startPage(search.getPageNo(), search.getPageSize());
        List<CronJob> cronJobList = cronJobMapper.select(search);
        PageInfo<CronJob> pageInfo = new PageInfo<>(cronJobList);
        return pageInfo;
    }

    public PageInfo<CronDispatch> searchCronDispatchList(CronDispatchSearch search) {
        PageHelper.startPage(search.getPageNum(), search.getPageSize());
        List<CronDispatch> cronDispatchList = cronDispatchMapper.select(search);
        return new PageInfo<>(cronDispatchList);
    }

    @Override
    public void createServiceRegister(ServiceRegistryRequest request) {
        request.validate();
        ServiceRegistry exist = serviceRegistryMapper.selectByName(request.getName());
        if (exist == null) {
            serviceRegistryMapper.insert(request.buildNewServiceRegistry());
        }
    }

    @Override
    public List<ServiceRegistry> findAllService() {
        return serviceRegistryMapper.selectAll();
    }

    @Override
    public ServiceRegistry findServiceById(long id) {
        ServiceRegistry registry = serviceRegistryMapper.selectById(id);
        if (registry == null) {
            throw new CronServerException(CronResponseCode.SERVICE_NOT_EXIST);
        }
        return registry;
    }

}
