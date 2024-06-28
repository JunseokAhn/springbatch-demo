package com.example.springbatchdemo.config;

import com.example.springbatchdemo.ChunkItemProcessor;
import com.example.springbatchdemo.ChunkItemReader;
import com.example.springbatchdemo.ChunkItemWriter;
import com.example.springbatchdemo.listener.ChunkListener;
import com.example.springbatchdemo.listener.JobListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
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
    private ChunkItemReader<?> itemReader;

    @Autowired
    private ChunkItemProcessor itemProcessor;

    @Autowired
    private ChunkItemWriter itemWriter;

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
//                .taskExecutor(taskExecutor())
                .listener(chunkListener)
                .build();
    }

    private TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncExecutor = new SimpleAsyncTaskExecutor("multi-processing");
        asyncExecutor.setConcurrencyLimit(10); // 병렬처리 동시 10개
        return asyncExecutor;
    }
}
