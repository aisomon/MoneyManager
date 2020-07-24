package com.example.moneymanager.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.moneymanager.dao.UserItemDao;
import com.example.moneymanager.entities.User;
import com.example.moneymanager.entities.UserItems;
import com.example.moneymanager.db.UserRoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ItemRepository {

    private UserItemDao userItemDao;
    private UserRepository userRepository;

    public ItemRepository(Application application) {
        userItemDao = UserRoomDatabase.getInstance(application).userItemDaoAbs();
        userRepository = new UserRepository(application);
    }

    public LiveData<List<UserItems>> getAllItemByUserId(int userId) {
        return userItemDao.getAllItemByUserId(userId);
    }

    public LiveData<List<UserItems>> getAllUserItemByCategory(String category, int userId) {
        return userItemDao.getAllUserItemByCategory(category, userId);
    }

    public void addItems(int id, String category, String amount , String date, String description,String transactionType) {
        new PrepareItemsAsync(userItemDao, userRepository, category, amount,date,description,transactionType ,id).execute(id);
    }

    private static class PrepareItemsAsync extends AsyncTask<Integer, Void, User> {

        private UserRepository userRepository;
        private String category;
        private String amount, description;
        private String date;
        private String transactionType;
        private int userId;
        private UserItemDao userItemDao;

        private PrepareItemsAsync(UserItemDao userItemDao, UserRepository userRepository, String category, String amount, String date,String transactionType,String description, int userId) {
            this.userRepository = userRepository;
            this.userItemDao = userItemDao;
            this.category= category;
            this.amount = amount;
            this.date= date;
            this.transactionType = transactionType;
            this.description = description;
            this.userId = userId;
        }


        @Override
        protected User doInBackground(Integer... ints) {

            return userRepository.getUserByUserId(ints[0]);
        }

        @Override
        protected void onPostExecute(User user) {
            UserItems userItems = new UserItems();
            userItems.setUserId(userId);
            userItems.setAmount(amount);
            userItems.setCategory(category);
            userItems.setDate(date);
            userItems.setTransactionType(transactionType);
            userItems.setDescription(description);

            new AddNewItem(userItemDao).execute(userItems);
        }
    }

    private static class AddNewItem extends AsyncTask<UserItems, Void, Void> {

        private UserItemDao userItemDao;

        private AddNewItem(UserItemDao userItemDao){
            this.userItemDao = userItemDao;
        }

        @Override
        protected Void doInBackground(UserItems... userItems) {
            userItemDao.addItem(userItems[0]);
            return null;
        }
    }
}
