package com.example.test_app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Selected {
    @PrimaryKey
    @NonNull
    String id;

    public Selected() {
    }

    public Selected(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
