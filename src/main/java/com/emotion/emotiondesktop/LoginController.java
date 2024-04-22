package com.emotion.emotiondesktop;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginController {

    @FXML
    private Button loginButton;
    @FXML
    @Setter
    private TextField emailField;
    @FXML
    @Setter
    private PasswordField passwordField;
    @FXML
    @Getter
    @Setter
    private Label loginInfo;
    @Setter
    private String apiUrl = "https://dev.escooters.blumilk.pl/api/login";
    @Getter
    private static String accessToken;
    @Setter
    @Getter
    private boolean isAdmin = false;

    public void userLogIn() {
        String email = emailField.getText();
        String password = passwordField.getText();
        if (email.isEmpty() || password.isEmpty()) {
            loginInfo.setText("Please fill in all fields");
            return;
        } else if (!email.contains("@") || !email.contains(".")) {
            loginInfo.setText("Invalid email format");
            return;
        }

        try {
            String jsonPayload = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";

            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject jsonResponse = getJsonObject(connection, jsonPayload);

            accessToken = jsonResponse.getString("access_token");
            JSONArray roleArray = jsonResponse.getJSONArray("0");

            for (int i = 0; i < roleArray.length(); i++) {
                String currentValue = roleArray.getString(i);
                if (currentValue.equals("HasAdminRole")) {
                    isAdmin = true;
                    break;
                }
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK && isAdmin) {
                loginInfo.setText("Login successful");
                EmotionApplication.showCitiesCrudView();
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            loginInfo.setText("Invalid email or password.");
        }
    }

    private static JSONObject getJsonObject(HttpURLConnection connection, String jsonPayload) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(jsonPayload);
        outputStream.flush();
        outputStream.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return new JSONObject(response.toString());
    }
}
