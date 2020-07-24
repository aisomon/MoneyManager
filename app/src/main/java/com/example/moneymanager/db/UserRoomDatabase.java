package com.example.moneymanager.db;


import android.content.Context;
import android.util.Log;
import com.example.moneymanager.dao.NotesDao;
import com.example.moneymanager.dao.TransactionDao;
import com.example.moneymanager.dao.UserDao;
import com.example.moneymanager.dao.UserItemDao;
import com.example.moneymanager.entities.DateConverter;
import com.example.moneymanager.entities.User;
import com.example.moneymanager.entities.UserItems;
import com.example.moneymanager.entities.UserNotes;
import com.example.moneymanager.entities.UserTransaction;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class, UserItems.class, UserNotes.class, UserTransaction.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class UserRoomDatabase extends RoomDatabase {

    private static final String LOG_TAG = UserRoomDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DB_NAME = "MoneyManager6.db";
    private static volatile UserRoomDatabase sInstance;



    public static UserRoomDatabase getInstance(Context context){
        if (sInstance == null){
        synchronized (LOCK){
            Log.d(LOG_TAG,"Creating new database instance");

            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    UserRoomDatabase.class,UserRoomDatabase.DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
    }
        Log.d(LOG_TAG,"Getting the database instance");
        return sInstance;
}
    public static void destroyInstance() {
        sInstance = null;
    }
    public abstract UserItemDao userItemDaoAbs();
    public abstract UserDao userDao();
    public abstract NotesDao getUserNotesDao();
    public abstract TransactionDao getTransactionDao();

    // for the changing the version , i have to do migration
//    public static synchronized UserRoomDatabase getInstance(Context context) {
//        if(instance == null) {
//            instance = createRoom(context);
//        }
//
//        return instance;
//    }
//
//    // if you want to change the version , you have to follow bellow code
//    private static UserRoomDatabase createRoom(Context context) {
//        return Room.databaseBuilder(context, UserRoomDatabase.class, DB_NAME).addMigrations(MIGRATION_2_3).build();
//    }
//
//    private static final Migration MIGRATION_2_3 = new Migration(3, 4) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE user_notes ADD COLUMN creator_name TEXT");
//        }
//    };
}
