package com.raj.Camunda8_kafka.controller;

import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/api/v1/")
public class StartProcessController {
   @Autowired
    ZeebeClient zeebeClient;
    @PostMapping("/start")
    public ResponseEntity<String> startProcess(@RequestBody Map<String, Object> variables) {
        try {
            zeebeClient.newCreateInstanceCommand()
                    .bpmnProcessId("Kafka-Message-Task")
                    .latestVersion()
                    .variables(variables)
                    .send()
                    .join();

            return ResponseEntity.ok("Process started successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to start process:" + e.getMessage());


        }
    }
}
