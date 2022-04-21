package com.skyflow.api.entities;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecordTokenRequest {

  List<Token> detokenizationParameters;

  public List<Token> getDetokenizationParameters() {
    return detokenizationParameters;
  }

  public void setDetokenizationParameters(List<Token> detokenizationParameters) {
    this.detokenizationParameters = detokenizationParameters;
  }
  
  
  public static RecordTokenRequest fromRecord(SkyflowRecord record ) {
    List<Token> tokens = record.getFields().values().stream().map( 
        (str) -> {
          Token token = new Token();
          token.setToken((String)str);
          return token;
        }
    ).toList();
    
    RecordTokenRequest request = new RecordTokenRequest();
    request.detokenizationParameters = tokens;
    return request;
    
  }
  
}
