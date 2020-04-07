package org.o6u.studentapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.o6u.studentapp.R;
import org.o6u.studentapp.adapters.GradeAdapter;
import org.o6u.studentapp.models.Grade;

import java.util.ArrayList;
import java.util.List;

public class GradesFragment extends Fragment {

    private RecyclerView mGradesList;
    private DatabaseReference mDatabaseRef;
    private List<Grade> grades;
    private GradeAdapter adapter;

    public GradesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_grades, container, false);

        //Define RecyclerView Elements
        mGradesList = mView.findViewById(R.id.grades_recyclerView);
        mGradesList.setHasFixedSize(true);
        mGradesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        grades = new ArrayList<>();

        adapter = new GradeAdapter(getActivity(), grades);
        mGradesList.setAdapter(adapter);

        getStudentsData();

        return mView;
    }

    //Get Data In RecyclerView
    private void getStudentsData(){

        final Bundle bundle = getArguments();
        String studentId = (String) bundle.get("STUDENT_ID");

        //KeepSynced
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Enrollements").child(studentId);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Grade grade = snapshot.getValue(Grade.class);
                    grades.add(grade);
                }
                adapter.notifyDataSetChanged();
                mDatabaseRef.keepSynced(true);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error In Connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
