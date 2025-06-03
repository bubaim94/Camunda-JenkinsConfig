package com.camunda8.swaggerApi.controller;

import com.camunda8.swaggerApi.dto.MessageRequest;
import com.camunda8.swaggerApi.worker.MessageTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.tracing.TracingProperties;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MessageController {
 @Autowired
 MessageTask messageTask;


    @PostMapping("/send")
    public String sendMesaage(@RequestBody MessageRequest messageRequest){
   messageTask.receiveMessage(messageRequest.getCorrelationKey(),messageRequest.getMessageName());
   return "Message sent successfully";
    }
}
