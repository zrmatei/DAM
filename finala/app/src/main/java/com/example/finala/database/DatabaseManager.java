package com.example.finala.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.finala.classes.Lab;

@Database(entities = {Lab.class}, version = 1, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {
    private static DatabaseManager connection;

    //metoda de creare conexiune
    public static DatabaseManager getInstance(Context context){
        if(connection != null){
            return connection;
        }
        connection = Room.databaseBuilder(context, DatabaseManager.class, "db_lab")
                .fallbackToDestructiveMigration()
                .build();
        return connection;
    }

    public abstract Dao getDao();
}
