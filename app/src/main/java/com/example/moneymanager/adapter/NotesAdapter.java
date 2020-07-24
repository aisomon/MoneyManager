package com.example.moneymanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.moneymanager.R;
import com.example.moneymanager.entities.UserNotes;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<UserNotes> userNotes;
    private Context context;
    private LayoutInflater layoutInflater;

    public NotesAdapter(Context context, List<UserNotes> userNotes) {
        this.context = context;
        this.userNotes = userNotes;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.notes_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) { // we call onBindViewHolder is called fot every row
        viewHolder.tv_title.setText(userNotes.get(i).getTitle());
        viewHolder.tv_description.setText(userNotes.get(i).getDescription());
        viewHolder.tv_note_date.setText(userNotes.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return userNotes.size();
    }

    // for swipe to get note position
    public UserNotes getNoteAt(int position){
        return userNotes.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_description;
        TextView tv_note_date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_note_title);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_note_date = itemView.findViewById(R.id.tv_note_date);
        }
    }
}
