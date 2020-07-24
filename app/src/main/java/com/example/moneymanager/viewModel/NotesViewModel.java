package com.example.moneymanager.viewModel;

import android.app.Application;

import com.example.moneymanager.entities.UserNotes;
import com.example.moneymanager.repository.NotesRepository;
import com.example.moneymanager.repository.UserRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class NotesViewModel extends AndroidViewModel {

    private NotesRepository notesRepository;
    private UserRepository userRepository;


    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepository = new NotesRepository(application);

    }

    public LiveData<List<UserNotes>> getAllNotesByUserId(long userId) {
        return notesRepository.getAllNotesByUserId(userId);
    }

    public void addNotes(int id, String title, String description, String date) {
        notesRepository.addNotes(id, title, description , date );
    }

    public void deleteNote(UserNotes userNotes){
        notesRepository.deleteNotes(userNotes);
    }
}
