package com.avatar.search.config;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class SchedulerConfig {

    @Value("${batch.schedule-cron}")
    private String cron;

    @Bean
    public JobDetail jobDetail(){
        return JobBuilder
                .newJob(BatchScheduledJob.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger jobTrigger(){
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                .cronSchedule(cron)
                .inTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        return TriggerBuilder
                .newTrigger()
                .forJob(jobDetail())
                .withSchedule(scheduleBuilder)
                .build();
    }

}
