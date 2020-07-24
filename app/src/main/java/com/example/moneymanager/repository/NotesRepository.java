package com.example.moneymanager.repository;

import android.app.Application;
import android.os.AsyncTask;
import com.example.moneymanager.dao.NotesDao;
import com.example.moneymanager.entities.User;
import com.example.moneymanager.entities.UserNotes;
import com.example.moneymanager.db.UserRoomDatabase;
import java.util.List;
import androidx.lifecycle.LiveData;

public class NotesRepository {
    private NotesDao notesDao;
    private UserRepository userRepository;

    public NotesRepository(Application application) {
        notesDao = UserRoomDatabase.getInstance(application).getUserNotesDao();
        userRepository = new UserRepository(application);
    }

    public LiveData<List<UserNotes>> getAllNotesByUserId(long userId) {
        return notesDao.getAllNotesByUserId(userId);
    }

    public void addNotes(int id, String title, String description, String date) {
        new PrepareNotesAsync(notesDao, userRepository, title, description,date, id).execute(id);
    }

    public void deleteNotes(UserNotes userNotes){
        new deleteNoteAsyncTask(notesDao).execute(userNotes);
    }
    private static class deleteNoteAsyncTask extends AsyncTask<UserNotes,Void,Void>{
        private NotesDao mAsyncTaskDao;
        deleteNoteAsyncTask(NotesDao notesDao){
            mAsyncTaskDao=notesDao;
        }
        @Override
        protected Void doInBackground(final UserNotes... params ){
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
    private static class PrepareNotesAsync extends AsyncTask<Integer, Void, User> {

        private UserRepository userRepository;
        private String title;
        private String description;
        private String date;
        private int userId;
        private NotesDao notesDao;

        private PrepareNotesAsync(NotesDao notesDao, UserRepository userRepository, String title, String description,String date, int userId){
            this.userRepository = userRepository;
            this.title = title;
            this.description = description;
            this.userId = userId;
            this.date = date;
            this.notesDao = notesDao;
        }

        @Override
        protected User doInBackground(Integer... ints) {

            return userRepository.getUserByUserId(ints[0]);
        }

        @Override
        protected void onPostExecute(User user) {
            UserNotes userNotes = new UserNotes();

            userNotes.setUserId(userId);
            userNotes.setTitle(title);
            userNotes.setDescription(description);
            userNotes.setDate(date);

            new AddNewNote(notesDao).execute(userNotes);
        }
    }

    private static class AddNewNote extends AsyncTask<UserNotes, Void, Void> {

        private NotesDao notesDao;

        private AddNewNote(NotesDao notesDao){
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(UserNotes... userNotes) {
            notesDao.addNotes(userNotes[0]);
            return null;
        }
    }
}
