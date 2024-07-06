package com.example.springbatchdemo.partitionExample;

import org.springframework.stereotype.Component;

@Component
public class ItemProcessor implements org.springframework.batch.item.ItemProcessor<Integer, Integer> {

    @Override
    public Integer process(Integer item) {
        return item;
    }
}
