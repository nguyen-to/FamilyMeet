package org.example.server.request.authrequest;

public class LoginRequest {
    private String email;
    private String password;
    private String deviceId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
