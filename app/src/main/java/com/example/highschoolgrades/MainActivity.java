package com.example.highschoolgrades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView coursesRv;
    private CourseListAdapter adapter;
    private CourseViewModel mCourseViewModel;
    private FloatingActionButton fab;
    private TextView comparisonSumBtn;

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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final Course course = adapter.getCourseAt(viewHolder.getAdapterPosition());
                mCourseViewModel.deleteCourse(course);
                /*Store the Course to be able to restore it*/
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.custom_dialog, null);
                builder.setView(dialogLayout);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView closeTV = dialogLayout.findViewById(R.id.dialog_close_tv);
                closeTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                TextView restoreTV = dialogLayout.findViewById(R.id.dialog_restore_tv);
                restoreTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCourseViewModel.insert(course);
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        }).attachToRecyclerView(coursesRv);

        mCourseViewModel.getComparisonSum().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                DecimalFormat df = new DecimalFormat("##.##");
                double sum = aDouble;
                comparisonSumBtn.setText(df.format(sum));
            }
        });

        comparisonSumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ResultActivity.class));
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
                Toast.makeText(this, "Course can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            if (course != null) {
                course.setId(id);
            }
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
