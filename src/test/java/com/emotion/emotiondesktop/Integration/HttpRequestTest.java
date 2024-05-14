package com.emotion.emotiondesktop.Integration;

import com.emotion.emotiondesktop.Helper.HttpRequest;
import com.emotion.emotiondesktop.Helper.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HttpRequestTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendHttpRequest_Success() throws Exception {
        String apiUrl = "https://dev.escooters.blumilk.pl/api/login";
        String method = "POST";
        String email = "admin@example.com";
        String password = "password";
        String jsonInputString = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
        int responseCode = HttpURLConnection.HTTP_OK;

        URL mockUrl = mock(URL.class);
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);

        when(mockUrl.openConnection()).thenReturn(mockConnection);

        when(mockConnection.getResponseCode()).thenReturn(responseCode);

        HttpResponse response = HttpRequest.sendHttpRequest(apiUrl, method, jsonInputString);

        assertEquals(responseCode, response.getResponseCode());
    }

    @Test
    void testSendHttpRequest_Failure() throws Exception {
        String apiUrl = "https://dev.escooters.blumilk.pl/api/login";
        String method = "POST";
        String email = "test@test.com";
        String password = "test";
        String jsonInputString = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
        int responseCode = HttpURLConnection.HTTP_UNAUTHORIZED;

        URL mockUrl = mock(URL.class);
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);

        when(mockUrl.openConnection()).thenReturn(mockConnection);

        try {
            when(mockConnection.getResponseCode()).thenReturn(responseCode);

            HttpRequest.sendHttpRequest(apiUrl, method, jsonInputString);

            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            assertEquals("HTTP request failed with response code: 401", e.getMessage());
        }

    }
}