package com.example.springbatchdemo;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ChunkItemProcessor implements ItemProcessor<Integer, Integer> {

    int num = 0;

    @Override
    public Integer process(Integer item) throws Exception {
        return num = num + item;
    }

}
