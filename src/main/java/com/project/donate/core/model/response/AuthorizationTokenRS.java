package com.project.donate.core.model.response;

public class AuthorizationTokenRS {

    private String token;

    public AuthorizationTokenRS(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
