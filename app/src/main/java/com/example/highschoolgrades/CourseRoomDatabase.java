package com.example.highschoolgrades;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Course.class}, version = 1)
public abstract class CourseRoomDatabase extends RoomDatabase {

    public abstract CourseDao courseDao();

    private static volatile CourseRoomDatabase INSTANCE;

    static CourseRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CourseRoomDatabase.class) {
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CourseRoomDatabase.class, "courses_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };


}
