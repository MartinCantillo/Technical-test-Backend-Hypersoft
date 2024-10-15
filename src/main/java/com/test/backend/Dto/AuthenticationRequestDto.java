package com.test.backend.Dto;

public class AuthenticationRequestDto {
    private String username;
    private String password;
    private String role;

    public AuthenticationRequestDto(String password, String role, String username) {
        this.password = password;
        this.role = role;
        this.username = username;
    }

    public AuthenticationRequestDto() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
