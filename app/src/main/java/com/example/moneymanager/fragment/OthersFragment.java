package com.example.moneymanager.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;


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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class OthersFragment extends Fragment implements View.OnClickListener {

    private View view;
    private FloatingActionButton floatingActionButton;
    private RecyclerView rv_food;
    private UserItemViewModel userItemViewModel;
    private ViewExpensesAdapter viewExpensesAdapter;
    private UserRoomDatabase userRoomDatabase;
    private TextView titleTv;

    public OthersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_others, container, false);
        floatingActionButton = view.findViewById(R.id.items_float_button);
        titleTv = view.findViewById(R.id.ib_tool_title);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_view_expenses);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        floatingActionButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        DialogFragment rechargeDialog = new DialogFragment();
        Bundle rBundle = new Bundle();
        rBundle.putString("from", Constants.addOthers);
        rechargeDialog.setArguments(rBundle);
        rechargeDialog.show(getChildFragmentManager(),"Others dialog");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {

        rv_food = view.findViewById(R.id.items_rv_fragment);
        userRoomDatabase = UserRoomDatabase.getInstance(getContext());
        userItemViewModel = ViewModelProviders.of(this).get(UserItemViewModel.class);
        titleTv.setText(Constants.addOthers);


        userItemViewModel.loadAllUserItemByCategory(Constants.addOthers,SharedPrefsUtils.getIntegerPreference(getContext(), Constant.USER_ID, 1))
                .observe(getViewLifecycleOwner(),userItemsList -> {
                    viewExpensesAdapter = new ViewExpensesAdapter(getActivity(),userItemsList);
                    rv_food.setAdapter(viewExpensesAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    rv_food.setLayoutManager(layoutManager);

                });
    }
}
