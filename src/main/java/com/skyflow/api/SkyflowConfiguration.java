package com.skyflow.api;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import com.fasterxml.jackson.annotation.JsonAlias;

public class SkyflowConfiguration {
  
  @JsonAlias({ "clientId", "clientID" })
  private String clientId;
  
  private String clientName;
  private String managementUrl;
  
  @JsonAlias({ "keyId", "keyID" })
  private String keyId;
  
  @JsonAlias({"privateKey"})
  private String privateKeyString;
  private String vaultId;
  private String vaultUrl;
  
  public String getManagementUrl() {
    return managementUrl;
  }
  public void setManagementUrl(String managementUrl) {
    this.managementUrl = managementUrl;
  }
  public String getVaultId() {
    return vaultId;
  }
  public void setVaultId(String vaultId) {
    this.vaultId = vaultId;
  }
  public String getVaultUrl() {
    return vaultUrl;
  }
  public void setVaultUrl(String vaultUrl) {
    this.vaultUrl = vaultUrl;
  }
  public String getClientId() {
    return clientId;
  }
  public void setClientId(String clientId) {
    this.clientId = clientId;
  }
  public String getClientName() {
    return clientName;
  }
  public void setClientName(String clientName) {
    this.clientName = clientName;
  }
  public String getTokenUri() {
    return managementUrl + "/v1/auth/sa/oauth/token";
  }
  
  public String getKeyId() {
    return keyId;
  }
  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }
  public String getPrivateKeyString() {
    return privateKeyString;
  }
  public void setPrivateKeyString(String privateKeyString) {
    this.privateKeyString = privateKeyString;
  }

  public int timeout() {
    return 1;
  }
  
  public TemporalUnit timeoutType() {
    return ChronoUnit.HOURS;
  }
  
  public String privKey() {
    return this.getPrivateKeyString();
  }
  
  public String keyId() {
    return this.keyId;
  }
  
  
  
}
