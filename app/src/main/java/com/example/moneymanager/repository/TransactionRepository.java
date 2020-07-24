package com.example.moneymanager.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.moneymanager.dao.NotesDao;
import com.example.moneymanager.dao.TransactionDao;
import com.example.moneymanager.db.UserRoomDatabase;
import com.example.moneymanager.entities.User;
import com.example.moneymanager.entities.UserNotes;
import com.example.moneymanager.entities.UserTransaction;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TransactionRepository {
    private TransactionDao transactionDao;
    private UserRepository userRepository;

    public TransactionRepository(Application application) {
        transactionDao = UserRoomDatabase.getInstance(application).getTransactionDao();
        userRepository = new UserRepository(application);
    }

    public LiveData<List<UserTransaction>> getAllTransactionByUserIdAndType(String transactionType, long userId) {
        return transactionDao.getAllTransactionByUserIdAndType(transactionType, userId);
    }

    public void addTransaction(int id, String name, String amount, String date, String transactionType) {
        new PrepareTransactionAsync(transactionDao, userRepository, name, amount,date, transactionType,id).execute(id);
    }

    public void deleteTransaction(UserTransaction userTransaction){
        new deleteTransactionAsyncTask(transactionDao).execute(userTransaction);
    }

    private static class deleteTransactionAsyncTask extends AsyncTask<UserTransaction,Void,Void>{
        private TransactionDao mAsyncTaskDao;
        deleteTransactionAsyncTask(TransactionDao transactionDao){
            mAsyncTaskDao=transactionDao;
        }
        @Override
        protected Void doInBackground(final UserTransaction... params ){
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
    private static class PrepareTransactionAsync extends AsyncTask<Integer, Void, User> {

        private UserRepository userRepository;
        private String name;
        private String amount;
        private String date;
        private String transactionType;
        private int userId;
        private TransactionDao transactionDao;

        private PrepareTransactionAsync(TransactionDao transactionDao, UserRepository userRepository, String name, String amount,String date,String transactionType, int userId){
            this.userRepository = userRepository;
            this.name = name;
            this.amount = amount;
            this.userId = userId;
            this.date = date;
            this.transactionType = transactionType;
            this.transactionDao = transactionDao;
        }

        @Override
        protected User doInBackground(Integer... ints) {

            return userRepository.getUserByUserId(ints[0]);
        }

        @Override
        protected void onPostExecute(User user) {
            UserTransaction userTransaction = new UserTransaction();

            userTransaction.setUserId(userId);
            userTransaction.setName(name);
            userTransaction.setAmount(amount);
            userTransaction.setDate(date);
            userTransaction.setTransactionType(transactionType);

            new AddTransaction(transactionDao).execute(userTransaction);
        }
    }

    private static class AddTransaction extends AsyncTask<UserTransaction, Void, Void> {

        private TransactionDao transactionDao;

        private AddTransaction(TransactionDao transactionDao){
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(UserTransaction... userTransactions) {
            transactionDao.addTransaction(userTransactions[0]);
            return null;
        }
    }


}
