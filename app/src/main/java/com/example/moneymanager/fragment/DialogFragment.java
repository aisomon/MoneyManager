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
import com.example.moneymanager.utils.Constants;
import com.example.moneymanager.utils.SharedPrefsUtils;
import com.example.moneymanager.viewModel.UserItemViewModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class DialogFragment extends AppCompatDialogFragment {

    private UserItemViewModel userItemViewModel;
    private Bundle bundle;
    private String bundleFrom;
    private EditText amountInputEditTex;
    private String amount, description;
    private String categories;
    private Date date;
    private DateFormat format;
    private EditText edDescription;


    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment,null);

        userItemViewModel = ViewModelProviders.of(this).get(UserItemViewModel.class);
        bundle = getArguments();
        amountInputEditTex = view.findViewById(R.id.ed_expenses_amount);
        edDescription = view.findViewById(R.id.ed_expenses_description);
        date = Calendar.getInstance().getTime();
        format = new SimpleDateFormat("dd/MM/yyyy");

        builder.setView(view)
                .setTitle("Enter your Amount")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bundleFrom = bundle.getString("from","");

                        if(!TextUtils.isEmpty(amountInputEditTex.getText().toString().trim())&& !TextUtils.isEmpty(edDescription.getText().toString().trim())){

                            if(bundleFrom.equals(Constants.addFood)){
                                amount = amountInputEditTex.getText().toString().trim();
                                categories = Constants.addFood;
                                description = edDescription.getText().toString().trim();
                                String transactionType = Constants.addExpense;
                                String dateOfExpenses = format.format(date);
                                if(!TextUtils.isEmpty(amountInputEditTex.getText().toString().trim())){
                                    userItemViewModel.addItems(SharedPrefsUtils
                                                    .getIntegerPreference(getContext(),
                                                            Constant.USER_ID,
                                                            1),
                                            categories,
                                            amount,
                                            dateOfExpenses,
                                            transactionType,
                                            description);
                                }
                            }
                            else if(bundleFrom.equals(Constants.addRecharge)){
                                amount = amountInputEditTex.getText().toString().trim();
                                categories = Constants.addRecharge;
                                description = edDescription.getText().toString().trim();
                                String transactionType = Constants.addExpense;
                                String dateOfExpenses = format.format(date);
                                if(!TextUtils.isEmpty(amountInputEditTex.getText().toString().trim())){
                                    userItemViewModel.addItems(SharedPrefsUtils
                                                    .getIntegerPreference(getContext(),
                                                            Constant.USER_ID,
                                                            1),
                                            categories,
                                            amount,
                                            dateOfExpenses,
                                            transactionType,description);
                                }
                            }
                            else if(bundleFrom.equals(Constants.addShopping)){
                                amount = amountInputEditTex.getText().toString().trim();
                                categories = Constants.addShopping;
                                description = edDescription.getText().toString().trim();
                                String transactionType = Constants.addExpense;
                                String dateOfExpenses = format.format(date);
                                if(!TextUtils.isEmpty(amountInputEditTex.getText().toString().trim())){
                                    userItemViewModel.addItems(SharedPrefsUtils
                                                    .getIntegerPreference(getContext(),
                                                            Constant.USER_ID,
                                                            1),
                                            categories,
                                            amount,
                                            dateOfExpenses,
                                            transactionType,
                                            description);

                                }
                            }
                            else if(bundleFrom.equals(Constants.addTransport)){
                                amount = amountInputEditTex.getText().toString().trim();
                                categories = Constants.addTransport;
                                description = edDescription.getText().toString().trim();
                                String transactionType = Constants.addExpense;
                                String dateOfExpenses = format.format(date);
                                if(!TextUtils.isEmpty(amountInputEditTex.getText().toString().trim())){
                                    userItemViewModel.addItems(SharedPrefsUtils
                                                    .getIntegerPreference(getContext(),
                                                            Constant.USER_ID,
                                                            1),
                                            categories,
                                            amount,
                                            dateOfExpenses,
                                            transactionType,
                                            description);

                                }
                            }
                            else if(bundleFrom.equals(Constants.addOthers)){
                                amount = amountInputEditTex.getText().toString().trim();
                                categories = Constants.addOthers;
                                description = edDescription.getText().toString().trim();
                                String transactionType = Constants.addExpense;
                                String dateOfExpenses = format.format(date);
                                if(!TextUtils.isEmpty(amountInputEditTex.getText().toString().trim())){
                                    userItemViewModel.addItems(SharedPrefsUtils
                                                    .getIntegerPreference(getContext(),
                                                            Constant.USER_ID,
                                                            1),
                                            categories,
                                            amount,
                                            dateOfExpenses,
                                            transactionType,
                                            description);

                                }
                            }
                            else if(bundleFrom.equals(Constants.addIncome)){
                                amount = amountInputEditTex.getText().toString().trim();
                                categories = Constants.addIncome;
                                description = edDescription.getText().toString().trim();
                                String transactionType = Constants.addIncome;
                                String dateOfExpenses = format.format(date);
                                if(!TextUtils.isEmpty(amountInputEditTex.getText().toString().trim())){
                                    userItemViewModel.addItems(SharedPrefsUtils
                                                    .getIntegerPreference(getContext(),
                                                            Constant.USER_ID,
                                                            1),
                                            categories,
                                            amount,
                                            dateOfExpenses,
                                            transactionType,
                                            description);

                                }
                            }

                            Toast.makeText(getContext(),"Amount added successfully",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(),"Amount didn't add",Toast.LENGTH_SHORT).show();
                        }
                        }

                });
        return builder.create();
    }

}

////// for another type of data getter
//    Calendar calendar = Calendar.getInstance();
//    String dateOfExpenses = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());