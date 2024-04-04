package com.example.csc325_firebase_webview_auth.model;

public class SignUpPerson {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SignUpPerson(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
