package com.emotion.emotiondesktop;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button loginButton;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginInfo;

    public void userLogIn() {
        String email = emailField.getText();
        String password = passwordField.getText();
        if (email.equals("admin@mail.com") && password.equals("admin")) {
            loginInfo.setText("ok");
        } else if (email.isEmpty() || password.isEmpty()) {
            loginInfo.setText("Please fill in all fields");
        } else if (!email.contains("@") || !email.contains(".")) {
            loginInfo.setText("Invalid email");
        }
        else {
            loginInfo.setText("Wrong email or password");
        }
    }
}
