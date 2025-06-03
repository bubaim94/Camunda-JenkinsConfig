package com.raj.Camunda8_kafka.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @KafkaListener(topics = "Camunda-Kafka", groupId = "kafka-id")
    public void consume(String messageName) {
        System.out.println("Message received: " + messageName);
    }
}
