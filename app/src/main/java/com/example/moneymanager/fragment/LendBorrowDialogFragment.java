package com.example.moneymanager.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moneymanager.R;
import com.example.moneymanager.config.Constant;
import com.example.moneymanager.entities.UserTransaction;
import com.example.moneymanager.utils.Constants;
import com.example.moneymanager.utils.SharedPrefsUtils;
import com.example.moneymanager.viewModel.TransactionViewModel;
import com.example.moneymanager.viewModel.UserItemViewModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class LendBorrowDialogFragment extends AppCompatDialogFragment {

    private TransactionViewModel transactionViewModel;


    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_lend_borrow_dialog,null);

        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Bundle bundle = getArguments();
                        final String bundleFrom = bundle.getString("lend","");
                        final EditText amountInputEditTex = view.findViewById(R.id.et_amount_LB);
                        final EditText nameInputEditTex = view.findViewById(R.id.et_name_LB);

                        if(!TextUtils.isEmpty(nameInputEditTex.getText().toString().trim()) || !TextUtils.isEmpty(amountInputEditTex.getText().toString().trim())){

                            if(bundleFrom.equals(Constants.addLend)){
                                String amount = amountInputEditTex.getText().toString().trim();
                                String name = nameInputEditTex.getText().toString().trim();
                                Date date = Calendar.getInstance().getTime();
                                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                String transactionType = Constants.addLend;
                                String dateOfExpenses = format.format(date);
                                if(!TextUtils.isEmpty(amountInputEditTex.getText().toString().trim())){
                                    transactionViewModel.addTransaction(SharedPrefsUtils
                                                    .getIntegerPreference(getContext(),
                                                            Constant.USER_ID,
                                                            1),
                                            name,
                                            amount,
                                            dateOfExpenses,
                                            transactionType);
                                }
                            }
                            else if(bundleFrom.equals(Constants.addBorrow)){

                                String amount = amountInputEditTex.getText().toString().trim();
                                String name = nameInputEditTex.getText().toString().trim();
                                Date date = Calendar.getInstance().getTime();
                                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                String transactionType = Constants.addBorrow;
                                String dateOfExpenses = format.format(date);
                                if(!TextUtils.isEmpty(amountInputEditTex.getText().toString().trim())){
                                    transactionViewModel.addTransaction(SharedPrefsUtils
                                                    .getIntegerPreference(getContext(),
                                                            Constant.USER_ID,
                                                            1),
                                            name,
                                            amount,
                                            dateOfExpenses,
                                            transactionType);
                                }
                            }

                        }
                        else {
                            Toast.makeText(getContext(),"Something wrong!!!",Toast.LENGTH_SHORT).show();
                        }
                    }

                });
        return builder.create();
    }

}

////// for another type of data getter
//    Calendar calendar = Calendar.getInstance();
//    String dateOfExpenses = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());