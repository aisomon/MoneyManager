package com.example.moneymanager.viewModel;

import android.app.Application;
import com.example.moneymanager.entities.UserTransaction;
import com.example.moneymanager.repository.TransactionRepository;
import com.example.moneymanager.repository.UserRepository;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class TransactionViewModel extends AndroidViewModel {

    private TransactionRepository transactionRepository;
    private UserRepository userRepository;


    public TransactionViewModel(@NonNull Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);

    }

    public LiveData<List<UserTransaction>> getAllTransactionByUserIdAndType(String transactionType, long userId) {
        return transactionRepository.getAllTransactionByUserIdAndType(transactionType, userId);
    }

    public void addTransaction(int id, String name, String amount, String date, String transactionType) {
        transactionRepository.addTransaction(id, name, amount, date, transactionType);
    }

    public void deleteTransaction(UserTransaction userTransaction){
        transactionRepository.deleteTransaction(userTransaction);
    }
}
