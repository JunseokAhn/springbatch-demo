package com.example.springbatchdemo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {

        log.info("beforeStep Instance : {}, ExecutionContext : {}, started at {}", stepExecution.getJobExecution().getJobInstance(), stepExecution.getExecutionContext().toString(), stepExecution.getStartTime());
        stepExecution.getExecutionContext().put("key", "stepValue");

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        log.info("afterStep Instance : {}, ExecutionContext : {}, started at {}", stepExecution.getJobExecution().getJobInstance(), stepExecution.getExecutionContext().toString(), stepExecution.getStartTime());
        return null;
    }
}