package com.peaceful.cron.server.modal;

import com.peaceful.cron.server.service.CronService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jun on 2018/5/6.
 */
@Service
public class CronDispatchService {

    @Autowired
    private CronService cronService;
}
