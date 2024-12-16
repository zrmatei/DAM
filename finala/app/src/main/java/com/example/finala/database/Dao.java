package com.example.finala.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finala.classes.Lab;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Query("SELECT * FROM labs")
    List<Lab>getall();

    @Insert
    long insert(Lab lab);

    @Update
    int update(Lab lab);

    @Delete
    int delete(Lab lab);
}
