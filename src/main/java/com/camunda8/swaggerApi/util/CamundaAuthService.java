package com.camunda8.swaggerApi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Component
public class CamundaAuthService {
    @Autowired
    private RestTemplate restTemplate;

    private HttpHeaders getLocalToken(String url) {
        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        HttpHeaders headers = response.getHeaders();
        List<String> cookies = headers.get("Set-Cookie");
        System.out.println("cookes value" +cookies);
        HttpHeaders authHeaders = new HttpHeaders();
        if (cookies != null) {
            for (String cookie : cookies) {
                authHeaders.add("Cookie", cookie.split(";", 2)[0]);
            }
        }
        return authHeaders;
    }

    public HttpHeaders getTaskListToken() {
        String url = "http://localhost:8080/api/login?username=demo&password=demo";

        return getLocalToken(url);
    }

    public HttpHeaders getOperateToken() {
        String url = "http://localhost:8080/api/login?username=demo&password=demo";
        return getLocalToken(url);
    }
}
