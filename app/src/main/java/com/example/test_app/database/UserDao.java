package com.example.test_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.test_app.model.UserResponse;

import java.util.List;

@Dao
public interface UserDao {
    @Query("Select * from 'UserResponse'")
    List<UserResponse> getAll();

    @Insert
    void insert(UserResponse user);

    @Delete
    void delete(UserResponse user);

    @Update
    void update(UserResponse user);

    @Query("Select * from UserResponse where email=:id")
    List<UserResponse> getUserById(String id);

    @Query("Delete from UserResponse")
    void deleteALL();
}
