package com.example.highschoolgrades.models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "courses_table")
public class Course {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id = 0;

    @NonNull
    @ColumnInfo(name = "course")
    private String course;

    @ColumnInfo(name = "grade")
    public double grade;

    @ColumnInfo(name = "points")
    public double points;

    public Course(@NotNull String course, double grade, double points) {
        this.course = course;
        this.grade = grade;
        this.points = points;
    }

    @NotNull
    public String getCourse() {
        return course;
    }

    public double getGrade() {
        return grade;
    }

    public double getPoints() {
        return points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
