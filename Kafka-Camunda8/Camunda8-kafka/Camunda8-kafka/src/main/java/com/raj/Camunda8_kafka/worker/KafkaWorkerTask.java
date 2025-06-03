package com.raj.Camunda8_kafka.worker;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaWorkerTask {
    @Autowired
    ZeebeClient zeebeClient;

    @JobWorker(type = "kafka-service")
    public void kafkaServiceTask(){
        System.out.println("Sending the task");
    }

    @KafkaListener(topics = "Camunda-Kafka", groupId = "kafka-id")
     public void receiveMessageTask(String businessKey,String message ){
        System.out.println("checking live message sending");
   zeebeClient.newPublishMessageCommand()
           .messageName(message)
           .correlationKey(businessKey)
           .variables("{\"status\":\"received\"}")
           .send()
           .join();
}
}
