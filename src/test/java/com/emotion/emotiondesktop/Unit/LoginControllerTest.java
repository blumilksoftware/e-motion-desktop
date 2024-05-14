package com.emotion.emotiondesktop.Unit;

import com.emotion.emotiondesktop.Controller.LoginController;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class LoginControllerTest {

    @BeforeAll
    public static void initJFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {
            });
        }
    }

    @InjectMocks
    LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserLogInWhenFieldsAreEmpty() {
        TextField emailField = mock(TextField.class);
        when(emailField.getText()).thenReturn("");

        loginController.setEmailField(emailField);
        loginController.setPasswordField(mock(PasswordField.class));
        loginController.setLoginInfo(mock(Label.class));

        loginController.userLogIn();

        verify(loginController.getLoginInfo()).setText("Please fill in all fields");
        verify(loginController.getLoginInfo()).setTextFill(javafx.scene.paint.Color.RED);
    }

    @Test
    void testUserLogInWhenInvalidEmailFormat() {
        TextField emailField = mock(TextField.class);
        PasswordField passwordField = mock(PasswordField.class);
        when(emailField.getText()).thenReturn("invalidemail");
        when(passwordField.getText()).thenReturn("password");

        loginController.setEmailField(emailField);
        loginController.setPasswordField(passwordField);
        loginController.setLoginInfo(mock(Label.class));

        loginController.userLogIn();

        verify(loginController.getLoginInfo()).setText("Invalid email format");
        verify(loginController.getLoginInfo()).setTextFill(javafx.scene.paint.Color.RED);
    }
}
