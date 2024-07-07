package com.example.springbatchdemo.batchExample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component("BatchJobListener")
public class JobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {

        log.info("beforeJob Instance : {}, ExecutionContext : {}, started at {}", jobExecution.getJobInstance(), jobExecution.getExecutionContext().toString(), jobExecution.getStartTime());
        jobExecution.getExecutionContext().put("key", "jobValue");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("afterJob Instance : {}, ExecutionContext : {}, started at {}", jobExecution.getJobInstance(), jobExecution.getExecutionContext().toString(), jobExecution.getStartTime());
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job Execution 성공");
        } else {
            log.error("Job Execution 실패");
        }
    }
}
