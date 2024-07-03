/**
 * 
 */
package com.example.springbatchdemo.chunkExample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChunkListener implements org.springframework.batch.core.ChunkListener {

    @Override
    public void beforeChunk(ChunkContext context) {
        log.info("beforeChunk : {}, loop {}", context.getStepContext().getStepName(), context.getStepContext().getStepExecution().getReadCount());
    }

    @Override
    public void afterChunk(ChunkContext context) {
        log.info("afterChunk : {}, loop {}", context.getStepContext().getStepName(), context.getStepContext().getStepExecution().getReadCount());
    }
    @Override
    public void afterChunkError(ChunkContext context) {
    }
}