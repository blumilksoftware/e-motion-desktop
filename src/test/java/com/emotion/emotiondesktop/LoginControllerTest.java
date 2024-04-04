package com.emotion.emotiondesktop;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeEach;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    @BeforeAll
    public static void initJFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }

    private LoginController loginController;
    private TextField emailField;
    private PasswordField passwordField;
    private Label loginInfo;

    @BeforeEach
    void setUp() {
        loginController = new LoginController();

        emailField = new TextField();
        passwordField = new PasswordField();
        loginInfo = new Label();

        loginController.setEmailField(emailField);
        loginController.setPasswordField(passwordField);
        loginController.setLoginInfo(loginInfo);
    }

    @Test
    void testUserLogIn_ValidCredentials() {
        emailField.setText("admin@mail.com");
        passwordField.setText("admin");
        loginController.userLogIn();
        assertEquals("ok", loginInfo.getText());
    }

    @Test
    void testUserLogIn_EmptyFields() {
        emailField.setText("");
        passwordField.setText("");
        loginController.userLogIn();
        assertEquals("Please fill in all fields", loginInfo.getText());
    }

    @Test
    void testUserLogIn_InvalidEmail() {
        emailField.setText("invalid_email");
        passwordField.setText("password");
        loginController.userLogIn();
        assertEquals("Invalid email", loginInfo.getText());
    }

    @Test
    void testUserLogIn_WrongCredentials() {
        emailField.setText("admin@mail.com");
        passwordField.setText("wrong_password");
        loginController.userLogIn();
        assertEquals("Wrong email or password", loginInfo.getText());
    }
}
