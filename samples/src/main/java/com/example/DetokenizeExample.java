package com.example;

import java.util.HashMap;
import java.util.Map;
import com.skyflow.api.SkyflowClient;
import com.skyflow.api.SkyflowConfiguration;
import com.skyflow.api.entities.RecordTokenRequest;
import com.skyflow.api.entities.RecordTokenResponse;
import com.skyflow.api.entities.SkyflowRecord;
import com.skyflow.api.entities.Token;
import com.skyflow.api.util.SkyflowException;
import com.sun.tools.javac.util.List;
import io.jsonwebtoken.lang.Collections;

public class DetokenizeExample {

    public static void main(String[] args) {

        try {
            SkyflowConfiguration config = new SkyflowConfiguration();
            config.setClientId("<your clientid>");
            config.setClientName("<client name>");
            config.setVaultId("<vault id>");
            config.setVaultUrl("<vault url>");
            config.setPrivateKeyString("<key string from config>");
            config.setManagementUrl("<management url>");
          
            SkyflowClient client = new SkyflowClient(config);
            
            RecordTokenRequest request = new RecordTokenRequest();
            Token token = new Token();
            token.setToken("<your_token>");
            request.setDetokenizationParameters(List.of( token ));
            

             RecordTokenResponse response = client.detokenize(request);
             
            System.out.println(response);
        } catch (SkyflowException e) {
            e.printStackTrace();
            System.out.println(e.getError());
        }

    }

}
