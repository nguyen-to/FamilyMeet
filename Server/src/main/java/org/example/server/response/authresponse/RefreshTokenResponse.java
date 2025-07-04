package org.example.server.response.authresponse;

import lombok.Builder;

@Builder
public class RefreshTokenResponse {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
