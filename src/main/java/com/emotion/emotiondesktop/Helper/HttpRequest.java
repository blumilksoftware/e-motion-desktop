package com.emotion.emotiondesktop.Helper;

import com.emotion.emotiondesktop.Controller.LoginController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
    public static HttpResponse sendHttpRequest(String apiUrl, String method, String jsonInputString) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Authorization", "Bearer " + LoginController.getAccessToken());
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        if (jsonInputString != null) {
            OutputStream os = connection.getOutputStream();
            os.write(jsonInputString.getBytes());
            os.flush();
            os.close();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return new HttpResponse(response.toString(), responseCode);
        } else {
            throw new Exception("HTTP request failed with response code: " + responseCode);
        }
    }
}
