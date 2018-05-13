package com.peaceful.cron.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peaceful.cron.server.dataobj.CronCreateJobDO;
import com.peaceful.cron.server.dataobj.CronDispatchSearch;
import com.peaceful.cron.server.dataobj.CronJobSearch;
import com.peaceful.cron.server.exception.CronResponseCode;
import com.peaceful.cron.server.exception.CronServerException;
import com.peaceful.cron.server.modal.CronDispatch;
import com.peaceful.cron.server.modal.CronDispatchMapper;
import com.peaceful.cron.server.modal.CronJob;
import com.peaceful.cron.server.modal.CronJobMapper;

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
public class CronServiceImpl implements CronService {

    @Autowired
    private CronJobMapper cronJobMapper;

    @Autowired
    private CronDispatchMapper cronDispatchMapper;

    @Autowired
    private ServerACK ack;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void createCronJob(CronCreateJobDO newCronJobDO) {
        try {
            CronJob cronJob = newCronJobDO.buildInsertRequest();
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

}
