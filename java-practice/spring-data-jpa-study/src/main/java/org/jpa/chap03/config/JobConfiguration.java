package org.jpa.chap03.config;

import org.jpa.chap03.listener.HelloListener;
import org.jpa.chap03.schedule.HelloJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class JobConfiguration {

    @Bean
    public JobDetail helloJobDetail() {
        return JobBuilder.newJob(HelloJob.class)
            .withIdentity("HelloJob")
            .storeDurably()
            .build();
    }

    @Bean
    public Trigger helloJobTrigger(JobDetail helloJobDetail) {
        return TriggerBuilder.newTrigger()
            .forJob(helloJobDetail)
            .withIdentity("HelloJobTrigger")
            .withSchedule(
                CronScheduleBuilder
                    .cronSchedule("*/5 * * * * ?")
            )
            .build();
    }

//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean(HelloListener helloListener) {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setGlobalJobListeners(helloListener);
//        return factory;
//    }
}