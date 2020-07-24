package com.example.moneymanager.dao;

import com.example.moneymanager.entities.User;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAllUser();

    @Insert
    void addUser(User... user);
//
//    @Query("SELECT * FROM users WHERE email=:email")
//    LiveData<User> getUser(String email);

    @Query("SELECT * FROM users WHERE email=:email")
    User getUser(String email);

    @Query("SELECT * FROM users WHERE securityAnswer=:securityAnswer")
    User getSecuredAnswer(String securityAnswer);

    @Query("SELECT * FROM users WHERE email=:email AND password=:password")
    LiveData<User> loginWithEmailAndPassword(String email, String password);

    @Query("SELECT * FROM users where email= :email AND password= :password")
    User getUser(String email, String password);

    @Query("SELECT * FROM users WHERE id=:id")
    User getUserById(int id);

    @Query("SELECT * FROM users WHERE id=:id")
    LiveData<User> getUserByIdInLiveData(int id);

    @Query("UPDATE users SET name= :name, email= :email WHERE id=:id")
    void updateUserById(int id, String name, String email);

    @Query("UPDATE users SET password= :password WHERE id=:id")
    void updateUserBySecurityAnswer(int id, String password);
}
