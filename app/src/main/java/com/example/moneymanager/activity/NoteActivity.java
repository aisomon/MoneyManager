package com.example.moneymanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.moneymanager.fragment.NotesFragment;
import com.example.moneymanager.R;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener {

    public ImageButton imageButtonAdd;
//    private ImageButton imageButtonPrev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        try {
            NotesFragment notesFragment = new NotesFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.notes_fragment_container, notesFragment)
                    .commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        init();
    }

    private void init() {
        imageButtonAdd = findViewById(R.id.ib_last_btn);
//        imageButtonPrev = findViewById(R.id.ib_prev_btn);
        imageButtonAdd.setOnClickListener(this);
//        imageButtonPrev.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ib_last_btn:

                try {
                    startActivity(new Intent(getApplicationContext(), AddnoteActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
//            case R.id.ib_prev_btn:
//                startActivity(new Intent(this, MainActivity.class));
//                finish();
//                break;
        }

    }
}
