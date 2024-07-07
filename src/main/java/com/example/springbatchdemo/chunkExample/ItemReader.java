/**
 *
 */
package com.example.springbatchdemo.chunkExample;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.stereotype.Component;

@Component("ChunkItemReader")
public class ItemReader<T> implements org.springframework.batch.item.ItemReader<Integer>, ItemStream {

    int num = 0;

    @Override
    public Integer read() {
        if (num < 10)
            return ++num;
        else
            return null;
    }

}
