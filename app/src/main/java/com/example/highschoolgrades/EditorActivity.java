package com.example.highschoolgrades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class EditorActivity extends AppCompatActivity {

    private TextView closeTV, deleteButtonTv, confirmButtonTv;
    private AutoCompleteTextInputEditText courseInput;
    private Spinner gradeSpinner, pointsSpinner;


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
    }
}
