package com.UserManagementusingJWT.responsejson;

import com.UserManagementusingJWT.model.Role;

public class AuthenticationResponse {
    private String token;
    private  Role role;
    private String name;
    private String email;

    public AuthenticationResponse(String token, Role role,String  name ,String email) {
        this.token = token;
        this.role=role;
        this.name=name;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "token='" + token + '\'' +
                ", role=" + role +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
