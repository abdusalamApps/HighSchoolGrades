package com.example.highschoolgrades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView coursesRv;
    private CourseListAdapter adapter;
    private CourseViewModel mCourseViewModel;
    private FloatingActionButton fab;
    private TextView comparisonSumBtn;
    private TextView meritVardeTV;
    private Spinner meriterSpinner;
    private TextView comparisonSumTV;
    private TextView pointsSumTV;
    private TextView gradesValuesTV;
    private TextView courseCountTV;
    private double sum;

    void findViews() {
        coursesRv = findViewById(R.id.courses_rv);
        fab = findViewById(R.id.fab);
        comparisonSumBtn = findViewById(R.id.comparisonSum_btn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        findViews();

        sum = 1.0;

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
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                if (aDouble != null) {
                    sum = aDouble;
                }
                comparisonSumBtn.setText(df.format(sum));
            }
        });

        comparisonSumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, ResultActivity.class));
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.activity_result, null);
                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();
                TextView closeTV = dialogView.findViewById(R.id.closeResult_tv);
                closeTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                Window window = dialog.getWindow();
                if (window != null) {
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

                meriterSpinner = dialogView.findViewById(R.id.meriter_spinner);
                meritVardeTV = dialogView.findViewById(R.id.meritvarde_tv);
                comparisonSumTV = dialogView.findViewById(R.id.comparison_sum_tv);
                mCourseViewModel.getComparisonSum().observe(MainActivity.this, new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        DecimalFormat df = new DecimalFormat("##.##");
                        comparisonSumTV.setText(df.format(aDouble));
                    }
                });

                pointsSumTV = dialogView.findViewById(R.id.pointsSum_tv);

                mCourseViewModel.getPointsSum().observe(MainActivity.this, new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        pointsSumTV.setText(String.valueOf(aDouble));
                    }
                });

                gradesValuesTV = dialogView.findViewById(R.id.grades_value_tv);
                mCourseViewModel.getGradesValues().observe(MainActivity.this, new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        gradesValuesTV.setText(String.valueOf(aDouble));
                    }
                });

                courseCountTV = dialogView.findViewById(R.id.courseCount_tv);
                mCourseViewModel.getSize().observe(MainActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        courseCountTV.setText(String.valueOf(integer));
                    }
                });

                meriterSpinner = dialogView.findViewById(R.id.meriter_spinner);
                setupSpinner();

                dialog.show();

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);

/*
                ActivityOptionsCompat activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, fab, "editor_cardView");
*/

                startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);
                /*AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.activity_editor, null);
                builder.setView(dialogLayout);
                final AlertDialog dialog = builder.create();
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView confirmTV = dialogLayout.findViewById(R.id.confirmBtn_tv);
                confirmTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();*/
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
        } else if (requestCode == CourseListAdapter.Companion.getEXISTING_COURSE_ACTIVITY_REQUEST_CODE() && resultCode == RESULT_OK) {
            Course course = null;
            if (data != null) {
                course = new Course(
                        data.getStringExtra(getString(R.string.Course)),
                        data.getDoubleExtra(getString(R.string.grade), 0.0),
                        data.getDoubleExtra(getString(R.string.points), 0.0)
                );
            }
            int id = 0;
            if (data != null) {
                id = data.getIntExtra("id", -1);
            }
            if (id == -1) {
                Toast.makeText(this, getString(R.string.failed_to_update), Toast.LENGTH_SHORT).show();
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

    private void setupSpinner() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.meriter_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meriterSpinner.setAdapter(adapter);

        meriterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        meritVardeTV.setText(String.valueOf(formatComparisonSum()));
                        break;
                    case 1:
                        meritVardeTV.setText(String.valueOf(0.5 + formatComparisonSum()));
                        break;
                    case 2:
                        meritVardeTV.setText(String.valueOf(1.0 + formatComparisonSum()));
                        break;
                    case 3:
                        meritVardeTV.setText(String.valueOf(1.5 + formatComparisonSum()));
                        break;
                    case 4:
                        meritVardeTV.setText(String.valueOf(2.0 + formatComparisonSum()));
                        break;
                    case 5:
                        meritVardeTV.setText(String.valueOf(2.5 + formatComparisonSum()));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private Double formatComparisonSum() {
        String s = comparisonSumTV.getText().toString();
        if (s.contains(",")){
            s = s.replace(",", ".");
        }
        return Double.parseDouble(s);
    }
}
