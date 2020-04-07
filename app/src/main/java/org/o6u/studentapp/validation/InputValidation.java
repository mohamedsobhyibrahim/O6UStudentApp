package org.o6u.studentapp.validation;

import android.support.design.widget.TextInputLayout;
import android.util.Patterns;

public class InputValidation {

    @SuppressWarnings("ConstantConditions")
    public boolean registrationValidation(TextInputLayout nameTIL, TextInputLayout emailTIL, TextInputLayout idTIL,
                                          TextInputLayout phoneTIL, TextInputLayout passwordTIL){

        String name = nameTIL.getEditText().getText().toString();
        String email = emailTIL.getEditText().getText().toString();
        String phone = phoneTIL.getEditText().getText().toString();
        String password = passwordTIL.getEditText().getText().toString();
        String id = idTIL.getEditText().getText().toString();

        if(name.isEmpty() || email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || id.isEmpty()
                || phone.isEmpty() || password.isEmpty()){

            if(name.isEmpty()){
                nameTIL.getEditText().setError("Enter your name!");
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailTIL.getEditText().setError("Email address not correct!");
            }
            if(email.isEmpty()){
                emailTIL.getEditText().setError("Enter your email!");
            }
            if(id.isEmpty()){
                idTIL.getEditText().setError("Enter your ID!");
            }
            if(phone.isEmpty()){
                phoneTIL.getEditText().setError("Enter your phone number!");
            }
            if(password.length() < 8){
                passwordTIL.getEditText().setError("Password should contains a-z, A-Z, 0-9 and min 8 characters!");
            }
            if(password.isEmpty()){
                passwordTIL.getEditText().setError("Enter password!");
            }
            return false;
        }
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    public boolean loginValidation(TextInputLayout emailTIL, TextInputLayout passwordTIL){

        String email = emailTIL.getEditText().getText().toString();
        String password = passwordTIL.getEditText().getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.isEmpty()) {

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailTIL.getEditText().setError("Email address not correct!");
            }
            if (email.isEmpty()) {
                emailTIL.getEditText().setError("Enter email address!");
            }
            if (password.isEmpty()) {
                passwordTIL.getEditText().setError("Enter password!");
            }
            return false;
        } else {
            return true;
        }
    }

    @SuppressWarnings("ConstantConditions")
    public boolean editValidation(TextInputLayout nameTIL, TextInputLayout phoneTIL, TextInputLayout yearTIL){

        String name = nameTIL.getEditText().getText().toString();
        String phone = phoneTIL.getEditText().getText().toString();
        String year = yearTIL.getEditText().getText().toString();

        if (name.isEmpty() || phone.isEmpty() || year.isEmpty()) {

            if (name.isEmpty()) {
                nameTIL.getEditText().setError("Enter Your Name!");
            }
            if (phone.isEmpty()) {
                phoneTIL.getEditText().setError("Enter Phone Number!");
            }
            if (year.isEmpty()) {
                yearTIL.getEditText().setError("Enter Year!");
            }
            return false;
        } else {
            return true;
        }
    }
}
