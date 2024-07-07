package com.example.springbatchdemo.chunkExample;

import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Component("ChunkItemWriter")
public class ItemWriter implements org.springframework.batch.item.ItemWriter<Integer> {

    @Override
    public void write(Chunk<? extends Integer> chunk) throws Exception {
        chunk.forEach(System.out::println); // 1 3 // 6 10 // 15 21 // 28 36 // 45 55
    }
}
