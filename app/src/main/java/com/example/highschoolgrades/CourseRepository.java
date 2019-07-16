package com.example.highschoolgrades;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CourseRepository {

    private CourseDao mCourseDao;
    private LiveData<List<Course>> mAllCourses;
    private LiveData<Double> mPointsSum;
    private LiveData<Double> mGradesValues;
    private LiveData<Double> mComparisonSum;
    private LiveData<Integer> mSize;

    CourseRepository(Application application) {
        CourseRoomDatabase db = CourseRoomDatabase.getDatabase(application);
        mCourseDao = db.courseDao();
        mAllCourses = mCourseDao.getALlCourses();
        mPointsSum = mCourseDao.getPointsSum();
        mGradesValues = mCourseDao.getGradesValues();
        mComparisonSum = mCourseDao.getComparisonSum();
        mSize = mCourseDao.getSize();
    }

    LiveData<List<Course>> getAllCourses() {
        return mAllCourses;
    }

    public void insert(Course course) {
        new insertAsyncTask(mCourseDao).execute(course);
    }

    public void update(Course course) {
        new updateAsyncTask(mCourseDao).execute(course);
    }

    public void deleteCourse(Course course) {
        new deleteAsyncTask(mCourseDao).execute(course);
    }

    LiveData<Double> getPointsSum() {
        return mPointsSum;
    }

    LiveData<Double> getGradesValues() {
        return mGradesValues;
    }

    LiveData<Double> getComparisonSum() {
        return mComparisonSum;
    }

    LiveData<Integer> getSize() {
        return mSize;
    }

    private static class insertAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao asyncCourseDao;

        insertAsyncTask(CourseDao dao) {
            asyncCourseDao = dao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            asyncCourseDao.insert(courses[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao asyncCourseDao;

        updateAsyncTask(CourseDao dao) {
            asyncCourseDao = dao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            asyncCourseDao.updateCourse(courses[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao asyncCourseDao;

        deleteAsyncTask(CourseDao dao) {
            asyncCourseDao = dao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            asyncCourseDao.deleteCourse(courses[0]);
            return null;
        }
    }

}
