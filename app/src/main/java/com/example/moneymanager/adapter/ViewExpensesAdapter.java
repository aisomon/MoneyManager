package com.example.moneymanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.moneymanager.R;
import com.example.moneymanager.entities.UserItems;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewExpensesAdapter extends RecyclerView.Adapter<ViewExpensesAdapter.ViewHolder> {
    private List<UserItems> userItemsList;
    private Context context;
    private LayoutInflater layoutInflater;


    public ViewExpensesAdapter(Context context, List<UserItems> userItemsList) {
        this.context = context;
        this.userItemsList = userItemsList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.expenses_view_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) { // we call onBindViewHolder is called fot every row

            viewHolder.tv_exchange_amount.setText(userItemsList.get(i).getAmount());
            viewHolder.tv_exchange_date.setText(userItemsList.get(i).getDate());
            viewHolder.tv_describe.setText(userItemsList.get(i).getDescription());


    }

    @Override
    public int getItemCount() {
        return userItemsList.size();
    }

//    // for swipe to get note position
//    public UserTransaction getLendAt(int position){
//        return userTransactions.get(position);
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_exchange_amount;
        TextView tv_exchange_date;
        TextView tv_describe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_exchange_amount = itemView.findViewById(R.id.tv_amount_expenses);
            tv_exchange_date = itemView.findViewById(R.id.tv_date_expenses);
            tv_describe = itemView.findViewById(R.id.tv_desc_expenses);
        }
    }
}
