/**
 *
 */
package com.example.springbatchdemo;

import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.stereotype.Component;

@Component
public class ChunkItemReader<T> extends AbstractItemStreamItemReader<Integer> {

    static int num = 0;

    @Override
    public Integer read() {
        if (num < 10)
            return ++num;
        else
            return null;
    }

}
