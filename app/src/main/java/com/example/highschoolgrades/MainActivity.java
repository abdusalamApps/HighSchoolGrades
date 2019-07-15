package com.example.highschoolgrades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;

    private RecyclerView coursesRv;
    private CourseListAdapter adapter;
    private CourseViewModel mCourseViewModel;
    private FloatingActionButton fab;


    void findViews() {
        coursesRv = findViewById(R.id.courses_rv);
        fab = findViewById(R.id.fab);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        adapter = new CourseListAdapter(this);
        coursesRv.setAdapter(adapter);
        coursesRv.setLayoutManager(new LinearLayoutManager(this));

        mCourseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        mCourseViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.setCourses(courses);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);
            }
        });
    }
}
