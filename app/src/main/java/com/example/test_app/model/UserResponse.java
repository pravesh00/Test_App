package com.example.test_app.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity
public class UserResponse implements Serializable {
    @ColumnInfo(name="address")
    String address;
    @ColumnInfo(name="phoneNumber")
    String phoneNumber;
    @ColumnInfo(name="name")
    String name;
    @ColumnInfo(name="email")
    String email;
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name="public_id")
    String public_id;

    public UserResponse() {
    }

    public UserResponse(String address, String phoneNumber, String name, String email, String public_id,int id) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
        this.public_id = public_id;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPublic_id() {
        return public_id;
    }

    public void setPublic_id(String public_id) {
        this.public_id = public_id;
    }
}
