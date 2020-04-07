package org.o6u.studentapp.registrationActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.o6u.studentapp.R;
import org.o6u.studentapp.activities.MainActivity;
import org.o6u.studentapp.customViews.CustomButton;
import org.o6u.studentapp.customViews.CustomTextView;
import org.o6u.studentapp.validation.InputValidation;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private CustomTextView titleToolbarTextView;

    private TextInputLayout fullNameTIL;
    private TextInputLayout emailTIL;
    private TextInputLayout idTIL;
    private TextInputLayout phoneTIL;
    private TextInputLayout passwordTIL;
    private CustomButton submitButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    //ProgressDialog
    private ProgressDialog mProgressDialog;

    //Validation
    InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();

        mAuth = FirebaseAuth.getInstance();

        //ProgressDialog
        mProgressDialog = new ProgressDialog(this);

        //Validation
        inputValidation = new InputValidation();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(inputValidation.registrationValidation(fullNameTIL,emailTIL,idTIL,phoneTIL,passwordTIL)) {

                    mProgressDialog.setTitle("Registering Student");
                    mProgressDialog.setMessage("Please Wait While We Create Your Account.");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();

                    registerUser();
                }
            }
        });
    }

    private void initViews(){

        mToolbar = (Toolbar) findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        titleToolbarTextView = (CustomTextView) findViewById(R.id.toolbar_title_text_view);
        titleToolbarTextView.setText(R.string.new_account);

        fullNameTIL = (TextInputLayout) findViewById(R.id.registration_name_text_input_layout);
        emailTIL = (TextInputLayout) findViewById(R.id.registration_email_text_input_layout);
        idTIL = (TextInputLayout) findViewById(R.id.registration_id_text_input_layout);
        phoneTIL = (TextInputLayout) findViewById(R.id.registration_phone_text_input_layout);
        passwordTIL = (TextInputLayout) findViewById(R.id.registration_password_text_input_layout);
        passwordTIL.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        submitButton = (CustomButton) findViewById(R.id.registration_submit_button);
    }

    @SuppressWarnings("ConstantConditions")
    private void registerUser () {

        final String name = fullNameTIL.getEditText().getText().toString();
        final String email = emailTIL.getEditText().getText().toString();
        final String id = idTIL.getEditText().getText().toString();
        final String phone = phoneTIL.getEditText().getText().toString();
        String password = passwordTIL.getEditText().getText().toString();


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Students").child(uid);

                    //Token ID
                    String deviceToken = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("image", "default");
                    userMap.put("name", name);
                    userMap.put("email", email);
                    userMap.put("id","ID: " + id);
                    userMap.put("phone",phone);
                    userMap.put("year","Year: ...");
                    userMap.put("gpa","GPA: ...");
                    userMap.put("department","Department: ...");
                    userMap.put("deviceToken", deviceToken);

                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                mProgressDialog.dismiss();
                                Intent mainIntent = new Intent(RegistrationActivity.this, MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            } else {
                                mProgressDialog.hide();
                                Toast.makeText(RegistrationActivity.this, "Error in Signing!. Please Try Again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    mProgressDialog.hide();
                    Toast.makeText(RegistrationActivity.this, "Cannot Sign In. Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
