package com.example;

import com.skyflow.api.SkyflowClient;
import com.skyflow.api.SkyflowConfiguration;
import com.skyflow.api.entities.SkyflowRecord;
import com.skyflow.api.util.SkyflowException;

public class GetByIdExample {

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
          SkyflowRecord record = client.getRecord("<your table name>", "id");
//          
//          
//            record.put("ids", ids);
//            record.put("table", "<you_table_name>");
//            record.put("redaction", RedactionType.PLAIN_TEXT.toString());
//            recordsArray.add(record);
//            records.put("records", recordsArray);
//
//            JSONObject response = skyflowClient.getById(records);
        } catch (SkyflowException e) {
            e.printStackTrace();
            System.out.println(e.getError());
        }

    }


}
