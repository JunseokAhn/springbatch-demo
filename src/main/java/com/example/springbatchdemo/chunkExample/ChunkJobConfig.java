package com.example.springbatchdemo.chunkExample;

import com.example.springbatchdemo.batchExample.JobListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
@Slf4j
@EnableBatchProcessing
public class ChunkJobConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private JobListener jobListener;

    @Autowired
    private ChunkListener chunkListener;

    @Autowired
    private ItemReader<?> itemReader;

    @Autowired
    private ItemProcessor itemProcessor;

    @Autowired
    private ItemWriter itemWriter;

    @Bean("chunkJob")
    public Job chunkJob() {
        return new JobBuilder("chunkJob", jobRepository)
                .listener(jobListener)
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
