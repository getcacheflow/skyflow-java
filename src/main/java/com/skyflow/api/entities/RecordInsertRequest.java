package com.skyflow.api.entities;

import java.util.List;

public class RecordInsertRequest {

  List<SkyflowRecord> records;
  boolean tokenization = true;
  
  public List<SkyflowRecord> getRecords() {
    return records;
  }
  public void setRecords(List<SkyflowRecord> records) {
    this.records = records;
  }
  public boolean isTokenization() {
    return tokenization;
  }
  public void setTokenization(boolean tokenization) {
    this.tokenization = tokenization;
  }
  
  
}
