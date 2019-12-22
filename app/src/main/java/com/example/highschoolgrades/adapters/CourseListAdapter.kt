package com.example.highschoolgrades.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.highschoolgrades.R
import com.example.highschoolgrades.models.Course
import com.example.highschoolgrades.ui.EditorActivity

class CourseListAdapter internal constructor(context: Context) : RecyclerView.Adapter<CourseListAdapter.CourseViewHolder>() {

    private val mInflater: LayoutInflater
    private var mCourses: List<Course>? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemView = mInflater.inflate(R.layout.course_list_item, parent, false)
        return CourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        if (mCourses != null) {
            val current = mCourses!![position]
            holder.courseTextView.text = current.course
            holder.pointsTextView.text = current.getPoints().toString()


            val gradeDouble = current.getGrade()
            var gradeString = ""
            if (gradeDouble == 20.0) {
                gradeString = "A"
                holder.gradeTextView.background = holder.itemView.context.getDrawable(R.drawable.rounded_icon_a)
            } else if (gradeDouble == 17.5) {
                gradeString = "B"
                holder.gradeTextView.background = holder.itemView.context.getDrawable(R.drawable.rounded_icon_b)
            } else if (gradeDouble == 15.0) {
                gradeString = "C"
                holder.gradeTextView.background = holder.itemView.context.getDrawable(R.drawable.rounded_icon_c)
            } else if (gradeDouble == 12.5) {
                gradeString = "D"
                holder.gradeTextView.background = holder.itemView.context.getDrawable(R.drawable.rounded_icon_d)
            } else if (gradeDouble == 10.0) {
                gradeString = "E"
                holder.gradeTextView.background = holder.itemView.context.getDrawable(R.drawable.rounded_icon_e)
            } else {
                gradeString = "F"
                holder.gradeTextView.background = holder.itemView.context.getDrawable(R.drawable.rounded_icon_f)
            }
            holder.gradeTextView.text = gradeString
        } else {
            holder.courseTextView.text = "No Courses"
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditorActivity::class.java)
            intent.putExtra("CourseId", mCourses!![position].id)
            intent.putExtra("Course", mCourses!![position].course)
            intent.putExtra("Grade", mCourses!![position].getGrade())
            intent.putExtra("Points", mCourses!![position].getPoints())
            val context = holder.itemView.context as Activity

            val cardView: View = holder.cardView
            val pair = Pair.create(cardView, "transition_cardView")

            val textView: View = holder.courseTextView
            val coursePair = Pair.create(textView, "transition_course")

            val gradeView: View = holder.gradeTextView
            val gradePair = Pair.create(gradeView, "transition_grade")

            val pointsView: View = holder.pointsTextView
            val pointsPair=Pair.create(pointsView, "transition_points")

            val activityOptionsCompat = ActivityOptions.makeSceneTransitionAnimation(holder.itemView.context as Activity,
                    pair, coursePair, gradePair, pointsPair)

            context.startActivityForResult(intent, EXISTING_COURSE_ACTIVITY_REQUEST_CODE)
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

//            context.startActivityForResult(intent, EXISTING_COURSE_ACTIVITY_REQUEST_CODE)
        }
    }

    fun setCourses(courses: List<Course>) {
        mCourses = courses
        notifyDataSetChanged()
    }

    fun getCourseAt(position: Int): Course {
        return mCourses!![position]
    }

    override fun getItemCount(): Int {
        return if (mCourses != null)
            mCourses!!.size
        else
            0
    }

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val courseTextView: TextView
        val gradeTextView: TextView
        val pointsTextView: TextView
        val cardView: CardView

        init {
            courseTextView = itemView.findViewById(R.id.course_tv)
            gradeTextView = itemView.findViewById(R.id.grade_tv)
            pointsTextView = itemView.findViewById(R.id.points_tv)
            cardView = itemView.findViewById(R.id.listItemCardView)
        }
    }

    companion object {
        val EXISTING_COURSE_ACTIVITY_REQUEST_CODE = 2
    }

}