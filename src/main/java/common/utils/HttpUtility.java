package common.utils;

import errors.ErrorCodesEnum;
import errors.SkyflowException;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpUtility {

    public static String sendRequest(String method, String requestUrl, JSONObject params) throws IOException, SkyflowException {
        HttpURLConnection connection = null;
        BufferedReader in = null;
        StringBuffer response = null;

        try {
            URL url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json");

            if (params != null && params.size() > 0) {
                connection.setDoOutput(true);
                try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                    byte[] input = params.toString().getBytes(StandardCharsets.UTF_8);
                    wr.write(input, 0, input.length);
                    wr.flush();
                }
            }

            int status = connection.getResponseCode();

            Reader streamReader;
            if (status > 299) {
                streamReader = new InputStreamReader(connection.getErrorStream());
            } else {
                streamReader = new InputStreamReader(connection.getInputStream());
            }

            response = new StringBuffer();
            in = new BufferedReader(streamReader);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            if (status > 299) {
                throw new SkyflowException(ErrorCodesEnum.Server, response.toString());
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response.toString();
    }

}
