package org.o6u.studentapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.o6u.studentapp.R;
import org.o6u.studentapp.activities.EditActivity;
import org.o6u.studentapp.customViews.CustomButton;
import org.o6u.studentapp.customViews.CustomTextView;
import org.o6u.studentapp.models.Grade;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private CircleImageView imageView;
    private CustomTextView nameTextView;
    private CustomTextView emailTextView;
    private CustomTextView phoneTextView;
    private CustomTextView idTextView;
    private CustomTextView departmentTextView;
    private CustomTextView gpaTextView;
    private CustomTextView yearTextView;
    private CustomButton editButton;

    // Database
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;

    private String userID;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_profile, container, false);

        imageView = mView.findViewById(R.id.profile_imageView);
        nameTextView = mView.findViewById(R.id.profile_name_textView);
        emailTextView = mView.findViewById(R.id.profile_email_textView);
        phoneTextView = mView.findViewById(R.id.profile_phone_textView);
        idTextView = mView.findViewById(R.id.profile_id_textView);
        departmentTextView = mView.findViewById(R.id.profile_department_textView);
        gpaTextView = mView.findViewById(R.id.profile_gpa_textView);
        yearTextView = mView.findViewById(R.id.profile_year_textView);
        editButton = mView.findViewById(R.id.profile_edit_button);

        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        //KeepSynced
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Students").child(userID);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                nameTextView.setText("Name: " + dataSnapshot.child("name").getValue().toString());
                emailTextView.setText("Email: " + dataSnapshot.child("email").getValue().toString());
                phoneTextView.setText("Phone: " + dataSnapshot.child("phone").getValue().toString());
                idTextView.setText("ID: " + dataSnapshot.child("id").getValue().toString());
                departmentTextView.setText("Department: " + dataSnapshot.child("department").getValue().toString());
                gpaTextView.setText("GPA: " + dataSnapshot.child("gpa").getValue().toString());
                yearTextView.setText("Year: " + dataSnapshot.child("year").getValue().toString());

                mDatabaseRef.keepSynced(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error In Connection!", Toast.LENGTH_SHORT).show();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EditActivity.class);
                startActivity(i);
            }
        });

        return mView;
    }
}
