package org.o6u.studentapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.o6u.studentapp.R;
import org.o6u.studentapp.customViews.CustomButton;
import org.o6u.studentapp.customViews.CustomEditText;
import org.o6u.studentapp.customViews.CustomTextView;
import org.o6u.studentapp.registrationActivities.LoginActivity;
import org.o6u.studentapp.registrationActivities.RegistrationActivity;
import org.o6u.studentapp.validation.InputValidation;

import java.util.HashMap;

public class EditActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private CustomTextView titleToolbarTextView;

    private TextInputLayout nameTIL;
    private TextInputLayout phoneTIL;
    private MaterialSpinner departmentSpinner;
    private String department;
    private TextInputLayout yearTIL;
    private CustomButton editButton;

    // Database
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;

    private String userID;

    private InputValidation validation;

    //ProgressDialog
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mToolbar = (Toolbar) findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        titleToolbarTextView = (CustomTextView) findViewById(R.id.toolbar_title_text_view);
        titleToolbarTextView.setText("Edit Account");

        nameTIL = (TextInputLayout) findViewById(R.id.edit_name_text_input_layout);
        phoneTIL = (TextInputLayout) findViewById(R.id.edit_phone_text_input_layout);
        departmentSpinner = (MaterialSpinner) findViewById(R.id.edit_department_spinner);
        departmentSpinner.setItems("CS", "IS", "GN");
        yearTIL = (TextInputLayout) findViewById(R.id.edit_year_text_input_layout);
        editButton = (CustomButton) findViewById(R.id.edit_button);

        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        //ProgressDialog
        mProgressDialog = new ProgressDialog(this);

        //KeepSynced
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Students").child(userID);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                nameTIL.getEditText().setText(dataSnapshot.child("name").getValue().toString());
                phoneTIL.getEditText().setText(dataSnapshot.child("phone").getValue().toString());
                yearTIL.getEditText().setText(dataSnapshot.child("year").getValue().toString());

                department = dataSnapshot.child("department").getValue().toString();
                departmentSpinner.setText(department);

                mDatabaseRef.keepSynced(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EditActivity.this, "Error In Connection!", Toast.LENGTH_SHORT).show();
            }
        });

        departmentSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if(item.equals("CS")){
                    department = "CS";
                } else if(item.equals("IS")){
                    department = "IS";
                }
                else if(item.equals("GN")){
                    department = "GN";
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation = new InputValidation();

                if (validation.editValidation(nameTIL, phoneTIL, yearTIL)) {

                    mProgressDialog.setTitle("Editing Information");
                    mProgressDialog.setMessage("Please Wait While We Edit Your Information.");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();

                    final DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Students");
                    final String current_user_id = mAuth.getCurrentUser().getUid();
                    mUserDatabase.child(current_user_id).child("name").setValue(nameTIL.getEditText().getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mUserDatabase.child(current_user_id).child("phone").setValue(phoneTIL.getEditText().getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    mUserDatabase.child(current_user_id).child("department").setValue(department).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    mUserDatabase.child(current_user_id).child("year").setValue(yearTIL.getEditText().getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    finish();
                                                                            }
                                                                            });
                                                                }
                                                            });
                                                }
                                            });
                                }
                            });
                }
            }
        });
    }
}
