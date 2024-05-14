package com.emotion.emotiondesktop.Helper;

import lombok.Getter;

@Getter
public class HttpResponse {
    private final String responseBody;
    private final int responseCode;

    public HttpResponse(String responseBody, int responseCode) {
        this.responseBody = responseBody;
        this.responseCode = responseCode;
    }
}
