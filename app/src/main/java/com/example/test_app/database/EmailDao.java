package com.example.test_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.test_app.model.Email;

import java.util.List;

@Dao
public interface EmailDao {
    @Query("Select * from Email")
    List<Email> getAll();

    @Insert
    void insert(Email email);

    @Delete
    void delete(Email email);

    @Update
    void update(Email email);

    @Query("Delete from Email")
    void deleteALL();



}
