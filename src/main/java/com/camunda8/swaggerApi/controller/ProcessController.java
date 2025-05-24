package com.camunda8.swaggerApi.controller;

import com.camunda8.swaggerApi.dto.UserFormRequest;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v2/process")
public class ProcessController {

    private final ZeebeClient zeebeClient;

    @Autowired
    public ProcessController(ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startProcess(@RequestBody Map<String, Object> variables) {
        try {
            zeebeClient.newCreateInstanceCommand()
                    .bpmnProcessId("Process_1lo5b1k")
                    .latestVersion()
                    .variables(variables)
                    .send()
                    .join();

            return ResponseEntity.ok("Process started successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to start process:" + e.getMessage());


        }
    }
    @PostMapping("/start-user-form")
    public ResponseEntity<String> startUserFormProcess(@RequestBody UserFormRequest request){
        zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId("Process_1lo5b1k")
                .latestVersion()
                .variables(Map.of(
                        "email",request.getEmail(),
                            "name",request.getName()
                ))
                .send()
                .join();
        return ResponseEntity.ok("user form process started");

    }
}
