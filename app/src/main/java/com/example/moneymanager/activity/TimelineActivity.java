package com.example.moneymanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;


import com.example.moneymanager.R;
import com.example.moneymanager.fragment.BalancedFragment;

public class TimelineActivity extends AppCompatActivity {

//    private ImageButton imageButtonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        BalancedFragment balancedFragment = new BalancedFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container,balancedFragment)
                .commit();

//        imageButtonBack= findViewById(R.id.ib_prev_btn);
//        imageButtonBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(TimelineActivity.this, MainActivity.class));
//                finish();
//            }
//        });
    }
}
