package com.example;

import java.util.HashMap;
import java.util.Map;
import com.skyflow.api.SkyflowClient;
import com.skyflow.api.SkyflowConfiguration;
import com.skyflow.api.entities.RecordInsertRequest;
import com.skyflow.api.entities.RecordInsertResponse;
import com.skyflow.api.entities.SkyflowRecord;
import com.skyflow.api.util.SkyflowException;
import com.sun.tools.javac.util.List;


public class InsertExample {

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
          
          SkyflowRecord record = new SkyflowRecord();
          Map<String,Object> fields = new HashMap<String,Object>();
          fields.put("first_name", "name ");
          record.setFields(fields);
          
          RecordInsertRequest request = new RecordInsertRequest();
          request.setRecords(List.of(record));
          request.setTokenization(true);
          
          RecordInsertResponse response  = client.insert("<table>", request);
          
          
          
//          
//            SkyflowConfiguration config = new SkyflowConfiguration("<your_vaultID>",
//                    "<your_vaultURL>", new DemoTokenProvider());
//            Skyflow skyflowClient = Skyflow.init(config);
//            JSONObject records = new JSONObject();
//            JSONArray recordsArray = new JSONArray();
//
//            JSONObject record = new JSONObject();
//            record.put("table", "<your_table_name>");
//
//            JSONObject fields = new JSONObject();
//            fields.put("first_name", "<your_field_name>");
//            record.put("fields", fields);
//            recordsArray.add(record);
//            records.put("records", recordsArray);
//
//            InsertOptions insertOptions = new InsertOptions();
//            JSONObject res = skyflowClient.insert(records, insertOptions);

            System.out.println(response.getRecords());
        } catch (SkyflowException e) {
            e.printStackTrace();
        }

    }

}
