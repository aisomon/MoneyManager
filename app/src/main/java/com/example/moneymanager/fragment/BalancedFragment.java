package com.example.moneymanager.fragment;


import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moneymanager.R;
import com.example.moneymanager.config.Constant;
import com.example.moneymanager.db.AppExecutors;
import com.example.moneymanager.db.UserRoomDatabase;
import com.example.moneymanager.utils.Constants;
import com.example.moneymanager.utils.ExpenseList;
import com.example.moneymanager.utils.SharedPrefsUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalancedFragment extends Fragment {

    private UserRoomDatabase userRoomDatabase;
    private PieChart pieChart;
    private TextView balanceTv, budgetTv,expenseTv;
    private TextView dateTv;

    private int balancedAmount, budgetAmount, expenseAmount;
    private int foodExpense,rechargeExpense,shoppingExpense,transportExpense, otherExpense;

    private String firstDate;

    ArrayList<ExpenseList> expenseList;



    public BalancedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_balanced, container, false);

        pieChart = view.findViewById(R.id.balanced_pieChart);
        userRoomDatabase = UserRoomDatabase.getInstance(getContext());

        balanceTv = view.findViewById(R.id.totalAmount_tv);
        budgetTv = view.findViewById(R.id.amount_for_budget);
        expenseTv = view.findViewById(R.id.amount_for_Expense);

        dateTv = view.findViewById(R.id.dateTextView);

        expenseList = new ArrayList<>();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_view_expenses);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        getAllBalancedAmount();
        setupPieChart();

        return view;
    }

    private void setupPieChart() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                getAllPieValue();

                expenseList.clear();
                if(foodExpense!=0)
                    expenseList.add(new ExpenseList("Food",foodExpense));
                if(rechargeExpense!=0)
                    expenseList.add(new ExpenseList("Recharge",rechargeExpense));
                if(shoppingExpense!=0)
                    expenseList.add(new ExpenseList("Shopping",shoppingExpense));
                if(transportExpense!=0)
                    expenseList.add(new ExpenseList("Transport",transportExpense));
                if(otherExpense!=0)
                    expenseList.add(new ExpenseList("Others",otherExpense));

            }

        });
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {

                List<PieEntry> pieEntries = new ArrayList<>();
                for(int i=0; i<expenseList.size();i++){
                    pieEntries.add(new PieEntry(expenseList.get(i).getAmount(),expenseList.get(i).getCategory()));
                }
                pieChart.setVisibility(View.VISIBLE);
                PieDataSet pieDataSet = new PieDataSet(pieEntries, null);
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                PieData pieData = new PieData(pieDataSet);

                pieData.setValueTextSize(18);
                pieData.setValueTextColor(Color.WHITE);
                pieData.setValueFormatter(new PercentFormatter());
                pieChart.setUsePercentValues(true);
                pieChart.setData(pieData);
                pieChart.animateY(1000);
                pieChart.invalidate();

                pieChart.getDescription().setText("");
                // bellow is not yet completed it's can customize
                // link for next https://github.com/PhilJay/MPAndroidChart/issues/2478
                Legend l = pieChart.getLegend();

            }
        });
    }

    private void getAllPieValue() {
            foodExpense = userRoomDatabase.userItemDaoAbs().getAmountByCategoryType(Constants.addFood,SharedPrefsUtils
                    .getIntegerPreference(getContext(),
                            Constant.USER_ID,
                            1));
            rechargeExpense = userRoomDatabase.userItemDaoAbs().getAmountByCategoryType(Constants.addRecharge,SharedPrefsUtils
                    .getIntegerPreference(getContext(),
                            Constant.USER_ID,
                            1));
            shoppingExpense = userRoomDatabase.userItemDaoAbs().getAmountByCategoryType(Constants.addShopping,SharedPrefsUtils
                    .getIntegerPreference(getContext(),
                            Constant.USER_ID,
                            1));
            transportExpense = userRoomDatabase.userItemDaoAbs().getAmountByCategoryType(Constants.addTransport,SharedPrefsUtils
                    .getIntegerPreference(getContext(),
                            Constant.USER_ID,
                            1));
            otherExpense = userRoomDatabase.userItemDaoAbs().getAmountByCategoryType(Constants.addOthers,SharedPrefsUtils
                    .getIntegerPreference(getContext(),
                            Constant.USER_ID,
                            1));
    }

    private void getAllBalancedAmount() {
        firstDate = userRoomDatabase.userItemDaoAbs().getFirstDate(SharedPrefsUtils
                        .getIntegerPreference(getContext(),
                                Constant.USER_ID,
                                1));
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                firstDate = userRoomDatabase.userItemDaoAbs().getFirstDate(SharedPrefsUtils
//                        .getIntegerPreference(getContext(),
//                                Constant.USER_ID,
//                                1));
//            }
//        });

        // it's already in a simple date format
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        String first = firstDate;    //df.format(new Date(firstDate));

        Date today = Calendar.getInstance().getTime();
        String todayDate= df.format(today);
        if(firstDate == null){
            first = todayDate;
        }
        String Date =first+" - "+todayDate;
        dateTv.setText(Date);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int budget = userRoomDatabase.userItemDaoAbs().getAmountByTransactionType(Constants.addIncome, SharedPrefsUtils
                        .getIntegerPreference(getContext(),
                                Constant.USER_ID,
                                1));
                budgetAmount = budget;
                int expense = userRoomDatabase.userItemDaoAbs().getAmountByTransactionType(Constants.addExpense,SharedPrefsUtils
                        .getIntegerPreference(getContext(),
                                Constant.USER_ID,
                                1));
                expenseAmount = expense;
                balancedAmount = budget - expense;
            }
        });

        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                balanceTv.setText(String.valueOf(balancedAmount));
                budgetTv.setText(String.valueOf(budgetAmount));
                expenseTv.setText(String.valueOf(expenseAmount));
            }
        });
    }

}
