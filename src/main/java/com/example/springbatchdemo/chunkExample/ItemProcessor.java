package com.example.springbatchdemo.chunkExample;

import org.springframework.stereotype.Component;

@Component("ChunkItemProcessor")
public class ItemProcessor implements org.springframework.batch.item.ItemProcessor<Integer, Integer> {

    int num = 0;

    @Override
    public Integer process(Integer item) throws Exception {
        return num = num + item;
    }

}
