package com.skyflow.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IdResponse {
  
  private String skyflowId;

  public String getSkyflowId() {
    return skyflowId;
  }

  public void setSkyflowId(String skyflowId) {
    this.skyflowId = skyflowId;
  }
  
  private SkyflowError error;

  public SkyflowError getError() {
    return error;
  }
  public void setError(SkyflowError error) {
    this.error = error;
  }

  
  
}
