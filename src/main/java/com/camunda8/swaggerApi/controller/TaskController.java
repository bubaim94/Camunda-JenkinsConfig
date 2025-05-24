package com.camunda8.swaggerApi.controller;

import com.camunda8.swaggerApi.util.CamundaAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final CamundaAuthService camundaAuthService;

    public TaskController(CamundaAuthService camundaAuthService) {
        this.camundaAuthService = camundaAuthService;
    }

    @GetMapping("/tasklist")
    public ResponseEntity<String> getTaskList() {
        try {
            // Get headers with Authorization token
            String url = "http://localhost:8080/v1/tasks/search";

            HttpHeaders headers = camundaAuthService.getTaskListToken(); // includes Bearer token
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, Object> requestBody = new HashMap<>();
            // requestBody.put("state", "CREATED");
            // requestBody.put("pageSize", 10);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );


            // Parse and pretty-print JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response.getBody());
            System.out.println("Parsed JSON: " + json.toPrettyString());

            return ResponseEntity.ok(json.toPrettyString());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch or parse task list: " + e.getMessage());
        }
    }
   @GetMapping("tasks/{taskId}")
    public ResponseEntity<String> getTaskById(@PathVariable("taskid")  String taskId) {
        try {
            System.out.println("task id value " +taskId);
            String url = "http://localhost:8080/v1/tasks/" +taskId;
            HttpHeaders headers = camundaAuthService.getTaskListToken();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Void> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class

            );
            // Parse and pretty-print JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response.getBody());
            System.out.println("Task JSON: " + json.toPrettyString());

            return ResponseEntity.ok(json.toPrettyString());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch task by ID: " + e.getMessage());
        }
    }
}



