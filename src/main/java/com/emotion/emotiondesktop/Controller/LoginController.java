package com.emotion.emotiondesktop.Controller;

import com.emotion.emotiondesktop.EmotionApplication;
import com.emotion.emotiondesktop.Helper.HttpRequest;
import com.emotion.emotiondesktop.Helper.HttpResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;

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
            loginInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        } else if (!email.contains("@") || !email.contains(".")) {
            loginInfo.setText("Invalid email format");
            loginInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/login";
            HttpResponse httpResponse = HttpRequest.sendHttpRequest(apiUrl, "POST", "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}");

            JSONObject jsonResponse = new JSONObject(httpResponse.getResponseBody());

            accessToken = jsonResponse.getString("access_token");
            JSONArray roleArray = jsonResponse.getJSONArray("abilities");

            for (int i = 0; i < roleArray.length(); i++) {
                String currentValue = roleArray.getString(i);
                if (currentValue.equals("HasAdminRole")) {
                    isAdmin = true;
                    break;
                }
            }

            int responseCode = httpResponse.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK && isAdmin) {
                loginInfo.setText("Login successful");
                loginInfo.setTextFill(javafx.scene.paint.Color.GREEN);
                EmotionApplication.showCitiesCrudView();
            }

        } catch (Exception e) {
            e.printStackTrace();
            loginInfo.setText("Invalid email or password.");
            loginInfo.setTextFill(javafx.scene.paint.Color.RED);
        }
    }
}
