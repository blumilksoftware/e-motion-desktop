package com.emotion.emotiondesktop;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

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

    public void userLogIn() {
        String email = emailField.getText();
        String password = passwordField.getText();
        if ("admin@mail.com".equals(email) && "admin".equals(password)) {
            loginInfo.setText("ok");
        } else if (email.isEmpty() || password.isEmpty()) {
            loginInfo.setText("Please fill in all fields");
        } else if (!email.contains("@") || !email.contains(".")) {
            loginInfo.setText("Invalid email");
        } else {
            loginInfo.setText("Wrong email or password");
        }
    }
}
