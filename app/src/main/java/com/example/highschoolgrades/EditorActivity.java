package com.example.highschoolgrades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class EditorActivity extends AppCompatActivity {

    private TextView closeTV, deleteButtonTv, confirmButtonTv;
    private AutoCompleteTextInputEditText courseInput;
    private Spinner gradeSpinner, pointsSpinner;
    private double mGrade = 0;
    private double mPoints = 0;


    private void findViews() {
        closeTV = findViewById(R.id.close_tb_iv);
        deleteButtonTv = findViewById(R.id.deleteBtn_tv);
        confirmButtonTv = findViewById(R.id.confirmBtn_tv);
        courseInput = findViewById(R.id.course_input);
        gradeSpinner = findViewById(R.id.grade_spinner);
        pointsSpinner = findViewById(R.id.points_spinner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        findViews();

        deleteButtonTv.setVisibility(View.GONE);

        if (getIntent().getStringExtra("From Item") != null) {
            deleteButtonTv.setVisibility(View.VISIBLE);

            courseInput.setText(getIntent().getStringExtra("From Item"));

            deleteButtonTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* TODO: Push Course back to MainActivity and then delete it from the database
                     * use startActivityForResults */
//                    Temporarily, only destroy activity
                    AlertDialog alertDialog = new AlertDialog.Builder(EditorActivity.this).create();
                    alertDialog.setTitle("Delete This Course?");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "DELETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                }
            });
        } else {
            confirmButtonTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent replyIntent = new Intent();
                    if (TextUtils.isEmpty(courseInput.getText().toString().trim())) {
                        setResult(RESULT_CANCELED, replyIntent);
                    } else {
                        replyIntent.putExtra(getString(R.string.Course), courseInput.getText().toString().trim());
                        replyIntent.putExtra(getString(R.string.grade), mGrade);
                        replyIntent.putExtra(getString(R.string.points), mPoints);
                        setResult(RESULT_OK, replyIntent);
                    }
                    finish();
                }
            });

        }

        closeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupSpinner(R.id.grade_spinner, gradeSpinner);
        setupSpinner(R.id.points_spinner, pointsSpinner);

    }

    private void setupSpinner(final int spinnerId, Spinner spinner) {
        int arrayRecourse;
        switch (spinnerId) {
            case R.id.grade_spinner:
                arrayRecourse = R.array.grades_spinner_array;
                break;
            case R.id.points_spinner:
                arrayRecourse = R.array.points_spinner_array;
                break;
            default:
                arrayRecourse = R.array.grades_spinner_array;


        }

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, arrayRecourse, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                switch (spinnerId) {
                    case R.id.grade_spinner:
                        if (selection.equals(getString(R.string.grade_a))) {
                            mGrade = 20.0;
                        } else if (selection.equals(getString(R.string.grade_b))) {
                            mGrade = 17.5;
                        } else if (selection.equals(getString(R.string.grade_c))) {
                            mGrade = 15.0;
                        } else if (selection.equals(getString(R.string.grade_d))) {
                            mGrade = 12.5;
                        } else if (selection.equals(getString(R.string.grade_e))) {
                            mGrade = 10.0;
                        } else if (selection.equals(getString(R.string.grade_f))) {
                            mGrade = 0.0;
                        }
                        break;
                    case R.id.points_spinner:
                        if (selection.equals(getString(R.string.points_100))) {
                            mPoints = 100.0;
                        } else if (selection.equals(getString(R.string.points_50))) {
                            mPoints = 50.0;
                        } else if (selection.equals(getString(R.string.points_150))) {
                            mPoints = 150.0;
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
