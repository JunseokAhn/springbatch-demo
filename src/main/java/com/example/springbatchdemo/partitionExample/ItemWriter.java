package com.example.springbatchdemo.partitionExample;

import org.springframework.batch.item.*;
import org.springframework.stereotype.Component;

@Component
public class ItemWriter implements org.springframework.batch.item.ItemWriter<Integer>, ItemStream {

    private ExecutionContext executionContext;

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.executionContext = executionContext;
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        this.executionContext = executionContext;
    }

    @Override
    public void write(Chunk<? extends Integer> chunk) {
        int sum = chunk.getItems().stream().mapToInt(Integer::intValue).sum();
        int previousSum = executionContext.getInt("partitionSum", 0);
        executionContext.putInt("partitionSum", previousSum + sum);

        chunk.forEach(System.out::println);
        System.out.println("sum : " + sum);
    }
}
