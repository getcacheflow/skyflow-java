package com.skyflow.api.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecordTokenResponse {

  
  private List<Token> records;
  
  public List<Token> getRecords() {
    return records;
  }
  public void setRecords(List<Token> records) {
    this.records = records;
  }
  
  
  private SkyflowError error;

  public SkyflowError getError() {
    return error;
  }
  public void setError(SkyflowError error) {
    this.error = error;
  }
  
  
  @JsonIgnore
  public Map<String,Object> toTokenMap() {
    Map<String,Object> tokenMap = new HashMap<>();
    records.stream().forEach((token) -> {
       tokenMap.put(token.getToken(), token.getValue());
    });    
    return tokenMap;
  }
  
}
