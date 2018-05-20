package com.peaceful.cron.server;

/**
 * Created by Jun on 2018/5/4.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:/spring/applicationDataSource.xml")
public class StartCronServer {
    public static void main(String[] args) {
        SpringApplication.run(StartCronServer.class, args);
    }


}
