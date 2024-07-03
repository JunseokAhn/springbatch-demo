package com.example.springbatchdemo;

import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
public class LaunchController {

    @Autowired
    private JobOperator jobOperator;
    @GetMapping(value = "/job")
    public void handle(@RequestParam String batchJobName) throws Exception {

        String name = "Job_run:" + System.currentTimeMillis();
        Properties props = new Properties();
        props.put("jobName", name);
        jobOperator.start(batchJobName, props);
    }
}
