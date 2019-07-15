package com.example.highschoolgrades;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private CourseRepository mRepository;
    private LiveData<List<Course>> mAllCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CourseRepository(application);
        mAllCourses = mRepository.getAllCourses();
    }

    public void insert(Course course) { mRepository.insert(course);}

    public LiveData<List<Course>> getAllCourses() { return mAllCourses; }
}
