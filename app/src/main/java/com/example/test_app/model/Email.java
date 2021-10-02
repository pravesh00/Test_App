package com.example.test_app.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Email implements Serializable {
    @ColumnInfo(name="body")
    String body;
    @PrimaryKey
    @NonNull
    String id;
    @ColumnInfo(name = "sender")
    String sender;
    @ColumnInfo(name="subject")
    String subject;
    @ColumnInfo(name="time")
    String time;
    @ColumnInfo(name="name")
    String name;

    public Email() {
    }

    public Email(String body, String id, String sender, String subject, String time,String name) {
        this.body = body;
        this.id = id;
        this.sender = sender;
        this.subject = subject;
        this.time = time;
        this.name=name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
