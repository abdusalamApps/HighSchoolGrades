package com.example.highschoolgrades.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.highschoolgrades.models.Course;
import com.example.highschoolgrades.repository.CourseRepository;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private CourseRepository mRepository;
    private LiveData<List<Course>> mAllCourses;
    private LiveData<Double> mPointsSum;
    private LiveData<Double> mGradesValues;
    private LiveData<Double> mComparisonSum;
    private LiveData<Integer> mSize;


    public CourseViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CourseRepository(application);
        mAllCourses = mRepository.getAllCourses();
        mPointsSum = mRepository.getPointsSum();
        mGradesValues = mRepository.getGradesValues();
        mComparisonSum = mRepository.getComparisonSum();
        mSize = mRepository.getSize();
    }

    public void insert(Course course) { mRepository.insert(course);}

    public void update(Course course) { mRepository.update(course);}

    public void deleteCourse(Course course) {
        mRepository.deleteCourse(course);
    }

    public LiveData<List<Course>> getAllCourses() { return mAllCourses; }

    public LiveData<Double> getPointsSum(){
        return mPointsSum;
    }

    public LiveData<Double> getGradesValues() {
        return mGradesValues;
    }

    public LiveData<Double> getComparisonSum() {
        return mComparisonSum;
    }

    public LiveData<Integer> getSize() {
        return mSize;
    }


}
