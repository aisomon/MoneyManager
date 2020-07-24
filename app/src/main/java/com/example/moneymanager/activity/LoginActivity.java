package com.example.moneymanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanager.R;
import com.example.moneymanager.config.Constant;
import com.example.moneymanager.db.AppExecutors;
import com.example.moneymanager.db.UserRoomDatabase;
import com.example.moneymanager.utils.Constants;
import com.example.moneymanager.utils.SharedPrefsUtils;
import com.example.moneymanager.viewModel.UserViewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;
    private SharedPreferences sp;

    private Button buttonLogin;
//    private UserDao db;
//    private UserRoomDatabase roomDatabase;
    private UserViewModel userViewModel;
    private UserRoomDatabase userRoomDatabase;


    private TextView textViewRegistration, textViewForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initializing view, listener and objects
        initViews();
        sp = getSharedPreferences("login",MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
            goToMainActivity();
        }
        initListeners();
    }

    private void initListeners() {
        buttonLogin.setOnClickListener(this);
        textViewRegistration.setOnClickListener(this);
        textViewForgotPassword.setOnClickListener(this);
    }

    private void initViews() {
        textInputEditTextEmail = findViewById(R.id.ed_email_id);
        textInputEditTextPassword = findViewById(R.id.ed_password_id);

        buttonLogin = findViewById(R.id.bt_login);
        textViewRegistration = findViewById(R.id.tv_dont_have_account);
        textViewForgotPassword = findViewById(R.id.tv_forgot_password);

        //Setup the userViewModel
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userRoomDatabase = UserRoomDatabase.getInstance(getApplicationContext());

//        roomDatabase = Room.databaseBuilder(this,UserRoomDatabase.class,"user")
//                .allowMainThreadQueries()
//                .build();
//
//        db = roomDatabase.userDao();

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            
            case R.id.bt_login:
                userLogin();
                break;

            case R.id.tv_dont_have_account:
                Intent intentRegister = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intentRegister);
                break;

            case R.id.tv_forgot_password:
                Intent intentForgotPassword = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intentForgotPassword);
                break;
        }

    }

    private void userLogin() {
        if(validate()){
            try {
                userViewModel.getUser(textInputEditTextEmail.getText().toString().trim(),textInputEditTextPassword.getText().toString().trim())
                        .observe(this,user ->{

                                    if(user != null){
                                        SharedPrefsUtils.setIntegerPreference(LoginActivity.this, Constant.USER_ID, user.getId());
                                        checkBudget();
                                        goToMainActivity();
                                        sp.edit().putBoolean("logged",true).apply();
                                        emptyTextInput();
                                        finish();
                                    }
                                    if(user == null){
                                        Toast.makeText(getApplicationContext(),"Incorrect email or password",Toast.LENGTH_SHORT).show();                                }
                                }

                                );
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    // for checking that, is the budget updated or not
    private void checkBudget() {
        int currentBudget;
        currentBudget = userRoomDatabase.userItemDaoAbs().getAmountByTransactionType(Constants.addIncome, SharedPrefsUtils
                .getIntegerPreference(getApplicationContext(),
                        Constant.USER_ID,
                        1));
        if(currentBudget == 0){
            Toast.makeText(getApplicationContext(),"Please update your Budget!",Toast.LENGTH_SHORT).show();
        }
    }


    private void goToMainActivity() {
        Intent intentHome = new Intent(this, MainActivity.class);
        startActivity(intentHome);
    }

    private void emptyTextInput() {

        textInputEditTextEmail.setText("");
        textInputEditTextPassword.setText("");
    }

    /*
     * Validating email and password, returns false if any of the validation fails
     * */
    private boolean validate() {
        if (TextUtils.isEmpty(textInputEditTextEmail.getText().toString().trim()) ||TextUtils.isEmpty(textInputEditTextPassword.getText().toString().trim()) ) {
            Toast.makeText(this, "Please fill up the requirement", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
