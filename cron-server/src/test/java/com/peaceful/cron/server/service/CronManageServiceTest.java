package com.peaceful.cron.server.service;

import com.peaceful.cron.server.dataobj.CreateJobRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Jun on 2018/5/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CronManageServiceTest {

    @Autowired
    private CronManageService cronService;

    @Test
    public void createCronJob() {
        for (int i= 0 ;i<100;i++){
            CreateJobRequest cronCreateJobDO = new CreateJobRequest();
            cronCreateJobDO.setName("abcd"+i);
            cronCreateJobDO.setCronExpression("0 0/1 * * * ?");
            cronService.createCronJob(cronCreateJobDO);
        }
    }

}