package com.skyflow.api.entities;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 
 * 
 * {"skyflow_id":"1cea8d6f-3e5f-4f3f-a90d-6e9d6549ffb3",
 *  "tokens":{
 *      "card_expiration":"86ea34f1-a886-4ae8-aa68-0974ca870a56",
 *      "card_number":"716e24ad-84d3-4218-95e1-c5e40d7d349c"
 *     }
 *  }
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class SkyflowRecord {
  
    public static final String KEY_FIELD = "skyflow_id";
    
    private String table;
    
    @JsonAlias({ "skyflow_id", "skyflowId" })
    private String skyflowId;
    
    public String getTable() {
      return table;
    }

    private  Map<String,Object> tokens;
    
    public Map<String, Object> getTokens() {
      return tokens;
    }

    public void setTokens(Map<String, Object> tokens) {
      this.tokens = tokens;
    }

    private  Map<String,Object> fields;

    public void setTable(String table) {
      this.table = table;
    }

    public Map<String, Object> getFields() {
      return fields;
    }

    public void setFields(Map<String, Object> fields) {
      if(fields != null) {
        skyflowId = (String)fields.get(KEY_FIELD);
      }
      
      this.fields = fields;
    }

    public String getSkyflowId() {
      return skyflowId;
    }

    public void setSkyflowId(String skyflowId) {
      this.skyflowId = skyflowId;
    }

}
