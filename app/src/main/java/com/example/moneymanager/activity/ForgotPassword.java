package com.example.moneymanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moneymanager.R;
import com.example.moneymanager.db.UserRoomDatabase;
import com.example.moneymanager.entities.User;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText fgSecureWord, fgNewPassword, fgConfirmPassword;
    private Button bt_fgConfirm;
    private UserRoomDatabase userRoomDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setTitle("Recovery password");

        init();
        bt_fgConfirm.setOnClickListener(this);
    }

    private boolean correctSecureWord() {
        String secureWord= fgSecureWord.getText().toString().trim().toLowerCase();
        User user = userRoomDatabase.userDao().getSecuredAnswer(secureWord);
        if(user == null){
            Toast.makeText(getApplicationContext(),"Invalid Secured word",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateFields() {
        if (TextUtils.isEmpty(fgSecureWord.getText().toString().trim())) {
            Toast.makeText(this, "Invalid secure world", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(fgNewPassword.getText().toString().trim())) {
            Toast.makeText(this, "Invalid new password filed", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(fgConfirmPassword.getText().toString().trim())) {
            Toast.makeText(this, "Invalid re password filed", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void init() {

        fgSecureWord = findViewById(R.id.fg_secure_word_id);
        fgNewPassword  = findViewById(R.id.fg_new_password);
        fgConfirmPassword = findViewById(R.id.fg_re_password);
        bt_fgConfirm = findViewById(R.id.btn_fg_password);
        userRoomDatabase = UserRoomDatabase.getInstance(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        if(validateFields()){
            if(correctSecureWord()){
                User user = userRoomDatabase.userDao().getSecuredAnswer(fgSecureWord.getText().toString().trim().toLowerCase());
                userRoomDatabase.userDao().updateUserBySecurityAnswer(user.getId(),fgNewPassword.getText().toString().trim().toLowerCase());
                Toast.makeText(getApplicationContext(),"Password successfully changed",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }

        }
    }
}
