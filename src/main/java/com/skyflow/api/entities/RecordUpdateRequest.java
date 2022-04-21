package com.skyflow.api.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecordUpdateRequest {
  
  @JsonIgnore
  SkyflowRecord record;

  public SkyflowRecord getRecord() {
    return record;
  }

  public void setRecord(SkyflowRecord record) {
    this.record = record;
  }
  
  
  
  
}
