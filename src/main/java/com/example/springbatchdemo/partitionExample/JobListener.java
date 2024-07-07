package com.example.springbatchdemo.partitionExample;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component("PartitionJobListener")
public class JobListener implements JobExecutionListener {

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            int totalSum = jobExecution.getStepExecutions()
                    .stream()
                    .mapToInt(stepExecution -> stepExecution.getExecutionContext().getInt("partitionSum", 0))
                    .sum();
            System.out.println("Total Sum: " + totalSum);
        }
        else {
            throw new RuntimeException();
        }
    }
}