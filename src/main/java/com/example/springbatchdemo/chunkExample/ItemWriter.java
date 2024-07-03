package com.example.springbatchdemo.chunkExample;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ItemWriter<T> extends AbstractItemStreamItemWriter<T> {

    @Override
    public void write(Chunk<? extends T> chunk) throws Exception {

        chunk.forEach(System.out::println); // 1 3 // 6 10 // 15 21 // 28 36 // 45 55
    }
}
