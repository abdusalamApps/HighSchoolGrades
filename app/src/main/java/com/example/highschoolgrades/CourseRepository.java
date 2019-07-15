package com.example.highschoolgrades;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CourseRepository {

    private CourseDao mCourseDao;
    private LiveData<List<Course>> mAllCourses;

    CourseRepository(Application application) {
        CourseRoomDatabase db = CourseRoomDatabase.getDatabase(application);
        mCourseDao = db.courseDao();
        mAllCourses = mCourseDao.getALlCourses();
    }

    LiveData<List<Course>> getAllCourses() {
        return mAllCourses;
    }

    public void insert(Course course) {
        new insertAsyncTask(mCourseDao).execute(course);
    }

    public void update(Course course){
       new updateAsyncTask(mCourseDao).execute(course);
    }

    public double getPointsSum() {
        return mCourseDao.getPointsSum();
    }

    public double getGradesValues() {
        return mCourseDao.getGradesValues();
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
}
