package com.emotion.emotiondesktop.Integration;

import com.emotion.emotiondesktop.LoginController;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

    @BeforeAll
    public static void initJFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {
            });
        }
    }

    @Mock
    private TextField emailFieldMock;
    @Mock
    private PasswordField passwordFieldMock;
    @Mock
    private Label loginInfoMock;

    private LoginController loginController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        loginController = new LoginController();
        loginController.setEmailField(emailFieldMock);
        loginController.setPasswordField(passwordFieldMock);
        loginController.setLoginInfo(loginInfoMock);
    }

    @Test
    public void testUserCanLogin() {
        when(emailFieldMock.getText()).thenReturn("admin@example.com");
        when(passwordFieldMock.getText()).thenReturn("password");

        loginController.userLogIn();

        verify(loginInfoMock).setText("Login successful");
    }

    @Test
    public void testUserLogInFailed() {
        when(emailFieldMock.getText()).thenReturn("admi1n@example.com");
        when(passwordFieldMock.getText()).thenReturn("password");

        loginController.userLogIn();

        verify(loginInfoMock).setText("Invalid email or password.");
    }
}
