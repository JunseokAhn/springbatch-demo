package com.example.springbatchdemo.partitionExample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Component
@EnableBatchProcessing
public class JobConfig {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private JobListener jobListener;

    @Autowired
    private ItemReader itemReader;

    @Autowired
    private ItemProcessor itemProcessor;

    @Autowired
    private ItemWriter itemWriter;

    @Bean("partitionJob")
    public Job partitionJob() {
        return new JobBuilder("partitionJob", jobRepository)
                .listener(jobListener)
                .start(partitionStep())
                .build();
    }

    private Step partitionStep() {
        return new StepBuilder("partitionStep", jobRepository)
                .partitioner(chunkStep().getName(), new RangePartitioner()) // chunkStep 파티셔닝 지정
                .partitionHandler(partitionHandler())
//                .listener(stepListener)
                .build();
    }


    private PartitionHandler partitionHandler() {
        TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
        handler.setStep(chunkStep());
        handler.setGridSize(10); // 10개 단위로 파티셔닝
//        handler.setTaskExecutor(new SimpleAsyncTaskExecutor()); // 비동기실행
        return handler;

    }

    private Step chunkStep() {
        return new StepBuilder("chunkStep", jobRepository)
                .<Integer,Integer>chunk(10, transactionManager) //Reader관련 세팅 <요구객체, 반환객체>
                .reader(itemReader)
                .processor(itemProcessor) // reader에서 null반환할때까지 무한반복호출
                .writer(itemWriter)
//                .listener(chunkListener)
                .build();
    }
}
