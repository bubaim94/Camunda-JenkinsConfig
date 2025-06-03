package com.camunda8.swaggerApi.worker;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MessageTask {
    @Autowired
    private  final ZeebeClient zeebeClient;

    public MessageTask(ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    @JobWorker(type = "messageSendTask")
    public void messageSendTask(){
        System.out.println("sending message to receiver message");
    }

    public void receiveMessage(String correlationKey, String messageName){
        zeebeClient.newPublishMessageCommand()
                .messageName(messageName)
                .correlationKey(correlationKey)
                .variables("{\"status\": \"received\"}")
                .send()
                .join();
    }
}
