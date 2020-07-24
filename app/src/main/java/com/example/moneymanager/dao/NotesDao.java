package com.example.moneymanager.dao;

import android.provider.ContactsContract;

import com.example.moneymanager.entities.UserNotes;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM user_notes")
    List<UserNotes> getAllNotes();

    @Query("SELECT * FROM user_notes Where userId=:userId")
    LiveData<List<UserNotes>> getAllNotesByUserId(long userId);

//    @Query("SELECT * FROM user_notes  WHERE userId=:id")
//    LiveData<List<UserNotes>> getAllNotesByUserId(int id);

    @Query("SELECT * FROM user_notes WHERE id=:id")
    List<UserNotes> getNoteById(int id);

    @Delete
    void delete(UserNotes userNotes);

    @Insert
    void addNotes(UserNotes... userNotes);
}
