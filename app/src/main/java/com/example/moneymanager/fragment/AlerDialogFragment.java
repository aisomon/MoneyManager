package com.example.moneymanager.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.moneymanager.config.Constant;
import com.example.moneymanager.db.UserRoomDatabase;
import com.example.moneymanager.utils.SharedPrefsUtils;

import androidx.appcompat.app.AppCompatDialogFragment;


public class AlerDialogFragment extends AppCompatDialogFragment {

    private UserRoomDatabase userRoomDatabase;

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        userRoomDatabase = UserRoomDatabase.getInstance(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Reset your budget")
                .setMessage("Are you sure, You want to delete all budget?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //start from here
                        userRoomDatabase.userItemDaoAbs().deleteByUserId(SharedPrefsUtils
                                .getIntegerPreference(getContext(),
                                        Constant.USER_ID,
                                        1));
                        Toast.makeText(getContext(),"Successfully deleted your total budget",Toast.LENGTH_SHORT).show();
                    }
                });


        return builder.create();
    }
}
