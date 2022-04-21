package com.skyflow.api;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.skyflow.api.entities.Authentication;
import com.skyflow.api.entities.IdResponse;
import com.skyflow.api.entities.RecordInsertRequest;
import com.skyflow.api.entities.RecordInsertResponse;
import com.skyflow.api.entities.RecordTokenRequest;
import com.skyflow.api.entities.RecordTokenResponse;
import com.skyflow.api.entities.RecordUpdateRequest;
import com.skyflow.api.entities.SkyflowError;
import com.skyflow.api.entities.SkyflowRecord;
import com.skyflow.api.util.ErrorCode;
import com.skyflow.api.util.JwtUtil;
import com.skyflow.api.util.SkyflowException;

/**
 * Entry point into making various skyflow client calls
 * 
 * 
 *
 */
public class SkyflowClient {
  
  private static final Logger logger = Logger.getLogger(SkyflowClient.class.getName()); 
  
  private SkyflowConfiguration config;
  private ObjectMapper mapper;
  private String accessToken;
  
  public SkyflowClient(SkyflowConfiguration config) {
    this.config = config;
  }
  

  private ObjectMapper getObjectMapper() {
    if(mapper != null) {
      return mapper;
    }
    mapper = new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false));
    return mapper;
  }
  
  

  
  /**
   * Creates a request builder with auth if needed
   */
  protected HttpRequest.Builder createBuilder(String url, boolean auth) throws SkyflowException {
    HttpRequest.Builder request =  HttpRequest.newBuilder()
        .header("Content-Type", "application/json")
        .uri(URI.create(url));
    
    if(auth) {
      request = request.header("Authorization", "Bearer " + getAccessToken());
    }
    return request;
  }
  
  /**
   * Sends request and parses the json response 
   */
  protected <R> R sendAndParse(HttpRequest.Builder request, Class<R> responseClass) throws SkyflowException {
   try {
     HttpClient client = HttpClient.newHttpClient();
     HttpResponse<String> response = client.send(request.build(), BodyHandlers.ofString());
     String responseBody = response.body();
    
     JsonParser parser = getObjectMapper().createParser(responseBody);
     return parser.readValueAs(responseClass);    
    } catch (Exception e) {
      throw new SkyflowException(ErrorCode.UnableToReadResponse, e);
    }
  }
  
  
  /**
   * Get to the url against the skyflow service
   * Calls getAccessToken for the token to use
   * 
   */
  protected <R> R get(String url, Class<R> responseClass) throws SkyflowException {    
    HttpRequest.Builder request = createBuilder(url, true).GET();
    return sendAndParse(request, responseClass);
  }
  
  
  /**
   * Post to the url against the skyflow service
   * Careful getAccessToken uses this and is used by this in the auth case
   * Make sure auth is true for all requests except getAccessToken
   * 
   */
  protected <T,R> R post(String url, T body, Class<R> responseClass, boolean auth) throws SkyflowException {
    StringWriter outStr = new StringWriter();
    try {
      getObjectMapper().writeValue(outStr, body);
    } catch (Exception e) {
      throw new SkyflowException(ErrorCode.InvalidJsonFormat, e);
    }
    
    HttpRequest.Builder request = createBuilder(url, auth)
        .POST(BodyPublishers.ofString(outStr.toString(), StandardCharsets.UTF_8));
    
    return sendAndParse(request, responseClass);
  }
  
  /**
   * PUT to the url against the skyflow service
   * Calls getAccessToken for the token to use
   * 
   */
  protected <T,R> R put(String url, T body, Class<R> responseClass) throws SkyflowException {
    StringWriter outStr = new StringWriter();
    try {
      getObjectMapper().writeValue(outStr, body);
    } catch (Exception e) {
      throw new SkyflowException(ErrorCode.InvalidJsonFormat, e);
    }
    
    HttpRequest.Builder request = createBuilder(url, true)
        .PUT(BodyPublishers.ofString(outStr.toString(), StandardCharsets.UTF_8));
    
    return sendAndParse(request, responseClass);
  }
  
  /**
   * Get access token for storing and retrieving values 
   */
  protected String getAccessToken() throws SkyflowException {
    if(accessToken != null) {
      return accessToken;        
    }
    
    String jwtToken = JwtUtil.getSignedJwt(config);
    
    Map<String, String> values = new HashMap<String,String>();
    values.put("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer");
    values.put("assertion", jwtToken);
    Authentication token = post(config.getTokenUri(), values, Authentication.class, false);
   
    SkyflowError error = token.getError();
    if(error != null) {
      throw new SkyflowException(ErrorCode.BearerThrownException, error);
    }
   
    return (accessToken = token.getAccessToken());      
  }
  
  protected String getVaultUrl() {
    return config.getVaultUrl() + "/v1/vaults/"+config.getVaultId();
  }

  
  /**
   * curl -i -X POST '$VAULT_URL/v1/vaults/$VAULT_ID/credit_cards' 
   * 
   * -d '{
        "tokenization": true,
        "records": [
            {
                "fields": {
                    "cardholder_name" : "John Doe",
                    "card_number" : "4111111111111111",
                    "exp_date" : "11",
                    "exp_year" : "2025"
                }
            }
        ]
      }'
   * 
   * 
   * Response:has tokens and the skyflow id
   * 
   * "records": [
       {
         "table": "persons",
         "skyflow_id": "9ec10cfd-3568-4c47-a8de-30432d4e3410",
         "tokens": {
          
           "card_number": "6200004684650005",
           "expiry_month": "e1a769a7-f542-4746-abfc-9b2f951d1e29",
           "expiry_year": "fa69a671-998a-4d38-a36f-58e837a1df85",
           "name_on_card": "3f172642-afcc-4d31-8fbb-3f8353a92ccd"
         }
       }
     ]
   * 
   */
  public RecordInsertResponse insert(String schema, RecordInsertRequest request) throws SkyflowException {
    String url = getVaultUrl() + "/" + schema;
    return this.post(url, request, RecordInsertResponse.class, true);
  }
  
  
  /**
   *  curl -i -X GET '$VAULT_URL/v1/vaults/$VAULT_ID/credit_cards/$SKYFLOW_ID?tokenization=true'
    {
      "fields": {
          "card_number": "8493-8940-9501-8968",
          "cardholder_name": "80ecd7d2-8d2a-496e-b749-4bb815c7a05b",
          "exp_month": "34cb211c-63c5-4b0f-905e-b30e9d0a1228",
          "exp_year": "d461dfeb-0e3d-4936-bdbf-a81c12ea99b7"
      }
    }
   */
  public SkyflowRecord getRecord(String schema, String id) throws SkyflowException {
    String url = getVaultUrl() + "/" + schema + "/" + id + "?tokenization=true";
    return this.get(url,SkyflowRecord.class);
  }
  

  /**
   * Updates a record based on the id
   */
  public IdResponse updateRecord(String schema, RecordUpdateRequest request) throws SkyflowException {
    String url =getVaultUrl() + "/" + schema + "/" + request.getRecord().getSkyflowId();
    return this.put(url, request, IdResponse.class);
  }
  
  
  /** 
   *  curl -i -X POST '$VAULT_URL/v1/vaults/$VAULT_ID/detokenize' \
   {
    "detokenizationParameters": [
        {
            "token": "80ecd7d2-8d2a-496e-b749-4bb815c7a05b"
        },
        {
            "token": "8493-8940-9501-8968"
        },
        {
            "token": "34cb211c-63c5-4b0f-905e-b30e9d0a1228"
        },
        {
            "token": "d461dfeb-0e3d-4936-bdbf-a81c12ea99b7"
        }
    ]
  }
  */
  public RecordTokenResponse detokenize(RecordTokenRequest req) throws SkyflowException {
    String url = getVaultUrl()+ "/detokenize";
    return this.post(url, req, RecordTokenResponse.class, true);
  }
 
  

}
