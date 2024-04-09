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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                loginInfo.setText("Login successful");
            }

            connection.disconnect();

        } catch (Exception e) {
            loginInfo.setText("Failed to log in. Invalid email or password.");
        }
    }
}
