package com.skyflow.api.entities;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecordInsertResponse {
  
  List<SkyflowRecord> records;
  
  public List<SkyflowRecord> getRecords() {
    return records;
  }
  public void setRecords(List<SkyflowRecord> records) {
    this.records = records;
  }
  
  private SkyflowError error;

  public SkyflowError getError() {
    return error;
  }
  public void setError(SkyflowError error) {
    this.error = error;
  }

}

