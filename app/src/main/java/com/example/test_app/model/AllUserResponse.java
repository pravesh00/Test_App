package com.example.test_app.model;

import java.util.List;

public class AllUserResponse {
    List<UserResponse> users;

    public AllUserResponse() {
    }

    public AllUserResponse(List<UserResponse> users) {
        this.users = users;
    }

    public List<UserResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponse> users) {
        this.users = users;
    }
}
