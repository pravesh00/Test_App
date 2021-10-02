package com.example.login.model;

public class SingUpBody {
    String email,password,address;

    public SingUpBody() {
    }

    public SingUpBody(String email, String password, String address) {
        this.email = email;
        this.password = password;
        this.address = address;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
