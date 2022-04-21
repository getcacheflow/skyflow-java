package com.skyflow.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SkyflowError {
  
  private Integer grpc_code;
  private Integer http_code;
  private String message;
  private String httpStatus;
  public Integer getGrpc_code() {
    return grpc_code;
  }
  public void setGrpc_code(Integer grpc_code) {
    this.grpc_code = grpc_code;
  }
  public Integer getHttp_code() {
    return http_code;
  }
  public void setHttp_code(Integer http_code) {
    this.http_code = http_code;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public String getHttpStatus() {
    return httpStatus;
  }
  public void setHttpStatus(String httpStatus) {
    this.httpStatus = httpStatus;
  }

}
