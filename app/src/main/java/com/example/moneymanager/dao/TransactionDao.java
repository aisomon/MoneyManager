package com.example.moneymanager.dao;

import com.example.moneymanager.entities.UserNotes;
import com.example.moneymanager.entities.UserTransaction;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TransactionDao {

    @Query("SELECT * FROM user_transaction")
    List<UserTransaction> getAllTransaction();

    @Query("SELECT * FROM user_transaction WHERE  transactionType =:transactionType and userId = :userId ")
    LiveData<List<UserTransaction>> getAllTransactionByUserIdAndType(String transactionType, long userId);

//    @Query("SELECT * FROM user_transaction ORDER BY date DESC , transactionType =:transactionType and userId = :userId ")
//    LiveData<List<UserTransaction>> getAllTransactionByUserIdAndType(String transactionType, long userId);

//    @Query("SELECT * FROM user_notes  WHERE userId=:id")
//    LiveData<List<UserNotes>> getAllNotesByUserId(int id);

    @Query("SELECT * FROM user_transaction WHERE id=:id")
    List<UserTransaction> getTransactionById(int id);

    @Delete
    void delete(UserTransaction userTransaction);

    @Insert
    void addTransaction(UserTransaction... userTransactions);
}
