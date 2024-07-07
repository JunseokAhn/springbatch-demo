package com.example.springbatchdemo.partitionExample;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.stereotype.Component;

@Component("PartitionItemReader")
public class ItemReader implements org.springframework.batch.item.ItemReader<Integer>, ItemStream {

    private int currentIndex;
    private int minValue;
    private int maxValue;

    @Override
    public Integer read() {
        //청크단위로 루프돌면서 값 반환
        if (currentIndex <= maxValue) {
            return currentIndex++;
        } else {
            return null;
        }
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        //RangePartitioner에서 설정한 값 load
        this.minValue = executionContext.getInt("minValue");
        this.maxValue = executionContext.getInt("maxValue");
        this.currentIndex = executionContext.getInt("currentIndex", this.minValue);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.putInt("currentIndex", this.currentIndex);
    }

}