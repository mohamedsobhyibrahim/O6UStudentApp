package org.o6u.studentapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.o6u.studentapp.R;
import org.o6u.studentapp.customViews.CustomButton;
import org.o6u.studentapp.customViews.CustomTextView;
import org.o6u.studentapp.models.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>
{
    List<Course> courses;
    Context context;

    boolean visibility = false;

    public CourseAdapter(Context context, List<Course> courses)
    {
        this.courses = courses;
        this.context = context;
    }

    private Context getContext()
    {
        return context;
    }

    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CourseAdapter.ViewHolder holder, int position)
    {
        Course course = courses.get(position);
        holder.titleTextView.setText(course.getTitle());
        holder.doctorNameTextView.setText(course.getDoctorName());
        holder.departmentTextView.setText("Department: " + course.getDepartment());
        holder.hoursTextView.setText("Hours: " + course.getHours());
    }

    @Override
    public int getItemCount()
    {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CustomTextView titleTextView;
        CustomTextView doctorNameTextView;
        CustomTextView departmentTextView;
        CustomTextView hoursTextView;
        CustomButton addButton;

        ViewHolder (View view)
        {
            super(view);
            addButton = view.findViewById(R.id.item_course_add_button);
            addButton.setOnClickListener(this);
            titleTextView = view.findViewById(R.id.item_course_name_textView);
            doctorNameTextView = view.findViewById(R.id.item_course_doctor_name_textView);
            departmentTextView = view.findViewById(R.id.item_course_department_textView);
            hoursTextView = view.findViewById(R.id.item_course_hours_textView);
        }

        @Override
        public void onClick(View v) {

            if(v == addButton){

            }
        }
    }
}
