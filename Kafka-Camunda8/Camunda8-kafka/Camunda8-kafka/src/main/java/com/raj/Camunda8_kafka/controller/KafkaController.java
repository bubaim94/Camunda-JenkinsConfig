package com.raj.Camunda8_kafka.controller;

import com.raj.Camunda8_kafka.dto.MessageRequest;
import com.raj.Camunda8_kafka.services.ProducerService;
import com.raj.Camunda8_kafka.worker.KafkaWorkerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
public class KafkaController {

    private final ProducerService producerService;
    private final KafkaWorkerTask kafkaWorkerTask;
    @Autowired
    public KafkaController(ProducerService producerService, KafkaWorkerTask kafkaWorkerTask) {
        this.producerService = producerService;
        this.kafkaWorkerTask = kafkaWorkerTask;
    }

    @PostMapping("/message")
    public String sendMessage(@RequestBody MessageRequest messageRequest) {
        kafkaWorkerTask.receiveMessageTask(messageRequest.getBusinessKey(), messageRequest.getMessage());
        return "Message received successfully";
    }
}
