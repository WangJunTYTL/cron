package com.peaceful.cron.server.service;

import com.peaceful.cron.server.dataobj.CronJobSearch;
import com.peaceful.cron.server.dataobj.CronJobTO;
import com.peaceful.cron.server.dataobj.CronUpdateJobDO;
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
import java.util.concurrent.TimeUnit;


/**
 * Created by Jun on 2018/5/12.
 */
@Service
public class CronDispatchServiceImpl implements CronDispatchService {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CronJobMapper cronJobMapper;
    @Autowired
    private CronDispatchMapper cronDispatchMapper;
    @Autowired
    private ServerACK ack;

    @Override
    @Transactional
    public void planNextDispatch(CronJob cronJob) {
        logger.info("planNextDispatch:{}", cronJob.getName());
        // update lock
        try {
            CronJob lock = cronJobMapper.lock(cronJob.getId());
            if (lock.getNextExecutionTime() != null && lock.getNextExecutionTime().compareTo(new Timestamp(System.currentTimeMillis())) >= 0) {
                // ignore  可能已经调用成功了
                logger.debug("not need repeat plan next dispatch time!");
                return;
            }
            if (lock.getStatus() == JobStatus.INIT) {
                logger.info("plan next dispatch time start:{}", cronJob.getName());
                lock.setNextExecutionTime(CronUtil.getNextExecutionTime(lock.getCronExpression()));
                lock.setStatus(JobStatus.WAIT);

                CronDispatch cronDispatch = new CronDispatch();
                cronDispatch.setName(lock.getName());
                cronDispatch.setDispatchTime(lock.getNextExecutionTime());
                cronDispatch.setStatus(DispatchStatus.INIT);

                cronJobMapper.update(lock);
                cronDispatchMapper.insert(cronDispatch);
                logger.info("plan next dispatch time end:{}", cronJob.getName());
            } else {
                logger.warn("不允许更新Job信息:{}", cronJob.getName());
            }
        } catch (DuplicateKeyException e) {
            //ignore
            logger.warn("重复计算的作业:{}", cronJob.getName());
        }
    }

    @Override
    @Transactional
    public void sendDispatch(CronDispatch cronDispatch) {
        // update lock
        String logInfo = cronDispatch.getId() + "-" + cronDispatch.getName();
        logger.info("send dispatch start:{}", logInfo);
        CronDispatch lock = cronDispatchMapper.lock(cronDispatch.getId());
        if (lock.getStatus() == DispatchStatus.INIT) {
            lock.setDispatchTime(new Timestamp(System.currentTimeMillis()));
            lock.setStatus(DispatchStatus.START);

            ack.sendDispatch(lock);// 发送指令

            cronDispatchMapper.update(lock);

            CronJob cronJob = cronJobMapper.lockByName(cronDispatch.getName());
            if (cronJob != null) {
                cronJob.setStatus(JobStatus.START);
                cronJobMapper.update(cronJob);
                logger.info("send dispatch end:{}", logInfo);
            } else {
                logger.error("未发现对应的CronJob信息：{}", cronDispatch.getName());
            }
        } else {
            logger.warn("不允许更新Dispatch信息:{}", logInfo);
        }
    }

    @Override
    public void killDispatch() {

    }

    @Override
    @Transactional
    public void ackDispatch(CronJobTO cronJobTO) {
        String logInfo = cronJobTO.getDispatchId() + "-" + cronJobTO.getName();
        logger.info("ack handle start:{}", logInfo);
        CronDispatch lock = cronDispatchMapper.lock(cronJobTO.getDispatchId());
        DispatchStatus status = switchAckCode(cronJobTO.getCode());
        lock.setStatus(status);
        lock.setAckCode(cronJobTO.getCode());
        lock.setCompleteTime(new Timestamp(System.currentTimeMillis()));
        cronDispatchMapper.update(lock);

        CronJob cronJob = cronJobMapper.lockByName(cronJobTO.getName());
        if (cronJob != null) {
            cronJob.setStatus(JobStatus.INIT);
            cronJobMapper.update(cronJob);
        } else {
            logger.error("未发现对应CronJob信息:{}", cronJobTO.getName());
        }

        logger.info("ack handle end:{}", logInfo);
    }

    @Override
    @Transactional
    public void checkDispatch(CronJob cronJob) {
        // 将Job信息强制改为 INIT，进行下次调度
        // 要加锁，更新Job信息的地方都需要
        CronJobTO jobTO = ack.checkDispatch(cronJob.getName());
        if (jobTO == null || switchAckCode(jobTO.getCode()) != DispatchStatus.START) {
            CronJob lock = cronJobMapper.lock(cronJob.getId());
            if (lock.getNextExecutionTime() != null && lock.getNextExecutionTime().compareTo(new Timestamp(System.currentTimeMillis())) >= 0) {
                // ignore  可能已经调用成功了
                logger.debug("not need repeat plan next dispatch time!");
                return;
            }
            CronUpdateJobDO updateJobDO = new CronUpdateJobDO();
            updateJobDO.setId(cronJob.getId());
            updateJobDO.setJobStatus(JobStatus.INIT);
            updateJobDO.setNextExecutionTime(CronUtil.getNextExecutionTime(cronJob.getCronExpression()));
            cronJobMapper.update(updateJobDO.buildUpdateRequest());
            logger.warn("job:{} dispatch timeout，plan time:{}, reset suc", cronJob.getName(), lock.getNextExecutionTime());
        }
    }

    //    COMMAND_ERROR(101, "Job指令有误"),
    //    END(201, "执行完毕"),
    //    ERROR(500, "Job执行抛出异常"),
    //    NOT_EXIST(501, "本地未发现该Job"),
    //    INTERRUPTED(502, "任务被打断"),
    //    JOB_INSTANCE_ILLEGAL(504, "Job必须继承Runnable接口"),;
    private DispatchStatus switchAckCode(int ackCode) {
        if (ackCode == 101 || ackCode == 500 || ackCode == 501 || ackCode == 502 || ackCode == 504) {
            return DispatchStatus.DISPATCH_ERROR;
        } else if (ackCode == 200) {
            return DispatchStatus.START;
        } else if (ackCode == 201) {
            return DispatchStatus.JOB_SUCCESS;
        } else {
            return DispatchStatus.UNKNOWN;
        }
    }
}
