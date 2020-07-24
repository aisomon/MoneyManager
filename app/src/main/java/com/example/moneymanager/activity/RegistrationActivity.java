package com.example.moneymanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.moneymanager.R;
import com.example.moneymanager.entities.User;
import com.example.moneymanager.db.UserRoomDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private Button signUpButton;
    private TextView alreadyHaveAccount;

//    private EditText edName;
    private EditText edEmail;
    private EditText edPassword, edConfPassword;
    private EditText edSecurityQuestions;

    private UserRoomDatabase userRoomDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        signUpButton = findViewById(R.id.btn_login);
        alreadyHaveAccount = findViewById(R.id.already_have_account);
//        edName = findViewById(R.id.reg_ed_name);
        edEmail = findViewById(R.id.reg_ed_email);
        edPassword = findViewById(R.id.reg_ed_password);
        edConfPassword = findViewById(R.id.reg_ed_conf_password);
        edSecurityQuestions = findViewById(R.id.ed_security_question);

        userRoomDatabase = UserRoomDatabase.getInstance(getApplicationContext());

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(validateFields() && userExists()){
                    registerUser();
                }
                else {
                    Toast.makeText(getApplicationContext(),"User already exists!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean userExists() {
        String email= edEmail.getText().toString().trim().toLowerCase();
        User user = userRoomDatabase.userDao().getUser(email);
        if( user!= null){
            return false;
        }
        return true;
    }

    private void registerUser(){

//                    String name = edName.getText().toString().trim();
                    String email= edEmail.getText().toString().trim().toLowerCase();
                    String password = edPassword.getText().toString().trim();
                    String question = edSecurityQuestions.getText().toString().trim().toLowerCase();
                    final User user = new User(null,email,password,question);
                    userRoomDatabase.userDao().addUser(user);
                    Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
    };


    private boolean validateFields() {

        if (TextUtils.isEmpty(edPassword.getText().toString().trim())) {
            Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edEmail.getText().toString().trim())|| !Patterns.EMAIL_ADDRESS.matcher(edEmail.getText().toString().trim()).matches()) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edConfPassword.getText().toString().trim())) {
            Toast.makeText(this, "Invalid confirm password", Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(edSecurityQuestions.getText().toString().trim())) {
            Toast.makeText(this, "Invalid confirm password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!edPassword.getText().toString().trim().matches(edConfPassword.getText().toString().trim())) {
            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
