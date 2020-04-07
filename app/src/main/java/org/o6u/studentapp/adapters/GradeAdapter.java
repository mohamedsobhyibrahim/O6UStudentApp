package org.o6u.studentapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.o6u.studentapp.R;
import org.o6u.studentapp.customViews.CustomTextView;
import org.o6u.studentapp.models.Grade;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder>
{
    List<Grade> grades;
    Context context;

    boolean visibility = false;

    public GradeAdapter(Context context, List<Grade> grades)
    {
        this.grades = grades;
        this.context = context;
    }

    private Context getContext()
    {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grade, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Grade grade = grades.get(position);
        holder.courseNameTextView.setText(grade.getCourseName());
        holder.quizOneTextView.setText(grade.getQuizOne());
        holder.quizTwoTextView.setText(grade.getQuizTwo());
        holder.midtermTextView.setText(grade.getMidterm());
        holder.sectionTextView.setText(grade.getSection());
        holder.attendanceTextView.setText(grade.getAttendance());
        holder.practicalTextView.setText(grade.getPractical());
        holder.finalExamTextView.setText(grade.getFinalExam());
        holder.totalTextView.setText(grade.getTotal());
    }

    @Override
    public int getItemCount()
    {
        return grades.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CustomTextView courseNameTextView;
        LinearLayout gradesLayout;
        CustomTextView quizOneTextView;
        CustomTextView quizTwoTextView;
        CustomTextView midtermTextView;
        CustomTextView sectionTextView;
        CustomTextView attendanceTextView;
        CustomTextView practicalTextView;
        CustomTextView finalExamTextView;
        CustomTextView totalTextView;

        ViewHolder (View view)
        {
            super(view);
            courseNameTextView = view.findViewById(R.id.item_grade_course_name_textView);
            courseNameTextView.setOnClickListener(this);
            gradesLayout = view.findViewById(R.id.item_grades_layout);
            quizOneTextView = view.findViewById(R.id.item_grade_quiz_one_textView);
            quizTwoTextView = view.findViewById(R.id.item_grade_quiz_two_textView);
            midtermTextView = view.findViewById(R.id.item_grade_midterm_textView);
            sectionTextView = view.findViewById(R.id.item_grade_section_textView);
            attendanceTextView = view.findViewById(R.id.item_grade_attendance_textView);
            practicalTextView = view.findViewById(R.id.item_grade_practical_textView);
            finalExamTextView = view.findViewById(R.id.item_grade_final_textView);
            totalTextView = view.findViewById(R.id.item_grade_total_textView);
        }

        @Override
        public void onClick(View v) {

            if(v == courseNameTextView){
                if(visibility){
                    gradesLayout.setVisibility(View.GONE);
                    visibility = false;
                } else {
                    gradesLayout.setVisibility(View.VISIBLE);
                    visibility = true;
                }
            }
        }
    }
}
