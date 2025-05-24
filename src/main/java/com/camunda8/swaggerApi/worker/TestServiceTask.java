package com.camunda8.swaggerApi.worker;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

@Component
public class TestServiceTask {

    @JobWorker(type="testServiceTask")
    public void testService(){
        System.out.println("Test service task");

    }
}
