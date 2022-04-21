package com.skyflow.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Token {

    private String token;
    private String valueType;
    private String value;
    
    public String getToken() {
      return token;
    }
    public void setToken(String token) {
      this.token = token;
    }
    public String getValueType() {
      return valueType;
    }
    public void setValueType(String valueType) {
      this.valueType = valueType;
    }
    public String getValue() {
      return value;
    }
    public void setValue(String value) {
      this.value = value;
    }
  
}
