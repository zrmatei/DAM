package com.example.finala.classes;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "labs")
public class Lab implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int number;

    public Lab(int id, String name, int number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Lab{" +
                "insert=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
