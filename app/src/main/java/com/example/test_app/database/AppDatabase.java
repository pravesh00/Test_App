package com.example.test_app.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.test_app.model.Email;
import com.example.test_app.model.UserResponse;

@Database(entities = {UserResponse.class, Email.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract EmailDao emailDao();

}
