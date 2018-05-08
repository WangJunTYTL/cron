package com.peaceful.cron.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jun on 2018/5/5.
 */
@Service
public class CronEngineStart {

    public static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private CronService cronService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public CronEngineStart() {
        scheduledExecutorService.scheduleAtFixedRate(new Dispatcher(), 1, 1, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(new ResetTimeoutJob(), 1, 1, TimeUnit.MINUTES);
        logger.info("cron engine start");
    }


    class Dispatcher implements Runnable {

        @Override
        public void run() {
            try {
                cronService.startDispatch();
            } catch (Exception e) {
                logger.error("Cron Dispatcher Error", e);
            }
        }
    }

    class ResetTimeoutJob implements Runnable {

        @Override
        public void run() {
            try {
                cronService.restart();
            } catch (Exception e) {
                logger.error("Cron Dispatcher Error", e);
            }
        }
    }


}
