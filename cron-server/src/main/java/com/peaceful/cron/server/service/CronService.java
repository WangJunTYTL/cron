package com.peaceful.cron.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peaceful.cron.server.dataobj.CronCreateJobDO;
import com.peaceful.cron.server.dataobj.CronDispatchSearch;
import com.peaceful.cron.server.dataobj.CronJobSearch;
import com.peaceful.cron.server.dataobj.CronUpdateDispatchDO;
import com.peaceful.cron.server.dataobj.CronUpdateJobDO;
import com.peaceful.cron.server.exception.CronResponseCode;
import com.peaceful.cron.server.exception.CronServerException;
import com.peaceful.cron.server.modal.CronDispatch;
import com.peaceful.cron.server.modal.CronDispatchMapper;
import com.peaceful.cron.server.modal.CronJob;
import com.peaceful.cron.server.modal.CronJobMapper;
import com.peaceful.cron.server.modal.DispatchStatus;
import com.peaceful.cron.server.modal.JobStatus;
import com.peaceful.cron.server.util.CronUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangjun38 on 2018/5/5.
 */
@Service
public class CronService {

    @Autowired
    private CronJobMapper cronJobMapper;

    @Autowired
    private CronDispatchMapper cronDispatchMapper;

    @Autowired
    private DispatchTransaction dispatchTransaction;

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

    public void restart() {
        CronJobSearch search = new CronJobSearch();
        search.setStatus(JobStatus.START);
        // 超时1分钟的任务，应该判断是否需要重新执行
        search.setNextExecutionTimeEnd(new Timestamp(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(1)));
        cronJobMapper.select(search).forEach(cronJob -> {
            // TODO 这里要去客户端判断是否需要重启，防止误杀
            // 将Job信息强制改为 INIT，进行下次调度
            CronUpdateJobDO updateJobDO = new CronUpdateJobDO();
            updateJobDO.setId(cronJob.getId());
            updateJobDO.setJobStatus(JobStatus.INIT);
            updateJobDO.setNextExecutionTime(CronUtil.getNextExecutionTime(cronJob.getCronExpression()));
            cronJobMapper.update(updateJobDO.buildUpdateRequest());
            logger.warn("job:{} dispatch timeout reset suc", cronJob.getName());

            // dispatch信息忽略

        });
    }

    public void startDispatch() {
        // 判断任务下次执行时间
        List<CronJob> cronJobList = cronJobMapper.selectListByStatus(JobStatus.INIT);
        cronJobList.forEach(cronJob -> {
            updateJobStatus2WAIT(cronJob);
        });

        // 对于应该到时的任务，发送执行指令
        CronDispatchSearch cronDispatch = new CronDispatchSearch();
        cronDispatch.setStatus(DispatchStatus.INIT);
        cronDispatch.setDispatchTimeEnd(new Timestamp(System.currentTimeMillis()));
        List<CronDispatch> cronDispatchList = cronDispatchMapper.select(cronDispatch);
        cronDispatchList.forEach(dispatch -> {
            // TODO 该块逻辑可以走队列服务或者并发处理，提高处理能力
            dispatchTransaction.send(dispatch);

            try {
                updateDispatchStatus2DISPATCH_SEND(dispatch);
            } catch (Exception e) {
                logger.error("cron dispatch error", e);
            }
        });
    }

    @Transactional
    void updateDispatchStatus2DISPATCH_SEND(CronDispatch cronDispatch) {
        // 更新Dispatch信息
        CronUpdateDispatchDO updateDispatchDO = new CronUpdateDispatchDO();
        updateDispatchDO.setId(cronDispatch.getId());
        updateDispatchDO.setStatus(DispatchStatus.DISPATCH_SEND);
        cronDispatchMapper.update(updateDispatchDO.buildUpdateRequest());

        // 更新Job信息
        CronJob cronJob = cronJobMapper.selectByName(cronDispatch.getName());
        CronUpdateJobDO updateJobDO = new CronUpdateJobDO();
        updateJobDO.setId(cronJob.getId());
        updateJobDO.setJobStatus(JobStatus.START);
        cronJobMapper.update(updateJobDO.buildUpdateRequest());

        logger.info("job:{} dispatch send suc", cronDispatch.getName());
    }

    @Transactional
    void updateJobStatus2WAIT(CronJob cronJob) {
        // 更新job信息
        CronUpdateJobDO updateJobDO = new CronUpdateJobDO();
        updateJobDO.setId(cronJob.getId());
        updateJobDO.setJobStatus(JobStatus.WAIT);
        updateJobDO.setNextExecutionTime(CronUtil.getNextExecutionTime(cronJob.getCronExpression()));
        cronJobMapper.update(updateJobDO.buildUpdateRequest());

        // 写入Dispatch指令信息
        CronDispatch cronDispatch = new CronDispatch();
        cronDispatch.setName(cronJob.getName());
        cronDispatch.setStatus(DispatchStatus.INIT);
        cronDispatch.setDispatchTime(updateJobDO.getNextExecutionTime());
        cronDispatchMapper.insert(cronDispatch);

        logger.info("job:{} next execute time:{}", cronJob.getName(), updateJobDO.getNextExecutionTime());
    }

    @Transactional
    void updateJobStatus2INIT(long dispatchId) {
        CronDispatch cronDispatch = cronDispatchMapper.selectById(dispatchId);
        CronJob cronJob = cronJobMapper.selectByName(cronDispatch.getName());

        // 更新job信息
        CronUpdateJobDO updateJobDO = new CronUpdateJobDO();
        updateJobDO.setId(cronJob.getId());
        updateJobDO.setJobStatus(JobStatus.INIT);
        cronJobMapper.update(updateJobDO.buildUpdateRequest());

        // 写入Dispatch指令信息 TODO
        CronUpdateDispatchDO updateDispatchDO = new CronUpdateDispatchDO();
        updateDispatchDO.setId(cronDispatch.getId());
        updateDispatchDO.setStatus(DispatchStatus.JOB_SUCCESS);
        updateDispatchDO.setCompleteTime(new Timestamp(System.currentTimeMillis()));
        cronDispatchMapper.update(updateDispatchDO.buildUpdateRequest());

        logger.info("job:{} execute suc", cronJob.getName());
    }

}
