package com.example.highschoolgrades;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert
    void insert(Course course);

    @Delete
    void deleteCourse(Course... course);

    @Query("DELETE from courses_table")
    void deleteAll();

    @Update
    void updateCourse(Course... course);

    @Query("SELECT * from courses_table ORDER BY course ASC")
    LiveData<List<Course>> getALlCourses();

    @Query("SELECT * from courses_table ORDER BY course ASC")
    List<Course> getCount();

    @Query("SELECT SUM(points) FROM courses_table")
    double getPointsSum();

    @Query("SELECT SUM(points * grade) FROM courses_table")
    double getGradesValues();
}
