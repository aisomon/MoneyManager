package com.example.moneymanager.dao;

import com.example.moneymanager.entities.UserItems;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserItemDao {

    @Query("select * from user_items order by date DESC")
    LiveData<List<UserItems>> loadAllUserItem();

//    @Query("SELECT sum(amount) FROM user_items")
//    int getTotalAmount();

    // for total for each category
    @Query("SELECT sum(amount) FROM user_items WHERE category =:category and userId =:userId")
    int getAmountByCategoryType(String category, long userId);

    @Query("select * from user_items WHERE category =:category and userId =:userId")
    LiveData<List<UserItems>> getAllUserItemByCategory(String category, long userId);


    @Query("select sum(amount) from user_items where transactionType =:transactionType and userId = :userId")
    int getAmountByTransactionType(String transactionType, long userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addItem(UserItems... userItems);

    @Query("select min(date) from user_items where userId = :userId ")
    String getFirstDate(long userId);

    @Query("DELETE FROM user_items WHERE userId = :userId")
    abstract void deleteByUserId(long userId);

    // bellows are not necessary
    @Delete
    void removeItem(UserItems userItems);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(UserItems userItems);

    @Query("SELECT * FROM user_items WHERE userId=:id")
    LiveData<List<UserItems>> getAllItemByUserId(int id);

    @Query("SELECT * FROM user_items WHERE id=:id")
    List<UserItems> getUserItemsId(int id);
}
