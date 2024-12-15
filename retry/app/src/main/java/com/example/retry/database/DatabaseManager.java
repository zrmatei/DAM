package com.example.retry.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.retry.classes.Course;

//clasa pt creare tabele + obtinere conexiune
@Database(entities = {Course.class}, version = 1, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {

    //asta e conexiunea
    private static DatabaseManager connection;
    public static DatabaseManager getConnection(Context context){
        //daca am deja o conex instantiata
        if(connection != null){
            return connection;
        }//altfel o creez folosind databaseBuilder
        connection = Room.databaseBuilder(context, DatabaseManager.class, "my_db")
                .fallbackToDestructiveMigration()
                .build();

        return connection;
    }

    //trb o metoda ce face conexiunea cu Dao
    public abstract Dao getDao();
}
