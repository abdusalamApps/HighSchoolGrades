package com.example.highschoolgrades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {
    public static final int EXISTING_COURSE_ACTIVITY_REQUEST_CODE = 2;

    private LayoutInflater mInflater;
    private List<Course> mCourses;

    CourseListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseViewHolder holder, final int position) {
        if (mCourses != null) {
            Course current = mCourses.get(position);
            holder.courseTextView.setText(current.getCourse());
            holder.pointsTextView.setText(String.valueOf(current.getPoints()));

            double gradeDouble = current.getGrade();
            String gradeString = "";
            if (gradeDouble == 20.0) {
                gradeString = "A";
                holder.gradeTextView.setBackground(holder.itemView.getContext().getDrawable(R.drawable.rounded_icon_a));
            } else if (gradeDouble == 17.5) {
                gradeString = "B";
                holder.gradeTextView.setBackground(holder.itemView.getContext().getDrawable(R.drawable.rounded_icon_b));
            } else if (gradeDouble == 15.0) {
                gradeString = "C";
                holder.gradeTextView.setBackground(holder.itemView.getContext().getDrawable(R.drawable.rounded_icon_c));
            } else if (gradeDouble == 12.0) {
                gradeString = "D";
                holder.gradeTextView.setBackground(holder.itemView.getContext().getDrawable(R.drawable.rounded_icon_d));
            } else if (gradeDouble == 10.0) {
                gradeString = "E";
                holder.gradeTextView.setBackground(holder.itemView.getContext().getDrawable(R.drawable.rounded_icon_e));
            } else {
                gradeString = "F";
                holder.gradeTextView.setBackground(holder.itemView.getContext().getDrawable(R.drawable.rounded_icon_f));
            }
            holder.gradeTextView.setText(gradeString);
        } else {
            holder.courseTextView.setText("No Courses");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), EditorActivity.class);
                intent.putExtra("From Item", mCourses.get(position).getCourse());
                Activity context = (Activity) holder.itemView.getContext();
                context.startActivityForResult(intent, EXISTING_COURSE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseTextView;
        private final TextView gradeTextView;
        private final TextView pointsTextView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTextView = itemView.findViewById(R.id.course_tv);
            gradeTextView = itemView.findViewById(R.id.grade_tv);
            pointsTextView = itemView.findViewById(R.id.points_tv);
        }
    }
}
