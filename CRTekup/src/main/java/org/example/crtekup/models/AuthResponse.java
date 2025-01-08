package org.example.crtekup.models;

public class AuthResponse {
    private String token;
    private String username;
    private Long userId;
    private String role;

    public AuthResponse(String token, String username, Long userId, String role) {
        this.token = token;
        this.username = username;
        this.userId = userId;
        this.role = role;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
