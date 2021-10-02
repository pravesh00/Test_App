package com.example.test_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.test_app.model.Email;
import com.example.test_app.model.Selected;

import java.util.List;

@Dao
public interface SelectedDao {
    @Query("Select * from Selected")
    List<Selected> getAll();

    @Insert
    void insert(Selected selected);

    @Delete
    void delete(Selected selected);

    @Update
    void update(Selected selected);

    @Query("Select * from Selected where id=:id")
    List<Selected> get(String id);

    @Query("Delete from Selected")
    void deleteAll();
}
