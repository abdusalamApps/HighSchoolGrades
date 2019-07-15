package com.example.highschoolgrades;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView coursesRv;
    private CourseListAdapter adapter;
    private CourseViewModel mCourseViewModel;
    private FloatingActionButton fab;
    private MaterialButton comparisonSumBtn;
    private Double mPointsSum = 0.0;
    private Double mGradesValues;

    void findViews() {
        coursesRv = findViewById(R.id.courses_rv);
        fab = findViewById(R.id.fab);
        comparisonSumBtn = findViewById(R.id.comparisonSum_btn);
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

        mCourseViewModel.getComparisonSum().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                Log.d(TAG, "onChanged: comparison sum is " + aDouble);
                comparisonSumBtn.setText(String.format("%s%s%s", getString(R.string.comparison_sum)," ", aDouble));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);
            }
        });

        comparisonSumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ResultActivity.class));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_COURSE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Course course = null;
            if (data != null) {
                course = new Course(
                        data.getStringExtra(getString(R.string.Course)),
                        data.getDoubleExtra(getString(R.string.grade), 0.0),
                        data.getDoubleExtra(getString(R.string.points), 0.0)
                );
            }
            mCourseViewModel.insert(course);
            Toast.makeText(
                    getApplicationContext(),
                    "course added",
                    Toast.LENGTH_LONG).show();
        } else if (requestCode == CourseListAdapter.EXISTING_COURSE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Course course = null;
            if (data != null) {
                course = new Course(
                        data.getStringExtra(getString(R.string.Course)),
                        data.getDoubleExtra(getString(R.string.grade), 0.0),
                        data.getDoubleExtra(getString(R.string.points), 0.0)
                );
            }
            int id = data.getIntExtra("id", -1);
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            course.setId(id);
            mCourseViewModel.update(course);
            Toast.makeText(
                    getApplicationContext(),
                    "updated",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "canceled",
                    Toast.LENGTH_LONG).show();
        }
    }
}
