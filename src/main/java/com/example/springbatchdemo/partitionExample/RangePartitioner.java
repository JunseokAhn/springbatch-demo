package com.example.springbatchdemo.partitionExample;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class RangePartitioner implements Partitioner {
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> result = new HashMap<>();
        int range = 100 / gridSize;
        int start = 0;
        int end = range;

        for (int i = 1; i <= gridSize; i++) {
            ExecutionContext executionContext = new ExecutionContext();
            executionContext.putInt("minValue", start);
            executionContext.putInt("maxValue", end);
            executionContext.putInt("partition", i);
            result.put("partition" + i, executionContext);
            start = end + 1;
            end = start + range - 1;
        } // 1-10, 11-20, ... 91-100
        return result;
    }
}