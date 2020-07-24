package com.example.moneymanager.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.moneymanager.R;
import com.example.moneymanager.config.Constant;
import com.example.moneymanager.db.UserRoomDatabase;
import com.example.moneymanager.fragment.AlerDialogFragment;
import com.example.moneymanager.fragment.DialogFragment;
import com.example.moneymanager.fragment.FoodFragment;
import com.example.moneymanager.fragment.LendBorrowFragment;
import com.example.moneymanager.fragment.OthersFragment;
import com.example.moneymanager.fragment.RechargeFragment;
import com.example.moneymanager.fragment.ShoppingFragment;
import com.example.moneymanager.fragment.TransportFragment;
import com.example.moneymanager.fragment.ViewExpensesFragment;
import com.example.moneymanager.utils.Constants;
import com.example.moneymanager.utils.SharedPrefsUtils;
import com.google.android.material.navigation.NavigationView;
import com.maltaisn.calcdialog.CalcDialog;
import java.math.BigDecimal;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, CalcDialog.CalcDialogCallback {

    // for toggle bar
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    private ImageButton foodAmount;
    private ImageButton rechargeAmount;
    private ImageButton shoppingAmount;
    private ImageButton transportAmount;
    private ImageButton otherAmount;
    private ImageButton notesButton;
    private ImageButton lendBorrowButton;
    private TextView userEmail;

    private UserRoomDatabase userRoomDatabase;
//    private long backPressedTime;
//    private Toast backToast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // for the toolbar , change the style.xml file
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.open,R.string.close);


        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        init();

        // to get user name for navigation header
        String name = String.valueOf(userRoomDatabase.userDao().getUserById(SharedPrefsUtils
                .getIntegerPreference(getApplicationContext(),
                        Constant.USER_ID,
                        1)).getEmail());
        // for navigation
        View nView = navigationView.getHeaderView(0);
        userEmail = nView.findViewById(R.id.nav_header_tv_name);
        userEmail.setText(name);
        rechargeAmount.setOnClickListener(this);
        shoppingAmount.setOnClickListener(this);
        transportAmount.setOnClickListener(this);
        otherAmount.setOnClickListener(this);
        foodAmount.setOnClickListener(this);
        notesButton.setOnClickListener(this);
        lendBorrowButton.setOnClickListener(this);


    }



    private void init() {
        foodAmount = (ImageButton) findViewById(R.id.bt_food_id);
        rechargeAmount = (ImageButton) findViewById(R.id.bt_recharge_id);
        shoppingAmount = (ImageButton) findViewById(R.id.bt_shopping_id);
        transportAmount = (ImageButton) findViewById(R.id.bt_transport_id);
        otherAmount = (ImageButton) findViewById(R.id.bt_others_id);
        notesButton  = (ImageButton) findViewById(R.id.bt_notes_id);
        lendBorrowButton = findViewById(R.id.bt_exchange_id);
        userRoomDatabase = UserRoomDatabase.getInstance(getApplicationContext());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.bt_food_id:

                FragmentManager fragmentManager = getSupportFragmentManager();
                FoodFragment foodFragment = new FoodFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container,foodFragment).addToBackStack(null).commit();

                break;

//            case R.id.bt_food_id:
//
//                DialogFragment foodDialog = new DialogFragment();
//                Bundle fBundle = new Bundle();
//                fBundle.putString("from", Constants.addFood);
//                foodDialog.setArguments(fBundle);
//                foodDialog.show(getSupportFragmentManager(),"Food dialog");
//                break;

            case R.id.bt_recharge_id:

                FragmentManager rFragmentManager = getSupportFragmentManager();
                RechargeFragment rechargeFragment = new RechargeFragment();
                rFragmentManager.beginTransaction().replace(R.id.fragment_container,rechargeFragment).addToBackStack(null).commit();
                break;

            case R.id.bt_shopping_id:

                FragmentManager sFragmentManager = getSupportFragmentManager();
                ShoppingFragment shoppingFragment = new ShoppingFragment();
                sFragmentManager.beginTransaction().replace(R.id.fragment_container,shoppingFragment).addToBackStack(null).commit();
                break;

            case R.id.bt_transport_id:

                FragmentManager tFragmentManager = getSupportFragmentManager();
                TransportFragment transportFragment = new TransportFragment();
                tFragmentManager.beginTransaction().replace(R.id.fragment_container,transportFragment).addToBackStack(null).commit();
                break;

            case R.id.bt_others_id:

                FragmentManager oFragmentManager = getSupportFragmentManager();
                OthersFragment othersFragment = new OthersFragment();
                oFragmentManager.beginTransaction().replace(R.id.fragment_container,othersFragment).addToBackStack(null).commit();;
                break;
            case R.id.bt_notes_id:
                Intent intentNotes = new Intent(getApplicationContext(), NoteActivity.class);
                startActivity(intentNotes);
                break;
            case R.id.bt_exchange_id:
                openFragment();

                break;
        }

    }

    private void openFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        LendBorrowFragment lendBorrowFragment = new LendBorrowFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,lendBorrowFragment).addToBackStack(null).commit();
        drawerLayout.closeDrawers();
    }


    // for navigation drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.item_time_line:{
                Intent intent = new Intent(getApplicationContext(), TimelineActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.item_reset_total:{
                openAlertDialog();
                break;
            }
            case R.id.item_view_expenses:

                FragmentManager fragmentManager = getSupportFragmentManager();
                ViewExpensesFragment viewExpensesFragment = new ViewExpensesFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container,viewExpensesFragment).addToBackStack(null).commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.item_upgrade:{
                DialogFragment othersDialog = new DialogFragment();
                Bundle oBundle = new Bundle();
                oBundle.putString("from", Constants.addIncome);
                othersDialog.setArguments(oBundle);
                othersDialog.show(getSupportFragmentManager(),"Upgrade dialog");
                break;
            }
            case R.id.item_calculator:{

                final CalcDialog calcDialog = new CalcDialog();
                calcDialog.getSettings().getInitialValue();
                calcDialog.show(getSupportFragmentManager(),"calc_dialog");
                break;
            }
            case R.id.item_share:{
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "This is a money budget app with a friendly user interface";// this is for app link
                String shareSubject = "MoneyManager app";

                shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                shareIntent.putExtra(Intent.EXTRA_TEXT,shareSubject);

                startActivity(Intent.createChooser(shareIntent,"Share with"));
                break;
            }
            case R.id.item_logout:{

                SharedPreferences preferences =getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openAlertDialog() {

        AlerDialogFragment alertFragment = new AlerDialogFragment();
        alertFragment.show(getSupportFragmentManager(),"Reset Total");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onValueEntered(int requestCode, @Nullable BigDecimal value) {

    }
}
