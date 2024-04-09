package com.emotion.emotiondesktop;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @BeforeAll
    public static void initJFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
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
    public void testUserLogIn_EmptyFields() {
        when(emailFieldMock.getText()).thenReturn("");
        when(passwordFieldMock.getText()).thenReturn("");

        loginController.userLogIn();

        verify(loginInfoMock).setText("Please fill in all fields");
    }

    @Test
    public void testUserLogIn_InvalidEmailFormat() {
        when(emailFieldMock.getText()).thenReturn("invalidemail");
        when(passwordFieldMock.getText()).thenReturn("password");

        loginController.userLogIn();

        verify(loginInfoMock).setText("Invalid email format");
    }
}
