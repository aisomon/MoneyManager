package com.example.moneymanager.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moneymanager.R;
import com.example.moneymanager.adapter.NotesAdapter;
import com.example.moneymanager.config.Constant;
import com.example.moneymanager.utils.SharedPrefsUtils;
import com.example.moneymanager.viewModel.NotesViewModel;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotesFragment extends Fragment {

    private RecyclerView rv_notes;
    private NotesViewModel notesViewModel;
    private NotesAdapter notesAdapter;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }


    @Override
    public void onResume() {
        super.onResume();

//        getAllNotes();
    }

    private void initView(View view) {
        rv_notes = view.findViewById(R.id.rv_notes);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        notesViewModel.getAllNotesByUserId(SharedPrefsUtils.getIntegerPreference(getContext(),Constant.USER_ID,1))
                .observe(getViewLifecycleOwner(), userNotes -> {
                    notesAdapter = new NotesAdapter(getActivity(),userNotes);
                    rv_notes.setAdapter(notesAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    rv_notes.setLayoutManager(layoutManager);

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

                notesViewModel.deleteNote(notesAdapter.getNoteAt(viewHolder.getAdapterPosition()));

                Toast.makeText(getContext(),"Note deleted",Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rv_notes);

    }

}
