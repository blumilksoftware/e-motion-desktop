package com.emotion.emotiondesktop.Integration;

import com.emotion.emotiondesktop.Controller.LoginController;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Mock
    HttpURLConnection httpURLConnection;

    @Test
    void testUserLogIn_WhenConnectionFails() throws Exception {
        TextField emailField = mock(TextField.class);
        PasswordField passwordField = mock(PasswordField.class);
        HttpURLConnection connection = mock(HttpURLConnection.class);
        Label loginInfo = mock(Label.class);

        loginController.setEmailField(emailField);
        loginController.setPasswordField(passwordField);
        loginController.setLoginInfo(loginInfo);

        when(emailField.getText()).thenReturn("valid@example.com");
        when(passwordField.getText()).thenReturn("password");
        when(connection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);
        when(connection.getErrorStream()).thenReturn(new ByteArrayInputStream("Invalid credentials".getBytes()));

        when(httpURLConnection.getOutputStream()).thenReturn(mock(DataOutputStream.class));
        when(httpURLConnection.getInputStream()).thenReturn(mock(InputStream.class));

        URL url = mock(URL.class);
        when(url.openConnection()).thenReturn(connection);

        loginController.userLogIn();

        verify(loginInfo).setText("Invalid email or password.");
        verify(loginInfo).setTextFill(javafx.scene.paint.Color.RED);
    }

    @Test
    void testUserLogIn_WhenConnectionSucceeds() throws Exception {
        TextField emailField = mock(TextField.class);
        PasswordField passwordField = mock(PasswordField.class);
        HttpURLConnection connection = mock(HttpURLConnection.class);
        Label loginInfo = mock(Label.class);

        loginController.setEmailField(emailField);
        loginController.setPasswordField(passwordField);
        loginController.setLoginInfo(loginInfo);

        when(emailField.getText()).thenReturn("admin@example.com");
        when(passwordField.getText()).thenReturn("password");
        when(connection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(connection.getInputStream()).thenReturn(new ByteArrayInputStream("{\"access_token\": \"token\"}".getBytes()));

        when(httpURLConnection.getOutputStream()).thenReturn(mock(DataOutputStream.class));
        when(httpURLConnection.getInputStream()).thenReturn(mock(InputStream.class));

        URL url = mock(URL.class);
        when(url.openConnection()).thenReturn(connection);

        loginController.userLogIn();

        verify(loginInfo).setText("Login successful");
        verify(loginInfo).setTextFill(javafx.scene.paint.Color.GREEN);
    }

    @Test
    void testUserLogIn_WhenAdmin() throws Exception {
        TextField emailField = mock(TextField.class);
        PasswordField passwordField = mock(PasswordField.class);
        HttpURLConnection connection = mock(HttpURLConnection.class);
        Label loginInfo = mock(Label.class);

        loginController.setEmailField(emailField);
        loginController.setPasswordField(passwordField);
        loginController.setLoginInfo(loginInfo);

        when(emailField.getText()).thenReturn("admin@example.com");
        when(passwordField.getText()).thenReturn("password");
        when(connection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(connection.getInputStream()).thenReturn(new ByteArrayInputStream("{\"access_token\": \"token\", \"abilities\": [\"HasAdminRole\"]}".getBytes()));

        when(httpURLConnection.getOutputStream()).thenReturn(mock(DataOutputStream.class));
        when(httpURLConnection.getInputStream()).thenReturn(mock(InputStream.class));

        URL url = mock(URL.class);
        when(url.openConnection()).thenReturn(connection);

        loginController.userLogIn();

        verify(loginInfo).setText("Login successful");
        verify(loginInfo).setTextFill(javafx.scene.paint.Color.GREEN);
    }

    @Test
    void testUserLogIn_WhenNotAdmin() throws Exception {
        TextField emailField = mock(TextField.class);
        PasswordField passwordField = mock(PasswordField.class);
        HttpURLConnection connection = mock(HttpURLConnection.class);
        Label loginInfo = mock(Label.class);

        loginController.setEmailField(emailField);
        loginController.setPasswordField(passwordField);
        loginController.setLoginInfo(loginInfo);

        when(emailField.getText()).thenReturn("user@example.com");
        when(passwordField.getText()).thenReturn("password");
        when(connection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(connection.getInputStream()).thenReturn(new ByteArrayInputStream("{\"access_token\": \"token\", \"abilities\": []}".getBytes()));

        when(httpURLConnection.getOutputStream()).thenReturn(mock(DataOutputStream.class));
        when(httpURLConnection.getInputStream()).thenReturn(mock(InputStream.class));

        URL url = mock(URL.class);
        when(url.openConnection()).thenReturn(connection);

        loginController.userLogIn();

        verify(loginInfo).setText("Invalid email or password.");
        verify(loginInfo).setTextFill(javafx.scene.paint.Color.RED);

        assertFalse(loginController.isAdmin());
    }

    @Test
    void testUserLogIn_WhenConnectionSucceedsAndAdmin() throws Exception {
        TextField emailField = mock(TextField.class);
        PasswordField passwordField = mock(PasswordField.class);
        HttpURLConnection connection = mock(HttpURLConnection.class);
        Label loginInfo = mock(Label.class);

        loginController.setEmailField(emailField);
        loginController.setPasswordField(passwordField);
        loginController.setLoginInfo(loginInfo);

        when(emailField.getText()).thenReturn("admin@example.com");
        when(passwordField.getText()).thenReturn("password");
        when(connection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(connection.getInputStream()).thenReturn(new ByteArrayInputStream("{\"access_token\": \"token\", \"abilities\": [\"HasAdminRole\"]}".getBytes()));

        when(httpURLConnection.getOutputStream()).thenReturn(mock(DataOutputStream.class));
        when(httpURLConnection.getInputStream()).thenReturn(mock(InputStream.class));

        URL url = mock(URL.class);
        when(url.openConnection()).thenReturn(connection);

        loginController.userLogIn();

        verify(loginInfo).setText("Login successful");
        verify(loginInfo).setTextFill(javafx.scene.paint.Color.GREEN);

        assertTrue(loginController.isAdmin());
    }
}
