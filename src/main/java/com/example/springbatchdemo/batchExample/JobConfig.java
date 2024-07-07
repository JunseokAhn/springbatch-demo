package com.example.springbatchdemo.batchExample;

import com.example.springbatchdemo.chunkExample.StepListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Component("BatchJobConfig")
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    private final JobListener jobListener;

    private final StepListener stepListener;

    @Bean("batchJob")
    public Job batchJob() {
        return new JobBuilder("batchJob", jobRepository)
                .listener(jobListener)
                .start(firstStep())
                .next(secondStep()).build();
    }

    private Step firstStep() {
        return new StepBuilder("startStep", jobRepository)
                .listener(stepListener)
                .tasklet((contribution, chunkContext) -> {
                    log.info("first tasklet step");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }


    private Step secondStep() {
        return new StepBuilder("nextStep", jobRepository)
                .listener(stepListener)
                .tasklet((contribution, chunkContext) -> {
                    log.info("second tasklet step");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

}
