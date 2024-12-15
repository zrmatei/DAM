package com.example.retry.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.retry.classes.Course;

import java.util.List;

//pt operatii DML (query-uri ce afecteaza tabelul)
@androidx.room.Dao
public interface Dao {

    //query e singura anotare ce are nevoie de string
    @Query("SELECT * FROM course")
    List<Course> getAll();

    @Insert
    long insert(Course course);

    @Update
    int update(Course course);

    @Delete
    int delete(Course course);

}
