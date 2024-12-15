package com.example.retry.classes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "course")
public class Course implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String labName;
    private int classNumber;

    @Ignore
    public Course(int classNumber, String labName) {
        this.classNumber = classNumber;
        this.labName = labName;
    }

    public Course(int id, String labName, int classNumber) {
        this.id = id;
        this.labName = labName;
        this.classNumber = classNumber;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", labName='" + labName + '\'' +
                ", classNumber=" + classNumber +
                '}';
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }
}
