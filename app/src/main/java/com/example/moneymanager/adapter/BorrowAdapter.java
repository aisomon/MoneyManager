package com.example.moneymanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moneymanager.R;
import com.example.moneymanager.entities.UserTransaction;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BorrowAdapter extends RecyclerView.Adapter<BorrowAdapter.ViewHolder> {
    private List<UserTransaction> userTransactions;
    private Context context;
    private LayoutInflater layoutInflater;

    public BorrowAdapter(Context context, List<UserTransaction> userTransactions) {
        this.context = context;
        this.userTransactions = userTransactions;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.lend_borrow_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) { // we call onBindViewHolder is called fot every row
        viewHolder.tv_borrow_name.setText(userTransactions.get(i).getName());
        viewHolder.tv__borrow_amount.setText(userTransactions.get(i).getAmount());
        viewHolder.tv_borrow_date.setText(userTransactions.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return userTransactions.size();
    }

    // for swipe to get note position
    public UserTransaction getLendAt(int position){
        return userTransactions.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_borrow_name;
        TextView tv__borrow_amount;
        TextView tv_borrow_date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_borrow_name = itemView.findViewById(R.id.tv_lb_name);
            tv__borrow_amount = itemView.findViewById(R.id.tv_lb_amount);
            tv_borrow_date = itemView.findViewById(R.id.tv_lb_date);
        }
    }
}
