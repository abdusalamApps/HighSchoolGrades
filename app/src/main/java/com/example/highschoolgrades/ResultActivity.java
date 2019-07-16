package com.example.highschoolgrades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {

    private TextView closeIV;
    private TextView courseCountTV;
    private TextView gradesValuesTV;
    private TextView pointsSumTV;
    private TextView comparisonSumTV;
    private AppCompatSpinner meriterSpinner;
    private TextView meritVardeTV;
    private ImageView shareIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        findViews();
        closeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CourseViewModel mCourseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        mCourseViewModel.getComparisonSum().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                DecimalFormat df = new DecimalFormat("##.##");
                comparisonSumTV.setText(df.format(aDouble));
            }
        });
        mCourseViewModel.getPointsSum().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                pointsSumTV.setText(String.valueOf(aDouble));
            }
        });
        mCourseViewModel.getGradesValues().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                gradesValuesTV.setText(String.valueOf(aDouble));
            }
        });

        mCourseViewModel.getSize().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                courseCountTV.setText(String.valueOf(integer));
            }
        });

        setupSpinner();
    }

    private void findViews() {
        closeIV = findViewById(R.id.closeResult_tv);
        courseCountTV = findViewById(R.id.courseCount_tv);
        gradesValuesTV = findViewById(R.id.grades_value_tv);
        pointsSumTV = findViewById(R.id.pointsSum_tv);
        comparisonSumTV = findViewById(R.id.comparison_sum_tv);
        meriterSpinner = findViewById(R.id.meriter_spinner);
        meritVardeTV = findViewById(R.id.meritvarde_tv);
//        shareIV = findViewById(R.id.share_imageView);
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
                        meritVardeTV.setText(String.valueOf(Double.parseDouble(comparisonSumTV.getText().toString())));
                        break;
                    case 1:
                        meritVardeTV.setText(String.valueOf(0.5 + Double.parseDouble(comparisonSumTV.getText().toString())));
                        break;
                    case 2:
                        meritVardeTV.setText(String.valueOf(1.0 + Double.parseDouble(comparisonSumTV.getText().toString())));
                        break;
                    case 3:
                        meritVardeTV.setText(String.valueOf(1.5 + Double.parseDouble(comparisonSumTV.getText().toString())));
                        break;
                    case 4:
                        meritVardeTV.setText(String.valueOf(2.0 + Double.parseDouble(comparisonSumTV.getText().toString())));
                        break;
                    case 5:
                        meritVardeTV.setText(String.valueOf(2.5 + Double.parseDouble(comparisonSumTV.getText().toString())));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
