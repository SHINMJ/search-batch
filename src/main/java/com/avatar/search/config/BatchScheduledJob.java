package com.avatar.search.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public class BatchScheduledJob extends QuartzJobBean {

    private final Job batchJob;
    private final JobLauncher jobLauncher;

    @Override
    protected void executeInternal(JobExecutionContext context)  {
        try {
            jobLauncher.run(batchJob,
                    new JobParametersBuilder()
                            .addString(batchJob.getName(), LocalDateTime.now().toString()).toJobParameters()
            );
            log.info("successfully job \n\n");
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.error(e.getMessage());
        }

    }
}
