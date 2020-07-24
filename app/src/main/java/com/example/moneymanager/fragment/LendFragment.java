package com.example.moneymanager.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moneymanager.R;
import com.example.moneymanager.adapter.LendAdapter;
import com.example.moneymanager.adapter.NotesAdapter;
import com.example.moneymanager.config.Constant;
import com.example.moneymanager.utils.Constants;
import com.example.moneymanager.utils.SharedPrefsUtils;
import com.example.moneymanager.viewModel.NotesViewModel;
import com.example.moneymanager.viewModel.TransactionViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class LendFragment extends Fragment {


    private RecyclerView rv_lend;
    private TransactionViewModel transactionViewModel;
    private LendAdapter lendAdapter;
    public LendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lend, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }


    private void initView(View view) {
        rv_lend = view.findViewById(R.id.rv_lend);

        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);

        transactionViewModel.getAllTransactionByUserIdAndType(Constants.addLend,SharedPrefsUtils.getIntegerPreference(getContext(), Constant.USER_ID, 1))
                .observe(getViewLifecycleOwner(), userTransactions -> {
                    lendAdapter = new LendAdapter(getActivity(),userTransactions);
                    rv_lend.setAdapter(lendAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    rv_lend.setLayoutManager(layoutManager);

                });
        // for deleting a notes by swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                transactionViewModel.deleteTransaction(lendAdapter.getLendAt(viewHolder.getAdapterPosition()));

                Toast.makeText(getContext(),"Lend deleted",Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rv_lend);

    }



}
