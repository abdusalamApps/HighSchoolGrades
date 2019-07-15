package com.example.highschoolgrades;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    private final CourseDao mDao;

    PopulateDbAsync(CourseRoomDatabase db) {
        mDao = db.courseDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.e("Count", "doInBackground: " + mDao.getCount().size());
        if (mDao.getCount().size() <= 0) {
            for (Course c : Constants.highSchoolSharedCourses) mDao.insert(c);
        }
        return null;
    }
}