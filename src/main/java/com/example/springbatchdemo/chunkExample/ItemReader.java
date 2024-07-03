/**
 *
 */
package com.example.springbatchdemo.chunkExample;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.stereotype.Component;

@Component
public class ItemReader<T> extends AbstractItemStreamItemReader<Integer> {

    static int num = 0;

    @Override
    public void open(ExecutionContext executionContext) {
        super.open(executionContext);
    }

    @Override
    public Integer read() {
        if (num < 10)
            return ++num;
        else
            return null;
    }

}
