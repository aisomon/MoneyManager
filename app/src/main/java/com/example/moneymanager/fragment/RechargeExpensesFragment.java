package com.example.moneymanager.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moneymanager.R;
import com.example.moneymanager.adapter.ViewExpensesAdapter;
import com.example.moneymanager.config.Constant;
import com.example.moneymanager.db.UserRoomDatabase;
import com.example.moneymanager.utils.Constants;
import com.example.moneymanager.utils.SharedPrefsUtils;
import com.example.moneymanager.viewModel.UserItemViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RechargeExpensesFragment extends Fragment {
    private RecyclerView rv_food;
    private UserItemViewModel userItemViewModel;
    private ViewExpensesAdapter viewExpensesAdapter;
    private UserRoomDatabase userRoomDatabase;
    private TextView totalFood;


    public RechargeExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_food_expenses, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {

        rv_food = view.findViewById(R.id.rv_food_expenses);
        totalFood = view.findViewById(R.id.tv_total_food_expenses);
        userRoomDatabase = UserRoomDatabase.getInstance(getContext());
        userItemViewModel = ViewModelProviders.of(this).get(UserItemViewModel.class);

        int totalFoodExpenses = userRoomDatabase.userItemDaoAbs().getAmountByCategoryType(Constants.addRecharge,SharedPrefsUtils.getIntegerPreference(getContext(), Constant.USER_ID, 1));
        totalFood.setText(String.valueOf(totalFoodExpenses));

        userItemViewModel.loadAllUserItemByCategory(Constants.addRecharge,SharedPrefsUtils.getIntegerPreference(getContext(), Constant.USER_ID, 1))
                .observe(getViewLifecycleOwner(),userItemsList -> {
                    viewExpensesAdapter = new ViewExpensesAdapter(getActivity(),userItemsList);
                    rv_food.setAdapter(viewExpensesAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    rv_food.setLayoutManager(layoutManager);

                });
    }

}
