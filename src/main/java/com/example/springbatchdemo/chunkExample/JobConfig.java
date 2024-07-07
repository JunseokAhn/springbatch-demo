package com.example.springbatchdemo.chunkExample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Component("ChunkJobConfig")
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    private final ChunkListener chunkListener;

    private final ItemReader itemReader;

    private final ItemProcessor itemProcessor;

    private final ItemWriter itemWriter;

    @Bean("chunkJob")
    public Job chunkJob() {
        return new JobBuilder("chunkJob", jobRepository)
//                .listener(jobListener)
                .start(chunkStep())
                .build();
    }

    private Step chunkStep() {
        return new StepBuilder("chunkStep", jobRepository)
                .<Integer,Integer>chunk(2, transactionManager) //Reader관련 세팅 <요구객체, 반환객체>
                .reader(itemReader)
                .processor(itemProcessor) // reader에서 null반환할때까지 무한반복호출
                .writer(itemWriter)
                .listener(chunkListener)
                .build();


    }

}
