package com.common.camunda.dto;

import lombok.Data;

@Data
public class AuthTokenPayload {

  private String client_id;
  private String client_secret;
  private String audience = "tasklist.com.common.camunda.io";
  private String grant_type = "client_credentials";
}
