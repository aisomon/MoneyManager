package com.example.moneymanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.moneymanager.R;
import com.example.moneymanager.config.Constant;
import com.example.moneymanager.utils.SharedPrefsUtils;
import com.example.moneymanager.viewModel.NotesViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddnoteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_title;
    private EditText et_description;
    private NotesViewModel notesViewModel;
    public Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        initView();
    }

    private void initView() {
        btn_save = findViewById(R.id.btn_save);
        et_title = findViewById(R.id.et_title);
        et_description = findViewById(R.id.et_description);

        btn_save.setOnClickListener(this);
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {

            try {
                if (!TextUtils.isEmpty(et_title.getText().toString().trim()) &&
                        !TextUtils.isEmpty(et_title.getText().toString().trim())) {
                    Date date = Calendar.getInstance().getTime();
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    String dateOfNotes = format.format(date);
                    notesViewModel
                            .addNotes(SharedPrefsUtils
                                            .getIntegerPreference(getApplicationContext(),
                                                    Constant.USER_ID,
                                                    1),
                                    et_title.getText().toString().trim(),
                                    et_description.getText().toString().trim(),
                                    dateOfNotes);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
