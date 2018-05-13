package com.peaceful.cron.server.service;

import com.peaceful.cron.server.dataobj.CronCreateJobDO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Jun on 2018/5/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CronServiceTest {

    @Autowired
    private CronService cronService;

    @Test
    public void createCronJob() {
        for (int i= 0 ;i<2000;i++){
            CronCreateJobDO cronCreateJobDO = new CronCreateJobDO();
            cronCreateJobDO.setName("abcd"+i);
            cronCreateJobDO.setCronExpression("0 0/1 * * * ?");
            cronService.createCronJob(cronCreateJobDO);
        }
    }
}